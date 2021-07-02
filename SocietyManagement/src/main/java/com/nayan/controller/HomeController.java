package com.nayan.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nayan.entity.Contacts;
import com.nayan.entity.Form;
import com.nayan.repo.ContactRepo;

@Controller
public class HomeController {
	
	@Autowired
	BCryptPasswordEncoder encoder;
	@Autowired
	private ContactRepo contactRepo;
	
	
	
	@RequestMapping("/")
	public String defaultHome(Model m) {
		m.addAttribute("title", "Home-Society Management System");
		return "home";
	}

	@RequestMapping("/home")
	public String home(Model m) {
		m.addAttribute("title", "Home-Society Management System");
		return "home";
	}

	@RequestMapping("/signup")
	public String signup(Model m) {
		m.addAttribute("title", "Sign Up-Society Management System");
		if (!m.containsAttribute("form")) {
			m.addAttribute("form", new Form());
		}
		return "signup";
	}
	
	
	@RequestMapping("/login")
	public String signin(Model m,HttpSession session) {
		//session.invalidate();
		m.addAttribute("title","Sign In");
		return "login";
	}
	
	
	@RequestMapping("/diary")	
	public String diary(Model m) {
		m.addAttribute("title","Society Diary");
		List<Contacts> contacts=contactRepo.findAll();
		m.addAttribute("contacts", contacts);
		return "diary";
	}

}
