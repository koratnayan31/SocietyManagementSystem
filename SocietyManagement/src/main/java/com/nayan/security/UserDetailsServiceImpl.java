package com.nayan.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nayan.entity.User;
import com.nayan.repo.UserRepo;

public class UserDetailsServiceImpl implements UserDetailsService {

	
	@Autowired
	private UserRepo userRepo;
		
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user=userRepo.findByUserNameIgnoreCase(username);
			
		if(user==null) {
			throw new UsernameNotFoundException("User not Found");
		} 
		CustomUserDetails currentUser=new CustomUserDetails(user);
		
		
		return currentUser;
	}

}
