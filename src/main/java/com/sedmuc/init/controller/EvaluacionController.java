package com.sedmuc.init.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sedmuc.init.dto.ChangePasswordForm;
import com.sedmuc.init.entitys.User;
import com.sedmuc.init.repository.AreaRepository;
import com.sedmuc.init.repository.RoleRepository;
import com.sedmuc.init.service.UserService;

@Controller
public class EvaluacionController {
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	AreaRepository areaRepository;
	
	@Autowired 
	UserService userService;
	
	
	/*
	 * Alta de evaluacion
	 */
	@GetMapping("/evaluacionForm")
	public String getUserForm(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("areas",areaRepository.findAll());
		model.addAttribute("roles",roleRepository.findAll());
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("listTab","active");
		return "evaluacion-form/evaluacion-view";
	}
	
}
