package com.imperion.learn.LMS.Security;


import com.imperion.learn.LMS.Entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@NoArgsConstructor
@Getter
@Setter
public class JWTservice {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getsecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    //remember authentication is for login..therfor check AuthLoginService
    //for any AccessToken and RefreshToken handle in authlogin service in login endpoint
    //before that handle AcessToken and RefreshToken here generation takes place here
    public String generateAccessToken(User user){//this user is an entity
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email",user.getEmail())
                .claim("roles", user.getRoles().toString())//this is for authorization
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))//for now 20 sec,make sure to keep min 1 hour for accesstoken
                .signWith(getsecretKey())
                .compact();
    }

    //refreshToken can Have Long expiration but Access toke expires in shorter duration
    //but we cann also login through refreshToken like no need to login angain angain
    //you have to login once and refreshtoken will be refreshed and used again
    public String generateRefreshToken(User user){//this user is an entity
        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000L*60*60*24*30*6))//6 months validity just like netflix
                .signWith(getsecretKey())
                .compact();
    }

    public Long getUserIdFromToken(String token){
        Claims claims=Jwts.parser()
                .verifyWith(getsecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.valueOf(claims.getSubject());
    }
}
