package com.zigar.user.utils.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class JwtToken {

    public static final String CLAIM_APP = "app";

    private String jwtId;
    private String token;
    private Map<String, Object> claims;

}
