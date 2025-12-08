package com.mycom.myapp.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L;

	// 인증 위해
	private final String username;
	private final String password;
	
	private final Collection<? extends GrantedAuthority> authorities;
	
	// 사용 위해 ( BL에서 )
	private final Long id;
	private final String name;
	private final String email;
	
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return null;
//	}
//
//	@Override
//	public String getPassword() {
//		return null;
//	}
//
//	@Override
//	public String getUsername() {
//		return null;
//	}

}
