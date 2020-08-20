package com.km.parcelorganizer.features.user.password;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResetPasswordDto {
	@NotBlank(groups = {ChangePasswordDto.Valid.class})
	private String token;

	@NotBlank(groups = {ChangePasswordDto.Valid.class})
	private String newPassword;

	public interface Valid {
	}
}
