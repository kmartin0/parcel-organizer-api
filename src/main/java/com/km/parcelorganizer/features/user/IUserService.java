package com.km.parcelorganizer.features.user;

import javax.validation.Valid;

public interface IUserService {

	User saveUser(@Valid User user);

	User getUserByAuthentication();

	User updateUser(@Valid User user);

	void deleteUser();

}
