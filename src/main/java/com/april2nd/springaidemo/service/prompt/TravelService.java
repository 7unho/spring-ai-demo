package com.april2nd.springaidemo.service.prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TravelService {
    private final ChatClient chatClient;

    public TravelService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String recommend(String question) {
        PromptTemplate template = new PromptTemplate("""
                당신은 {role} 입니다.
                아래 질문에 {format} 형식으로 답하세요.
                질문: {question}
                """);

        return chatClient
                .prompt(template.create(Map.of(
                        "role", "여행 전문가",
                        "format", "번호 목록 3개",
                        "question", question
                )))
                .call()
                .content();
    }

    public String recommendByConcat(String role, String format, String question) {
        String prompt = "당신은 " + role + "입니다\n"
                + "아래 질문에 " + format + " 형식으로 답하세요. \n\n"
                + "질문: " + question;

        return chatClient.prompt().user(prompt).call().content();
    }

    public String recommendCustom(String role, String format, String question) {
        PromptTemplate template = new PromptTemplate("""
                당신은 {role} 입니다.
                아래 질문에 {format} 형식으로 답하세요.
                질문: {question}
                """);

        return chatClient
                .prompt(template.create(Map.of(
                        "role", role,
                        "format", format,
                        "question", question
                )))
                .call()
                .content();
    }
}
