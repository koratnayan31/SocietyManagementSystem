package com.nayan.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import com.nayan.helper.Utility;

@Entity
@Table(name = "Expenses")
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "Please Give Name of Expense")
	@Size(min = 3, message = "expense Name Should be Mminimum of 3 Charechters")
	private String expenseName;

	@NotBlank(message = "Please Choose Expense Type")
	private String expenseType="0";

	@NotBlank(message = "Please Describe the Expense")
	private String description;

	@NotNull(message = "Enter Expense Amount")
	@Min(value = 1, message = "Expense amount should be atleast 1 Rs.")
	private int amount;

	@CreationTimestamp
	private Date timeStamp;

	public Expense() {
		super();
	}

	public Expense(int id,
			@NotBlank(message = "Please Give Name of Expense") @Size(min = 3, message = "expense Name Should be Mminimum of 3 Charechters") String expenseName,
			@NotBlank(message = "Please Choose Expense Type") String expenseType,
			@NotBlank(message = "Please Discribe the Expense") String description,
			@Min(value = 1, message = "Expense amount should be atleast 1 Rs.") @NotNull(message = "Enter Expense Amount") int amount,
			Date timeStamp) {
		super();
		this.id = id;
		this.expenseName = expenseName;
		this.expenseType = expenseType;
		this.description = description;
		this.amount = amount;
		this.timeStamp = timeStamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExpenseName() {
		return expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = Utility.toCamelCase(expenseName);
	}

	public String getExpenseType() {
		switch (expenseType) {
		case "1": {

			return "Bill";
		}
		case "2": {

			return "Salary Payment";
		}
		case "3": {

			return "New Purchase";
		}
		case "4": {

			return "Repairing";
		}
		case "5": {

			return "Other";
		}
		default:
			return "Not Specified";
		}
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = Utility.toCamelCase(expenseType);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getTimeStamp() {
		Calendar cal=Calendar.getInstance();
		cal.setTime(timeStamp);
		String time=cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
		return time;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "Expense [id=" + id + ", expenseName=" + expenseName + ", expenseType=" + expenseType + ", description="
				+ description + ", amount=" + amount + ", timeStamp=" + timeStamp + "]";
	}

}
