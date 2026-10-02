package com.april2nd.springaidemo.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {
    @Bean
    public ChatClient memoryChatClient(ChatClient.Builder builder, ChatMemory chatMemory) {
        return builder
                .build();
    }
}
