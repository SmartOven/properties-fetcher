package ru.panteleevya.token;

import lombok.Getter;

import java.time.Instant;

@Getter
public class IamToken {
    private final String value;
    private final Instant expiresAt;

    private IamToken(String value, Instant expiresAt) {
        this.value = value;
        this.expiresAt = expiresAt;
    }

    public static IamToken fromDto(IamTokenDto iamTokenDto) {
        return new IamToken(iamTokenDto.iamToken(), Instant.parse(iamTokenDto.expiresAt()));
    }
}
