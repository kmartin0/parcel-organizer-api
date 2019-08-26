package com.km.parcelorganizer.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {

	// Description
	private String description;

	// the field that causes the exception.
	private String target;

	// The description of what caused the error
	private String targetDescription;

	public ForbiddenException(String description, String target, String targetDescription) {
		this.description = description;
		this.target = target;
		this.targetDescription = targetDescription;
	}

}
