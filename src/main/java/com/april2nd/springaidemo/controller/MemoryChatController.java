package com.april2nd.springaidemo.controller;

import com.april2nd.springaidemo.service.MemoryChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class MemoryChatController {
    private final MemoryChatService memoryChatService;

    @PostMapping("/api/chat/{conversationId}")
    public String chat(@PathVariable String conversationId, @RequestBody String message) {
        return memoryChatService.chat(conversationId, message);
    }

    @GetMapping(
            value = "/api/chat/{conversationId}/stream",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<String> stream(@PathVariable String conversationId, @RequestParam String message) {
        return memoryChatService.chatStream(conversationId, message);
    }
}
