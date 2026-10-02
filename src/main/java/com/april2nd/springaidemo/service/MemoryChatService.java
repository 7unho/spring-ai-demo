package com.april2nd.springaidemo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MemoryChatService {
    private final ChatClient chatClient;

    public MemoryChatService(@Qualifier("memoryChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String chat(String conversationId, String message) {
        return chatClient.prompt()
                .user(message)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }
}
