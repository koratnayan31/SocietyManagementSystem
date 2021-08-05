package com.nayan.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class MaintenanceBill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int maintenanceBillId;
	
	private String forMonthYear;
	private String billAmount;
	private Date dueDate;
	
	@CreationTimestamp
	private Date timeStamp;
	
	
	
	public int getMaintenanceBillId() {
		return maintenanceBillId;
	}
	public String getForMonthYear() {
		return forMonthYear;
	}
	public String getBillAmount() {
		return billAmount;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public Date getDueDate() {
		return this.dueDate;
	}
	public void setMaintenanceBillId(int maintenanceBillId) {
		this.maintenanceBillId = maintenanceBillId;
	}
	public void setForMonthYear(String forMonthYear) {
		this.forMonthYear = forMonthYear;
	}
	public void setBillAmount(String billAmount) {
		this.billAmount = billAmount;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public void setDueDate(String dueDate) {
		SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
		Date date=null;
		try
		{
			date=format.parse(dueDate);
		}catch (ParseException ex) {
			System.out.println("Due Date Setting failed");
		}
		this.dueDate = date;
	}
	@Override
	public String toString() {
		return "MaintenanceBill [maintenanceBillId=" + maintenanceBillId + ", forMonthYear=" + forMonthYear
				+ ", billAmount=" + billAmount + ", dueDate=" + dueDate + ", timeStamp=" + timeStamp + "]";
	}
	
}
