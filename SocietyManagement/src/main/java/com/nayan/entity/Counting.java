package com.nayan.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Counting {
	
	@Id
	private short id;
	private int totalHouse; //total number of house in society including not registered houses
	private int registeredHouse; //total houses which are registered in system 
	private long securityBalance; //current available balance of society
	private long pendingBillTotal; //total balance which is due but not paid by society member
	public Counting() {
		super();
	}
	public Counting(short id, int totalHouse, int registeredHouse, long securityBalance, long pendingBillTotal) {
		super();
		this.id = id;
		this.totalHouse = totalHouse;
		this.registeredHouse = registeredHouse;
		this.securityBalance = securityBalance;
		this.pendingBillTotal = pendingBillTotal;
	}
	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public int getTotalHouse() {
		return totalHouse;
	}
	public void setTotalHouse(int totalHouse) {
		this.totalHouse = totalHouse;
	}
	public int getRegisteredHouse() {
		return registeredHouse;
	}
	public void setRegisteredHouse(int registeredHouse) {
		this.registeredHouse = registeredHouse;
	}
	public long getSecurityBalance() {
		return securityBalance;
	}
	public void setSecurityBalance(long securityBalance) {
		this.securityBalance = securityBalance;
	}
	public long getPendingBillTotal() {
		return pendingBillTotal;
	}
	public void setPendingBillTotal(long pendingBillTotal) {
		this.pendingBillTotal = pendingBillTotal;
	}
	
}
