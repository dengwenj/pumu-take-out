package vip.dengwj.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JWTUtils {
    // 创建jwt
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 返回 jwt
        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .setClaims(claims)
            .setExpiration(new Date(System.currentTimeMillis() + ttlMillis))
            .compact();
    }

    // 解析 jwt
    public static Map<String, Object> parseJWT(String secretKey, String token) {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();
    }
}
