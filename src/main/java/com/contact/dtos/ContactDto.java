package com.contact.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

	private String firstName;
	private String lastName;
	private String work;
	private String email;
	private String phone;
	private String imgUrl;
	private String description;
	private String address;
	private String dob;
}
