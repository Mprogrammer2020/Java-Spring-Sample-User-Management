package com.efx.user.dto;

import javax.validation.constraints.NotNull;

public class ChangePasswordDto {
	
	@NotNull(message = "Current Password is missing.")
	private String currentPassword;
	
	@NotNull(message = "New Password is missing.")
	private String newPassword;

	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
