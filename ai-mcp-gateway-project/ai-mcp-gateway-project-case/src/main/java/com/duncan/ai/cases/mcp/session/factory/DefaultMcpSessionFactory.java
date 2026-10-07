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


    // 这个是责任链里面的策略handler,就是有了这个handler可以进行责任链的传递

    /**
     * 1.入参
     * 2.上下文
     * 3.返回值
     * @return
     */
    public StrategyHandler<String, DefaultMcpSessionFactory.DynamicContext, Flux<ServerSentEvent<String>>> strategyHandler() {
        return rootNode;
    }

    // 这个上下文是干啥的
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {
        private SessionConfigVO sessionConfigVO;
    }
}
