package com.nayan.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nayan.entity.Expense;
import com.nayan.entity.MaintenanceBill;
import com.nayan.entity.Notice;
import com.nayan.entity.SocietyMember;
import com.nayan.helper.Maintenance;
import com.nayan.repo.ExpenseRepo;
import com.nayan.repo.FormRepo;
import com.nayan.repo.NoticeRepo;
import com.nayan.repo.SocietyMemberRepo;
import com.nayan.repo.projection.CreationTime;

@Controller
@RequestMapping("/user")
public class SocietyMemberUtilityController {

	@Autowired
	private SocietyMemberRepo memberRepo;
	@Autowired
	private FormRepo formRepo;
	@Autowired
	private NoticeRepo noticeRepo;
	@Autowired
	private ExpenseRepo expenseRepo;
	@Autowired
	private Maintenance maintenance;
	

	@GetMapping("")
	public String home() {
		return "redirect:/user/profile";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model m, Principal principal) {
		m.addAttribute("title", "Dashboard");
		return "user/notice-board";
	}

	@GetMapping("/profile")
	public String profile(Model m, Principal principal) {
		m.addAttribute("isOriginal", true);
		SocietyMember member = memberRepo.getSocietyMemberByEmailIgnoreCase(principal.getName());
		if (member.getProfile() == null || member.getProfile() == "") {
			member.setProfile("https://ui-avatars.com/api/?name=" + member.getFirstName() + " " + member.getLastName()
					+ "&background=random&rounded=true");
			m.addAttribute("isOriginal", false);
		}
		m.addAttribute("member", member);
		CreationTime time = formRepo.findByEmailIgnoreCaseAndMobileNumber(member.getEmail(), member.getMobileNumber());
		m.addAttribute("recordDate", time.getRecordDate().toString());

		m.addAttribute("title", "Profile");
		return "user/profile";
	}

	@GetMapping("/view-expense")
	public String viewExpense(Model m, HttpSession session) {
		m.addAttribute("title","View Expense of Society");
		List<Expense> expenses =expenseRepo.findAll(Sort.by(Direction.DESC,"timeStamp"));
		m.addAttribute("expenses", expenses);
		return "user/ViewExpense";
	}
	
	@GetMapping("/notice-board")
	public String noticeBoard(Model m) {
		m.addAttribute("title", "Notice-Board");
		List<Notice> notices=noticeRepo.findAll(Sort.by(Direction.DESC,"noticeOn"));
		m.addAttribute("notices",notices);
		return "user/NoticeBoard";
	}
	
	@RequestMapping("/maintenance")
	public String maintenance(Model m) {
		m.addAttribute("title","Maintenance");
		
		Calendar today=Calendar.getInstance();
		String data=(today.get(Calendar.MONTH)+1)+"/"+today.get(Calendar.YEAR);
		
		maintenance.setForMonthYear(data);
		List<Expense> expenses=new ArrayList<>();
		double total=0;
		MaintenanceBill bill=maintenance.getBill();
		if(bill!=null)
		{
			m.addAttribute("exist",true);
			m.addAttribute("bill",bill);
			expenses=maintenance.getExpenses();
			total=maintenance.getAmount();
			m.addAttribute("expenses", expenses);
			m.addAttribute("total", total);
		}else {
			m.addAttribute("expenses", expenses);
			m.addAttribute("exist",false);
		}
		
		return "user/maintenance";
	}
	
	@PostMapping("/maintenance")
	public String maintenancePost(Model m,@RequestParam("forMonthYear") String data) {
		m.addAttribute("title","Maintenance");
		
		maintenance.setForMonthYear(data);
		List<Expense> expenses=new ArrayList<>();
		double total=0;
		MaintenanceBill bill=maintenance.getBill();
		if(bill!=null)
		{
			m.addAttribute("exist",true);
			m.addAttribute("bill",bill);
			expenses=maintenance.getExpenses();
			total=maintenance.getAmount();
			m.addAttribute("expenses", expenses);
			m.addAttribute("total", total);
		}else {
			m.addAttribute("expenses", expenses);
			m.addAttribute("exist",false);
		}
		return "user/maintenance";
	}
	
}
