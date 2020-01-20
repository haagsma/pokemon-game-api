package com.poke.boot.filters;

import com.poke.boot.services.TokenAuthenticationService;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {

    private Logger logger = Logger.getLogger(JWTAuthenticationFilter.class);

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {

        try {
            Authentication authentication = TokenAuthenticationService
                    .getAuthentication((HttpServletRequest) request);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            HttpServletRequest req = (HttpServletRequest) request;
            logger.warn("Token inválido: " + e + "\nPath: " + req.getRequestURI());
        }
        filterChain.doFilter(request, response);
    }
}
