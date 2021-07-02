package com.nayan.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import com.nayan.helper.Utility;

@Entity
public class Form {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int formId;

	@NotBlank(message = "First Name is required")
	@Size(min = 2, message = "First Name should be of minimum 2 charecter")
	private String firstName;

	private String middleName;

	@NotBlank(message = "Last Name is required")
	@Size(min = 2, message = "Last Name should be of minimum 2 charecter")
	private String lastName;
	
	@NotBlank(message="Please Choose Gender")
	@Column(length = 10)
	private String Gender;

	@NotBlank(message = "Mobile Number is required")
	@Size(min = 10, max = 10, message = "Mobile Number should have 10 digit")
	private String mobileNumber;

	@NotBlank(message = "Email can not be blank")
	@Pattern(message = "Enter valid Email", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
	@Column(unique = true)
	private String email;
	
	@Size(min = 12, max = 12, message = "Enter valid Adhar Card Number")
	@Column(unique	 = true)
	private String adharcardNo;

	@NotBlank(message = "Society house number is required")
	private String societyHouseNo;

	private boolean isOwner;
	
	@NotBlank(message = "Email can not be blank")
	@Pattern(message = "Enter valid Email", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
	private String ownerEmail;

	
	@Size(min=8,message = "Password should be of minimum 8 charector")
	private String password;

	private String profileImageUrl;

	private String adharUrl;

	private String ownerTenantProofUrl;

	private String comment;
	private boolean isAccountActive;
	private boolean isRequestReviewed;

	@CreationTimestamp
	private Date recordDate;

	public Form() {
		super();
	}

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName =Utility.toCamelCase(firstName);
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName =Utility.toCamelCase(middleName);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName =Utility.toCamelCase(lastName);
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

	public String getAdharcardNo() {
		return adharcardNo;
	}

	public void setAdharcardNo(String adharcardNo) {
		this.adharcardNo = adharcardNo;
	}

	public String getSocietyHouseNo() {
		return societyHouseNo;
	}

	public void setSocietyHouseNo(String societyHouseNo) {
		this.societyHouseNo =Utility.toCamelCase(societyHouseNo);
	}

	public boolean isOwner() {
		return isOwner;
	}

	public void setOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getAdharUrl() {
		return adharUrl;
	}

	public void setAdharUrl(String adharUrl) {
		this.adharUrl = adharUrl;
	}

	public String getOwnerTenantProofUrl() {
		return ownerTenantProofUrl;
	}

	public void setOwnerTenantProofUrl(String ownerTenantProofUrl) {
		this.ownerTenantProofUrl = ownerTenantProofUrl;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isAccountActive() {
		return isAccountActive;
	}

	public void setAccountActive(boolean isAccountActive) {
		this.isAccountActive = isAccountActive;
	}

	public boolean isRequestReviewed() {
		return isRequestReviewed;
	}

	public void setRequestReviewed(boolean isRequestReviewed) {
		this.isRequestReviewed = isRequestReviewed;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = Utility.toCamelCase(gender);
	}

	public Form(int formId, String firstName, String middleName, String lastName, String gender, String mobileNumber,
			String email, String adharcardNo, String societyHouseNo, boolean isOwner, String ownerEmail, String password,
			String profileImageUrl, String adharUrl, String ownerTenantProofUrl, String comment,
			boolean isAccountActive, boolean isRequestReviewed, Date recordDate) {
		super();
		this.formId = formId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		Gender = gender;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.adharcardNo = adharcardNo;
		this.societyHouseNo = societyHouseNo;
		this.isOwner = isOwner;
		this.ownerEmail = ownerEmail;
		this.password = password;
		this.profileImageUrl = profileImageUrl;
		this.adharUrl = adharUrl;
		this.ownerTenantProofUrl = ownerTenantProofUrl;
		this.comment = comment;
		this.isAccountActive = isAccountActive;
		this.isRequestReviewed = isRequestReviewed;
		this.recordDate = recordDate;
	}

	@Override
	public String toString() {
		return "Form [formId=" + formId + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + ", Gender=" + Gender + ", mobileNumber=" + mobileNumber + ", email=" + email
				+ ", adharcardNo=" + adharcardNo + ", societyHouseNo=" + societyHouseNo + ", isOwner=" + isOwner
				+ ", ownerEmail=" + ownerEmail + ", password=" + password + ", profileImageUrl=" + profileImageUrl
				+ ", adharUrl=" + adharUrl + ", ownerTenantProofUrl=" + ownerTenantProofUrl + ", comment=" + comment
				+ ", isAccountActive=" + isAccountActive + ", isRequestReviewed=" + isRequestReviewed + ", recordDate="
				+ recordDate + "]";
	}
	
	public String getName() {
		String name = this.firstName + " ";
		if (this.middleName != null || this.middleName != "" || this.middleName != " ")
			name += this.middleName + " ";
		name += this.lastName;
		return Utility.toCamelCase(name);
	}

}
