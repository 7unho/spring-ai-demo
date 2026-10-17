package com.april2nd.springaidemo.service;

import com.april2nd.springaidemo.jev.JevClient;
import com.april2nd.springaidemo.jev.JevProperties;
import com.april2nd.springaidemo.model.Genre;
import com.april2nd.springaidemo.model.Movie;
import com.april2nd.springaidemo.model.ReviewAnalysis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MovieService {
    private final ChatClient chatClient;
    private final JevClient jevClient;

    public MovieService(ChatClient chatClient, JevClient jevClient) {
        this.chatClient = chatClient;
        this.jevClient = jevClient;
    }

    public Movie findMovie(String title) {
        return chatClient.prompt()
                .user(u -> u.text("크리스포터 놀란이 제작한 오디세이의 정보를 알려줘. 감독 이름, 영화 제목, 개봉 년도").param("title", title))
                .call()
                .entity(Movie.class);
    }

    public List<Movie> findMoviesByDirector(String director, int count) {
        return chatClient.prompt()
                .user(u -> u.text("{director} 감독의 대표작 {count}편을 알려줘")
                        .param("director", director)
                        .param("count", count)
                )
                .call()
                .entity(new ParameterizedTypeReference<List<Movie>>() {
                });
    }

    /* 문자열로 JSON 직접 요청
        ```Json ... ```
     */
    public String askRawJsonTrap(String title) {
        String raw = chatClient.prompt()
                .user(u -> u.text("영화 '{title}'의 정보를 title, year, director 필드를 가진 JSON만 응답해줘")
                        .param("title", title)
                )
                .call()
                .content();

        log.info("[MovieService.askRawJsonTrap] TRAP RAW : {}", raw);

        return raw;
    }

    // 프롬프트에 마크다운 금지를 명시
    public String askRawFixed(String title) {
        String raw = chatClient.prompt()
                .user(u -> u.text("""
                                영화 '{title}'의 정보를 title, year, director 필드를 가진 JSON만 응답해줘
                                
                                *Response in JSON format without markdown tags*
                                """)
                        .param("title", title)
                )
                .call()
                .content();

        log.info("[MovieService.askRawFixed] TRAP FIXED RAW : {}", raw);

        return raw;
    }

    public String describeFormat() {
        return "";
    }

    public ReviewAnalysis analyzeReviewWithJev(String review) {
        Map<String, String> genreCriteria = new LinkedHashMap<>();
        for (Genre g : Genre.values()) {
            genreCriteria.put(g.name(), g.description());
        }

        Map<String, JevClient.Decision> answers = jevClient.decide(
                review,
                Map.of(
                        "genre", Map.of(
                                "type", "choice",
                                "instructions", "리뷰가 다루는 영화의 장르는?",
                                "criteria", genreCriteria
                        ),
                        "sentiment", Map.of(
                                "type", "score",
                                "instructions", "리뷰 작성자의 영화 평가는?",
                                "criteria", List.of("매우 부정", "부정", "보통", "긍정", "매우 긍정")
                        ),
                        "spoiler", Map.of(
                                "type", "noul",
                                "instructions", "리뷰가 결말이나 반전을 드러내는가?"
                        )
                )
        );

        JevClient.Decision genre = answers.get("genre");

        return new ReviewAnalysis(
                Genre.valueOf(genre.choice()),
                answers.get("sentiment").score(),
                answers.get("spoiler").noul() >= 0.5,
                genre.confidence()
        );
    }
}
