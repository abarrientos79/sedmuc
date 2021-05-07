package com.sedmuc.init.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;	
import org.springframework.stereotype.Service;

import com.sedmuc.init.dto.ChangePasswordForm;
import com.sedmuc.init.entitys.User;
import com.sedmuc.init.repository.AreaRepository;
import com.sedmuc.init.repository.UserRepository;

@Service
//Implementacion de la capa de servicios de la aplicación
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Iterable<User> getAllUsers(){
		return userRepository.findAll();
	}


	private boolean checkUsernameAvailable(User user) throws Exception {
		Optional<User> userFound = userRepository.findByUsername(user.getUsername());
		if (userFound.isPresent()) {
			throw new Exception("Username no disponible");
		}
		return true;
	}
	
	private boolean checkPasswordValid(User user) throws Exception {
		if ( !user.getPassword().equals(user.getConfirmPassword())) {
			throw new Exception("Password y Confirm Password no son iguales");
		}
		return true;
	}


	@Override
	public User createUser(User user) throws Exception {
		if (checkUsernameAvailable(user) && checkPasswordValid(user)) {
			String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(encodePassword);
			user = userRepository.save(user);
		}
		return user;
	}
	
	@Override
	public User getUserById(Long id) throws Exception {
		User user = userRepository.findById(id).orElseThrow(() -> new Exception("El User no existe"));
		return user;
	}


	@Override
	public User updateUser(User fromUser) throws Exception {
		User toUser = getUserById(fromUser.getId());
		mapUser(fromUser, toUser);
		return userRepository.save(toUser);
	}
	
	/**
	 * Map everythin but the password.
	 * @param from
	 * @param to
	 */
	protected void mapUser(User from,User to) {
		to.setAreas(from.getAreas());
		to.setUsername(from.getUsername());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteUser(Long id) throws Exception {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new Exception("User no encontrado en deleteUser -"+this.getClass().getName()));

		userRepository.delete(user);
	}
	
	@Override
	public User changePassword(ChangePasswordForm form) throws Exception {
		User user = getUserById(form.getId());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//if ( !isLoggedUserADMIN() && !user.getPassword().equals(form.getCurrentPassword())) {
		if ( !isLoggedUserADMIN() && !passwordEncoder.matches(form.getCurrentPassword(), user.getPassword()) ) {
				throw new Exception ("Current Password invalido.");
		}

		if( user.getPassword().equals(form.getNewPassword())) {
			throw new Exception ("Nuevo debe ser diferente al password actual.");
		}

		if( !form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception ("Nuevo Password y Current Password no coinciden.");
		}

		String encodePassword = bCryptPasswordEncoder.encode(form.getNewPassword());
		user.setPassword(encodePassword);
		return userRepository.save(user);
	}
	
	private boolean isLoggedUserADMIN() {
		//Obtener el usuario logeado
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDetails loggedUser = null;
		Object roles = null;

		//Verificar que ese objeto traido de sesion es el usuario
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;

			roles = loggedUser.getAuthorities().stream()
					.filter(x -> "ROLE_ADMIN".equals(x.getAuthority())).findFirst()
					.orElse(null); 
		}
		return roles != null ? true : false;
	}
	
	public User getLoggedUser() throws Exception {
		//Obtener el usuario logeado
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDetails loggedUser = null;

		//Verificar que ese objeto traido de sesion es el usuario
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
		}
		
		User myUser = userRepository
				.findByUsername(loggedUser.getUsername()).orElseThrow(() -> new Exception(""));
		
		return myUser;
	}
}

