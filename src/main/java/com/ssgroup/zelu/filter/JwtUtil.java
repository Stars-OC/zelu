package com.ssgroup.zelu.filter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssgroup.zelu.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class JwtUtil {

    private final static String SECRET = UUID.randomUUID().toString().replaceAll("-", "");

    private final static ObjectMapper mapper = new ObjectMapper();

    private static SecretKey stringToSecretKey(String secret) {
        return new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    public static String createJwt(User user, Integer validTime) {
        long now = System.currentTimeMillis();
        long expire = now + validTime * 3600000L;

        return Jwts.builder()
                .setIssuer("zelu")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expire))
                .claim("uid", user.getUid())
                .claim("username", user.getUsername())
                .claim("nickname", user.getNickname())
                .claim("hasAvatar", user.getHasAvatar())
                .claim("role", user.getRole())
//                .claim("authorities", authorities)
                .signWith(stringToSecretKey(SECRET))
                .compact();
    }

    /**
     * 验证并返回 JWT Body
     *
     * @return {@link Claims}
     */
    public static Claims getClaims(String jwt)
            throws JwtException, IllegalArgumentException {
        return Jwts.parserBuilder()
                .setSigningKey(stringToSecretKey(SECRET))
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    /**
     * 从 JWT 中取出 uid
     * <p>此操作不验证 JWT 签名</p>
     *
     * @return uid or null
     */
    public static Integer getUid(String jwt) {
        // JWT 是 Base64Url 编码
        String base64 = jwt.substring(jwt.indexOf('.') + 1, jwt.lastIndexOf('.'))
                .replaceAll("-", "+")
                .replaceAll("_", "/");

        String payload = new String(Base64.getDecoder().decode(base64));

        try {
            JsonNode node = mapper.readTree(payload);
            return node.get("uid").intValue();
        } catch (JacksonException e) {
            log.error("Cannot read uid from token");
            return null;
        }
    }
}
