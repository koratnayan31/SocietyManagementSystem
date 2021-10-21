package com.nayan.error;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		final int ERROR_CODE = Integer.valueOf(status.toString());
		final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		
		System.out.println(baseUrl);
		
		switch (ERROR_CODE) {
		case 404:
			System.err.println("Somepage is being request which doesn't exist or page have some mis-configuration");
			return "404";
		case 403:
			System.err.println("Alert! below user is trying to access forbidden page");
			System.err.println(request.getUserPrincipal().getName());
			return "403";
		case 500:
			System.err.println("Some internal processing doen't happened currectly");
			return "500";
		}

		
		return "error";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

}
