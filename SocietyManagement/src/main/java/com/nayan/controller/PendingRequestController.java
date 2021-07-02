package com.nayan.controller;

import java.security.Principal;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nayan.entity.Form;
import com.nayan.entity.Message;
import com.nayan.entity.SocietyMember;
import com.nayan.entity.User;
import com.nayan.helper.SendEmail;
import com.nayan.helper.Utility;
import com.nayan.repo.FormRepo;
import com.nayan.repo.UserRepo;
import com.nayan.service.ServiceProvider;

@Controller
@RequestMapping("/admin/pending-requests")
public class PendingRequestController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private FormRepo formRepo;

	@Autowired
	private ServiceProvider provider;

	@Autowired
	private SendEmail mailer;

	// default redirection
	@GetMapping("")
	public String requestReview() {
		return "redirect:/admin/pending-requests/0";
	}

	// show pending requests
	@GetMapping("/{page}")
	public String requestReview(@PathVariable("page") Integer page, Model m, Principal principal) {

		User user = userRepo.findByUserNameIgnoreCase(principal.getName());
		m.addAttribute("user", user);
		m.addAttribute("title", "Request Review");

		Pageable pageRequest = PageRequest.of(page, 2);

		Page<Form> requests = formRepo.findAllPendingRequest(pageRequest);
		m.addAttribute("isEmpty",requests.isEmpty());
		m.addAttribute("requests", requests);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPage", requests.getTotalPages());

		return "admin/PendingRequest";
	}

	// view request
	@GetMapping("/view/{formId}")
	public String viewrequest(@PathVariable("formId") Integer formId, Model m, HttpSession session,
			Principal principal) {
		Form form = null;
		try {
			form = formRepo.findById(formId).get();
		} catch (NoSuchElementException ex) {
			session.setAttribute("message", new Message("There is no data available for Form", "alert-danger"));
			return "redirect:/admin/pending-requests/0";
		}
		m.addAttribute("title", Utility.toCamelCase(form.getFirstName() + " " + form.getLastName()));

		m.addAttribute("form", form);
		return "admin/ViewRequest";
	}

	@GetMapping("/approve")
	public String approve(HttpSession session) {
		session.setAttribute("message", new Message("No request to Approve", "alert-warning"));

		return "redirect:/admin/pending-requests";
	}

	@GetMapping("/reject")
	public String reject(HttpSession session) {
		session.setAttribute("message", new Message("No request to Reject", "alert-warning"));

		return "redirect:/admin/pending-requests";
	}

	// approve request
	@GetMapping("/approve/{id}")
	public String approve(@PathVariable("id") Integer id, HttpSession session) {

		Form form;
		SocietyMember savedMember = null;

		// checking if there is any form exists with this id
		try {
			form = formRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			session.setAttribute("message", new Message("There is no request found to approve", "alert-danger"));
			return "redirect:/admin/pending-requests/0";
		}

		// if form is there, then update form data
		try {
			if (form != null) {
				form.setRequestReviewed(true);
				form.setAccountActive(true);
				form.setComment("Account is Activated Successfully");
				formRepo.save(form);

				// adding member as society member

				try {
					savedMember = provider.addSocietyMember(form);
					if (savedMember == null)
						throw new Exception("Saved member is null. It seems that saving of data is failed");
				} catch (Exception ex) {
					ex.printStackTrace();
					session.setAttribute("message",
							new Message("Unable to Approve Request.  Please Try Again...", "alert-danger"));
					return "redirect:/admin/pending-requests/0";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message",
					new Message("Unable to Approve Request. Please Try Again...", "alert-danger"));
			return "redirect:/admin/pending-requests";
		}

		// setting up login registry

		provider.addUserAsSocietyMember(savedMember);
		session.setAttribute("message", new Message("Request approved successfully", "alert-success"));
		mailer.setTo(savedMember.getEmail());
		mailer.setSubject("Welcome to LDCE Society");
		String message = "Dear " + savedMember.getName() + ",\nYou have signed up with us on " + form.getRecordDate()
				+ ".\nWe are happy to "
				+ "tell you that you are now member of LDCE society.\nYou can access your account with"
				+ " your log in credential which you have given us when signing up";
		mailer.setBody(message);
		try {
			if (!mailer.send()) {
				session.setAttribute("message",
						new Message("Request approved successfully but confirmation mail of request is "
								+ "not successfully sent. Please send manually mail to " + savedMember.getEmail(),
								"alert-warning"));
			}
		} catch (Exception e) {
			session.setAttribute("message",
					new Message(
							"Request approved successfully but confirmation mail of request is "
									+ "not successfully sent. Please send manually mail to " + savedMember.getEmail(),
							"alert-warning"));
		}
		return "redirect:/admin/pending-requests";
	}

	// reject request
	@GetMapping("/reject/{id}")
	public String reject(@PathVariable("id") Integer id, HttpSession session, @RequestParam("reason") String message) {

		Form form;
		try {
			form = formRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			session.setAttribute("message", new Message("There is no data available for Form", "alert-danger"));
			return "redirect:/admin/pending-requests/0";
		}
		try {
			if (form != null && !form.isAccountActive() && !form.isRequestReviewed()) {
				form.setRequestReviewed(true);
				form.setAccountActive(false);
				form.setComment(message);
				formRepo.save(form);
				session.setAttribute("message",
						new Message("Request Rejection Completed Successfully", "alert-success"));
				mailer.setTo(form.getEmail());
				mailer.setSubject("Account review of LDCE Society");
				String msg = "Dear " + form.getName()
						+ ",\nWe are sorry to let you know that your account with us can not be activated successfully"
						+ "and reason for that is '" + message + "' ";
				mailer.setBody(msg);
				try {
					if (!mailer.send()) {
						System.out.println("This is executing");
						session.setAttribute("message", new Message(
								"Request Rejection Completed Successfully, but confirmation for the same is not sent "
										+ "successfully",
								"alert-warning"));
					}
				} catch (Exception e) {
					e.printStackTrace();
					session.setAttribute("message", new Message(
							"Request Rejection Completed Successfully, but confirmation for the same is not sent "
									+ "successfully",
							"alert-warning"));
				}
			} else {
				session.setAttribute("message", new Message("There is no data available for Form", "alert-danger"));
			}
		} catch (Exception e) {
			session.setAttribute("message",
					new Message("Unable to Reject Request. Please Try Again...", "alert-danger"));
		}

		return "redirect:/admin/pending-requests";
	}

}
