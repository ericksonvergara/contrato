package com.redsuelva.legalContract.pagoFacil.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.redsuelva.legalContract.pagoFacil.security.enums.RoleName;




@Entity
public class Role {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    public Role() {
    }

   

    public Role( @NotNull RoleName roleName) {
		this.roleName = roleName;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public RoleName getRoleName() {
		return roleName;
	}



	public void setRoleName(RoleName roleName) {
		this.roleName = roleName;
	}



}
