package com.duncan.ai.trigger;


import com.alibaba.fastjson.JSON;
import com.duncan.ai.proxy.api.IOpenAiApiProxy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController()
@CrossOrigin("*")
@RequestMapping("/v1/")
public class OpenAiApiProxyController {
    @Resource
    private IOpenAiApiProxy openAiApiProxy;

    @RequestMapping(value = "chat/completions", method = RequestMethod.POST)
    public Object completions(@RequestBody Object request) {
        log.info("请求入参： {}", JSON.toJSONString(request));
        return openAiApiProxy.completions(request);
    }

}
