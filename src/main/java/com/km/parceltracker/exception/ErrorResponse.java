package com.km.parceltracker.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
class ErrorResponse implements Serializable {
	// The HTTP Status Code
	private int code;

	// A developer-facing human-readable error message in English.
	private String message;

	// The Status
	private String status;

	// (Optional) Additional error information that the client code can use to handle
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private TargetError[] details;

	ErrorResponse(ApiErrorCode apiErrorCode, String message, TargetError... details) {
		this.code = apiErrorCode.getHttpStatus().value();
		this.message = message;
		this.status = apiErrorCode.name();
		this.details = details;
	}

	ErrorResponse(ApiErrorCode apiErrorCode, String message) {
		this.code = apiErrorCode.getHttpStatus().value();
		this.message = message;
		this.status = apiErrorCode.name();
	}
}
