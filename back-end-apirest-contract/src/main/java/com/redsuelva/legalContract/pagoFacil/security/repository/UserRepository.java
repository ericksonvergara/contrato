package com.redsuelva.legalContract.pagoFacil.security.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redsuelva.legalContract.pagoFacil.security.entity.Users;

import javax.transaction.Transactional;


@Repository
public interface UserRepository  extends JpaRepository<Users, Integer>{
	
	Optional<Users> findByDni(String dni);
	
	List<Users> findAll();

	boolean existsByDni(String dni);

	
	boolean existsByStateUser(String dni);

}
