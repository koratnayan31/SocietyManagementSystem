package com.nayan.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nayan.entity.Expense;

public interface ExpenseRepo extends JpaRepository<Expense, Integer>{
	
	public List<Expense> findAllByTimeStampBetween(Date start,Date end);
}
