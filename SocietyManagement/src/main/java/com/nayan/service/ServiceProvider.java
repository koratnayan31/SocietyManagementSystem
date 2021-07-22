package com.nayan.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nayan.entity.Counting;
import com.nayan.entity.Expense;
import com.nayan.entity.Form;
import com.nayan.entity.House;
import com.nayan.entity.MaintenanceBill;
import com.nayan.entity.SocietyMember;
import com.nayan.entity.User;
import com.nayan.exception.AdharNumberAlreadyExist;
import com.nayan.exception.EmailAlreadyExist;
import com.nayan.helper.Utility;
import com.nayan.repo.CountingRepo;
import com.nayan.repo.ExpenseRepo;
import com.nayan.repo.FormRepo;
import com.nayan.repo.MaintenanceBillRepo;
import com.nayan.repo.SocietyMemberRepo;
import com.nayan.repo.UserRepo;

@Service
public class ServiceProvider {

	@Autowired
	private FormRepo formRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private SocietyMemberRepo societyMemberRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CountingRepo countingRepo;

	@Autowired
	private MaintenanceBillRepo billRepo;

	@Autowired
	private ExpenseRepo expenseRepo;

	public boolean isAccepatableEmail(String email) {
		boolean exist = societyMemberRepo.existsSocietyMemberByEmail(email);
		Form form = null;
		if (!exist) {
			form = formRepo.findFormByEmailWhoseRequestNotReviewed(email);
			if (form != null) {
				exist = true;
			}
		}
		return !exist;
	}

	public boolean isAccepatableAdharcard(String adhar) {
		boolean exist = societyMemberRepo.existsSocietyMemberByAdharcardNumber(adhar);
		Form form = null;
		if (!exist) {
			form = formRepo.findFormByAdharWhoseRequestNotReviewed(adhar);
			if (form != null) {
				exist = true;
			}
		}
		return !exist;
	}

	public boolean isValidOwnerEmail(String email) {
		SocietyMember societyMember;
		try {
			societyMember = societyMemberRepo.getSocietyMemberByEmailIgnoreCase(email);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (societyMember != null) {
			return true;
		}
		return false;
	}

	public Form addForm(Form form) throws AdharNumberAlreadyExist, EmailAlreadyExist {

		if (!isAccepatableEmail(form.getEmail())) {
			throw new EmailAlreadyExist("This Email address is already exists");
		}

		if (!isAccepatableAdharcard(form.getAdharcardNo())) {
			throw new AdharNumberAlreadyExist(
					"This Adhar No is already exists. Check your adharcard number. If this is mistake than contact society secretory");
		}
		form.setPassword(passwordEncoder.encode(form.getPassword()));
		formRepo.save(form);
		return form;
	}

	public SocietyMember addSocietyMember(Form form) {
		SocietyMember societyMember = new SocietyMember();

		// setting member variable
		societyMember.setFirstName(form.getFirstName());
		societyMember.setLastName(form.getLastName());
		societyMember.setMiddleName(form.getMiddleName());
		societyMember.setGender(form.getGender());
		societyMember.setAdharcardNumber(form.getAdharcardNo());
		societyMember.setEmail(form.getEmail());
		societyMember.setProfile(form.getProfileImageUrl());
		societyMember.setSocietyName("LD College of Engineering");
		societyMember.setMobileNumber(form.getMobileNumber());
		societyMember.setSocietyHouseNo(form.getSocietyHouseNo());

		House house = null;
		if (form.isOwner()) {
			house = new House();
			house.setForRent(false);

			// setting owner name
			String name = societyMember.getFirstName() + " ";
			if (societyMember.getMiddleName() != null || societyMember.getLastName() != "") {
				name += societyMember.getMiddleName() + " ";
			}
			name += societyMember.getLastName();
			house.setOwnerName(name);
			house.setHouseNo(form.getSocietyHouseNo());
			house.setMembers(List.of(societyMember));

		} else {
			SocietyMember owner = societyMemberRepo.getSocietyMemberByEmailIgnoreCase(form.getOwnerEmail());
			House ownerHouse = owner.getHouse();
			List<SocietyMember> members = ownerHouse.getMembers();
			members.add(societyMember);
			ownerHouse.setMembers(members);
			societyMember.setHouse(ownerHouse);
		}

		SocietyMember savedMember = null;
		try {
			savedMember = societyMemberRepo.save(societyMember);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return null;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		if (form.isOwner()) {
			house.setOwnerId(savedMember.getMemberId());
			societyMember.setHouse(house);
			try {
				savedMember = societyMemberRepo.save(societyMember);
				if (!this.incrementRegisteredHouse())
					throw new Exception("House Incrementation Failed");
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
				return null;
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

		return savedMember;
	}

	public User addUserAsSocietyMember(SocietyMember societyMember) {
		User newUser = new User();
		User user = userRepo.findByUserNameIgnoreCase(societyMember.getEmail());
		if (user != null) {
			user.setRole(user.getRole() + " " + "ROLE_SOCIETY_MEMBER");
			return userRepo.save(user);
		} else {
			newUser.setName(Utility.getName(societyMember.getFirstName(), societyMember.getMiddleName(),
					societyMember.getLastName()));
			newUser.setRole("ROLE_SOCIETY_MEMBER");
			newUser.setUserName(societyMember.getEmail());
			newUser.setPassword(formRepo.getFormByEmailWhoseAccountIsActive(societyMember.getEmail()).getPassword());
			return userRepo.save(newUser);
		}
	}

	@Transactional(rollbackOn = Exception.class)
	private boolean incrementRegisteredHouse() {
		try {
			Counting countRecord = countingRepo.getOne(Short.valueOf("1"));
			countRecord.setRegisteredHouse(countRecord.getRegisteredHouse() + 1);
			countingRepo.save(countRecord);
			return true;
		} catch (EntityNotFoundException ex) {
			ex.printStackTrace();
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	
	//it take parameter as start date of month and due date for bill
	@Transactional(rollbackOn = Exception.class)
	public boolean generateMaintenanceBill(Date date, Date dueDate) {
		if (date == null)
			return false;

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		Date endDate = cal.getTime();

		float share=calculateBillAmount(date, endDate);

		// update the record
		MaintenanceBill bill = new MaintenanceBill();
		bill.setBillAmount(share + "");
		int month=cal.get(Calendar.MONTH) + 1;
		String MM;
		if(month<10)
			MM="0"+month;
		else
			MM=""+month;
		bill.setForMonthYear(cal.get(Calendar.YEAR) + "/" + MM);

		cal.setTime(dueDate);
		String dueDateForBill = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.YEAR);
		bill.setDueDate(dueDateForBill);
		System.out.println(dueDateForBill);

		billRepo.save(bill);

		return true;
	}

	
	
	//calculate bill amount between two date 
	private float calculateBillAmount(Date startDate, Date endDate) {
		List<Expense> expenses = expenseRepo.findAllByTimeStampBetween(startDate, endDate);

		if (expenses == null)
			return 0;

		// calculate total expense
		float total = 0;
		for (Expense expense : expenses) {
			total += expense.getAmount();
		}

		// fetching total number of houses
		Counting count = null;
		try {
			count = countingRepo.getOne(Short.valueOf("1"));
		} catch (EntityNotFoundException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		int totalHouse = count.getTotalHouse();
		
		
		total=total/totalHouse;

		return total;
	}

	
	
	/*
	 * @param it take start date of month as input to calculate share of individual house
	 */
	public String prefetchAmount(Date date) {
		if (date == null)
			return "0";

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		Date endDate = cal.getTime();

		float total = this.calculateBillAmount(date, endDate);
		return "" + total;
	}

}
