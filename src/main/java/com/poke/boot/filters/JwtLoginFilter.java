package com.poke.boot.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poke.boot.dto.AccountCredentialsDTO;
import com.poke.boot.services.TokenAuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    public JwtLoginFilter(String url, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException {

        AccountCredentialsDTO accountCredentialsDTO = new ObjectMapper()
                .readValue(request.getInputStream(), AccountCredentialsDTO.class);

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        accountCredentialsDTO.getUsername(),
                        accountCredentialsDTO.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    @Override
    public void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication authentication) throws IOException, ServletException {
        TokenAuthenticationService.addAuthentication(response, authentication.getName());
    }

}
