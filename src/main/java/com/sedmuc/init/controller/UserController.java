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
public class UserController {
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	AreaRepository areaRepository;
	
	@Autowired 
	UserService userService;
	
	/*
	 * Utilizado en loguin
	 */
	@GetMapping({"/","/login"})
	public String index() {
		return "index";
	}
	
	/*
	 * Alta del usuario
	 */
	@GetMapping("/userForm")
	public String getUserForm(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("areas",areaRepository.findAll());
		model.addAttribute("roles",roleRepository.findAll());
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("listTab","active");
		return "user-form/user-view";
	}
	
	/*
	 * Utilizado en alta de usuario para cuando se evia a grabar por post
	 */
	@PostMapping("/userForm")
	public String postUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
			if(result.hasErrors()) {
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
			}else {
				try {//Aca tendras error porque este metodo no existe, pero lo crearemos en la siguiente seccion.
					userService.createUser(user);
					model.addAttribute("userForm", new User());
					model.addAttribute("listTab","active");
				} catch (Exception e) {
					model.addAttribute("formMessageError",e.getMessage());
					model.addAttribute("userForm", user); 
					model.addAttribute("formTab","active");
				}
			}

			model.addAttribute("areas",areaRepository.findAll());
			model.addAttribute("userList",userService.getAllUsers());
			model.addAttribute("roles",roleRepository.findAll());
			return "user-form/user-view";
		}
	
	/*
	 * Utilizado en la edicion de datos de un usuario cando se edita
	 */
	@GetMapping("/editUser/{id}")
	public String getEditUserForm(Model model, @PathVariable(name="id") Long id) throws Exception {
		User user = userService.getUserById(id);
		
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("areas",areaRepository.findAll());
		model.addAttribute("roles",roleRepository.findAll());
		model.addAttribute("userForm", user);
		model.addAttribute("formTab","active");//Activa el tab del formulario.
		model.addAttribute("editMode",true);//Mira siguiente seccion para mas informacion
		model.addAttribute("passwordForm",new ChangePasswordForm(id));
		
		return "user-form/user-view";
	}
	
	
	/*
	 * Utilizado en la edicion del change password por post
	 */
	@PostMapping("/editUser")
	public String postEditUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode","true");
			model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
		}else {
			try {
				userService.updateUser(user);
				model.addAttribute("userForm", new User());
				model.addAttribute("listTab","active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
				model.addAttribute("userList", userService.getAllUsers());
				model.addAttribute("areas",areaRepository.findAll());
				model.addAttribute("roles",roleRepository.findAll());
				model.addAttribute("editMode","true");
				model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
			}
		}
		
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("areas",areaRepository.findAll());
		model.addAttribute("roles",roleRepository.findAll());
		return "user-form/user-view";
		
	}
	
	/*
	 * Utilizado en el boton cacelar del usuario
	 */
	@GetMapping("/editUser/cancel")
	public String cancelEditUser(ModelMap model) {
		return "redirect:/userForm";
	}
	
	/*
	 * Utilizado en el boton eliminar usuario
	 */
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(Model model, @PathVariable(name="id") Long id) {
		try {
			userService.deleteUser(id);
		} catch (Exception e) {
			model.addAttribute("deleteError","The user could not be deleted.");
		}
		return getUserForm(model);
	}
	
	@PostMapping("/editUser/changePassword")
	public ResponseEntity<String> postEditUseChangePassword(@Valid @RequestBody ChangePasswordForm form, Errors errors) {
		try {
			if( errors.hasErrors()) {
				String result = errors.getAllErrors()
                        .stream().map(x -> x.getDefaultMessage())
                        .collect(Collectors.joining(""));

				throw new Exception(result);
			}
			userService.changePassword(form);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("Success");
	}
}
