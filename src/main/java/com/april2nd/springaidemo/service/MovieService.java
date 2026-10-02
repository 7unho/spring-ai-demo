package com.april2nd.springaidemo.service;

import com.april2nd.springaidemo.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MovieService {
    private final ChatClient chatClient;

    public MovieService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
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
}
