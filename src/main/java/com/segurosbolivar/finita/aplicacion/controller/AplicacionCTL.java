package com.segurosbolivar.finita.aplicacion.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.segurosbolivar.finita.aplicacion.dto.Deceval;
import com.segurosbolivar.finita.aplicacion.dto.MensajeVista;
import com.segurosbolivar.finita.aplicacion.dto.Parametro;
import com.segurosbolivar.finita.aplicacion.dto.UsuarioLogin;
import com.segurosbolivar.finita.aplicacion.service.IComunidadService;
import com.segurosbolivar.finita.aplicacion.service.IGenericoService;
import com.segurosbolivar.finita.aplicacion.util.Constantes;
import com.segurosbolivar.finita.aplicacion.util.Log;
import com.segurosbolivar.finita.aplicacion.util.MediaTypeUtils;
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
    private ServletContext servletContext;
	
	@Autowired
	IGenericoService genericoService;

	@Autowired
	IComunidadService comunidadService;

	private UsuarioLogin usuario= new UsuarioLogin();
	private Deceval deceval= new Deceval();
	private String viewState=Constantes.INICIANDO;	
	private MensajeVista mensaje= new MensajeVista();
	private boolean download=false;
	private String folderPermisos="";

	@PostConstruct
	private void init() {
		logger.info(Log.logStartBeans(this.getClass().getName()));
		this.folderPermisos+=System.getProperty("java.io.tmpdir");
		try {
			File f = new File(this.folderPermisos);
			if(f.canWrite()) {
			     this.folderPermisos+=",Puede Escribir";
			} else {
				this.folderPermisos+=", No puede Escribir";
			}
		}catch (Exception e) {
			Log.getError(logger, e);
		}
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
			salida.add(String.class);
			this.genericoService.callProcedimientoPl(Constantes.PKG_FIN_ARCHIVO_CONTABLE_PBD_PROCESO_REVERSION, null, salida, false);
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return Constantes.URL_HOME_APLICACION;
	}

	@PostMapping(value="/aplicacion/generar")
	public String generarArchivoDeceval(Model model,@ModelAttribute("deceval") Deceval deceval) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		try{
			Parametro par1= new Parametro(java.sql.Date.class, new java.sql.Date(deceval.getFechInicial().getTime()));
			Parametro par2= new Parametro(java.sql.Date.class, new java.sql.Date(deceval.getFechFinal().getTime()));
			List<Parametro>parametrosEntrada= new ArrayList<Parametro>();
			parametrosEntrada.add(par1);
			parametrosEntrada.add(par2);
			List<Class<?>> salida= new ArrayList<Class<?>>();
			salida.add(String.class);
			salida.add(String.class);
			HashMap<String, Object> dataRespuesta=this.genericoService.callProcedimientoPl(Constantes.PKG_FIN_ARCHIVO_CONTABLE_PBD_GENERA_ARCHIVO, parametrosEntrada, salida, false);
			Object res= dataRespuesta.get(Constantes.CLAVE_RESPUESTA+"1");
			if(res!=null)
				if(res.toString().equals("0")) {
					this.setDownload(this.comunidadService.getArchivoDeceval());
				}
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return Constantes.URL_HOME_APLICACION;
	}


	@GetMapping(value="/aplicacion/downloadDeceval")
	public ResponseEntity<InputStreamResource> downloadFile1() throws IOException {		
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, Constantes.DECEVAL+".txt"); 
        File file = new File(Utilidades.rutaTemporal()+Constantes.DECEVAL+".txt");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file)); 
        this.setDownload(false);        
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName()).contentType(mediaType).contentLength(file.length()).body(resource);
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

	@ModelAttribute("deceval")
	public Deceval getDeceval() {
		return deceval;
	}

	public void setDeceval(Deceval deceval) {
		this.deceval = deceval;
	}

	@ModelAttribute("download")
	public boolean isDownload() {
		return download;
	}

	public void setDownload(boolean download) {
		this.download = download;
	}

	@ModelAttribute("folderPermisos")
	public String getFolderPermisos() {		
		return folderPermisos;
	}

	public void setFolderPermisos(String folderPermisos) {
		this.folderPermisos = folderPermisos;
	}
	
	
	
}
