package com.parking.infrastructure.configuration.security;
//
//import io.smallrye.jwt.auth.principal.*;
//import org.jose4j.jwt.JwtClaims;
//import org.jose4j.jwt.MalformedClaimException;
//import org.jose4j.jwt.NumericDate;
//import org.jose4j.jwt.consumer.InvalidJwtException;

import io.smallrye.jwt.auth.principal.*;
import jakarta.annotation.Priority;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.context.ApplicationScoped;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;


import java.nio.charset.StandardCharsets;
import java.util.Base64;

@ApplicationScoped
@Alternative
@Priority(1)
public class JWTokenCaller
        extends JWTCallerPrincipalFactory
{

    @Override
    public JWTCallerPrincipal parse(String token, JWTAuthContextInfo authContextInfo) throws ParseException {
        try {
            String json = new String(Base64.getUrlDecoder().decode(token.split("\\.")[1]), StandardCharsets.UTF_8);

            JwtClaims parse = JwtClaims.parse(json);

            if (NumericDate.now().isOnOrAfter(parse.getExpirationTime())) {
                throw new ParseException("Invalid token");
            }

            return new DefaultJWTCallerPrincipal(parse);
        } catch (InvalidJwtException ex) {
            throw new ParseException(ex.getMessage());
        } catch (MalformedClaimException e) {
            throw new RuntimeException(e);
        }
    }
}
