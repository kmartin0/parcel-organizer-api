package com.km.parceltracker.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TargetError {
	// Key of the field.
	private final String target;

	// Error message describing the error to an end user.
	private final String error;
}
