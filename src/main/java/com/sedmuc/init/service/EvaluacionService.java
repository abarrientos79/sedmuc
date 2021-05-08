package com.sedmuc.init.service;

import java.util.Optional;

import com.sedmuc.init.dto.ChangePasswordForm;
//import com.sedmuc.init.dto.ChangePasswordForm;
import com.sedmuc.init.entitys.Evaluacion;
import com.sedmuc.init.entitys.User;


//Capa de servicios de la aplicación
public interface EvaluacionService {

	public Iterable<Evaluacion> getAllEvaluaciones();
	
	public User findOneByUsername(String user);
	
	public Iterable<User> findAllUsers();

	public Evaluacion createEvaluacion(Evaluacion formEvaluacion) throws Exception;

	public Evaluacion getEvaluacionById(Long id) throws Exception;

	public Evaluacion updateEvaluacion(Evaluacion evaluacion) throws Exception;
	
	public void deleteEvaluacion(Long id) throws Exception;

	
		
}