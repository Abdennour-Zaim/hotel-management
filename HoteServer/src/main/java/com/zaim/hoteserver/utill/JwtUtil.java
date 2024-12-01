package com.zaim.hoteserver.utill;

import com.zaim.hoteserver.controller.auth.AuthController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public String generateToken(Map<String, Object> extraClaims, UserDetails details) {

            if (extraClaims == null) {
                System.out.println("extraClaims is null");
            } else {
                System.out.println("extraClaims: " + extraClaims);
            }

            if (details == null) {
                System.out.println("UserDetails is null");
            } else {
                System.out.println("Username: " + details.getUsername());
            }
            logger.info("generateToken(Map<String, Object> extraClaims, UserDetails details) invoked");
            String test = Jwts.builder().setClaims(extraClaims).setSubject(details.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                    .signWith(SignatureAlgorithm.HS256, getSigningKey()).compact();

            return test;

    }

    public String generateToken(UserDetails details) {
        logger.info("generateToken(userDetails)  invoked");
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", "admin");
        return generateToken(claims, details);
    }

    public boolean isTokenValid(String token,UserDetails userdetails) {
        final String username=extractUsername(token);
        return (username.equals(userdetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody();
    }
    
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }
    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private SecretKey getSigningKey() {
        logger.info("getSigningKey() invoked");
        byte[] keyBytes = Decoders.BASE64.decode("0f19facdc309a91b3b901aac588570e1fbae237380e7e94773011163e4b7ad59");
        if(keyBytes==null){
            logger.error("getSigningKey() invoked failed");
        }else {
            logger.info("getSigningKey() invoked completed");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
