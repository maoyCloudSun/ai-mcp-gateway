package com.duncan.ai.session.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Sinks;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * 会话的配置项
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionConfigVO {
    /**
     * 唯一标识
     */
    private String sessionId;

    public SessionConfigVO(String sessionId, Sinks.Many<ServerSentEvent<String>> sink) {
        this.sessionId = sessionId;
        this.sink = sink;
        this.createTime = Instant.now();
        this.lastAccessedTime = Instant.now();
        this.active = true;
    }

    /**
     * 接受长链接的响应
     */
    private Sinks.Many<ServerSentEvent<String>> sink;


    private Instant createTime;

    /**
     * 标识内存可见
     */
    private volatile Instant lastAccessedTime;

    /**
     * 可以被其他线程看见
     */
    private volatile boolean active;

    public void markInactive() {
        this.active = false;
    }


    public void updateLastAccessed() {
        this.lastAccessedTime = Instant.now();
    }

    public boolean isExpired(long timeoutMinutes) {
        return lastAccessedTime.isBefore(Instant.now().minus(timeoutMinutes, ChronoUnit.MINUTES));
    }

}

