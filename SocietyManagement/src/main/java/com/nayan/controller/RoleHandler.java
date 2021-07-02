package com.nayan.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.nayan.entity.SocietyMember;
import com.nayan.entity.User;
import com.nayan.repo.SocietyMemberRepo;
import com.nayan.repo.UserRepo;

@Controller
public class RoleHandler {

	@Autowired
	private UserRepo userRepo;
	
	
	@Autowired
	private SocietyMemberRepo memberRepo;
	
	@GetMapping("/login/success")
	public String determineRole(Authentication authentication,HttpSession session,Principal principal) {

		Collection<? extends GrantedAuthority> userAuthority = authentication.getAuthorities();
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority grant : userAuthority) {
			roles.add(grant.getAuthority());
		}

		if (roles.contains("ROLE_ADMIN")) {
			User user = userRepo.findByUserNameIgnoreCase(principal.getName());
			session.setAttribute("user", user);
			
			return "redirect:/admin/pending-requests/0";
		}
		if (roles.contains("ROLE_SOCIETY_MEMBER")) {
			SocietyMember user=memberRepo.getSocietyMemberByEmailIgnoreCase(principal.getName());
			session.setAttribute("user", user);
			
			return "redirect:/user/notice-board";
		}
		return "/error";
	}
}
