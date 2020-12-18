package com.segurosbolivar.finita.aplicacion.controller;

import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.segurosbolivar.finita.aplicacion.dto.UsuarioLogin;
import com.segurosbolivar.finita.aplicacion.service.IComunidadService;
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
public class EstadoOrdernesPago {

	public static final Logger logger = Logger.getLogger(EstadoOrdernesPago.class);
	
	@Autowired
	IGenericoService genericoService;

	@Autowired
	IComunidadService comunidadService;

	private UsuarioLogin usuario= new UsuarioLogin();
	private String viewState=Constantes.INICIANDO;	

	@PostConstruct
	private void init() {
		logger.info(Log.logStartBeans(this.getClass().getName()));
		
	}


	@GetMapping("/estadoOrdenesPago")
	public String goHome(Model model,@SessionAttribute("usuarioLogin") UsuarioLogin user,@RequestParam(value = "viewState",defaultValue = "0")String viewState,@RequestParam(value = "reload",defaultValue = "0")String reload) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		try {
			this.setUsuario(user);
			Utilidades.datosDeLogin(model,user);	
		}catch (Exception e) {
			Log.getError(logger, e);
		}		
		return Constantes.NOMBRE_FOLDER_CONTADOR+"/"+Constantes.NOMBRE_FOLDER_CONTADOR_OPCIONES +"/"+Constantes.NOMBRE_URL_CONTADOR_5;
	}



	@ModelAttribute("usuarioLogin")
	public UsuarioLogin getUsuario() {
		return usuario;
	}


	public void setUsuario(UsuarioLogin usuario) {
		this.usuario = usuario;
	}

	@ModelAttribute("viewState")
	public String getViewState() {
		return viewState;
	}

	public void setViewState(String viewState) {
		this.viewState = viewState;
	}	
	
}
