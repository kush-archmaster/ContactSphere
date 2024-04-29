package com.contact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contact.dtos.UserDto;
import com.contact.entities.User;
import com.contact.mapper.ContactSchemaMapper;
import com.contact.models.Message;
import com.contact.repositories.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginSignupController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactSchemaMapper contactSchemaMapper;
	
	@Autowired
	private PasswordEncoder encoder;

	@PostMapping(path = "/register")
	public String registerUser(
			@Valid @ModelAttribute("user") UserDto userDto, 
			BindingResult result, 
			@RequestParam(name = "tnc", defaultValue = "false") Boolean tnc, 
			Model model, 
			HttpSession httpSession) {
		try {
			if(!tnc) {
				throw new Exception("You have not agreed to Terms and Conditions.");
			}
			
			/* Server side validation */
			if(result.hasErrors()) {
				model.addAttribute("user", userDto);
				return "signup";
			}
			
			/* save in db */
			User user = contactSchemaMapper.toUser(userDto);
			user.setRole("USER");
			user.setActive(true);
			user.setPassword(encoder.encode(userDto.getPassword()));
			userRepository.save(user);
			
			log.info("User Registered: {}", user);
			
			/* Registration successful alert message */
			model.addAttribute("user", new UserDto());
			httpSession.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));
		} 
		catch (DataIntegrityViolationException e) {
			model.addAttribute("user", userDto);
			httpSession.setAttribute("message", new Message("Email already exists!", "alert-danger"));
		}
		catch (Exception e) {
			model.addAttribute("user", userDto);
			httpSession.setAttribute("message", new Message(e.getMessage(), "alert-danger"));
		}
		return "signup";
	}
	
}
