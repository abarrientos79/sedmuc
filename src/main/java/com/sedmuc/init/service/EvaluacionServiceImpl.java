package com.sedmuc.init.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;	
import org.springframework.stereotype.Service;

import com.sedmuc.init.dto.ChangePasswordForm;
import com.sedmuc.init.entitys.Evaluacion;
import com.sedmuc.init.entitys.Evaluacion_Estado;
import com.sedmuc.init.entitys.User;
import com.sedmuc.init.repository.AreaRepository;
import com.sedmuc.init.repository.EvaluacionRepository;
import com.sedmuc.init.repository.Evaluacion_EstadoRepository;
import com.sedmuc.init.repository.UserRepository;

@Service
//Implementacion de la capa de servicios de la aplicación
public class EvaluacionServiceImpl implements EvaluacionService{

	@Autowired
	EvaluacionRepository evaluacionRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Evaluacion_EstadoRepository evaluacion_EstadoRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Iterable<Evaluacion> getAllEvaluaciones(){
		return evaluacionRepository.findAll();
	}
	
	
	public User findOneByUsername(String user){
		return userRepository.findOneByUsername(user);
	}

	public Iterable<User> findAllUsers(){
		return userRepository.findAll();
	}
	
	public Iterable<Evaluacion_Estado> findAllEvaluacion_Estado() {
		return evaluacion_EstadoRepository.findAll();
	}

	@Override
	public Evaluacion createEvaluacion(Evaluacion evaluacion) throws Exception {
		evaluacion = evaluacionRepository.save(evaluacion);
		return evaluacion;
	}
	

	@Override
	public Evaluacion getEvaluacionById(Long id) throws Exception {
		Evaluacion evaluacion = evaluacionRepository.findById(id).orElseThrow(() -> new Exception("La evaluacion no existe"));
		return evaluacion;
	}


	@Override
	public Evaluacion updateEvaluacion(Evaluacion fromEvaluacion) throws Exception {
		Evaluacion toEvaluacion = getEvaluacionById(fromEvaluacion.getId());
		mapEvaluacion(fromEvaluacion, toEvaluacion);
		return evaluacionRepository.save(toEvaluacion);
	}
	
	/**
	 * Map everythin but the password.
	 * @param from
	 * @param to
	 */
	protected void mapEvaluacion(Evaluacion from,Evaluacion to) {
		to.setSecuencia(from.getSecuencia());
		to.setEvaluador(from.getEvaluador());
		to.setEvaluado(from.getEvaluado());
		to.setNota_final(from.getNota_final());
		to.setEstado_id(from.getEstado_id());
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteEvaluacion(Long id) throws Exception {
		Evaluacion evaluacion = evaluacionRepository.findById(id)
				.orElseThrow(() -> new Exception("User no encontrado en deleteUser -"+this.getClass().getName()));

		evaluacionRepository.delete(evaluacion);
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
