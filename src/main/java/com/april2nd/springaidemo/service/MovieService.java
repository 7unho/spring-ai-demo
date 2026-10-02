package com.april2nd.springaidemo.service;

import com.april2nd.springaidemo.model.Movie;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .entity(new ParameterizedTypeReference<List<Movie>>() {});
    }

    public String askRawJsonTrap(String title) {
        return "JsonTypeMismatch";
    }

    public String askRawFixed(String title) {
        return "correct";
    }

    public String describeFormat() {
        return "";
    }
}
