package com.nayan.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import com.nayan.helper.Utility;

@Entity
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int memberId;
	private String name;
	private String mobileNumber;
	private String email;
	private String actualRole;
	@CreationTimestamp
	private Date createdOn;

	public Admin() {
		super();
	}

	public Admin(int memberId, String name, String mobileNumber, String email, String actualRole, Date createdOn) {
		super();
		this.memberId = memberId;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.actualRole = actualRole;
		this.createdOn = createdOn;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMember_id(int memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name =Utility.toCamelCase(name);
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getActualRole() {
		return actualRole;
	}

	public void setActualRole(String actualRole) {
		this.actualRole =Utility.toCamelCase(actualRole);
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	

}
