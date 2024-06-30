package io.github.alancs7.book.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticateResponse {

    private String token;
}
