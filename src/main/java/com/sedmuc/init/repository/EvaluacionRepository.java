package com.sedmuc.init.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sedmuc.init.entitys.Evaluacion;

@Repository
public interface EvaluacionRepository extends CrudRepository<Evaluacion, Long>{
	
	//public Optional<Evaluacion> findByUsername(String username);
	
	public Optional<Evaluacion> findById(Long id);

}
