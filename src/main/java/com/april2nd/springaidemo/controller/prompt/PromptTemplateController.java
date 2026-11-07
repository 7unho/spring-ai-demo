package com.april2nd.springaidemo.controller.prompt;

import com.april2nd.springaidemo.service.prompt.ChatService;
import com.april2nd.springaidemo.service.prompt.TravelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/s05/template")
public class PromptTemplateController {
    private final TravelService travelService;
    private final ChatService chatService;

    public PromptTemplateController(TravelService travelService, ChatService chatService) {
        this.travelService = travelService;
        this.chatService = chatService;
    }

    @GetMapping("/travel/concat")
    public Map<String, String> travelConcat(
            @RequestParam(defaultValue = "여행 전문가") String role,
            @RequestParam(defaultValue = "번호 목록 3개") String format,
            @RequestParam(defaultValue = "가을에 가기 좋은 국내 여행지를 추천해 주세요") String question) {
        return Map.of("answer", travelService.recommendByConcat(role, format, question));
    }

    @GetMapping("/travel")
    public Map<String, String> travel(
            @RequestParam(defaultValue = "가을에 가기 좋은 국내 여행지를 추천해 주세요") String question) {
        return Map.of(
                "question", question,
                "answer", travelService.recommend(question)
        );
    }

    @GetMapping("/travel/custom")
    public Map<String, String> travelCustom(
            @RequestParam(defaultValue = "요리 연구가") String role,
            @RequestParam(defaultValue = "표 형식") String format,
            @RequestParam(defaultValue = "1인 가구를 위한 간단한 저녁 메뉴를 알려주세요") String question) {
        return Map.of("answer", travelService.recommendCustom(role, format, question));
    }

    @GetMapping("/prompt-file")
    public Map<String, String> promptFile() {
        return Map.of("classpath:/prompts/system.st", chatService.currentSystemPrompt());
    }

    @GetMapping("/chat/raw")
    public Map<String, String> chatRaw(
            @RequestParam(defaultValue = "환불은 며칠 이내에 신청해야 하나요?") String question) {
        return Map.of(
                "answer", chatService.ask(question),
                "확인 포인트", "{service} 자리가 치환되지 않은 채 전달됩니다."
        );
    }

    @GetMapping("/chat")
    public Map<String, String> chat(
            @RequestParam(defaultValue = "00쇼핑 고객센터") String service,
            @RequestParam(defaultValue = "환불은 며칠 이내에 신청해야 하나요?") String question) {
        return Map.of(
                "service", service,
                "answer", chatService.ask(service, question)
        );
    }
}
