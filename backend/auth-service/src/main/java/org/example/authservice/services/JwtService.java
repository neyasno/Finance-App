package org.example.authservice.services;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.secret-key}")
    private String SECRET_KEY = "";
    private static final String ISSUER = "auth-service";
    private static final Integer EXPIRATION = 900; // 15 minutes

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
