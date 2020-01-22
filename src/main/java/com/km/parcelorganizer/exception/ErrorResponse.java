package com.km.parcelorganizer.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
class ErrorResponse implements Serializable {

	// The Status
	private String error;

	// A developer-facing human-readable error description in English.
	private String description;

	// The HTTP Status Code
	private int code;

	// (Optional) Additional error information that the client code can use to handle
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private TargetError[] details;

	ErrorResponse(ApiErrorCode apiErrorCode, String description, TargetError... details) {
		this.code = apiErrorCode.getHttpStatus().value();
		this.description = description;
		this.error = apiErrorCode.name();
		this.details = details;
	}

	ErrorResponse(ApiErrorCode apiErrorCode, String description) {
		this.code = apiErrorCode.getHttpStatus().value();
		this.description = description;
		this.error = apiErrorCode.name();
	}
}
