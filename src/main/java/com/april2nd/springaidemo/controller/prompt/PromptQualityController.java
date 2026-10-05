package com.april2nd.springaidemo.controller.prompt;

import com.april2nd.springaidemo.dto.BeforeAfterResponse;
import com.april2nd.springaidemo.service.prompt.PromptQualityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s05")
public class PromptQualityController {
    private final PromptQualityService promptQualityService;

    /* Before/After: "이 기사 요약해줘" vs "역할·형식·제약 명시 */
    @GetMapping("/quality/summary")
    public BeforeAfterResponse summary(@RequestParam(required = false) String article) {
        return promptQualityService.summaryBeforeAfter(Optional.ofNullable(article).orElse(PromptQualityService.SAMPLE_ARTICLE));
    }

    @GetMapping("/role/separated")
    public Map<String, String> roleSeparated(@RequestParam(defaultValue = "@Transactional의 전파 속성을 설명해주세요")String question) {
        return Map.of("question", question,
                "answer", promptQualityService.roleSeparated(question)
        );
    }
}
