package com.spring.restfulwebservices.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	
	@Autowired
	private UserDaoService service;
	
	@GetMapping(path="/users")
	public List<User> retriveAllUsers() {
		System.out.println("Hello");
		return service.findAll();
	}
	
	@GetMapping(path="/users/{id}")
	public User retriveUser(@PathVariable int id) throws UserNotFoundException {
		User user = service.findOne(id);
		if(user==null){
			throw new UserNotFoundException("id-"+ id);
		}
		return user;
	}
	
	// Delete Method
	@DeleteMapping(path="/users/{id}")
	public void deleteUser(@PathVariable int id) throws UserNotFoundException {
		User user = service.deleteById(id);
		if(user==null){
			throw new UserNotFoundException("id-"+ id);
		}
		
		// "all-users", SERVER_PATH + "/users"
		// retrieveAllUsers
//		Resource<User> resource = new Resource<User>(user);
//		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retriveAllUsers());
//		resource.add(linkTo.withRel("all-users"));
		
		// HATEOAS
		
	}
	
	// Created
	// input - details of user
	// output - Created & return the created URI
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
		User savedUser = service.save(user);
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
}
