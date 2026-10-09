package nl.kmartin.parcelorganizer.api.features.user;

import nl.kmartin.parcelorganizer.api.features.user.password.ChangePasswordDto;
import nl.kmartin.parcelorganizer.api.features.user.password.ForgotPasswordDto;
import nl.kmartin.parcelorganizer.api.features.user.password.ResetPasswordDto;

import javax.validation.Valid;

public interface IUserService {

	User saveUser(@Valid User user);

	User getUserByAuthentication();

	User updateUser(@Valid User user);

	void deleteUser();

	void changePassword(@Valid ChangePasswordDto changePasswordDto);

	void forgotPassword(ForgotPasswordDto forgotPasswordDto);

	void resetPassword(ResetPasswordDto resetPasswordDto);
}
