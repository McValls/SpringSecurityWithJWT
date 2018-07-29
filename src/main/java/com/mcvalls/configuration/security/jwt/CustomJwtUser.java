package com.mcvalls.configuration.security.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 
 * @author mcvalls
 *
 *	Custom org.springframework.security.core.userdetails.User implementation.
 *
 */

@SuppressWarnings("serial")
public class CustomJwtUser extends User{

	public CustomJwtUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

}
