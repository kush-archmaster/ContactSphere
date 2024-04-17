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
public class UserDto {

	@NotEmpty(message = "Please enter your name!")
	@Size(min = 2, max = 25, message = "Name must be between 2 to 25 characters!")
	@Pattern(regexp = "^[a-zA-Z]+(?:\\s[a-zA-Z]+)?$", message = "Invalid name!" )
	private String name;
	
	@Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email!")
	private String email;
	
	@NotEmpty(message = "Password must not be empty!")
	@Size(min=3, max = 10, message = "Password must be between 3 to 10 characters!")
	private String password;
	
	private String about;
}
