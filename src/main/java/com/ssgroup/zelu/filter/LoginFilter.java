package com.ssgroup.zelu.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURL().toString();
        if (url.contains("/user/login") || url.contains("/user/register")){
            chain.doFilter(servletRequest,servletResponse);
            return;
        }

        String token = request.getHeader("token");
        response.setContentType("text/plain; charset=utf-8");
        if (token == null){
            response.getWriter().write("token 不存在请登录");
            return;
        }

        try {
            Claims claims = JwtUtil.getClaims(token);
            if(claims == null) {
                response.getWriter().write("token 不存在请登录");
                return;
            }

            chain.doFilter(servletRequest,servletResponse);

        }catch (JwtException | IllegalArgumentException | StringIndexOutOfBoundsException e){
            String error = e.getMessage();
            log.error("Verify token failed: {}", error);

            if (e instanceof StringIndexOutOfBoundsException) {
                error = "Token 错误";
            } else if (error.contains("expired")) {
                error = "Token 已过期";
            } else if (error.contains("signature")) {
                error = "签名不匹配";
            }

            response.getWriter().write(error);
        }
    }
}
