package com.km.parceltracker.security;

import com.km.parceltracker.features.user.User;
import com.km.parceltracker.features.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String s) {
		User user = userRepository.findByEmail(s).orElseThrow(() -> new InvalidGrantException("Bad credentials"));

		return new UserPrincipal(
				user
		);
	}
}
