package com.bu.respository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bu.entities.WebUser;

public interface WebUserRepository extends CrudRepository<WebUser, Long> {

	List<WebUser> findByFirstName(String firstName);
	
    List<WebUser> findByLastName(String lastName);
    
    List<WebUser> findByFirstNameAndLastName(String firstName, String lastName);
}
