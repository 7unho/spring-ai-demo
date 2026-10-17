package com.april2nd.springaidemo.model;

public record ReviewAnalysis(
        Genre genre,
        double sentiment, // 0( 매우 부정 ) ~ 4( 매우 긍정 )
        boolean spoiler,
        Double confidence // Jev만 제공( LLM 버전은 null )
) {
}
