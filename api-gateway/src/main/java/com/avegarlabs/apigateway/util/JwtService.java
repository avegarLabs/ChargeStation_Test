package com.avegarlabs.apigateway.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtService {

    private static final String SECRET = "38ItKWDRLzIPz6MldrFpz5516AIroCjBRvxJo499VPE3Vm5F4P4W95rnMhRulRINQf5Eqgkag5S16Uo1e59";

    public void tokenValidation(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
