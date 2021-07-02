package com.nayan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nayan.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, Integer> {
	public Admin findByEmailIgnoreCase(String email);
}
