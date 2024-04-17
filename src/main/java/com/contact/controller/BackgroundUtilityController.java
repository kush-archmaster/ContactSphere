package com.contact.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BackgroundUtilityController {

	@GetMapping("/removeMessageFromSession")
	public void removeAttribute(HttpSession session) {
		session.removeAttribute("message");
		log.info("Session attribute reset");
	}
}
