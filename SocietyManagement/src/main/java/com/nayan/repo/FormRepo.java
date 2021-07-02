package com.nayan.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nayan.entity.Form;
import com.nayan.repo.projection.CreationTime;

public interface FormRepo extends JpaRepository<Form, Integer> {
	@Query("select f from Form f where f.email=:email and f.isAccountActive=1")
	public Form getFormByEmailWhoseAccountIsActive(@Param("email") String email);

	@Query("select f from Form f where f.email=:email and f.isRequestReviewed=0")
	public Form findFormByEmailWhoseRequestNotReviewed(@Param("email") String email);

	@Query("select f from Form f where f.adharcardNo=:adhar and f.isRequestReviewed=0")
	public Form findFormByAdharWhoseRequestNotReviewed(@Param("adhar") String adhar);

	public Page<Form> findAll(Pageable page);

	@Query("select f from Form f where f.isRequestReviewed=0")
	public Page<Form> findAllPendingRequest(Pageable page);

	public CreationTime findByEmailIgnoreCaseAndMobileNumber(String email, String mobileNumber);
}
