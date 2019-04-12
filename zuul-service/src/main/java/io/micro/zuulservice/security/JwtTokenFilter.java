package io.micro.zuulservice.security;

import io.jsonwebtoken.JwtException;
import io.micro.zuulservice.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        if (token != null) {
            if (!jwtTokenProvider.isTokenPresentDB(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token!");
                throw new CustomException("Invalid JWT token" + HttpStatus.UNAUTHORIZED);
            }

            try {
                jwtTokenProvider.validToken(token);
            } catch (JwtException | IllegalArgumentException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token!");
                throw new CustomException("Invalid JWT token" + HttpStatus.UNAUTHORIZED);
            }

            Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
        }

        filterChain.doFilter(request, response);
    }
}
