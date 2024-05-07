package com.contact.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.contact.dtos.ContactDto;
import com.contact.entities.Contact;
import com.contact.entities.User;
import com.contact.mapper.ContactSchemaMapper;
import com.contact.models.Message;
import com.contact.repositories.ContactRepository;
import com.contact.repositories.UserRepository;
import com.contact.utility.ImageUploadUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private ContactSchemaMapper contactSchemaMapper;
	
	@Autowired
	private ImageUploadUtil imageUploadUtil;
	
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
	
	@PostMapping("/saveContact")
	public String saveContact(@Valid @ModelAttribute("contact") ContactDto contactDto,
			BindingResult result, 
			@RequestParam("imageFile") MultipartFile imageFile,
			Model model, 
			Principal principal,
			HttpSession session) {
		try {
			if(result.hasErrors()) {
				model.addAttribute("contact", contactDto);
				return "/normal_user/add_contact_form";
			}
			
			User user = userRepository.getUserByEmail(principal.getName());
			if(user!=null) {
				Contact contact = contactSchemaMapper.toContact(contactDto);
				/*
				 * Check if contact is already added with the name
				 */
				List<Contact> rejectedContact = user.getContact().stream()
				.filter(savedContact -> contact.getEmail().equalsIgnoreCase(savedContact.getEmail())).collect(Collectors.toList());
				
				if(!rejectedContact.isEmpty()) {
					session.setAttribute("message", new Message("Contact Already exists with the email Id!", "alert-danger"));
					model.addAttribute("contact", contactDto);
					return "/normal_user/add_contact_form";
				}
				
				/* 
				 * processing and uploading image
				 */
				String fileName = imageUploadUtil.saveImage(imageFile);
				contact.setImgUrl(fileName);
				
				/*
				 * link contact with the user
				 */
				contact.setUser(user);
				user.getContact().add(contact);
				
				userRepository.save(user);
				log.info("Contact successfully saved!");
				
				model.addAttribute("contact", new ContactDto());
				session.setAttribute("message", new Message(String.format("Successfully Added %s", contact.getFirstName()), "alert-success"));
			}
			
		} catch (Exception e) {
			model.addAttribute("user", contactDto);
			session.setAttribute("message", new Message("Something went wrong! Please try again after sometime.", "alert-danger"));
		}
		return "/normal_user/add_contact_form";
	}
	
	/*
	 * Pagination support , configured max records in each page= 5
	 */
	@GetMapping("/viewContacts/{page}")
	public String showContactList(@PathVariable Integer page, Model model, Principal principal) {
		List<ContactDto> contactDtoList = new ArrayList<>();
		model.addAttribute("title", "ContactSphere - Show Contacts List");
		
		User user = userRepository.getUserByEmail(principal.getName());
		Page<Contact> contactsPage = contactRepository.findContactsByUserId(user.getId(), PageRequest.of(page, 6));
		if(!contactsPage.isEmpty()) {
			contactDtoList = contactSchemaMapper.toContactDtoList(contactsPage.getContent());
		}
		
		model.addAttribute("contactsList", contactDtoList);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", contactsPage.getTotalPages());
		return "/normal_user/view_contacts2";
	}
	
	
	@GetMapping("/contact/{contactId}")
	public String showContactList(@PathVariable Long contactId, Model model, Principal principal) {
		Optional<Contact> contactOptional = contactRepository.findById(contactId);
		Contact contact = contactOptional.get();
		if(principal.getName().equalsIgnoreCase(contact.getUser().getEmail())) {
			model.addAttribute("contact", contact);
			model.addAttribute("title", "ContactSphere - ".concat(contact.getFirstName()).concat(" ").concat(contact.getLastName()));
		}
		return "/normal_user/contact_detail";
	}
	
	@GetMapping("/contact/delete/{contactId}")
	public String deleteContact(@PathVariable Long contactId, Principal principal, HttpSession session) {
		Optional<Contact> contactOptional = contactRepository.findById(contactId);
		Contact contact = contactOptional.get();
		/*
		 * if user is authenticated for deleting the contact.
		 */
		if(principal.getName().equalsIgnoreCase(contact.getUser().getEmail())) {
			/*
			 * unlink contact from user, since we have set cascade.All
			 */
			contact.setUser(null);
			/* remove image */
			
			contactRepository.deleteById(contactId);
			session.setAttribute("message", new Message("Contact Deleted Successfully!", "alert-success"));
		}
		return "redirect:/user/viewContacts/0";
	}
}
