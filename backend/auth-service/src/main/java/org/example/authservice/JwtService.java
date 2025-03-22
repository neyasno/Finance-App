package org.example.authservice;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService{

    private static final String SECRET_KEY = "your-256-bit-secret-your-256-bit-secret"; // Должно быть 256 бит (32 символа)
    private static final String ISSUER = "auth-service";
    private static final Integer EXPIRATION = 3600;

    public String generateToken(String userId) {
        try {
            JWSSigner signer = new MACSigner(SECRET_KEY.getBytes());

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(userId)
                    .issuer(ISSUER)
                    .expirationTime(Date.from(Instant.now().plusSeconds(EXPIRATION)))
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Error generating token", e);
        }
    }

    public boolean validateToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());

            return signedJWT.verify(verifier) && !isTokenExpired(signedJWT);
        } catch (ParseException | JOSEException e) {
            return false;
        }
    }

    public String extractUserId(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new RuntimeException("Error extracting user ID", e);
        }
    }

    private boolean isTokenExpired(SignedJWT signedJWT) throws ParseException {
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        return expirationTime.before(new Date());
    }
}
