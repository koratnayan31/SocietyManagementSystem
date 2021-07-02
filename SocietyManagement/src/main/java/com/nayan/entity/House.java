package com.nayan.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import com.nayan.helper.Utility;

@Entity
public class House {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String houseNo;

	private String ownerName;

	private int ownerId;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "house_member", joinColumns = @JoinColumn(name = "house_id"), inverseJoinColumns = @JoinColumn(name = "member_id"))
	private List<SocietyMember> members;

	private boolean isForRent;

	@Override
	public String toString() {
		return "House [id=" + id + ", houseNo=" + houseNo + ", ownerName=" + ownerName + ", ownerId=" + ownerId
				+ ", isForRent=" + isForRent + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = Utility.toCamelCase(houseNo);
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = Utility.toCamelCase(ownerName);
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public boolean isForRent() {
		return isForRent;
	}

	public void setForRent(boolean isForRent) {
		this.isForRent = isForRent;
	}

	public List<SocietyMember> getMembers() {
		return members;
	}

	public void setMembers(List<SocietyMember> members) {
		this.members = members;
	}

	public House() {
		super();
	}

	public House(int id, String houseNo, String ownerName, int ownerId, boolean isForRent) {
		super();
		this.id = id;
		this.houseNo = houseNo;
		this.ownerName = ownerName;
		this.ownerId = ownerId;
		this.isForRent = isForRent;
	}
}
