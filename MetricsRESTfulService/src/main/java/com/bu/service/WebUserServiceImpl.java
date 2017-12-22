package com.bu.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bu.entities.WebUser;
import com.bu.respository.WebUserRepository;

@Service
public class WebUserServiceImpl implements WebUserService {

	@Inject
	private WebUserRepository repository;

	public WebUser findById(Long id) {
		WebUser customer = repository.findOne(id);
		if (null == customer) {
			throw new NoSuchElementException("Customer with id " + id + " not found");
		}
		return customer;
	}

	public List<WebUser> findByFirstName(String firstName) {
		List<WebUser> users = repository.findByFirstName(firstName);
		return users;
	}

	public List<WebUser> findByLastName(String lastName) {
		List<WebUser> users = repository.findByLastName(lastName);
		return users;
	}

	public List<WebUser> findByFirstNameAndLastName(String firstName, String lastName) {
		List<WebUser> users = repository.findByFirstNameAndLastName(firstName, lastName);
		return users;
	}
	
	public List<WebUser> findAll() {
		return (List<WebUser>) repository.findAll();
	}
	
	public void delete(Long id) {
		WebUser customer = repository.findOne(id);
		if (customer != null) {
			repository.delete(id);
		}
	}

	public WebUser save(WebUser customer) {
		return repository.save(customer);
	}
}
