package com.km.parceltracker.features.user;

public interface IUserService {

	User saveUser(User user);

	User getUserByAuthentication();

	User updateUser(User user);

	void deleteUser();

}
