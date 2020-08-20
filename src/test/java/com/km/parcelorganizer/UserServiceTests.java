package com.km.parcelorganizer;

import com.km.parcelorganizer.exception.ForbiddenException;
import com.km.parcelorganizer.exception.ResourceAlreadyExistsException;
import com.km.parcelorganizer.features.user.password.ChangePasswordDto;
import com.km.parcelorganizer.features.user.User;
import com.km.parcelorganizer.features.user.UserRepository;
import com.km.parcelorganizer.features.user.UserServiceImpl;
import com.km.parcelorganizer.security.UserPrincipal;
import com.km.parcelorganizer.util.MessageResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@SpringBootTest
class UserServiceTests {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private MessageResolver messageResolver;

	@InjectMocks
	private UserServiceImpl userService;

	@Autowired
	private Validator validator;

	@Test
	void testSaveUser_isSaved() {
		User user = new User();
		user.setEmail("email@gmail.com");
		user.setName("Jason");
		user.setPassword("pass");
		Mockito.when(passwordEncoder.encode(any())).thenReturn("encryptedPassword");

		userService.saveUser(user);

		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, User.Create.class);
		Assertions.assertEquals(0, constraintViolations.size());
		Assertions.assertEquals("encryptedPassword", user.getPassword());
		Mockito.verify(userRepository, Mockito.times(1)).save(user);
	}

	@Test
	void testSaveExistingUserEmail_throwsResourceAlreadyExistsException() {
		String existingEmail = "email@gmail.com";
		User existingUser = new User();
		existingUser.setEmail(existingEmail);

		Mockito.when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(Optional.of(new User()));
		Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> userService.saveUser(existingUser));
	}

	@Test
	void testUpdateUser_isUpdated() {
		User authenticatedUser = setAuthenticatedUser();
		User updatedUser = new User();
		updatedUser.setPassword("pass");
		updatedUser.setName("newName");
		updatedUser.setEmail("newEmail@gmail.com");

		Mockito.when(passwordEncoder.matches(authenticatedUser.getPassword(), updatedUser.getPassword())).thenReturn(true);

		userService.updateUser(updatedUser);
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(updatedUser, User.Update.class);
		Assertions.assertEquals(0, constraintViolations.size());
		Mockito.verify(userRepository, Mockito.times(1)).save(updatedUser);
	}

	@Test
	void testUpdateUserNotUpdatingEmail_findByEmailNotQueried() {
		User authenticatedUser = setAuthenticatedUser();
		User updatedUser = new User();
		updatedUser.setPassword("pass");
		updatedUser.setName("newName");
		updatedUser.setEmail(authenticatedUser.getEmail());

		Mockito.when(passwordEncoder.matches(authenticatedUser.getPassword(), updatedUser.getPassword())).thenReturn(true);

		userService.updateUser(updatedUser);

		Mockito.verify(userRepository, Mockito.times(0)).findByEmail(any());
	}

	@Test
	void testUpdateUserToExistingEmail_throwsResourceAlreadyExistsException() {
		User authenticatedUser = setAuthenticatedUser();
		User updatedUser = new User();
		updatedUser.setPassword("pass");
		updatedUser.setName("newName");
		updatedUser.setEmail("newEmail@gmail.com");

		Mockito.when(userRepository.findByEmail("newEmail@gmail.com")).thenReturn(Optional.of(new User()));
		Mockito.when(passwordEncoder.matches(authenticatedUser.getPassword(), updatedUser.getPassword())).thenReturn(true);
		Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> userService.updateUser(updatedUser));
	}

	@Test
	void testUpdateUserInvalidPassword_throwsForbiddenException() {
		User authenticatedUser = setAuthenticatedUser();
		User updatedUser = new User();
		updatedUser.setPassword("invalidPassword");
		updatedUser.setName("newName");
		updatedUser.setEmail("newEmail@gmail.com");

		Mockito.when(passwordEncoder.matches(authenticatedUser.getPassword(), updatedUser.getPassword())).thenReturn(false);

		Assertions.assertThrows(ForbiddenException.class, () -> userService.updateUser(updatedUser));
	}

	@Test
	void testDeleteUser_isDeleted() {
		User authenticatedUser = setAuthenticatedUser();
		userService.deleteUser();
		Mockito.verify(userRepository, Mockito.times(1)).delete(authenticatedUser);
	}

	@Test
	void testChangePassword_isChanged() {
		User authenticatedUser = setAuthenticatedUser();
		ChangePasswordDto changePasswordDto = new ChangePasswordDto();
		changePasswordDto.setCurrentPassword(authenticatedUser.getPassword());
		changePasswordDto.setNewPassword("newPassword");

		Mockito.when(passwordEncoder.matches(authenticatedUser.getPassword(), changePasswordDto.getCurrentPassword())).thenReturn(true);
		Mockito.when(passwordEncoder.encode(changePasswordDto.getNewPassword())).thenReturn("newEncryptedPassword");

		userService.changePassword(changePasswordDto);

		User expectedUser = new User();
		expectedUser.setId(authenticatedUser.getId());
		expectedUser.setName(authenticatedUser.getName());
		expectedUser.setEmail(authenticatedUser.getEmail());
		expectedUser.setPassword("newEncryptedPassword");

		Mockito.verify(userRepository, Mockito.times(1)).save(expectedUser);
	}

	@Test
	void testChangePasswordInvalidOldPassword_throwsForbiddenException() {
		User authenticatedUser = setAuthenticatedUser();
		ChangePasswordDto changePasswordDto = new ChangePasswordDto();
		changePasswordDto.setCurrentPassword("InvalidPassword");
		changePasswordDto.setNewPassword("newPassword");

		Mockito.when(passwordEncoder.matches(authenticatedUser.getPassword(), changePasswordDto.getCurrentPassword())).thenReturn(false);

		Assertions.assertThrows(ForbiddenException.class, () -> userService.changePassword(changePasswordDto));
	}

	@Test
	void testGetUserByAuthentication_returnsAuthenticatedUser() {
		User authenticatedUser = setAuthenticatedUser();
		Assertions.assertEquals(authenticatedUser, userService.getUserByAuthentication());
	}

	/**
	 * Helper method to inject a principal user authentication in Spring Security Context.
	 *
	 * @return The authenticated user.
	 */
	private User setAuthenticatedUser() {
		User user = new User();
		user.setPassword("pass");
		user.setName("Jason");
		user.setEmail("email");
		user.setId(1L);

		Authentication auth = Mockito.mock(Authentication.class);

		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
		SecurityContextHolder.setContext(securityContext);

		UserPrincipal userPrincipal = new UserPrincipal(user);
		Mockito.when(auth.getPrincipal()).thenReturn(userPrincipal);

		return user;
	}

}
