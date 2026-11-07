package com.april2nd.springaidemo.service.prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ChatService {
    private final ChatClient chatClient;

    @Value("classpath:/prompts/system.st")
    private Resource systemResource;

    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String ask(String question) {
        return chatClient.prompt()
                .system(systemResource)
                .user(question)
                .call()
                .content();
    }

    public String ask(String service, String question) {
        String systemText = new PromptTemplate(systemResource)
                .render(Map.of("service", service));

        return chatClient.prompt()
                .system(systemText)
                .user(question)
                .call()
                .content();
    }

    public String currentSystemPrompt() {
        return new PromptTemplate(systemResource).getTemplate();
    }
}
