package com.sedmuc.init.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.sedmuc.init.entitys.Evaluacion;
import com.sedmuc.init.repository.AreaRepository;
import com.sedmuc.init.service.EvaluacionService;

@Controller
public class EvaluacionController {
	//@Autowired
	//RoleRepository roleRepository;
	
	@Autowired
	AreaRepository areaRepository;
	
	
	@Autowired 
	EvaluacionService evaluacionService;
	
	
	/*
	 * Alta de evaluacion
	 */
	@GetMapping("/evaluacionForm")
	public String getEvaluacionForm(Model model) {
		model.addAttribute("evaluacionForm", new Evaluacion());
		//model.addAttribute("areas",areaRepository.findAll());
		//model.addAttribute("roles",roleRepository.findAll());
		//model.addAttribute("userLogeado", evaluacionService.findOneByUsername('agomez');
		//Trae las Evaluacion que lugo utiliza la lista
		model.addAttribute("evaluacionList", evaluacionService.getAllEvaluaciones());
		model.addAttribute("listaUsuarios", evaluacionService.findAllUsers());
		model.addAttribute("listaEstados", evaluacionService.findAllEvaluacion_Estado());
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
					//model.addAttribute("userLogeado", evaluacionService.findOneByUsername("agomez"));
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
			model.addAttribute("listaUsuarios", evaluacionService.findAllUsers());
			model.addAttribute("listaEstados", evaluacionService.findAllEvaluacion_Estado());
			
			
			return "evaluacion-form/evaluacion-view";
	}
	
	/*
	 * Utilizado en la edicion de datos de la evaluacion
	 */
	@GetMapping("/editEvaluacion/{id}")
	public String getEditEvaluacion(Model model, @PathVariable(name="id") Long id) throws Exception {
		Evaluacion evaluacion = evaluacionService.getEvaluacionById(id);
		model.addAttribute("evaluacionList", evaluacionService.getAllEvaluaciones());
		model.addAttribute("evaluacionForm", evaluacion);
		model.addAttribute("listaUsuarios", evaluacionService.findAllUsers());
		model.addAttribute("listaEstados", evaluacionService.findAllEvaluacion_Estado());
		model.addAttribute("formTab","active");//Activa el tab del formulario.
		model.addAttribute("editMode",true);//parametro de edicion
		
		
		return "evaluacion-form/evaluacion-view";
	}
	
	/*
	 * Utilizado en la edicion de la evaluacion al enviar a grabar
	 */
	@PostMapping("/editEvaluacion")
	public String postEditUserForm(@Valid @ModelAttribute("evaluacionForm")Evaluacion evaluacion, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("evaluacionForm", evaluacion);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode","true");
		}else {
			try {
				evaluacionService.updateEvaluacion(evaluacion);
				model.addAttribute("evaluacionForm", new Evaluacion());
				model.addAttribute("listTab","active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("evaluacionForm", evaluacion);
				model.addAttribute("formTab","active");
				model.addAttribute("evaluacionList", evaluacionService.getAllEvaluaciones());
				model.addAttribute("listaUsuarios", evaluacionService.findAllUsers());
				model.addAttribute("listaEstados", evaluacionService.findAllEvaluacion_Estado());
				model.addAttribute("editMode","true");
		
			}
		}
		
		model.addAttribute("evaluacionList", evaluacionService.getAllEvaluaciones());
		model.addAttribute("listaUsuarios", evaluacionService.findAllUsers());
		model.addAttribute("listaEstados", evaluacionService.findAllEvaluacion_Estado());
		return "evaluacion-form/evaluacion-view";
		
	}
	
	/*
	 * Utilizado en el boton cacelar del usuario
	 */
	@GetMapping("/editEvaluacion/cancel")
	public String cancelEditEvaluacion(ModelMap model) {
		return "redirect:/evaluacionForm";
	}
	
	/*
	 * Utilizado en el boton eliminar Evaluacion
	 */
	@GetMapping("/deleteEvaluacion/{id}")
	public String deleteEvaluacion(Model model, @PathVariable(name="id") Long id) {
		try {
			evaluacionService.deleteEvaluacion(id);
		} catch (Exception e) {
			model.addAttribute("deleteError","La evaluacion no pudo ser eliminada.");
		}
		return getEvaluacionForm(model);
	}


	
}
