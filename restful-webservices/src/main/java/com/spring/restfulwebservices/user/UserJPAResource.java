package com.spring.restfulwebservices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
public class UserJPAResource {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping(path="/jpa/users")
	public List<User> retriveAllUsers() {
		System.out.println("Hello");
		return userRepository.findAll();
	}
	
	@GetMapping(path="/jpa/users/{id}")
	public User retriveUser(@PathVariable int id) throws UserNotFoundException {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()){
			throw new UserNotFoundException("id-"+ id);
		}
		return user.get();
	}
	
	// Delete Method
	@DeleteMapping(path="/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) throws UserNotFoundException {
		userRepository.deleteById(id);
		
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
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
		User savedUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	
	@GetMapping(path="/jpa/users/{id}/posts")
	public List<Post> retrivePosts(@PathVariable int id) throws UserNotFoundException {
		Optional<User> userOptional = userRepository.findById(id);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		
		return userOptional.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) throws UserNotFoundException{
		Optional<User> userOptional = userRepository.findById(id);
		
		if(!userOptional.isPresent()){
			throw new UserNotFoundException("id-"+id);
		}
		
		User user = userOptional.get();
		post.setUser(user);
		postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(post.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	
}
