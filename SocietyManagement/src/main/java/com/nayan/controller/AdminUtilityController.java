package com.nayan.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nayan.entity.Admin;
import com.nayan.entity.Contacts;
import com.nayan.entity.Expense;
import com.nayan.entity.MaintenanceBill;
import com.nayan.entity.Message;
import com.nayan.entity.Notice;
import com.nayan.entity.User;
import com.nayan.repo.AdminRepo;
import com.nayan.repo.ContactRepo;
import com.nayan.repo.ExpenseRepo;
import com.nayan.repo.MaintenanceBillRepo;
import com.nayan.repo.NoticeRepo;
import com.nayan.repo.UserRepo;
import com.nayan.service.ServiceProvider;

@Controller
@RequestMapping("/admin")
public class AdminUtilityController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AdminRepo adminRepo;

	@Autowired
	private ContactRepo contactRepo;

	@Autowired
	private NoticeRepo noticeRepo;

	@Autowired
	private ExpenseRepo expenseRepo;

	@Autowired
	private MaintenanceBillRepo maintenanceRepo;

	@Autowired
	private ServiceProvider service;

	// normal redirection
	@RequestMapping("")
	public String nullHome() {
		return "redirect:/admin/pending-requests/0";
	}

	@RequestMapping("/")
	public String onlySlashHome() {
		return "redirect:/admin/pending-requests/0";
	}

	// profile page handler
	@RequestMapping("/profile")
	public String profile(Principal principal, Model m, HttpSession session) {
		m.addAttribute("title", "Profile");
		User user = userRepo.findByUserNameIgnoreCase(principal.getName());
		session.setAttribute("user", user);

		Admin admin = adminRepo.findByEmailIgnoreCase(user.getUserName());
		m.addAttribute("admin", admin);

		return "admin/profile";
	}

	// diary contact management
	@GetMapping("/add-contact-to-diary")
	public String diary(Model m) {
		m.addAttribute("title", "Add Contact");
		m.addAttribute("contact", new Contacts());
		return "admin/diary";
	}

	@PostMapping("/add-contact")
	public String addContact(@Valid @ModelAttribute("contact") Contacts contact, BindingResult result, Model m,
			HttpSession session) {
		if (result.hasErrors()) {
			m.addAttribute("title", "Add Contact");
			m.addAttribute("contact", contact);
			return "admin/diary";
		}

		if (contactRepo.save(contact) != null)
			session.setAttribute("message", new Message("Contact added to Diary successfully", "alert-success"));
		else {
			session.setAttribute("message",
					new Message("Contact can not added into Diary. Try again...", "alert-danger"));
		}
		return "redirect:add-contact-to-diary";
	}

	// notice board management
	@GetMapping("/notice-board")
	public String noticeBoard(Model m) {
		m.addAttribute("title", "NoticeBoard");
		m.addAttribute("notice", new Notice());
		return "admin/noticeboard";
	}

	@PostMapping("/add-notice")
	public String addNotice(@Valid @ModelAttribute("notice") Notice notice, BindingResult result, Model m,
			HttpSession session, @RequestParam(value = "isEmergency", defaultValue = "false") Boolean isEmergency) {
		if (result.hasErrors()) {
			m.addAttribute("title", "NoticeBoard");
			m.addAttribute("notice", notice);
			return "admin/noticeboard";
		}
		notice.setEmergency(isEmergency);

		if (noticeRepo.save(notice) != null)
			session.setAttribute("message", new Message("Notice added to Notice Board successfully", "alert-success"));
		else
			session.setAttribute("message", new Message("Something went wrong!! Try Again...", "alert-danger"));

		return "redirect:notice-board";
	}

	// expense management
	@GetMapping("/add-expense")
	public String expense(Model m) {
		m.addAttribute("title", "Add Expense");
		m.addAttribute("expense", new Expense());
		return "admin/expense";
	}

	@PostMapping("/add-expense")
	public String addExpense(@Valid @ModelAttribute("expense") Expense expense, BindingResult result, Model m,
			HttpSession session) {
		if (result.hasErrors()) {
			m.addAttribute("title", "Add Expense");
			m.addAttribute("expense", expense);
			return "admin/expense";
		}

		if (expenseRepo.save(expense) != null) {
			session.setAttribute("message", new Message("Expense added to Expense List successfully", "alert-success"));
		} else
			session.setAttribute("message", new Message("Something went wrong!! Try Again...", "alert-danger"));

		return "redirect:add-expense";
	}

	// generate maintenance bill

	@GetMapping("/generate-maintenance-bill")
	public String generateBillInit(Model m) {

		m.addAttribute("title", "Generate Maintenance Bill");

		if (!m.containsAttribute("bill")) {
			m.addAttribute("bill", new MaintenanceBill());
		}

		return "admin/generateBill";
	}

	// maintenance bill detail fetching
	@PostMapping("/generate-maintenance-bill-fetch")
	public String prefetch(@ModelAttribute MaintenanceBill bill, Model m, HttpSession session) {

		m.addAttribute("title", "Generate Maintenace Bill");
		m.addAttribute("bill", bill);
		String forMonthYear = bill.getForMonthYear();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM");
		Date date = null;

		try {
			date = formatter.parse(forMonthYear);
		} catch (ParseException ex) {
			session.setAttribute("message", new Message("Month Year is not valid", "alert-danger"));
			return "admin/generateBill";
		}
		if (date != null) {
			if (maintenanceRepo.existsMaintenanceBillByForMonthYear(forMonthYear)) {
				session.setAttribute("message",
						new Message("Maintenance bill is already generated for this month", "alert-warning"));
				return "admin/generateBill";
			}

			// fetching bill amount and return it
			try {
				bill.setBillAmount(service.prefetchAmount(date));
				m.addAttribute("bill", bill);
				return "admin/generateBill";
			} catch (Exception ex) {
				session.setAttribute("message",
						new Message("Something went wrong!!!  Please Try Again Later...", "alert-danger"));
				return "admin/generateBill";
			}

		}

		session.setAttribute("message",
				new Message("Something went wrong!!!  Please Try Again Later...", "alert-danger"));
		return "admin/generateBill";
	}

	@PostMapping("/generate-maintenance-bill-verified")
	public String generateMaintenance(MaintenanceBill bill, Model m, HttpSession session) {

		m.addAttribute("title", "Generate Maintenace Bill");
		m.addAttribute("bill",bill);
		String forMonthYear = bill.getForMonthYear();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM");
		Date date = null;

		// checking yyyy/MM is alright or not
		try {
			date = formatter.parse(forMonthYear);
		} catch (ParseException ex) {
			session.setAttribute("message", new Message("Month Year is not valid", "alert-danger"));
			m.addAttribute("bill",new MaintenanceBill());
			return "admin/generateBill";
		}

		// check whether bill is already generated or not
		if (date != null) {
			if (maintenanceRepo.existsMaintenanceBillByForMonthYear(forMonthYear)) {
				session.setAttribute("message",
						new Message("Maintenance bill is already generated for this month", "alert-warning"));
				m.addAttribute("bill",new MaintenanceBill());
				return "admin/generateBill";
			}
			if (service.generateMaintenanceBill(date, bill.getDueDate())) {
				session.setAttribute("message",
						new Message("Maintenance bill generated successfully", "alert-success"));
				return "redirect:generate-maintenance-bill";
			}
		}

		session.setAttribute("message",
				new Message("Something went wrong!!!  Please Try Again Later...", "alert-danger"));
		m.addAttribute("bill", bill);
		return "redirect:generate-maintenance-bill";
	}

}
