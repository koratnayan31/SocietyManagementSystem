package com.nayan.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nayan.entity.Form;
import com.nayan.entity.Message;
import com.nayan.exception.AdharNumberAlreadyExist;
import com.nayan.exception.EmailAlreadyExist;
import com.nayan.exception.FileEmptyException;
import com.nayan.exception.FileSizeLimitCrossedException;
import com.nayan.exception.FileTypeNotAllowedException;
import com.nayan.helper.FileUploader;
import com.nayan.service.ServiceProvider;

@Controller
public class RegistrationController {

	@Autowired
	private FileUploader fileUploader;

	@Autowired
	private ServiceProvider provider;

	
	
	@PostMapping("/do_register")
	public String register(@Valid @ModelAttribute("form") Form form, BindingResult result,
			@RequestParam("isOwner") boolean isOwner, @RequestParam("gender") String gender,
			@RequestParam("adharcardProof") MultipartFile adharcardProof,
			@RequestParam("profileImage") MultipartFile profileImage,
			@RequestParam("ownerProof") MultipartFile ownerProof, HttpSession session, RedirectAttributes attr,
			Model model) {

		if (!provider.isAccepatableEmail(form.getEmail())) {
			result.rejectValue("email", "error.form", "This Email address is already exists");
		}
		if (!provider.isAccepatableAdharcard(form.getAdharcardNo())) {
			result.rejectValue("adharcardNo", "error.form",
					"This Adhar No is already exists. Check your adharcard number. If this is mistake than contact secretory");
		}
		
		if(!isOwner&&!provider.isValidOwnerEmail(form.getOwnerEmail())) {
			result.rejectValue("ownerEmail","error.form","You can register only after your owner is registered. Please check your Owner Email id correct and registered with us");
		}

		if (!result.hasErrors()) {
			try {
				form.setAdharUrl(fileUploader.uploadFile(adharcardProof, "adharcard", form.getAdharcardNo()));
			} catch (FileEmptyException ex) {
				result.rejectValue("adharUrl", "error.form", ex.getMessage());

			} catch (FileSizeLimitCrossedException ex) {
				result.rejectValue("adharUrl", "error.form", ex.getMessage());
			} catch (FileTypeNotAllowedException ex) {
				result.rejectValue("adharUrl", "error.form", ex.getMessage());
				ex.getMessage();
			}

			try {
				form.setOwnerTenantProofUrl(fileUploader.uploadFile(ownerProof, "ownerProof", form.getAdharcardNo()));
			} catch (FileEmptyException ex) {
				result.rejectValue("ownerTenantProofUrl", "error.form", ex.getMessage());
			} catch (FileSizeLimitCrossedException ex) {
				result.rejectValue("ownerTenantProofUrl", "error.form", ex.getMessage());
			} catch (FileTypeNotAllowedException ex) {
				result.rejectValue("ownerTenantProofUrl", "error.form", ex.getMessage());
			}

			try {
				form.setProfileImageUrl(fileUploader.uploadFile(profileImage, "profileImage", form.getAdharcardNo()));
			} catch (FileSizeLimitCrossedException ex) {
				result.rejectValue("profileImageUrl", "error.form", ex.getMessage());
			} catch (FileEmptyException ex) {
				form.setProfileImageUrl(null);
				System.out.println(form.getAdharcardNo() + " user has not set his/her Profile Image");
			} catch (FileTypeNotAllowedException ex) {
				result.rejectValue("profileImageUrl", "error.form", ex.getMessage());
			}
		}

		if (!result.hasErrors()) {
			form.setOwner(isOwner);
			form.setGender(gender);
			// default parameter
			form.setAccountActive(false);
			form.setComment("Your account is being reviewed. We will inform you when your request will be reviewed");
			form.setRequestReviewed(false);
	
			try {
				provider.addForm(form);
			} catch (EmailAlreadyExist ex) {
				result.rejectValue("email", "error.form", ex.getMessage());
			} catch (AdharNumberAlreadyExist ex) {
				result.rejectValue("adharcardNo", "error.form", ex.getMessage());
			}
		}

		if (result.hasErrors()) {
			System.out.println(result);
			attr.addFlashAttribute("form", form);
			attr.addFlashAttribute("org.springframework.validation.BindingResult.form", result);
			return "redirect:/signup";
		}

		session.setAttribute("message",new Message("Your Registration request is submitted successfully. You will be notified when your request status will change","alert-success"));
				
		return "redirect:/";
	}

}
