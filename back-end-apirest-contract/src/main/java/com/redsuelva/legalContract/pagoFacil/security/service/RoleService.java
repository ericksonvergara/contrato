package com.redsuelva.legalContract.pagoFacil.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redsuelva.legalContract.pagoFacil.security.entity.Role;
import com.redsuelva.legalContract.pagoFacil.security.enums.RoleName;
import com.redsuelva.legalContract.pagoFacil.security.repository.RoleRepository;




@Service
@Transactional
public class RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	
	 public Optional<Role> getByRoleName(RoleName roleName){

		 return roleRepository.findByRoleName(roleName);
	    }

	    public void save(Role role){

		 roleRepository.save(role);
	    }
	

}
