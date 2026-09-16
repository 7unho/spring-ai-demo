package com.april2nd.springaidemo.controller;

import com.april2nd.springaidemo.model.Answer;
import com.april2nd.springaidemo.model.Question;
import com.april2nd.springaidemo.service.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {
    private final OpenAIService openAIService;

    public QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return new Answer();
    }
}
