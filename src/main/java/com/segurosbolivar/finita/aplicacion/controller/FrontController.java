package com.segurosbolivar.finita.aplicacion.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.segurosbolivar.finita.aplicacion.Exception.CustomeFieldValidationException;
import com.segurosbolivar.finita.aplicacion.entity.rol.Finsgroles;
import com.segurosbolivar.finita.aplicacion.entity.rol.Finsgusuarios;
import com.segurosbolivar.finita.aplicacion.entity.rol.FinsgusuariosRoles;
import com.segurosbolivar.finita.aplicacion.repository.FinsgrolesRepository;
import com.segurosbolivar.finita.aplicacion.repository.FinsgusuariosRolesRepository;
import com.segurosbolivar.finita.aplicacion.service.FinsgusuariosService;

@Controller
public class FrontController {

	private final String TAB_FORM = "formTab";
	private final String TAB_LIST = "listTab";
	
	@Autowired
	FinsgusuariosService userService;
	@Autowired
	FinsgusuariosRolesRepository usersRolesRepository;
	@Autowired
	FinsgrolesRepository roleRepository;
	
	@GetMapping({"/","/login"})
	public String index(ModelMap  model) {
		return "index";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		
		model.addAttribute("signup",true);
		model.addAttribute("userForm", new Finsgusuarios());
		
		return "user-form/user-signup";
	}
	
	@PostMapping("/signup")
	public String signupAction(@Valid @ModelAttribute("userForm")Finsgusuarios user, BindingResult result, ModelMap model) {
	    
		model.addAttribute("signup",true);
		model.addAttribute("userForm", user);
		
		if(result.hasErrors()) {
			
			return "user-form/user-signup";
			
		}else {
			try {
				
				userService.createUser(user);
				model.addAttribute("creacion",true);
				
			} catch (CustomeFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
			}catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
			}
		}
		return index(model);
	}
	

	private void baseAttributerForUserForm(Model model, Finsgusuarios user,String activeTab) {
		
		Iterable<Finsgusuarios> userList =  userService.getAllUsers();
		
		model.addAttribute("userForm", user);
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles",roleRepository.findAll());
		model.addAttribute(activeTab,"active");
	}
	

	@GetMapping("/userForm")
	public String userForm(Model model) {
		
		baseAttributerForUserForm(model, new Finsgusuarios(), TAB_LIST );
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		model.addAttribute("username",username.toLowerCase());
		
		FinsgusuariosRoles usersRoles = usersRolesRepository.findByUxrUsuario(username);
		String rol = usersRoles.getFinsgroles().getRolCodigo();
		
		if(rol.equalsIgnoreCase("SUPERUSUARIO")) {
			model.addAttribute("RolAut","SuperUsuario");
			return "user-superusuario/user-superusuario";
			
		}if (rol.equalsIgnoreCase("CONTADOR")){
			model.addAttribute("RolAut","Contador");
			return "user-contador/user-contador";
			
		}else {
			//Rol consultas no esta dentro del alcance
			return "/";
		}
		
	}
	
	@PostMapping("/userForm")
	public String createUser(@Valid @ModelAttribute("userForm")Finsgusuarios user, BindingResult result, Model model) {
		
		
		if(result.hasErrors()) {
			baseAttributerForUserForm(model, user, TAB_FORM);
		}else {
			try {
				userService.createUser(user);
				baseAttributerForUserForm(model, new Finsgusuarios(), TAB_LIST );
				
			} catch (CustomeFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
				baseAttributerForUserForm(model, user, TAB_FORM );
			}catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				baseAttributerForUserForm(model, user, TAB_FORM );
			}
		} 
		return "user-form/user-view";
	}
		
	@GetMapping("/userForm/cancel")
	public String cancelEditUser(ModelMap model) {
		return "redirect:/userForm";
	}
	
}


