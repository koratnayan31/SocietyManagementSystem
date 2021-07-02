package com.nayan.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nayan.entity.User;

public class CustomUserDetails implements UserDetails {

	private User user;
	
	public CustomUserDetails() {
		super();
	}
	public CustomUserDetails(User user) {
		super();
		this.user=user;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		
		List<SimpleGrantedAuthority> authorities=new ArrayList<SimpleGrantedAuthority>();
		SimpleGrantedAuthority authority=null;

		for(String role:user.getRole().split(" ")) {
			authority=new SimpleGrantedAuthority(role);
			authorities.add(authority);
		}
		

		return authorities;
	}

	public String getPassword() {
		return user.getPassword();
	}

	public String getUsername() {
		
		return user.getUserName();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;			
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

}
