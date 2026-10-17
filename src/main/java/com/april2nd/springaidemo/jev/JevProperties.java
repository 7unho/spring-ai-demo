package com.april2nd.springaidemo.jev;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jev")
public record JevProperties(
        Boolean enabled,
        String apiKey,
        String baseUrl,
        String model
) {
    public Boolean enabled() {
        return this.enabled();
    }
}