package com.april2nd.springaidemo.controller;

import com.april2nd.springaidemo.model.Movie;
import com.april2nd.springaidemo.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/one")
    public Movie one(@RequestParam(defaultValue = "오디세이") String title) {
        return movieService.findMovie(title);
    }

    @GetMapping("/by-director")
    public List<Movie> byDirector(
            @RequestParam(defaultValue = "크리스토퍼 놀란") String director,
            @RequestParam(defaultValue = "3") int count) {
        return movieService.findMoviesByDirector(director, count);
    }

    @GetMapping("/raw-json-trap")
    public String rawJsonTrap(@RequestParam(defaultValue = "오디세이") String title) {
        return "";
    }

    @GetMapping("/raw-json-fixed")
    public String rawJsonFixed(@RequestParam(defaultValue = "오디세이") String title) {
        return "";
    }

    @GetMapping("/format")
    public String format() {
        return movieService.describeFormat();
    }

}
