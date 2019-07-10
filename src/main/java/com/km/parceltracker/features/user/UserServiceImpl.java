package com.km.parceltracker.features.user;

import com.km.parceltracker.exception.ResourceAlreadyExistsException;
import com.km.parceltracker.exception.ResourceNotFoundException;
import com.km.parceltracker.security.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UserServiceImpl implements IUserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Validated({User.Create.class})
	public User saveUser(User user) {
		// Validate if the user doesn't exist yet. Or throw resource exists exception.
		userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
			throw new ResourceAlreadyExistsException(User.class, "username", u.getUsername());
		});

		// Encrypt the user password.
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// Save and return the user.
		return userRepository.save(user);
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public User getUserByAuthentication() {
		// Get the authenticated user id.
		Long authenticatedUserId = SecurityHelper.getPrincipal().getId();

		// Return the user or throw resource not found exception.
		return userRepository.findById(authenticatedUserId)
				.orElseThrow(() -> new ResourceNotFoundException(User.class, authenticatedUserId));
	}

	@Override
	@Validated({User.Update.class})
	@PreAuthorize("isAuthenticated()")
	public User updateUser(User user) {
		// Get the authenticated user id.
		Long authenticatedUserId = SecurityHelper.getPrincipal().getId();

		// Get the user that needs to be updated. Or throw resource not found exception.
		User userToUpdate = userRepository.findById(authenticatedUserId)
				.orElseThrow(() -> new ResourceNotFoundException(User.class, authenticatedUserId));

		// Don't allow for the id to be changed.
		user.setId(authenticatedUserId);

		// If password was changed encode it. Otherwise keep the old password.
		if (user.getPassword() == null) user.setPassword(userToUpdate.getPassword());
		else user.setPassword(passwordEncoder.encode(user.getPassword()));

		// Save and return the updated user.
		return userRepository.save(user);
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public void deleteUser() {
		// Get the authenticated user id.
		Long authenticatedUserId = SecurityHelper.getPrincipal().getId();

		// Get the user that needs to be deleted. Or throw resource not found exception.
		User userToDelete = userRepository.findById(authenticatedUserId)
				.orElseThrow(() -> new ResourceNotFoundException(User.class, authenticatedUserId));

		// Delete the user
		userRepository.delete(userToDelete);
	}

}
