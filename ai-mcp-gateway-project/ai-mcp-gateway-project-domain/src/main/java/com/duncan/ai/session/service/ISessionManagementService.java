package com.duncan.ai.session.service;

import com.duncan.ai.session.model.valobj.SessionConfigVO;

public interface ISessionManagementService {
    SessionConfigVO createSession(String gatewayId);

    void removeSession(String sessionId);

    SessionConfigVO getSession(String sessionId);

    void cleanupExpiredSessions();

    void shutDown();

}
