package com.ziyear.volcano.util;

import com.ziyear.volcano.config.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * 功能描述 : TODO
 *
 * @author you_name 2021-10-27 21:41
 */
@Component
@RequiredArgsConstructor
public class JwtUtil {
    public static final Key ACCESS_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final Key REFRESH_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final AppProperties appProperties;

    public String createAccessToken(UserDetails userDetails) {
        return createJwtToken(userDetails, appProperties.getJwt().getAccessTokenExpireTime(), ACCESS_KEY);
    }

    public String createRefreshToken(UserDetails userDetails) {
        return createJwtToken(userDetails, appProperties.getJwt().getRefreshTokenExpireTime(), REFRESH_KEY);
    }

    public String createAccessTokenWithRefreshToken(String token) {
        return parseClaims(token, REFRESH_KEY).map(claims ->
                Jwts.builder()
                        .setClaims(claims)
                        .setExpiration(new Date(System.currentTimeMillis() + appProperties.getJwt().getAccessTokenExpireTime()))
                        .setIssuedAt(new Date())
                        .signWith(ACCESS_KEY, SignatureAlgorithm.HS256)
                        .compact())
                .orElseThrow(() -> new AccessDeniedException("访问被拒绝！"));
    }

    public Optional<Claims> parseClaims(String token, Key key) {
        try {
            Claims body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return Optional.of(body);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean validateAccessTokenWithoutExpiration(String token) {
        return validateToken(token, ACCESS_KEY, false);
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, ACCESS_KEY, true);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, REFRESH_KEY, true);
    }

    private boolean validateToken(String token, Key key, boolean isExpiredInvalid) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parse(token);
            return true;
        } catch (Exception e) {
            if (e instanceof ExpiredJwtException) {
                return !isExpiredInvalid;
            }
            return false;
        }
    }

    private String createJwtToken(UserDetails userDetails, long expireTime, Key key) {
        long now = System.currentTimeMillis();
        return Jwts.builder().setId("volcano")
                .claim("authorities", userDetails.getAuthorities().stream().
                        map(GrantedAuthority::getAuthority).collect(toList()))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expireTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
