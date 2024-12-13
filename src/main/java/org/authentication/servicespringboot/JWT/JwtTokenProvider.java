package org.authentication.servicespringboot.JWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.authentication.servicespringboot.Demo.Exceptions.BlogAppException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date()).setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Firma JWT no valida");
        }catch (MalformedJwtException e){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Token JWT no valido");
        }catch (ExpiredJwtException e){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Token JWT caducada");
        }catch (UnsupportedJwtException e){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Token JWT no compatible");
        }catch (IllegalArgumentException e){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "La cadena claims JWT esta vacia");
        }
    }

}
