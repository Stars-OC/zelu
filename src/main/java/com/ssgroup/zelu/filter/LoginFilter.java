package com.ssgroup.zelu.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssgroup.zelu.pojo.Result;
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

    private final static ObjectMapper mapper = new ObjectMapper();
        /**
     * 过滤器方法，用于处理请求和响应
     *
     * @param servletRequest  Servlet请求对象
     * @param servletResponse Servlet响应对象
     * @param chain           过滤器链
     * @throws IOException      如果处理过程中发生I/O错误
     * @throws ServletException 如果处理过程中发生Servlet错误
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        // 将ServletRequest和ServletResponse转换为HttpServletRequest和HttpServletResponse
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获取请求的URL
        String url = request.getRequestURL().toString();

        // 如果URL包含"/user/login"或"/user/register"，则不进行鉴权，直接放行
        if (url.contains("/user/login") || url.contains("/user/register")) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 获取请求头中的token
        String token = request.getHeader("token");
        response.setContentType("text/plain; charset=utf-8");

        String result = "";
        // 如果token为空，则返回"token不存在，请登录"
        if (token == null) {
            result = mapper.writeValueAsString(Result.failure("token不存在，请登录"));
            response.getWriter().write(result);
            return;
        }

        try {
            // 使用JwtUtil工具类获取token中的claims信息
            Claims claims = JwtUtil.getClaims(token);
            // 如果claims信息为空，则返回"token不存在，请登录"
            if (claims == null) {
                result = mapper.writeValueAsString(Result.failure("token不存在，请登录"));
                response.getWriter().write(result);
                return;
            }

            // 放行请求，继续处理
            chain.doFilter(servletRequest, servletResponse);

        } catch (JwtException | IllegalArgumentException | StringIndexOutOfBoundsException e) {
            String error = e.getMessage();
            log.error("Verify token failed: {}", error);

            // 如果抛出异常类型为StringIndexOutOfBoundsException，则将错误信息设置为"Token错误"
            if (e instanceof StringIndexOutOfBoundsException) {
                error = "Token错误";
            // 如果抛出异常类型为"expired"，则将错误信息设置为"Token已过期"
            } else if (error.contains("expired")) {
                error = "Token已过期";
            // 如果抛出异常类型为"signature"，则将错误信息设置为"签名不匹配"
            } else if (error.contains("signature")) {
                error = "签名不匹配";
            } else {
                error = "Token无效";
            }

            result = mapper.writeValueAsString(Result.failure(403,error));
            // 返回错误信息
            response.getWriter().write(result);
        }
    }

}
