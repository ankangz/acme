package com.ankang.acme.user.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Map;
import java.util.UUID;

public class JwtTokenUtils {
    
    private static Key generatorKey(){
        String temp = "f441062089814206aa52c2a47ea711c2";
        SignatureAlgorithm ssa = SignatureAlgorithm.ES256;
        byte[] bytes = DatatypeConverter.parseBase64Binary(temp);
        Key key = new SecretKeySpec(bytes,ssa.getJcaName());
        return key;
    }
    
    public static String generatorToken(Map<String,Object> payLoad){
        ObjectMapper objectMapper = new ObjectMapper();
        
        try {
            return Jwts.builder().setPayload(objectMapper.writeValueAsString(payLoad))
                    .signWith(SignatureAlgorithm.HS256,generatorKey()).compact();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Claims parseToken(String token){
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(generatorKey()).parseClaimsJws(token);
        return claimsJws.getBody();
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
    }
    
}
