package pl.pwr.news.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.user.User;
import pl.pwr.news.service.user.UserService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static io.jsonwebtoken.Jwts.builder;
import static io.jsonwebtoken.Jwts.parser;
import static java.time.Instant.ofEpochMilli;
import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneOffset.UTC;
import static java.util.Optional.empty;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;

@Service
public final class TokenHandler {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private UserDetailsService userService;


    public User parseUserFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return (User) userService.loadUserByUsername(username);
    }

    public String createTokenForUser(User user) {
        return builder()
                .setSubject(user.getUsername())
                .setExpiration(Date.from(now().plusDays(10).toInstant(UTC)))
                .signWith(SignatureAlgorithm.HS512, "secret")
                .compact();
    }
}
