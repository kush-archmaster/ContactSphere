package com.contact.entities;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

	@Id
	@GeneratedValue(generator = "user_seq")
	@SequenceGenerator(name = "user_seq", sequenceName = "MY_SEQ", allocationSize = 1)
	private Long id;
	@Column(name = "user_name", nullable = false, length = 100)
	private String name;
	@Column(name = "user_email", unique = true)
	private String email;
	private String password;
	private String role;
	private boolean isActive;
	private String imgUrl;
	@Column(length = 500)
	private String about;
	private Timestamp createdAt;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Contact> contact = new HashSet<>();
}
