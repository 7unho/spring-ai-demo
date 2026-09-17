package com.april2nd.springaidemo.service;

import com.april2nd.springaidemo.model.Answer;
import com.april2nd.springaidemo.model.Question;

public interface OpenAIService {
    Answer getAnswer(Question question);
}
