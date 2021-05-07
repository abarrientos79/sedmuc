package com.sedmuc.init.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sedmuc.init.entitys.Evaluacion;
import com.sedmuc.init.repository.AreaRepository;
import com.sedmuc.init.repository.RoleRepository;
import com.sedmuc.init.service.EvaluacionService;

@Controller
public class EvaluacionController {
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	AreaRepository areaRepository;
	
	@Autowired 
	EvaluacionService evaluacionService;
	
	/*
	 * Alta de evaluacion
	 */
	@GetMapping("/evaluacionForm")
	public String getUserEvaluacion(Model model) {
		model.addAttribute("evaluacionForm", new Evaluacion());
		//model.addAttribute("areas",areaRepository.findAll());
		//model.addAttribute("roles",roleRepository.findAll());
		//model.addAttribute("evaluacionList", evaluacionService.getAllEvaluaciones());
		model.addAttribute("listTab","active");
		return "evaluacion-form/evaluacion-view";
	}
	
	/*
	 * Utilizado en alta de la evaluacion  cuando se evia a grabar por post
	 */
	@PostMapping("/evaluacionForm")
	public String postEvaluacionForm(@Valid @ModelAttribute("evaluacionForm")Evaluacion evaluacion, BindingResult result, ModelMap model) {
			if(result.hasErrors()) {
				model.addAttribute("evaluacionForm", evaluacion);
				model.addAttribute("formTab","active");
			}else {
				try {//Aca tendras error porque este metodo no existe, pero lo crearemos en la siguiente seccion.
					evaluacionService.createEvaluacion(evaluacion);
					model.addAttribute("evaluacionForm", new Evaluacion());
					model.addAttribute("listTab","active");
				} catch (Exception e) {
					model.addAttribute("formMessageError",e.getMessage());
					model.addAttribute("evaluacionForm", evaluacion); 
					model.addAttribute("formTab","active");
				}
			}

			//model.addAttribute("areas",areaRepository.findAll());
			//model.addAttribute("roles",roleRepository.findAll());
			model.addAttribute("evaluacionList",evaluacionService.getAllEvaluaciones());
			return "evaluacion-form/evaluacion-view";
	}
	
}
