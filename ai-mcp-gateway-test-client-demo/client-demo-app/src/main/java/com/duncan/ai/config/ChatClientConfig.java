package com.duncan.ai.config;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.ai.chat.client.DefaultChatClientBuilder;
import org.springframework.ai.chat.client.observation.ChatClientObservationConvention;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.chat.client.ChatClient;
@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient.Builder chatClientBuilder(OpenAiChatModel chatModel) {
        return new DefaultChatClientBuilder(chatModel, ObservationRegistry.NOOP, (ChatClientObservationConvention) null,null);
    }

}
