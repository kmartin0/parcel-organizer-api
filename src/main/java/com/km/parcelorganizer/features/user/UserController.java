package com.km.parcelorganizer.features.user;

import com.km.parcelorganizer.features.user.password.ChangePasswordDto;
import com.km.parcelorganizer.features.user.password.ForgotPasswordDto;
import com.km.parcelorganizer.features.user.password.ResetPasswordDto;
import com.km.parcelorganizer.util.Endpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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
	public User saveUser(@Validated({User.Create.class}) @RequestBody User user) {

		return userService.saveUser(user);
	}

	@GetMapping(path = Endpoints.GET_USER)
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("isAuthenticated()")
	public User getUser() {

		return userService.getUserByAuthentication();
	}

	@PutMapping(path = Endpoints.UPDATE_USER)
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("isAuthenticated()")
	public User updateUser(@Validated({User.Update.class}) @RequestBody User user) {

		return userService.updateUser(user);
	}

	@DeleteMapping(path = Endpoints.DELETE_USER)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("isAuthenticated()")
	public void deleteUser() {

		userService.deleteUser();
	}

	@PostMapping(path = Endpoints.CHANGE_PASSWORD)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("isAuthenticated()")
	public void changePassword(@RequestBody ChangePasswordDto changePasswordDto) {

		userService.changePassword(changePasswordDto);
	}

	@PostMapping(path = Endpoints.FORGOT_PASSWORD)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {

		userService.forgotPassword(forgotPasswordDto);
	}

	@PostMapping(path = Endpoints.RESET_PASSWORD)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {

		userService.resetPassword(resetPasswordDto);
	}

}
