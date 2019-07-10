package com.km.parceltracker.features.user;

import com.km.parceltracker.exception.ResourceAlreadyExistsException;
import com.km.parceltracker.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User saveUser(User user) {

		// Validate if the user doesn't exist yet.
		userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
			throw new ResourceAlreadyExistsException(User.class, "username", u.getUsername());
		});

		return userRepository.save(user);
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(User.class, id));
	}

	@Override
	public User updateUser(User user) {

		// Throw not found exception if the user doesn't exist.
		userRepository.findById(user.getId())
				.orElseThrow(() -> new ResourceNotFoundException(User.class, user.getId()));

		return userRepository.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		User userToDelete = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(User.class, id));

		userRepository.delete(userToDelete);
	}

}
