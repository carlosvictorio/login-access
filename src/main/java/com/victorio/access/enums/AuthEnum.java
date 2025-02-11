package com.victorio.access.enums;

public enum AuthEnum {
	ADMIN("admin"),
	USER("user");
	
	private String role;
	
	AuthEnum(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}

}
