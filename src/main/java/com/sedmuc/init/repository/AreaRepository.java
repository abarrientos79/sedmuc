package com.sedmuc.init.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sedmuc.init.entitys.Area;

@Repository
public interface AreaRepository extends CrudRepository<Area, Long>{

}