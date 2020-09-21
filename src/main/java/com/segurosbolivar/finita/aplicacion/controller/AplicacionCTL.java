package com.segurosbolivar.finita.aplicacion.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.segurosbolivar.finita.aplicacion.dto.MensajeVista;
import com.segurosbolivar.finita.aplicacion.dto.Parametro;
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

	private UsuarioLogin usuario= new UsuarioLogin();
	private String viewState=Constantes.INICIANDO;	
	private MensajeVista mensaje= new MensajeVista();
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechInicial = new Date();
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechFinal = new Date();

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

	@GetMapping(value="/aplicacion/cierre")
	public String generarCierre(Model model) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		try{
			List<Class<?>> salida= new ArrayList<Class<?>>();
			salida.add(String.class);
			this.genericoService.callProcedimientoPl(Constantes.PKG_FIN_ARCHIVO_CONTABLE_PBD_PROCESO_CIERRE, null, salida, false);
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return Constantes.URL_HOME_APLICACION;
	}

	@GetMapping(value="/aplicacion/reversion")
	public String generarReversion(Model model) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		try{
			List<Class<?>> salida= new ArrayList<Class<?>>();
			salida.add(String.class);
			this.genericoService.callProcedimientoPl(Constantes.PKG_FIN_ARCHIVO_CONTABLE_PBD_PROCESO_REVERSION, null, salida, false);
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return Constantes.URL_HOME_APLICACION;
	}

	@PostMapping(value="/aplicacion/generar")
	public String generarArchivoDeceval(Model model,@ModelAttribute("fechInicial") Date fechaInicial,@ModelAttribute("fechFinal")Date fechaFinal) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		try{
			Parametro par1= new Parametro(Date.class, fechaInicial);
			Parametro par2= new Parametro(Date.class, fechaFinal);
			List<Parametro>parametrosEntrada= new ArrayList<Parametro>();
			parametrosEntrada.add(par1);
			parametrosEntrada.add(par2);
			List<Class<?>> salida= new ArrayList<Class<?>>();
			salida.add(String.class);
			this.genericoService.callProcedimientoPl(Constantes.PKG_FIN_ARCHIVO_CONTABLE_PBD_GENERA_ARCHIVO, parametrosEntrada, salida, false);
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return Constantes.URL_HOME_APLICACION;
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

	@ModelAttribute("mensaje")
	public MensajeVista getMensaje() {
		return mensaje;
	}


	public void setMensaje(MensajeVista mensaje) {
		this.mensaje = mensaje;
	}

	@ModelAttribute("fechInicial")
	public Date getFechInicial() {
		return fechInicial;
	}


	public void setFechInicial(Date fechInicial) {
		this.fechInicial = fechInicial;
	}


	@ModelAttribute("fechFinal")
	public Date getFechFinal() {
		return fechFinal;
	}


	public void setFechFinal(Date fechFinal) {
		this.fechFinal = fechFinal;
	}
	
	

}
