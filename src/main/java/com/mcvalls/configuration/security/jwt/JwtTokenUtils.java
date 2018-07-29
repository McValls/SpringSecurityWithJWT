package com.mcvalls.configuration.security.jwt;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * @author mcvalls
 * Utils class to generate and parse tokens.
 */

@Component
public class JwtTokenUtils {

    private String secret = "Y0uR_S3cR3T!!!";

    /**
     * 
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    @SuppressWarnings("unchecked")
	public User parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            ArrayList<LinkedHashMap<String, String>> roles = (ArrayList<LinkedHashMap<String, String>>) body.get("roles");
            List<GrantedAuthority> authorityList = new ArrayList<>();
            for(LinkedHashMap<String, String> rol : roles) {
            	authorityList.add(new SimpleGrantedAuthority(rol.get("authority")));
            }

            User u = new CustomJwtUser(
            		(String) body.get("userId"), 
            		(String) body.get("password"), 
            		authorityList);
            return u;

        } catch (JwtException | ClassCastException e) {
        	e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    public String generateToken(User u) {
    		
        Claims claims = Jwts.claims().setSubject(u.getUsername());
        claims.put("userId", u.getUsername());
        claims.put("password", u.getPassword());
        claims.put("roles", u.getAuthorities());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}