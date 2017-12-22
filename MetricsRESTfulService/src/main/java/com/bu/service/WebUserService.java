package com.bu.service;

import java.util.List;

import com.bu.entities.WebUser;

public interface WebUserService {

	WebUser findById(Long id);

	List<WebUser> findByFirstName(String firstName);

	List<WebUser> findByLastName(String lastName);

	List<WebUser> findByFirstNameAndLastName(String firstName, String lastName);
	
	List<WebUser> findAll();
	
	void delete(Long id);

	WebUser save(WebUser customer);
}
