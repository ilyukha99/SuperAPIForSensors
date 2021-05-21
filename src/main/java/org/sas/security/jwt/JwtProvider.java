package org.sas.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;
    private final Logger logger = Logger.getLogger(JwtProvider.class.getName());

    public String generateToken(String userLogin, int tokenDurability) {
        Date date = Date.from(LocalDate.now().plusDays(tokenDurability).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(userLogin)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getLoginFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        }
        catch (UnsupportedJwtException unsupportedJwtException) {
            logger.severe("JwtProvider isTokenValid(): argument does not represent an Claims JWS. " +
                    unsupportedJwtException.getMessage());
        }
        catch (MalformedJwtException malformedJwtException) {
            logger.severe("JwtProvider isTokenValid(): string is not a valid JWS. " +
                    malformedJwtException.getMessage());
        }
        catch (SignatureException signatureException) {
            logger.severe("JwtProvider isTokenValid(): JWS signature validation fails. " +
                    signatureException.getMessage());
        }
        catch (ExpiredJwtException expiredJwtException) {
            logger.severe("JwtProvider isTokenValid(): the validity period has expired. " +
                    expiredJwtException.getMessage());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            logger.severe("JwtProvider isTokenValid(): string is null or empty or only whitespace. " +
                    illegalArgumentException.getMessage());
        }
        return null;
    }
}
