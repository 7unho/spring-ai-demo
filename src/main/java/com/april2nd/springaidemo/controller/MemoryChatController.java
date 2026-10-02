package com.april2nd.springaidemo.controller;

import com.april2nd.springaidemo.service.MemoryChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemoryChatController {
    private final MemoryChatService memoryChatService;

    @PostMapping("/api/chat/{conversationId}")
    public String chat(@PathVariable String conversationId, @RequestBody String message) {
        return memoryChatService.chat(conversationId, message);
    }
}
