package com.april2nd.springaidemo.jev;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class JevClient {
    private final RestClient restClient;
    private final JevProperties props;

    public JevClient(JevProperties props) {
        this.props = props;
        this.restClient = RestClient.builder()
                .baseUrl(props.baseUrl())
                .defaultHeader("Authorization", "Bearer " + props.apiKey())
                .build();
    }

    public boolean enabled() {
        return props.enabled();
    }

    public Map<String, Decision> decide(Object state, Map<String, Object> questions) {
        JevResponse response = restClient.post()
                .uri("/v1/systemone")
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        Map.of(
                                "model", props.model(),
                                "state", state,
                                "questions", questions)
                )
                .retrieve()
                .body(JevResponse.class);
        return response.answers();
    }

    public record JevResponse(String model, Map<String, Decision> answers) {

    }

    public record Decision(
            String type,
            String choice,
            Double score,
            String recommend,
            Double noul,
            Double confidence,
            Map<String, Double> Probabilities) {

    }
}
