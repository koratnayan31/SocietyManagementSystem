package com.nayan.repo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nayan.entity.MaintenanceBill;

public interface MaintenanceBillRepo extends JpaRepository<MaintenanceBill, Integer> {
	
	public boolean existsMaintenanceBillByForMonthYear(String monthYear);
	public MaintenanceBill findByForMonthYear(String yearMonth);
	

}
