package com.ilginbor.LibraryManagementSystem.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.security.jwt")
public record JwtProperties(String secretKey, long expiration) {
}
