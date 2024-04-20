package com.contact.entities;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

	@Id
	@GeneratedValue(generator = "contact_seq")
	@SequenceGenerator(name = "contact_seq", sequenceName = "MY_SEQ2", allocationSize = 1)
	private Long cId;
	private String firstName;
	private String lastName;
	private String work;
	private String email;
	private String phone;
	private String imgUrl;
	@Column(length = 10000)
	private String description;
	private Date dob;
	private String address;
	
	@ManyToOne
	private User user;
}
