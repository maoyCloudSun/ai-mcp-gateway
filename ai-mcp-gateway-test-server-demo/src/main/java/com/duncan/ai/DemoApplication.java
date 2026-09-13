package com.duncan.ai;


import com.duncan.ai.service.TestService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    /**
     * 将Tool的工具加载到这个Tool Provider里面
     * @param testService
     * @return
     */
    @Bean
    public ToolCallbackProvider testTools(TestService testService) {
        return MethodToolCallbackProvider.builder().toolObjects(testService).build();
    }
}