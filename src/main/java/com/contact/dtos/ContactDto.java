package com.contact.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

	@Size(min = 0, max = 25, message = "Name must be between 2 to 25 characters!")
	@Pattern(regexp = "^[a-zA-Z]+(?:\\s[a-zA-Z]+)?$", message = "Invalid first name!" )
	private String firstName;
	
	@Size(min = 0, max = 25, message = "Name must be between 2 to 25 characters!")
	@Pattern(regexp = "^[a-zA-Z]+(?:\\s[a-zA-Z]+)?$", message = "Invalid last name!" )
	private String lastName;
	private String work;
	
	@Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email!")
	private String email;
	
	private String phone;
	private String imgUrl;
	private String description;
	
	@NotEmpty(message = "Address is missing!")
	private String address;
	@NotEmpty(message = "Date of Birth is missing!")
	private String dob;
}
