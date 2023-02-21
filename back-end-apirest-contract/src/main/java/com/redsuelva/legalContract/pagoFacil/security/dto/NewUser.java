package com.redsuelva.legalContract.pagoFacil.security.dto;

import java.util.HashSet;
import java.util.Set;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class NewUser {
	
	@NotBlank
    private String dni;
	
    @NotBlank
    private String names;
    
    @NotBlank
    private String surNames;

	@NotBlank
	private String expeditionDate;

	@NotBlank
	private String phone;
    @NotBlank
    private String password;
    private Set<String> roles = new HashSet<>();

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getSurNames() {
		return surNames;
	}

	public void setSurNames(String surNames) {
		this.surNames = surNames;
	}

	public String getExpeditionDate() {
		return expeditionDate;
	}

	public void setExpeditionDate(String expeditionDate) {
		this.expeditionDate = expeditionDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {

		return password;
	}
	public void setPassword(String password) {

		this.password = password;
	}
	public Set<String> getRoles() {

		return roles;
	}
	public void setRoles(Set<String> roles) {

		this.roles = roles;
	}
    
    
    
    

}
