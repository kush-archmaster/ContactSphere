package com.contact.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.contact.dtos.ContactDto;
import com.contact.entities.User;
import com.contact.mapper.ContactSchemaMapper;
import com.contact.repositories.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactSchemaMapper contactSchemaMapper;
	
	@ModelAttribute
	public void addUserToModel(Model model, Principal principal) {
		String username = principal.getName();  // emailId
		User user = userRepository.getUserByEmail(username);
		if(user!=null) {
			model.addAttribute("user", contactSchemaMapper.toUserDto(user));
		}
	}

	@GetMapping("/dashboard")
	public String getUserDashboard(Model model, Principal principal) {
		model.addAttribute("title", "ContactSphere - Dashboard");
		return "/normal_user/user_dashboard";
	}
	
	@GetMapping("/addContact")
	public String addContactForm(Model model) {
		model.addAttribute("title", "ContactSphere - Add Contact");
		model.addAttribute("contact", new ContactDto());
		return "/normal_user/add_contact_form";
	}
}
