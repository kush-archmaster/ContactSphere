package com.contact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.contact.entities.User;

@Controller
@RequestMapping("/")
public class HomePageController {

	@GetMapping
	public String landHomePage(Model model) {
		model.addAttribute("title", "ContactSphere - One Stop Solution");
		return "home";
	}
	
	@GetMapping("about")
	public String aboutPage(Model model) {
		model.addAttribute("title", "ContactSphere - One Stop Solution");
		return "about";
	}
	
	@GetMapping("signup")
	public String signupUser(Model model) {
		model.addAttribute("title", "ContactSphere - One Stop Solution");
		model.addAttribute("user", new User());
		return "signup";
	}
}
