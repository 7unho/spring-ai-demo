package com.april2nd.springaidemo;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AiRunner implements CommandLineRunner {
    private final ChatClient chatClient;

    public AiRunner(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public void run(String... args) throws Exception {
        // prompot -> user/system -> call/stream -> content/entity
        String answer = chatClient.prompt()
                .user("Spring AI를 한 문장으로 정의해줘")
                .call()
                .content();

        System.out.println(answer);
    }
}
