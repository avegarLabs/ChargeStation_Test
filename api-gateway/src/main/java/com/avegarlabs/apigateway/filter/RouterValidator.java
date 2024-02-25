package com.avegarlabs.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    public static final List<String> openAPIEndPoints = List.of(
            "/api/auth/register",
            "/api/auth/authenticate",
            "/api/auth/validate",
            "/api/public",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openAPIEndPoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
