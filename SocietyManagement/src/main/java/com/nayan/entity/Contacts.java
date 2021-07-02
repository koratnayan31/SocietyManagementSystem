package com.nayan.entity;

import java.util.Date;

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
public class Contacts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int contactId;
	
	@NotBlank(message="First Name is required")
	@Size(min = 2, message = "First Name should be of minimum 2 charecter")
	private String firstName;
	
	@NotBlank(message = "Last Name is required")
	@Size(min = 2, message = "Last Name should be of minimum 2 charecter")
	private String lastName;
	
	@NotBlank(message = "Mobile Number is required")
	@Size(min = 10, max = 10, message = "Mobile Number should have 10 digit")
	private String mobileNumber;
	
	@NotBlank(message = "Email can not be blank")
	@Pattern(message = "Enter valid Email", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
	private String email;
	
	@NotBlank(message="Write something about person`s post")
	@Size(min = 3, message = "Write something about person`s post with atleast 3 charecter")
	private String post;

	@CreationTimestamp
	private Date createdOn;

	public Contacts() {
		super();
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName =Utility.toCamelCase(firstName);
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
		this.mobileNumber =mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post =Utility.toCamelCase(post);
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Contacts(int contactId, String firstName, String lastName, String mobileNumber,
			String email, String post, Date createdOn) {
		super();
		this.contactId = contactId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.post = post;
		this.createdOn = createdOn;
	}
}
