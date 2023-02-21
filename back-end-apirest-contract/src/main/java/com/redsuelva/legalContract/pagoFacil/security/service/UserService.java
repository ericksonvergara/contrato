package com.redsuelva.legalContract.pagoFacil.security.service;



import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redsuelva.legalContract.pagoFacil.security.entity.Users;
import com.redsuelva.legalContract.pagoFacil.security.repository.UserRepository;




@Service
@Transactional
public class UserService {


	@Autowired
	UserRepository userRepository;
	
	
	public Optional<Users> getByDni(String dni){
        return userRepository.findByDni(dni);
    }

    public boolean existsByDni(String dni){
        return userRepository.existsByDni(dni);
    }
 

   public Users findById(int id) {
	   
	   Optional<Users> userDb = userRepository.findById(id);
	   
	  if(userDb.isPresent()) {
		  Users user = userDb.get();
		  return user;
	  }
	  return null;
   }
   
  public Users findByDni( String dni) {
	   
	   Optional<Users> userDb = userRepository.findByDni(dni);
	   
	  if(userDb.isPresent()) {
		  Users user = userDb.get();
		  return user;
	  }
	  return null;
   }
    
    public Users save(Users user){
    boolean isUserDb=  userRepository.existsByDni(user.getDni());
	if( isUserDb){
	  return null;
  	}
	return	userRepository.save(user);
    }

    


	public List<Users> getUsers(){
		return userRepository.findAll();
	}
	
	public List<Users> getUsersManager(){	
		List<Users> users	=	userRepository.findAll();
		List<Users> usersManager	=	new ArrayList<>();
		if(users != null) {
			for(int i =0; i<users.size();  i++) {
				if( users.get(i).isStateUser() == true &&   users.get(i).getRoles().size() == 1 ) { 
					usersManager.add(users.get(i));
				}
			}
		}
				
				
				return usersManager;
	}

	public boolean verifyUser(String dni, String dateEX){
		boolean isExist=false;
		Users user =findByDni(dni);
		if(user != null){
			String date =user.getExpeditionDate();
			if(date.equals(dateEX)){
				isExist=true;
			}
		}
		return isExist;
	}

	public String RecoverPassword(String dni, String password){

		String msg="no se actualizo";
		Users user =findByDni(dni);
		if(user != null){
			user.setPassword(password);
			save(user);
		}
		return msg;
	}


}
