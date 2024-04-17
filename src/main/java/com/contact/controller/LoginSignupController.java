package com.contact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contact.entities.User;
import com.contact.models.Message;
import com.contact.repositories.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class LoginSignupController {
	
	@Autowired
	private UserRepository userRepository;

	@PostMapping(path = "/register")
	public String registerUser(@ModelAttribute("user") User user, 
			@RequestParam(name = "tnc", defaultValue = "false") Boolean tnc, 
			Model model, HttpSession httpSession) {
		try {
			if(!tnc) {
				throw new Exception("You have not agreed to Terms and Conditions.");
			}
			user.setRole("USER");
			user.setActive(true);
			userRepository.save(user);
			System.out.println(user);
			model.addAttribute("user", new User());
			httpSession.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));
		} catch (Exception e) {
			model.addAttribute("user", new User());
			httpSession.setAttribute("message", new Message("Something went wrong !! "+e.getMessage(), "alert-danger"));
		}
		return "signup";
	}
}
