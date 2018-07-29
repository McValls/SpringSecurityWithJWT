package com.mcvalls.configuration.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * 
 * @author mcvalls
 *
 * Custom authentication token with a "token" property.
 */
@SuppressWarnings("serial")
public class CustomJwtAuthenticationToken extends UsernamePasswordAuthenticationToken{

	
	public CustomJwtAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}
	
	public CustomJwtAuthenticationToken(String token) {
		super(token, token);
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	private String token;

}
