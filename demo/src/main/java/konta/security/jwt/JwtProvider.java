package konta.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.persistence.Column;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
//Lap trinh huong khia canh AOP
@Slf4j

@Component
public class JwtProvider {

    @Value("${jwt.secret_key}")
    private String SECRET_KEY;

    public String generateToken(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date( new Date().getTime() + 7200000))
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY).compact();
    }

    public Boolean validateToken(String token) {
        try
        {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        }
        catch (Exception e)
        {
            log.error("Exception {}", e.getMessage());
        }
        return false;
    }

    public String getUsernameFromToken(String token)
    {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
