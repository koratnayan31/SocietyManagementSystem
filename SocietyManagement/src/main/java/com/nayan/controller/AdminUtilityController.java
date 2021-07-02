package com.nayan.controller;

import java.security.Principal;

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
import com.nayan.entity.Message;
import com.nayan.entity.Notice;
import com.nayan.entity.User;
import com.nayan.repo.AdminRepo;
import com.nayan.repo.ContactRepo;
import com.nayan.repo.ExpenseRepo;
import com.nayan.repo.NoticeRepo;
import com.nayan.repo.UserRepo;

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
			HttpSession session,@RequestParam(value = "isEmergency",defaultValue = "false") Boolean isEmergency ) {
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
		m.addAttribute("expense",new Expense());
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
}
