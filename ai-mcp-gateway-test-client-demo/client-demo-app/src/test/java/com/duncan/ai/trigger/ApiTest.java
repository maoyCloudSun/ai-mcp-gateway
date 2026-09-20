package com.duncan.ai.trigger;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallback;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Resource
    private ChatClient.Builder chatBuilder;


    @Test
    public void test() {
        /**
         * 1.设置模型
         * 2.设置
         */
        ChatClient chat = chatBuilder.defaultOptions(OpenAiChatOptions.builder()
                .model("GLM-5.3")
                .toolCallbacks(
                        new SyncMcpToolCallbackProvider(
                                sseMcpClient()
                        ).getToolCallbacks()
                ).build()
        ).build();
        log.info("测试结果： {}", chat.prompt("有哪些工具可以使用").call().content());
    }


    public McpSyncClient sseMcpClient() {
        HttpClientSseClientTransport sseClientTransport = HttpClientSseClientTransport
                .builder("http://127.0.0.1:8080/sse")
                .build();

        // 这边是通过mcp的协议进行连接
        McpSyncClient mcpSyncClient = McpClient.sync(sseClientTransport).requestTimeout(Duration.ofMinutes(3000)).build();
        McpSchema.InitializeResult sse = mcpSyncClient.initialize();
        log.info("sse mcp init: {}", sse);
        return mcpSyncClient;
    }
}
