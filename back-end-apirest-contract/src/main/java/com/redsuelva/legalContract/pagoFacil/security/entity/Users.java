package com.redsuelva.legalContract.pagoFacil.security.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Users {
 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@NotNull
	@Column(unique = true)
    private String dni;
	
	@NotNull
    private String names;

	@NotNull
	private String surNames;

	@NotNull
	private String expeditionDate;

	@NotNull
    private String phone;

	@NotNull
	private String password;
	
	@NotNull
	private boolean stateUser = true;
	
	@NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

	public Users() {
	}


	public Users(String dni, String names, String surNames, String expeditionDate, String phone, String password) {
		this.dni = dni;
		this.names = names;
		this.surNames = surNames;
		this.expeditionDate = expeditionDate;
		this.phone = phone;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getSurnames() {
		return surNames;
	}

	public void setSurnames(String surnames) {
		this.surNames = surnames;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean isStateUser() {
		return stateUser;
	}

	public void setStateUser(boolean stateUser) {
		this.stateUser = stateUser;
	}


	@Override
	public String toString() {
		return "Users{" +
				"id=" + id +
				", dni='" + dni + '\'' +
				", names='" + names + '\'' +
				", surNames='" + surNames + '\'' +
				", expeditionDate='" + expeditionDate + '\'' +
				", phone='" + phone + '\'' +
				", password='" + password + '\'' +
				", stateUser=" + stateUser +
				", roles=" + roles +
				'}';
	}
}
