package com.segurosbolivar.finita.aplicacion.controller;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.apache.log4j.Logger;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.segurosbolivar.finita.aplicacion.Exception.CustomeFieldValidationException;
import com.segurosbolivar.finita.aplicacion.dto.UsuarioLogin;
import com.segurosbolivar.finita.aplicacion.entity.rol.Finsgusuarios;
import com.segurosbolivar.finita.aplicacion.entity.rol.FinsgusuariosRoles;
import com.segurosbolivar.finita.aplicacion.repository.FinsgrolesRepository;
import com.segurosbolivar.finita.aplicacion.repository.FinsgusuariosRolesRepository;
import com.segurosbolivar.finita.aplicacion.service.FinsgusuariosService;
import com.segurosbolivar.finita.aplicacion.service.IGenericoService;
import com.segurosbolivar.finita.aplicacion.util.Log;

@Controller
@SessionAttributes("usuarioLogin")
public class FrontController {

	public static final Logger logger = Logger.getLogger(FrontController.class);
	private final String TAB_FORM = "formTab";
	private final String TAB_LIST = "listTab";
	
	
	private UsuarioLogin usuario;
	
	@Autowired
	FinsgusuariosService userService;
	@Autowired
	FinsgusuariosRolesRepository usersRolesRepository;
	@Autowired
	FinsgrolesRepository roleRepository;
	
	@PostConstruct
	public void init() {
		logger.info(Log.logStartBeans(FrontController.class.getName()));		
	}
	
	@GetMapping({"/","/login"})
	public String index(ModelMap  model) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		return "index";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		model.addAttribute("signup",true);
		model.addAttribute("userForm", new Finsgusuarios());
		
		return "user-form/user-signup";
	}
	
	@PostMapping("/signup")
	public String signupAction(@Valid @ModelAttribute("userForm")Finsgusuarios user, BindingResult result, ModelMap model) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		
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
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		
		Iterable<Finsgusuarios> userList =  userService.getAllUsers();		
		model.addAttribute("userForm", user);
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles",roleRepository.findAll());		
		model.addAttribute(activeTab,"active");
	}
	

	@GetMapping("/userForm")
	public String userForm(Model model,@ModelAttribute("usuarioLogin")UsuarioLogin usuarioLogin) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		
		baseAttributerForUserForm(model, new Finsgusuarios(), TAB_LIST);		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		model.addAttribute("username",username.toLowerCase());
		
		FinsgusuariosRoles usersRoles = usersRolesRepository.findByUxrUsuario(username);
		String rol = usersRoles.getFinsgroles().getRolCodigo();				
		
		usuarioLogin.setUsername(username);
		if(rol.equalsIgnoreCase("SUPERUSUARIO")) {
			usuarioLogin.setRol("SuperUsuario");
			model.addAttribute("RolAut","SuperUsuario");
			return "user-superusuario/user-superusuario";
			
			
		}if (rol.equalsIgnoreCase("CONTADOR")){
			usuarioLogin.setRol("Contador");
			model.addAttribute("RolAut","Contador");
			return "user-contador/user-contador";
			
		}else {
			//Rol consultas no esta dentro del alcance
			return "/";
		}
		
	}
	
	@PostMapping("/userForm")
	public String createUser(@Valid @ModelAttribute("userForm")Finsgusuarios user, BindingResult result, Model model) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		
		if(result.hasErrors()) {
			baseAttributerForUserForm(model, user, TAB_FORM);
		}else {
			try {
				userService.createUser(user);
				baseAttributerForUserForm(model, new Finsgusuarios(), TAB_LIST);
				
			} catch (CustomeFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
				baseAttributerForUserForm(model, user, TAB_FORM);
			}catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				baseAttributerForUserForm(model, user, TAB_FORM);
			}
		} 
		return "user-form/user-view";
	}
		
	@GetMapping("/userForm/cancel")
	public String cancelEditUser(ModelMap model) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		return "redirect:/userForm";
	}

	public UsuarioLogin getUsuario() {
		return usuario;
	}

	@ModelAttribute("usuarioLogin")
	public void setUsuario(UsuarioLogin usuario) {
		this.usuario = usuario;
	}
	
	
	
}


