package com.nayan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nayan.entity.SocietyMember;

public interface SocietyMemberRepo extends JpaRepository<SocietyMember, Integer> {
	public SocietyMember getSocietyMemberByEmailIgnoreCase(String email);
	public boolean existsSocietyMemberByEmail(String email);
	public boolean existsSocietyMemberByAdharcardNumber(String adhar);
}
