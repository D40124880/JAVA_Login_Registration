package com.codingdojo.events.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.events.models.User;
import com.codingdojo.events.service.LogAndRegService;
import com.codingdojo.events.validator.LogAndRegValidator;

@Controller
public class LogAndRegController {
	
	private final LogAndRegService logandregService;
	private final LogAndRegValidator logandregValidator;
	
	public LogAndRegController(LogAndRegService logandregService, LogAndRegValidator logandregValidator) {
		this.logandregService = logandregService;
		this.logandregValidator = logandregValidator;
	}
	
	@RequestMapping("/")
	public String registerForm(@ModelAttribute User user) {
		return "/events/Index.jsp";
	}
	
	@RequestMapping(value="/registration", method=RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute User user, BindingResult result, HttpSession session) {
		logandregValidator.validate(user, result);
		if(result.hasErrors()) {
			return "/events/Index.jsp";
		}else {
			User tempUser = logandregService.registerUser(user);
			session.setAttribute("uid", tempUser.getId());
			return "redirect:/dashboard";
		}
	}
	
	@RequestMapping(value="/login" ,method=RequestMethod.POST)
	public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
		boolean check = logandregService.authenticateUser(email, password);
		if(check == false) {
			model.addAttribute("error", "You could not be logged in. Please try again");
			return "/events/Index.jsp";
		} else {
			User tempUser = logandregService.findByEmail(email);
			session.setAttribute("uid", tempUser.getId());
			return "redirect:/dashboard";
		}
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
