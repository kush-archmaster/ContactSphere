package com.contact.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.contact.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long>{

	@Query("from Contact as co where co.user.id = :userId")
	public List<Contact> findContactsByUserId(Long userId);
}
