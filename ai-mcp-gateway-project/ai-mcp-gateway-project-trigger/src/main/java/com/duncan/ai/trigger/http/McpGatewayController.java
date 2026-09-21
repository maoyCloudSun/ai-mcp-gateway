package com.duncan.ai.trigger.http;

import com.duncan.ai.IMcpGatewayService;
import com.duncan.ai.cases.mcp.IMcpSessionService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowCredentials = "false",allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST})
@RequestMapping("/")
public class McpGatewayController implements IMcpGatewayService {
    @Resource
    IMcpSessionService iMcpSessionService;

    @GetMapping(value = "{gatewayId}/mcp/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Override
    public Flux<ServerSentEvent<String>> establishSSEConnection(@PathVariable("gatewayId") String gatewayId) throws Exception {
        try {
            log.info("建议 MCP 连接 {}", gatewayId);

            if (StringUtils.isEmpty(gatewayId)) {
                throw new RuntimeException("传入非法值");
            }

            return iMcpSessionService.createMcpSession(gatewayId);
        } catch (Exception e) {
            log.info("建议 MCP 连接失败 {}", gatewayId);
            throw e;
        } finally {
            log.info("结束~~");
        }
    }
}
