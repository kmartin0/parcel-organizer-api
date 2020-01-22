package com.km.parcelorganizer.features.user;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordDto {
	@NotBlank(groups = {Valid.class})
	private String currentPassword;

	@NotBlank(groups = {Valid.class})
	private String newPassword;

	public interface Valid {
	}
}
