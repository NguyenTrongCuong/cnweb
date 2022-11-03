package com.example.demo.application.filter;

import com.example.demo.domain.model.ResponseBody;
import com.example.demo.domain.service.crud.SessionService;
import com.example.demo.utils.HttpUtils;
import com.example.demo.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter implements Filter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SessionService sessionService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        try {
            if(authorizationHeader == null || authorizationHeader.length() < "Bearer ".length()) {
                throw new IllegalArgumentException("Token is invalid.");
            }

            String accessToken = authorizationHeader.substring("Bearer ".length());
            TokenUtils.verifyToken(accessToken);

            if(!this.sessionService.existsBySessionId(accessToken)) throw new IllegalArgumentException("Token is invalid.");

            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch (Exception e) {
            e.printStackTrace();
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

            HttpUtils.writeJSONToHttpServletResponse(
                    httpServletResponse,
                    this.objectMapper.writeValueAsString(
                            ResponseBody.builder()
                                    .code(9998)
                                    .message("Token is invalid.")
                                    .build()
                    )
            );
            return;
        }
    }
}
