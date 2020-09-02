package com.segurosbolivar.finita.aplicacion.controller;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.segurosbolivar.finita.aplicacion.dto.UsuarioLogin;
import com.segurosbolivar.finita.aplicacion.service.IGenericoService;
import com.segurosbolivar.finita.aplicacion.util.Constantes;
import com.segurosbolivar.finita.aplicacion.util.Log;
import com.segurosbolivar.finita.aplicacion.util.Utilidades;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
@Controller
public class AplicacionCTL {
	
	public static final Logger logger = Logger.getLogger(AplicacionCTL.class);
	
	@Autowired
	IGenericoService genericoService;
	
	private UsuarioLogin usuario;
	
	@PostConstruct
	private void init() {
		logger.info(Log.logStartBeans(this.getClass().getName()));
	}
	
	
    @GetMapping("/aplicacion")
	public String irAplicacion(Model model,@SessionAttribute("usuarioLogin") UsuarioLogin user) {
    	logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
    	try {
			this.setUsuario(user);
			Utilidades.datosDeLogin(model,user);	
		}catch (Exception e) {
			Log.getError(logger, e);
		}		
    	return Constantes.NOMBRE_FOLDER_CONTADOR+"/"+Constantes.NOMBRE_FOLDER_CONTADOR_OPCIONES +"/"+Constantes.NOMBRE_URL_CONTADOR_3;
    }
    
    public UsuarioLogin getUsuario() {
		return usuario;
	}


	public void setUsuario(UsuarioLogin usuario) {
		this.usuario = usuario;
	}  

}
