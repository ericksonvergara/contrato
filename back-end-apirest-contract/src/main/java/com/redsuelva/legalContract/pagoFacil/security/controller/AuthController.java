package com.redsuelva.legalContract.pagoFacil.security.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import com.redsuelva.legalContract.pagoFacil.dto.RecoverPassword;
import com.redsuelva.legalContract.pagoFacil.entity.Contract;
import com.redsuelva.legalContract.pagoFacil.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.redsuelva.legalContract.pagoFacil.dto.Message;
import com.redsuelva.legalContract.pagoFacil.security.dto.JwtDto;
import com.redsuelva.legalContract.pagoFacil.security.dto.LoginUser;
import com.redsuelva.legalContract.pagoFacil.security.dto.NewUser;
import com.redsuelva.legalContract.pagoFacil.security.entity.Role;
import com.redsuelva.legalContract.pagoFacil.security.entity.Users;
import com.redsuelva.legalContract.pagoFacil.security.enums.RoleName;
import com.redsuelva.legalContract.pagoFacil.security.jwt.JwtProvider;
import com.redsuelva.legalContract.pagoFacil.security.repository.UserRepository;
import com.redsuelva.legalContract.pagoFacil.security.service.RoleService;
import com.redsuelva.legalContract.pagoFacil.security.service.UserService;




@CrossOrigin( origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserService userService;

	@Autowired
	ContractService contractService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	UserRepository userRepository;
	
////	@PreAuthorize("hasRole('SUPER_ADMIN')")
//	@PostMapping("/nuevo")
//    public ResponseEntity<?> nuevo(@Valid @RequestBody NewUser newUser, BindingResult bindingResult){
//        if(bindingResult.hasErrors())
//            return new ResponseEntity(new Mensaje("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
//        if(userService.existsByUserName(newUser.getUserName()))
//            return new ResponseEntity(new Mensaje("El Nombre del usuario ya existe"), HttpStatus.BAD_REQUEST);
//        
//        Users user =
//                new Users(newUser.getName(), newUser.getUserName(),
//                        passwordEncoder.encode(newUser.getPassword()));
//        Set<Role> roles = new HashSet<>();
//        roles.add(roleService.getByRoleName(RoleName.ROLE_GESTOR).get());
//        if(newUser.getRoles().contains("admin"))
//            roles.add(roleService.getByRoleName(RoleName.ROLE_ADMIN).get());
//        user.setRoles(roles);
//        userService.save(user);
//        return new ResponseEntity(new Mensaje("usuario guardado admin"), HttpStatus.CREATED);
//    }
	
	@PostMapping("/register")
    public ResponseEntity<?> nuevo(@Valid  @RequestBody NewUser newUser, BindingResult bindingResult){


        if(bindingResult.hasErrors()) {
			return new ResponseEntity(new Message("campos mal puestos"), HttpStatus.BAD_REQUEST);
		}

        if(userService.existsByDni(newUser.getDni())){
			return new ResponseEntity(new Message("La cedula del usuario ya existe"), HttpStatus.BAD_REQUEST);
		}
		Contract  contract = contractService.findBydni(newUser.getDni());
		if(contract == null){
			return new ResponseEntity(new Message("Usuario no tiene contrato registrado"), HttpStatus.BAD_REQUEST);
		}

        Users user = new Users(newUser.getDni(), newUser.getNames(), newUser.getSurNames(),
				newUser.getExpeditionDate(), newUser.getPhone(), passwordEncoder.encode(newUser.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByRoleName(RoleName.ROLE_CLIENT).get());
        if(newUser.getRoles().contains("admin"))
        	roles.add(roleService.getByRoleName(RoleName.ROLE_ADMIN).get());
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity(new Message("usuario registrado"), HttpStatus.CREATED);
    }

	@PostMapping("/registerAdmin")
	public ResponseEntity<?> nuevoSuperAdmin(@Valid  @RequestBody NewUser newUser, BindingResult bindingResult){


		if(bindingResult.hasErrors()) {
			return new ResponseEntity(new Message("campos mal puestos"), HttpStatus.BAD_REQUEST);
		}

		if(userService.existsByDni(newUser.getDni())){
			return new ResponseEntity(new Message("La cedula del usuario ya existe"), HttpStatus.BAD_REQUEST);
		}

		Users user = new Users(newUser.getDni(), newUser.getNames(), newUser.getSurNames(),
				newUser.getExpeditionDate(), newUser.getPhone(), passwordEncoder.encode(newUser.getPassword()));
		Set<Role> roles = new HashSet<>();
		roles.add(roleService.getByRoleName(RoleName.ROLE_CLIENT).get());
		if(newUser.getRoles().contains("admin"))
			roles.add(roleService.getByRoleName(RoleName.ROLE_ADMIN).get());
		user.setRoles(roles);
		userService.save(user);
		return new ResponseEntity(new Message("usuario registrado"), HttpStatus.CREATED);
	}


	 @PostMapping("/login")
	    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){
		 System.out.println("--------->>>>>>>>>>>>login<<<<<<<<<<---------------------\n");
		    	 Optional<Users>  user= userService.getByDni(loginUser.getDni());

		 if(!user.isPresent()) {
			 return new ResponseEntity(new Message("Usuario No existe"), HttpStatus.BAD_REQUEST);
		 }
		      if(!user.get().isStateUser()) {
		    	  return new ResponseEntity(new Message("Usuario Inactivo"), HttpStatus.BAD_REQUEST);
		      }

	        if(bindingResult.hasErrors())
	            return new ResponseEntity(new Message("campos mal puestos"), HttpStatus.BAD_REQUEST);
	        Authentication authentication =
	                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getDni(), loginUser.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        String jwt = jwtProvider.generateToken(authentication);
	        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
	        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
	        
	        int id =userRepository.findByDni(userDetails.getUsername()).get().getId();
	        jwtDto.setId(id);
	        System.out.print("este es el nuevo longin"+id);
	        
	        
	        return new ResponseEntity(jwtDto, HttpStatus.OK);
	    }


	@PostMapping("/verifyuser")
	public ResponseEntity<?> verifyUser(@Valid @RequestBody RecoverPassword recoverPassword) {
		boolean isExist = userService.verifyUser(recoverPassword.getDni(), recoverPassword.getExpeditionDate());

		if (isExist) {
			return new ResponseEntity(new Message("Usuario existe"), HttpStatus.OK);
		} else {
			return new ResponseEntity(new Message("Datos incorrectos"), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/UpdateUser")
	public ResponseEntity<?> UpdatePassword(@Valid @RequestBody RecoverPassword recoverPassword) {

		userService.RecoverPassword(recoverPassword.getDni(), passwordEncoder.encode(recoverPassword.getPassword()));

		return new ResponseEntity(new Message("Datos actualizados"), HttpStatus.OK);
	}
	
	
	

}
