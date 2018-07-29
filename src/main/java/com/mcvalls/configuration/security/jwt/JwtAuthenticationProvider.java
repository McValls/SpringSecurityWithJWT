package com.mcvalls.configuration.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 
 * @author mcvalls
 * Custom AuthenticationProvider that tries to retrieveUser by the given token.
 * 
 * It is added to an AuthenticationProviders list on the application startup.
 */

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private JwtTokenUtils jwtUtil;

	@Override
	public boolean supports(Class<?> authentication) {
		return (CustomJwtAuthenticationToken.class.isAssignableFrom(authentication));
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}
	
	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		CustomJwtAuthenticationToken jwtAuthenticationToken = (CustomJwtAuthenticationToken) authentication;
		String token = jwtAuthenticationToken.getToken();

		User parsedUser = jwtUtil.parseToken(token);

		if (parsedUser == null) {
			throw new AuthenticationCredentialsNotFoundException("JWT token is not valid");
		}

		UserDetails userDetails = User
				.withUsername(parsedUser.getUsername())
				.password(parsedUser.getPassword())
				.authorities(parsedUser.getAuthorities()).build();
		return userDetails;
	}

}