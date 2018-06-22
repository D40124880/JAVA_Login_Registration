package com.codingdojo.register.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.register.models.User;
import com.codingdojo.register.service.UserService;
import com.codingdojo.register.validators.UserValidator;

@Controller
public class UserController {

	private final UserService userService;
	private final UserValidator userValidator;
	
	public UserController(UserService userService, UserValidator userValidator) {
		this.userService = userService;
		this.userValidator = userValidator;
	}
	
	 @RequestMapping("/registration")
    public String registerForm(@ModelAttribute("user") User user) {
		 System.out.println("reg get");
        return "/authentication/registrationPage.jsp";
    }
    @RequestMapping("/login")
    public String login() {
    	System.out.println("login get");
        return "/authentication/loginPage.jsp";
    }
    
    @RequestMapping(value="/registration", method=RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
        // if result has errors, return the registration page (don't worry about validations just now)
        // else, save the user in the database, save the user id in session, and redirect them to the /home route
    	userValidator.validate(user, result);
    	System.out.println("reg post");
    	if(result.hasErrors()) {
    		return "/authentication/registrationPage.jsp";
    	} else {
    		User tempUser = userService.registerUser(user);
    		session.setAttribute("id", tempUser.getId());
    		return "redirect:/home";
    	}
    	
    }
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
        // if the user is authenticated, save their user id in session
        // else, add error messages and return the login page
    	System.out.println("login post");
    	boolean check = userService.authenticateUser(email, password);
    	if(check == false) {
    		model.addAttribute("error", "You could not be looged in. Try again!");
    		return "/authentication/loginPage.jsp";
    	} else {
    		User tempUser = userService.findByEmail(email);
    		session.setAttribute("id", tempUser.getId());
    		return "redirect:/home";
    	}
    }
    
    @RequestMapping("/home")
    public String home(HttpSession session, Model model) {
    	if(session.getAttribute("id") == null) {
    		return "redirect:/registration";
    	}
        // get user from session, save them in the model and return the home page
    	System.out.println("home");
    	Long tempId = (Long) session.getAttribute("id");
    	User tempUser = userService.findUserById(tempId);
    	model.addAttribute("user", tempUser);
    	
    	return "/authentication/homePage.jsp";
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        // invalidate session
        // redirect to login page
    	System.out.println("logout");
    	session.invalidate();
    	return "redirect:/login";
    }
}
