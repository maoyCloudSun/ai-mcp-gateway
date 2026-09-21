package com.duncan.ai.cases.mcp.session.factory;


import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.duncan.ai.cases.mcp.session.node.RootNode;
import com.duncan.ai.session.model.valobj.SessionConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

@Service
public class DefaultMcpSessionFactory {

    @Resource
    private RootNode rootNode;


    public StrategyHandler<String, DefaultMcpSessionFactory.DynamicContext, Flux<ServerSentEvent<String>>> strategyHandler() {
        return rootNode;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {
        private SessionConfigVO sessionConfigVO;
    }
}
