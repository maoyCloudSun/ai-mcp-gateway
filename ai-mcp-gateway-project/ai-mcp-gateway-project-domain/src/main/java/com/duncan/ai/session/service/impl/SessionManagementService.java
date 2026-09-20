package com.duncan.ai.session.service.impl;

import com.duncan.ai.session.model.valobj.SessionConfigVO;
import com.duncan.ai.session.service.ISessionManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SessionManagementService implements ISessionManagementService {
    // 这里面有个容器
    private static final long SESSION_TIMEOUT_MINUTES = 30;

    private final ScheduledExecutorService cleanupScheduler = Executors.newSingleThreadScheduledExecutor();

    private final Map<String, SessionConfigVO> activeSessions = new ConcurrentHashMap<>();


    @Override
    public SessionConfigVO createSession(String gatewayId) {
        String sessionId = UUID.randomUUID().toString();
        Sinks.Many<ServerSentEvent<String>> sink = Sinks.many().multicast().onBackpressureBuffer();

        String messageEndpoint = "/" + gatewayId + "/mcp/message?sessionId" + sessionId;
        sink.tryEmitNext(ServerSentEvent.<String>builder()
                        .event("endpoint")
                        .data(messageEndpoint)
                .build());
        SessionConfigVO sessionConfigVO = new SessionConfigVO(sessionId, sink);
        activeSessions.put(sessionId, sessionConfigVO);
        log.info("创建会话 gatewayId: {}", gatewayId);

        return sessionConfigVO;
    }

    public SessionManagementService() {
        cleanupScheduler.scheduleAtFixedRate(this::cleanupExpiredSessions, 5, 5, TimeUnit.MINUTES);
    }

    @Override
    public void removeSession(String sessionId) {
        if (null == sessionId) {
            return;
        }

        SessionConfigVO remove = activeSessions.remove(sessionId);
        remove.markInactive();

        try {
            remove.getSink().tryEmitComplete();
        } catch (Exception e) {
            log.error("关闭会话失败", e);
        }
    }

    @Override
    public SessionConfigVO getSession(String sessionId) {
        if (null == sessionId || sessionId.isEmpty()) {
            return null;
        }

        SessionConfigVO sessionConfigVO = activeSessions.get(sessionId);
        if (sessionConfigVO != null && sessionConfigVO.isActive()) {
            sessionConfigVO.updateLastAccessed();
            return sessionConfigVO;
        }
        return null;
    }

    @Override
    public void cleanupExpiredSessions() {
        int cleanedCount = 0;
        Set<Map.Entry<String, SessionConfigVO>> entries = activeSessions.entrySet();
        for (Map.Entry<String, SessionConfigVO> entry : entries) {
            SessionConfigVO value = entry.getValue();
            if (value.isExpired(SESSION_TIMEOUT_MINUTES) || !value.isActive()) {
                removeSession(value.getSessionId());
                cleanedCount++;
            }

        }

        if (cleanedCount > 0) {
            log.info("清理了 {}", cleanedCount);
        }

    }

    @Override
    public void shutDown() {
        for (String s : activeSessions.keySet()) {
            removeSession(s);
        }

        cleanupScheduler.shutdown();

        try {
            if (!cleanupScheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                cleanupScheduler.shutdown();
            }
        } catch (InterruptedException e) {
            cleanupScheduler.shutdown();
            Thread.currentThread().interrupt();
        }
    }
}
