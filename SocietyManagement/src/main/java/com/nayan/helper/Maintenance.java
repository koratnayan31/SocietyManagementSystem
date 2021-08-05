package com.nayan.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nayan.entity.Expense;
import com.nayan.entity.MaintenanceBill;
import com.nayan.repo.ExpenseRepo;
import com.nayan.repo.MaintenanceBillRepo;

@Component
public class Maintenance {
	private String forMonthYear;
	private double amount;
	private List<Expense> expenses;
	@Autowired
	private ExpenseRepo expenseRepo;
	@Autowired
	private MaintenanceBillRepo billRepo;

	public String getForMonthYear() {
		return forMonthYear;
	}

	public void setForMonthYear(String forMonthYear) {
		this.forMonthYear = forMonthYear;
	}

	public double getAmount() {
		List<Expense> expenses;
		try {
			if (this.expenses == null) {
				expenses = this.getExpenses();
			} else {
				expenses = this.expenses;
			}
		} catch (NullPointerException ex) {
			System.out.println("Null Pointer");
			return -1;
		}
		
		double totalAmount=0;
		for(Expense expense:expenses) {
			totalAmount+=expense.getAmount();
		}
		this.amount=totalAmount;

		return totalAmount;
	}

	public List<Expense> getExpenses() {

		if (this.forMonthYear != null) {
			SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
			Date startDate = null;
			Date endDate = null;
			try {
				startDate = format.parse(this.forMonthYear);
				Calendar cal = Calendar.getInstance();
				cal.setTime(startDate);
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
				endDate = cal.getTime();
			} catch (ParseException ex) {
				ex.printStackTrace();
				return null;
			}
			expenses = expenseRepo.findAllByTimeStampBetween(startDate, endDate);
			return expenses;
		} else {
			return null;
		}
	}
	
	public MaintenanceBill getBill() {
		String year=this.getForMonthYear().split("/")[1];
		String month=this.getForMonthYear().split("/")[0];
		boolean exist=billRepo.existsMaintenanceBillByForMonthYear(year+"/"+month);
		
		if(!exist)
			return null;
		
		MaintenanceBill bill=billRepo.findByForMonthYear(year+"/"+month);
		return bill;
	}

}
