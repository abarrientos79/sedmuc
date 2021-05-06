package com.sedmuc.init.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sedmuc.init.entitys.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{

}