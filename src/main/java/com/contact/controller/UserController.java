package com.contact.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@GetMapping("/dashboard")
	public String getUserDashboard(Model model, Principal principal) {
		String username = principal.getName();  // emailId
		User user = userRepository.getUserByEmail(username);
		if(user!=null) {
			model.addAttribute("user", contactSchemaMapper.toUserDto(user));
			model.addAttribute("title", "ContactSphere - Dashboard");
		}
		return "/normal_user/user_dashboard";
	}
}
