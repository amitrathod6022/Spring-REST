package com.amit.springrest;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amit.springrest.dto.UserDTO;
import com.amit.springrest.exception.UserErrorResponse;
import com.amit.springrest.exception.UserNotFoundException;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/rest/api")
public class UserRestController {

	private static final List<UserDTO> USERS = new ArrayList<>();

	@PostConstruct
	private void loadData() {
		UserDTO user1 = new UserDTO(1, "Amit", "Rathod");
		UserDTO user2 = new UserDTO(2, "John", "Doe");
		UserDTO user3 = new UserDTO(3, "Jane", "Doe");

		USERS.add(user1);
		USERS.add(user2);
		USERS.add(user3);
	}

	@GetMapping("/users")
	private List<UserDTO> getUserList() {
		return USERS;

	}

	@GetMapping("/users/{userId}")
	private UserDTO getUser(@PathVariable int userId) {
		if (userId < 0 || userId >= USERS.size()) {
			System.out.println("--------");
			throw new UserNotFoundException("User not found -" + userId);
		}
		return USERS.get(userId);
	}

	@ExceptionHandler(UserNotFoundException.class)
	private ResponseEntity<UserErrorResponse> studentNotFound(UserNotFoundException ex) {
		UserErrorResponse error = new UserErrorResponse();
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(ex.getMessage());
		error.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
}
