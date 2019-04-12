package io.micro.zuulservice.security;

import com.netflix.discovery.converters.Auto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.micro.zuulservice.bean.auth.JwtToken;
import io.micro.zuulservice.bean.auth.MongoUserDetails;
import io.micro.zuulservice.bean.auth.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private static final String AUTH = "auth";

    private static final String AUTHORIZATION = "authorization";

    private String secretKey = "secret-key";

    private long validityMilliseconds = 3600000;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    RestTemplate restTemplate;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public JwtToken createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(AUTH, roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityMilliseconds);

        String token = Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity).signWith(SignatureAlgorithm.ES256, secretKey).compact();

        JwtToken jwtToken = new JwtToken(token);

        restTemplate.postForObject("http://mongodb-service/db/token/save", jwtToken, ResponseEntity.class);

        return jwtToken;
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION);
    }

    public boolean validToken(String token) {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

        return true;
    }

    public boolean isTokenPresentDB(String token) {
        JwtToken jwtToken = restTemplate.getForObject("http://mongodb-service/db/token/find/" + token, JwtToken.class);

        return jwtToken != null;
    }

    public UserDetails getUserDetails(String token) {
        String username = getUsername(token);

        List<String> roles = getRoles(token);

        UserDetails userDetails = new MongoUserDetails(username, roles.toArray(new String[roles.size()]));

        return userDetails;
    }

    public List<String> getRoles(String token) {
        return (List<String>) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get(AUTH);
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = getUserDetails(token);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
