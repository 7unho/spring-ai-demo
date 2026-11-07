package com.april2nd.springaidemo.controller;

import com.april2nd.springaidemo.dto.BeforeAfterResponse;
import com.april2nd.springaidemo.service.prompt.ReasoningService;
import com.april2nd.springaidemo.service.prompt.SentimentClassifyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/s05/technique")
public class TechniqueController {
    private final SentimentClassifyService sentimentClassifyService;
    private final ReasoningService reasoningService;

    public TechniqueController(
            SentimentClassifyService sentimentClassifyService,
            ReasoningService reasoningService) {
        this.sentimentClassifyService = sentimentClassifyService;
        this.reasoningService = reasoningService;
    }

    @GetMapping("/zero-shot")
    public Map<String, String> zeroShot(
            @RequestParam(defaultValue = "가격 내비 성능은 괜찮은데 배터리가 하루도 못 갑니다") String review) {
        return Map.of(
                "review", review,
                "answer", sentimentClassifyService.zeroShot(review)
        );
    }

    @GetMapping("/few-shot")
    public Map<String, String> fewShot(
            @RequestParam(defaultValue = "가격 내비 성능은 괜찮은데 배터리가 하루도 못 갑니다") String review) {
        return Map.of(
                "review", review,
                "answer", sentimentClassifyService.fewShot(review)
        );
    }

    @GetMapping("/shot/compare")
    public BeforeAfterResponse shotCompare(
            @RequestParam(defaultValue = "가격 내비 성능은 괜찮은데 배터리가 하루도 못 갑니다") String review) {
        return sentimentClassifyService.compare(review);
    }
}
