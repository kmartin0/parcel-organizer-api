package com.km.parcelorganizer.security;

import com.km.parcelorganizer.features.user.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityHelper {

	public static UserPrincipal getPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return (UserPrincipal) authentication.getPrincipal();
	}

	public static User getPrincipalUser() {
		return getPrincipal().getUser();
	}

	public static void validatePrincipalIsUser(Long userId) {
		if (!getPrincipal().getUser().getId().equals(userId)) {
			throw new AccessDeniedException("Unauthorized to make this action.");
		}
	}

}
