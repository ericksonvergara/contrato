package com.redsuelva.legalContract.pagoFacil.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.redsuelva.legalContract.pagoFacil.security.entity.UserMain;
import com.redsuelva.legalContract.pagoFacil.security.entity.Users;





@Service
public class UserDetailsServiceImp  implements UserDetailsService{
	
	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userService.getByDni(username).get();
		  return UserMain.build(user);
	}

}
