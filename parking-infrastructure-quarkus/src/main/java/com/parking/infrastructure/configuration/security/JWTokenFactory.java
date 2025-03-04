package com.parking.infrastructure.configuration.security;

//import io.smallrye.jwt.build.Jwt;
//import io.smallrye.jwt.build.JwtClaimsBuilder;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Set;

public class JWTokenFactory {

    private static final String ISSUER = "https://issuer.org";
    private static final long DURATION = 1800;
    private static final String SECRET = "4453fd5e8408dc24655669d0a37ef72e";

    public static String generateToken(String username, Set<String> roles) {
        JwtClaimsBuilder claimsBuilder = Jwt.claims()
                .issuer(ISSUER)
                .subject(username)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(DURATION))
                .groups(roles);
        return claimsBuilder.jws().signWithSecret(SECRET);
    }

    public static String encode(CharSequence password) {
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.toString().getBytes(StandardCharsets.UTF_8));
            return new BigInteger(1, crypt.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            System.err.println(ex.getMessage());
            return "";
        }
    }
}
