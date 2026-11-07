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

    @GetMapping("/role/anti-pattern")
    public BeforeAfterResponse antiPattern(@RequestParam(defaultValue = "@Transactional의 전파 속성을 설명해주세요")String question) {
        return promptQualityService.roleSeparationAntiPattern(question);
    }

    @GetMapping("/system-prompt/customer-center")
    public Map<String, String> customerCenter(
            @RequestParam(defaultValue = "00쇼핑") String company,
            @RequestParam(defaultValue = "주문한 상품을 개봉했는데 환불이 되나요?") String question) {
        return Map.of(
                "company", company,
                "question", question,
                "answer", promptQualityService.customerCenterBot(company, question)
        );
    }

    @GetMapping("/system-prompt/out-of-policy")
    public Map<String, String> outOfPolicy(
            @RequestParam(defaultValue = "00쇼핑") String company,
            @RequestParam(defaultValue = "이 회사 주가 전망이 어떤가요?") String question) {
        return Map.of(
                "question", question,
                "answer", promptQualityService.customerCenterBot(company, question),
                "기대 동작", "'상담원 연결을 도와드리겠습니다'로 답해야 정상"
        );
    }
}
