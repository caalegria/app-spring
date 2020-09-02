package com.segurosbolivar.finita.aplicacion.util;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;

import com.segurosbolivar.finita.aplicacion.dto.UsuarioLogin;

public class Utilidades {
	
	public static final Logger logger = Logger.getLogger(Utilidades.class);
	
	public static void datosDeLogin(Model model,UsuarioLogin login) {		
		model.addAttribute("userLogin",login.getUsername());
		model.addAttribute("RolAut",login.getRol());
		logger.info(Log.getCurrentClassAndMethodNames(Utilidades.class.getName(), "Usuario Logeado: "+login.toString()));
	}

}
