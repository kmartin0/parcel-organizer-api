package com.km.parceltracker.features.user;

import com.km.parceltracker.util.Endpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

	private final IUserService userService;

	@Autowired
	public UserController(IUserService userService) {
		this.userService = userService;
	}

	@PostMapping(path = Endpoints.SAVE_USER, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public User registerCompany(@Validated({User.Create.class}) @RequestBody User user) {

		return userService.saveUser(user);
	}

	@GetMapping(path = Endpoints.GET_USER_BY_ID)
	@ResponseStatus(HttpStatus.OK)
	public User getCompanyById(@PathVariable Long userId) {

		return userService.getUserById(userId);
	}

	@PutMapping(path = Endpoints.UPDATE_USER)
	@ResponseStatus(HttpStatus.OK)
	public User updateCompany(@Validated({User.Update.class}) @RequestBody User user) {

		return userService.updateUser(user);
	}

	@DeleteMapping(path = Endpoints.DELETE_USER)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable Long userId) {
		userService.deleteUser(userId);
	}

}
