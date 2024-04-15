package com.contact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

	@RequestMapping("/home")
	public String landHomePage(Model model) {
		model.addAttribute("title", "ContactSphere - One Stop Solution");
		return "home";
	}
	
	@RequestMapping("/about")
	public String aboutPage(Model model) {
		model.addAttribute("title", "ContactSphere - One Stop Solution");
		return "about";
	}
}
