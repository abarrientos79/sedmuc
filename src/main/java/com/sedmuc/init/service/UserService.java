package com.sedmuc.init.service;

import com.sedmuc.init.dto.ChangePasswordForm;
import com.sedmuc.init.entitys.User;

//Capa de servicios de la aplicación
public interface UserService {

	public Iterable<User> getAllUsers();
	
	
	public User findOneByUsername(String username) throws Exception;
		
	public User createUser(User formUser) throws Exception;
	
	public User getUserById(Long id) throws Exception;
	
	public User updateUser(User user) throws Exception;
	
	public void deleteUser(Long id) throws Exception;
	
	public User changePassword(ChangePasswordForm form) throws Exception;
	
}