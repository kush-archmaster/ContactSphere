package com.contact.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.contact.entities.Contact;

@Transactional
public interface ContactRepository extends JpaRepository<Contact, Long>{

	@Query("from Contact as co where co.user.id = :userId")
	public Page<Contact> findContactsByUserId(Long userId, Pageable pageReq);

}
