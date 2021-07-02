package com.nayan.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.nayan.helper.Utility;

@Entity
public class SocietyMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int memberId;

	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = Utility.toCamelCase(gender);
	}

	private String mobileNumber;

	@Column(unique = true)
	private String email;

	@Column(unique = true)
	private String adharcardNumber;

	private String societyName;
	private String societyHouseNo;

	@ManyToOne(cascade = CascadeType.ALL)
	private House house;

	private String profile;

	@CreationTimestamp
	private Date approvedOn;

	@Override
	public String toString() {
		return "SocietyMember [memberId=" + memberId + ", firstName=" + firstName + ", middleName=" + middleName
				+ ", lastName=" + lastName + ", gender=" + gender + ", mobileNumber=" + mobileNumber + ", email="
				+ email + ", adharcardNumber=" + adharcardNumber + ", societyName=" + societyName + ", societyHouseNo="
				+ societyHouseNo + ", house=" + house + ", profile=" + profile + "]";
	}

	public SocietyMember() {
		super();
	}

	public SocietyMember(int memberId, String firstName, String middleName, String lastName, String mobileNumber,
			String email, String adharcardNumber, String societyName, String societyHouseNo, House house,
			String profile) {
		super();
		this.memberId = memberId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.adharcardNumber = adharcardNumber;
		this.societyName = societyName;
		this.societyHouseNo = societyHouseNo;
		this.house = house;
		this.profile = profile;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = Utility.toCamelCase(firstName);
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = Utility.toCamelCase(middleName);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = Utility.toCamelCase(lastName);
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

	public String getAdharcardNumber() {
		return adharcardNumber;
	}

	public void setAdharcardNumber(String adharcardNumber) {
		this.adharcardNumber = adharcardNumber;
	}

	public String getSocietyName() {
		return societyName;
	}

	public void setSocietyName(String societyName) {
		this.societyName = societyName;
	}

	public String getSocietyHouseNo() {
		return societyHouseNo;
	}

	public void setSocietyHouseNo(String societyHouseNo) {
		this.societyHouseNo = Utility.toCamelCase(societyHouseNo);
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Date getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(Date approvedOn) {
		this.approvedOn = approvedOn;
	}

	public String getName() {
		String name = this.firstName + " ";
		if (this.middleName != null || this.middleName != "" || this.middleName != " ")
			name += this.middleName + " ";
		name += this.lastName;
		return Utility.toCamelCase(name);
	}
}