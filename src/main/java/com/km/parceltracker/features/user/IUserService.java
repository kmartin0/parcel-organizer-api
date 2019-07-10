package com.km.parceltracker.features.user;

public interface IUserService {

	User saveUser(User user);

	User getUserById(Long id);

	User updateUser(User user);

	void deleteUser(Long id);

}
