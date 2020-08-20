package com.km.parcelorganizer.features.user;

import com.km.parcelorganizer.features.user.password.ChangePasswordDto;
import com.km.parcelorganizer.features.user.password.ForgotPasswordDto;
import com.km.parcelorganizer.features.user.password.ResetPasswordDto;

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
