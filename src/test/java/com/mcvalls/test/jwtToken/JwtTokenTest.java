package com.mcvalls.test.jwtToken;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.mcvalls.configuration.security.jwt.CustomJwtUser;
import com.mcvalls.configuration.security.jwt.JwtTokenUtils;

@RunWith(JUnit4.class)
public class JwtTokenTest {

	@Test
	public void generateToken() {
		JwtTokenUtils utils = new JwtTokenUtils();
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("USER"));
		CustomJwtUser user = new CustomJwtUser("mcvalls", "mcv4115", authorities);
		String token = utils.generateToken(user);
		
		assertNotNull(token);
		assertNotEquals("", token);
		
		System.out.println(token);
	}
	
}
