package com.km.parcelorganizer.features.user;

import com.km.parcelorganizer.exception.ForbiddenException;
import com.km.parcelorganizer.exception.ResourceAlreadyExistsException;
import com.km.parcelorganizer.security.SecurityHelper;
import com.km.parcelorganizer.util.MessageResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
public class UserServiceImpl implements IUserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final MessageResolver messageResolver;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, MessageResolver messageResolver) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.messageResolver = messageResolver;
	}

	@Override
	@Validated({User.Create.class})
	public User saveUser(User user) {
		// Validate if the user doesn't exist yet. Or throw resource exists exception.
		userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
			throw new ResourceAlreadyExistsException(User.class, "email", u.getEmail());
		});

		// Encrypt the user password.
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// Save and return the user.
		return userRepository.save(user);
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public User getUserByAuthentication() {
		// Return the user or throw resource not found exception.
		return SecurityHelper.getPrincipalUser();
	}

	@Override
	@Validated({User.Update.class})
	@PreAuthorize("isAuthenticated()")
	public User updateUser(User user) {
		// Get the user that needs to be updated.
		User userToUpdate = SecurityHelper.getPrincipalUser();

		// Don't allow for the id to be changed.
		user.setId(userToUpdate.getId());

		// Check if the supplied password matches the current password. Else throw ForbiddenException
		if (passwordEncoder.matches(user.getPassword(), userToUpdate.getPassword())) {
			user.setPassword(userToUpdate.getPassword());
		} else {
			throw new ForbiddenException(
					messageResolver.getMessage("message.incorrect.credentials"),
					"password",
					messageResolver.getMessage("message.incorrect.password")
			);
		}

		// If the email is being changed, check if the new email is not already in use.
		if (!user.getEmail().equals(userToUpdate.getEmail())) {
			userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
				throw new ResourceAlreadyExistsException(User.class, "email", u.getEmail());
			});
		}

		// Save and return the updated user.
		return userRepository.save(user);
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public void deleteUser() {
		// Get the user that needs to be deleted.
		User userToDelete = SecurityHelper.getPrincipalUser();

		// Delete the user
		userRepository.delete(userToDelete);
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	@Validated({ChangePasswordDto.Valid.class, User.Create.class})
	public void changePassword(ChangePasswordDto changePasswordDto) {
		// Get the user that needs to be updated.
		User userToUpdate = SecurityHelper.getPrincipalUser();

		// encode and save new password if the old password matches the user password. Else throw forbidden exception.
		if (passwordEncoder.matches(changePasswordDto.getOldPassword(), userToUpdate.getPassword())) {
			userToUpdate.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
			userRepository.save(userToUpdate);
		} else {
			throw new ForbiddenException(
					messageResolver.getMessage("message.incorrect.credentials"),
					"oldPassword",
					messageResolver.getMessage("message.incorrect.password")
			);
		}
	}

}
