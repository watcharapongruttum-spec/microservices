package com.example.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.*;
import org.springframework.web.servlet.function.*;

@Configuration
public class GatewayConfig {

    private final RestTemplate restTemplate;

    public GatewayConfig() {
        this.restTemplate = new RestTemplate();
        this.restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) {
                return false;
            }
        });
    }

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route()
                .POST("/api/auth/**", req -> forward(req, "http://localhost:8081"))
                .GET("/api/auth/**",  req -> forward(req, "http://localhost:8081"))
                .POST("/api/products/**", req -> forward(req, "http://localhost:8082"))
                .GET("/api/products/**",  req -> forward(req, "http://localhost:8082"))
                .PUT("/api/products/**",  req -> forward(req, "http://localhost:8082"))
                .DELETE("/api/products/**", req -> forward(req, "http://localhost:8082"))
                .POST("/api/orders/**", req -> forward(req, "http://localhost:8083"))
                .GET("/api/orders/**",  req -> forward(req, "http://localhost:8083"))
                .build();
    }

    private ServerResponse forward(ServerRequest request, String targetBase) {
        try {
            String path = request.uri().getRawPath();
            String query = request.uri().getRawQuery();
            String targetUrl = targetBase + path + (query != null ? "?" + query : "");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            request.headers().asHttpHeaders().forEach((k, v) -> {
                if (!k.equalsIgnoreCase("host") && !k.equalsIgnoreCase("content-length")) {
                    headers.addAll(k, v);
                }
            });

            String body = "";
            try { body = request.body(String.class); } catch (Exception ignored) {}

            HttpEntity<String> entity = new HttpEntity<>(body.isEmpty() ? null : body, headers);
            HttpMethod method = HttpMethod.valueOf(request.method().name());

            ResponseEntity<String> response = restTemplate.exchange(targetUrl, method, entity, String.class);

            return ServerResponse.status(response.getStatusCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response.getBody() != null ? response.getBody() : "");

        } catch (Exception e) {
            return ServerResponse.status(HttpStatus.BAD_GATEWAY)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}