package com.mcvalls.configuration.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

/**
 * 
 * @author mcvalls
 * 
 * This class filter all the request to the server and mark as successful
 * only those where a valid JWT Token is present.
 *
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	protected JwtAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}
	
	public JwtAuthenticationFilter() {
		super("/*");
	}
	
	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		return true;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Bearer ")) {
			throw new IllegalArgumentException("No JWT token found in request headers");
		}
		String authToken = header.substring(7);
		CustomJwtAuthenticationToken token = new CustomJwtAuthenticationToken(authToken);
		return getAuthenticationManager().authenticate(token);
	}
	
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        chain.doFilter(request, response);
    }

}
