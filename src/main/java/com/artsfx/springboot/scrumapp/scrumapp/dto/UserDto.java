package com.artsfx.springboot.scrumapp.scrumapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.artsfx.springboot.scrumapp.scrumapp.validation.PasswordMatches;
import com.artsfx.springboot.scrumapp.scrumapp.validation.ValidEmail;

@PasswordMatches
public class UserDto {

	@ValidEmail
	@NotNull
	@NotEmpty
	private String email;
	
	@NotNull
	@NotEmpty
	private String password;
	
	@NotNull
	@NotEmpty
	private String matchingPassword;
	
	public UserDto(String email, String password, String matchingPassword) {
	
		this.email = email;
		this.password = password;
		this.matchingPassword = matchingPassword;
	}
	
	public UserDto() {}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the matchingPassword
	 */
	public String getMatchingPassword() {
		return matchingPassword;
	}

	/**
	 * @param matchingPassword the matchingPassword to set
	 */
	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}
	
	
}
