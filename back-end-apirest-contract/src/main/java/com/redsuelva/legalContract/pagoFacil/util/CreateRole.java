package com.redsuelva.legalContract.pagoFacil.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.redsuelva.legalContract.pagoFacil.security.entity.Role;
import com.redsuelva.legalContract.pagoFacil.security.enums.RoleName;
import com.redsuelva.legalContract.pagoFacil.security.service.RoleService;


@Component
public class CreateRole implements CommandLineRunner {

	  @Autowired
	 RoleService roleService;
	 
	@Override
	public void run(String... args) throws Exception {
	/*	Role roleAdmin = new Role(RoleName.ROLE_ADMIN);
		Role roleClient = new Role(RoleName.ROLE_CLIENT);
        roleService.save(roleAdmin);
        roleService.save(roleClient);*/
		
	}

}
