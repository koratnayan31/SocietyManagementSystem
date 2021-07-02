package com.nayan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nayan.entity.House;

public interface HouseRepo extends JpaRepository<House, Integer> {
	
	
}
