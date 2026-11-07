package com.april2nd.springaidemo.service.prompt;

import com.april2nd.springaidemo.dto.BeforeAfterResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SentimentClassifyService {
    private final PromptRunner runner;

    @Value("classpath:/prompts/few-shot-sentiment.st")
    private Resource fewShotResource;

    public SentimentClassifyService(PromptRunner runner) {
        this.runner = runner;
    }

    public String zeroShot(String review) {
        return runner.user("다음 리뷰의 감성을 분류하세요. \n\n 리뷰: " + review);
    }

    public String fewShot(String review) {
        String prompt = new PromptTemplate(fewShotResource)
                .render(Map.of("review", review));

        return runner.user(prompt);
    }


    public BeforeAfterResponse compare(String review) {
        String zeroPrompt = "다음 리뷰의 감성을 분류하세요. \n\n 리뷰: " + review;
        String fewPrompt = new PromptTemplate(fewShotResource)
                .render(Map.of("review", review));

        PromptRunner.Result zero = runner.userWithTokens(zeroPrompt);
        PromptRunner.Result few = runner.userWithTokens(fewPrompt);

        return new BeforeAfterResponse(
                "Zero-Shot vs Few-Shot (감성 분류)",
                "Zero-Shot - 지시만", zeroPrompt, zero.answer(), zero.totalTokens(),
                "Few-Shot - 예시 2개", fewPrompt, few.answer(), few.totalTokens()
        );
    }
}
