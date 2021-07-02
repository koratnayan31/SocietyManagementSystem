package com.nayan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nayan.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
	public User findByUserNameIgnoreCase(String username);
}
