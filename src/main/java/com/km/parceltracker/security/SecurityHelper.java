package com.km.parceltracker.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityHelper {

	public static UserPrincipal getPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return (UserPrincipal) authentication.getPrincipal();
	}

	public static void validatePrincipalIsUser(Long userId) {
		if (!getPrincipal().getId().equals(userId)) {
			throw new AccessDeniedException("Unauthorized to make this action.");
		}
	}

}
