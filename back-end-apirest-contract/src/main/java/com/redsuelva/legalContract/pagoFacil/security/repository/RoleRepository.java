package com.redsuelva.legalContract.pagoFacil.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redsuelva.legalContract.pagoFacil.security.entity.Role;
import com.redsuelva.legalContract.pagoFacil.security.enums.RoleName;



@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Optional<Role> findByRoleName(RoleName roleName);
	

}
