package com.segurosbolivar.finita.aplicacion.controller;

import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.segurosbolivar.finita.aplicacion.dto.UsuarioLogin;
import com.segurosbolivar.finita.aplicacion.service.FinsgusuariosService;
import com.segurosbolivar.finita.aplicacion.util.Log;

@Controller
@SessionAttributes("usuarioLogin")
public class AutenticaciónACL {

	public static final Logger logger = Logger.getLogger(AutenticaciónACL.class);

	
	private UsuarioLogin usuario;
	
	@Autowired
	FinsgusuariosService userService;
	

	@PostConstruct
	public void init() {
		logger.info(Log.logStartBeans(AutenticaciónACL.class.getName()));		
	}
	
	@GetMapping({"/","/login"})
	public String index(ModelMap  model) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		return "index";
	}


	public UsuarioLogin getUsuario() {
		return usuario;
	}

	@ModelAttribute("usuarioLogin")
	public void setUsuario(UsuarioLogin usuario) {
		this.usuario = usuario;
	}
	
	
	
}


