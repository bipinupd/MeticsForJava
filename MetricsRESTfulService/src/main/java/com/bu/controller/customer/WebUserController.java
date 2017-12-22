package com.bu.controller.customer;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bu.controller.AbstractHttpController;
import com.bu.entities.WebUser;
import com.bu.service.WebUserService;
import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;

@RestController
public class WebUserController extends AbstractHttpController {

	@Inject
	private WebUserService webUserService;

	@Inject
	private JsonAndObjectMapper jsonAndObjectMapper;

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = JSON_MIME_TYPE)
	@ResponseBody
	@Timed
	@ExceptionMetered
	public ResponseEntity<String> get(@PathVariable Long id, HttpServletRequest request) {
		logApiCalls(request);
		String requestUrl = linkTo(WebUserController.class).withSelfRel().getHref();
		WebUser customer = webUserService.findById(id);
		String response = jsonAndObjectMapper.mapUserToJson(customer, requestUrl);
		return new ResponseEntity<String>(response, createHeaders(request.getMethod()), HttpStatus.OK);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = JSON_MIME_TYPE)
	@ResponseBody
	@Timed
	@ExceptionMetered
	public ResponseEntity<String> getAll(HttpServletRequest request) {
		logApiCalls(request);
		String requestUrl = linkTo(WebUserController.class).withSelfRel().getHref();
		List<WebUser> users = webUserService.findAll();
		String response = jsonAndObjectMapper.mapUsersToJson(users, requestUrl);
		return new ResponseEntity<String>(response, createHeaders(request.getMethod()), HttpStatus.OK);
	}


	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE, produces = JSON_MIME_TYPE)
	@ResponseBody
	@Timed
	@ExceptionMetered
	public ResponseEntity<String> delete(@PathVariable Long id, HttpServletRequest request) {
		logApiCalls(request);
		try {
			WebUser customer = webUserService.findById(id);
			System.out.println(customer.toString() + " found and will be deleted");
		} catch (NoSuchElementException e) {
		
		}
		webUserService.delete(id);
		return new ResponseEntity<String>(createHeaders(request.getMethod()), HttpStatus.NO_CONTENT);
	}


	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, produces = JSON_MIME_TYPE)
	@ResponseBody
	@Timed
	@ExceptionMetered
	public ResponseEntity<String> put(@PathVariable Long id, @RequestBody final String requestBody,
			HttpServletRequest request) {
		logApiCalls(request);
		WebUser customer = webUserService.findById(id);
		customer = jsonAndObjectMapper.mapJsonToUser(id, requestBody);
		webUserService.save(customer);
		String requestUrl = linkTo(WebUserController.class).withSelfRel().getHref();
		String response = jsonAndObjectMapper.mapUserToJson(customer, requestUrl);
		return new ResponseEntity<String>(response, createHeaders(request.getMethod()), HttpStatus.OK);
	}


	@RequestMapping(value = "/user", method = RequestMethod.POST, produces = JSON_MIME_TYPE)
	@ResponseBody
	@Timed
	@ExceptionMetered
	public ResponseEntity<String> post(@RequestBody final String requestBody, HttpServletRequest request) {
		logApiCalls(request);
		WebUser customer = jsonAndObjectMapper.mapJsonToUser(requestBody);
		customer = webUserService.save(customer);
		String requestUrl = request.getRequestURL().toString();
		String response = jsonAndObjectMapper.mapUserToJson(customer, requestUrl);
		return new ResponseEntity<String>(response, createHeaders(requestUrl, customer.getId(), request.getMethod()),
				HttpStatus.CREATED);
	}
}
