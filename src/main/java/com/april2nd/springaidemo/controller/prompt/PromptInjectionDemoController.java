package com.april2nd.springaidemo.controller.prompt;

import com.april2nd.springaidemo.dto.InjectionDefenseResult;
import com.april2nd.springaidemo.service.prompt.PromptInjectionDemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s05/injection")
public class PromptInjectionDemoController {
    private static final String SLIDE_ATTACK = "";
    private final PromptInjectionDemoService promptInjectionDemoService;

    @GetMapping("/defense")
    public InjectionDefenseResult defense(@RequestParam(defaultValue = SLIDE_ATTACK) String userInput) {
        return null;
    }
}
