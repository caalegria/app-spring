package com.segurosbolivar.finita.aplicacion.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.segurosbolivar.finita.aplicacion.dto.CapsulaOperacion;
import com.segurosbolivar.finita.aplicacion.dto.MensajeVista;
import com.segurosbolivar.finita.aplicacion.dto.UsuarioLogin;
import com.segurosbolivar.finita.aplicacion.entity.Fincuentas;
import com.segurosbolivar.finita.aplicacion.service.IGenericoService;
import com.segurosbolivar.finita.aplicacion.util.Constantes;
import com.segurosbolivar.finita.aplicacion.util.Log;


/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
@Controller
public class PlanCuentaCTL {

	public static final Logger logger = Logger.getLogger(PlanCuentaCTL.class);


	@Autowired
	IGenericoService genericoService;

	/*
	 * Variables
	 */
	private Fincuentas cuenta= new Fincuentas();	
	private Fincuentas cuentaBusqueda= new Fincuentas();		
	private UsuarioLogin usuario= new UsuarioLogin();
	private List<Fincuentas> cuentas = new ArrayList<Fincuentas>();
	private String editMode=Constantes.INICIANDO;	
	private MensajeVista mensaje= new MensajeVista();

	@PostConstruct
	private void init() {
		logger.info(Log.logStartBeans(this.getClass().getName()));

	}

	@GetMapping("/plaCuentas")
	public String irPlanCuenta(Model model,@SessionAttribute("usuarioLogin") UsuarioLogin user,@RequestParam(value = "editMode",defaultValue = "redirect")String editMode) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));	
		try {
			this.setUsuario(user);		
			if(editMode.contentEquals("redirect")) {
				if(editMode.contentEquals("0")){
					this.setCuenta(new Fincuentas());
					this.setCuentaBusqueda(new Fincuentas());
					this.setMensaje(new MensajeVista());
					this.cuentas.clear();
				}if(editMode.contentEquals("2")) {
					this.setCuentaBusqueda(new Fincuentas());
				}
			}else if(editMode.contentEquals("redirect")) {
				if(this.editMode.contentEquals(Constantes.BUSCANDO))
					this.setMensaje(new MensajeVista());				
			}else if (editMode.contentEquals("0")) {
				this.setMensaje(new MensajeVista());
			}
				
			this.loadData();			
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return Constantes.NOMBRE_FOLDER_CONTADOR+"/"+Constantes.NOMBRE_FOLDER_CONTADOR_OPCIONES +"/"+Constantes.NOMBRE_URL_CONTADOR_1;
	}

	@PostMapping("/plaCuentas/guardar")
	public String savPlaCuenta(ModelMap model,@ModelAttribute("cuenta")Fincuentas cuenta) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));	
		logger.info("Cuenta data "+ cuenta.toString());
		CapsulaOperacion operacion=guardarCuenta(cuenta);
		this.setMensaje(operacion.getMensaje());
		if(operacion.isEstadoOperacion()) {
			if(cuentaBusqueda.getId().trim().contentEquals("") && this.editMode.contentEquals(Constantes.BUSCANDO)) {
				this.setCuentaBusqueda(new Fincuentas());
				this.cuentas.clear();
				this.loadData();				
			}else {
				this.cuentas.add(cuenta);
			}
		}						
		this.cuenta= new Fincuentas();
		this.setEditMode(Constantes.INICIANDO);
		return Constantes.URL_HOME_PLAN_CUENTA;
	}

	@PostMapping("/plaCuentas/actualizar")
	public String actPlaCuenta(ModelMap model,@ModelAttribute("cuenta")Fincuentas cuenta) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));	
		logger.info("Cuenta data "+ cuenta.toString());
		CapsulaOperacion operacion=actualizarCuenta(cuenta);
		this.setMensaje(operacion.getMensaje());		
		if(operacion.isEstadoOperacion()) {
			if(cuentaBusqueda.getId().trim().contentEquals("") && this.editMode.contentEquals(Constantes.BUSCANDO)) {
				this.setCuentaBusqueda(new Fincuentas());
				this.cuentas.clear();
				this.loadData();				
			}
		}		
		this.cuenta= new Fincuentas();
		this.setEditMode(Constantes.INICIANDO);
		return Constantes.URL_HOME_PLAN_CUENTA;
	}
	
	@PostMapping("/plaCuentas/buscar")	
	public String busPlaCuenta(ModelMap model,@ModelAttribute("filtroBusqueda")Fincuentas filtroBusqueda) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));	
		logger.info("Filtro: "+ filtroBusqueda);
		this.cuentas.clear();
		if(!filtroBusqueda.getId().trim().contentEquals(""))
			this.cuentas.add((Fincuentas) this.genericoService.getObjetctById(Fincuentas.class, filtroBusqueda.getId()));
		else {
			this.loadData();
		}	
		this.setEditMode(Constantes.BUSCANDO);
		return Constantes.URL_HOME_PLAN_CUENTA;
	}

	@GetMapping("/plaCuentas/editar")
	public String editPlaCuenta(ModelMap model,@RequestParam("cuentaEditId")String idCuenta) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));			
		this.cuenta=this.localizarData(idCuenta);
		logger.info("Cuenta data "+ cuenta.toString());
		this.editMode=Constantes.CRUD_ACTUALIZAR;	
		this.setMensaje(new MensajeVista());
		return Constantes.URL_HOME_PLAN_CUENTA;
	}

	@GetMapping("/plaCuentas/bloquear")
	public String bloPlaCuenta(ModelMap model,@RequestParam("cuentaEditId")String idCuenta) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));			
		Fincuentas cntTmp=this.localizarData(idCuenta);
		logger.info("Cuenta data "+ cntTmp.toString());
		CapsulaOperacion operacion=actualizarEstadoCuenta(cntTmp);
		this.setMensaje(operacion.getMensaje());		
		return Constantes.URL_HOME_PLAN_CUENTA;
	}

	/*
	 * Cargar Cuentas desde la BD
	 */
	@SuppressWarnings("unchecked")
	public void loadData() {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));		
		if(cuentas.isEmpty())
			this.cuentas.addAll((Collection<? extends Fincuentas>) genericoService.getObjects(Fincuentas.class));		
	}

	/*
	 * Metodo para localizar el objeto de la cuenta
	 * desde el listado de cuentas de la pagina, con el numero de
	 * idCuenta(CTA_CUENTA)
	 */
	private Fincuentas localizarData(String idCuenta) {
		try {
			Fincuentas cuentaTmp= new Fincuentas(idCuenta);
			cuentaTmp= this.cuentas.get(this.cuentas.indexOf(cuentaTmp));
			return cuentaTmp;
		}catch (Exception e) {
			return null;
		}	
	}

	public void prepararNuevaCuenta(Model model,String editMode) {		
		if(!editMode.equals("redirect")) {
			this.editMode=editMode;
			if(this.editMode.equals("1")) 
				this.cuenta= new Fincuentas();
		}	
	}


	public CapsulaOperacion guardarCuenta(Fincuentas cuenta) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));			
		try {
			cuenta.setFechaCreacion(new Date());
			cuenta.setEstado(Constantes.ESTADO_ACTIVO);
			this.genericoService.saveObject(cuenta);			
			
			return new CapsulaOperacion(true, Constantes.CODIGO_OPERACION_EXITOSA, Constantes.CODIGO_OPERACION_ERRO_TIPO_CORRECTO,new MensajeVista(Constantes.RENDERIZAR, Constantes.MSJ_OPERACION_EXITOSA.replace(Constantes.KEY_OPERACION, Constantes.OPERACION_GUARDAR),Constantes.CORRECTO));
		}catch (Exception e) {
			Log.getError(logger, e);			
			return new CapsulaOperacion(false, Constantes.CODIGO_OPERACION_ERROR, Constantes.CODIGO_OPERACION_ERRO_TIPO_CORRECTO,e,new MensajeVista(Constantes.RENDERIZAR, Constantes.MSJ_OPERACION_FALLIDA,Constantes.WARN));
		}
	}

	public CapsulaOperacion actualizarCuenta(Fincuentas cuenta) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));			
		try {
			cuenta.setFechaUltimaModificacion(new Date());			
			this.genericoService.updateObject(cuenta);
			return new CapsulaOperacion(true, Constantes.CODIGO_OPERACION_EXITOSA, Constantes.CODIGO_OPERACION_ERRO_TIPO_CORRECTO,new MensajeVista(Constantes.RENDERIZAR, Constantes.MSJ_OPERACION_EXITOSA.replace(Constantes.KEY_OPERACION, Constantes.OPERACION_ACTUALIZAR),Constantes.CORRECTO));
		}catch (Exception e) {
			Log.getError(logger, e);			
			return new CapsulaOperacion(false, Constantes.CODIGO_OPERACION_ERROR, Constantes.CODIGO_OPERACION_ERRO_TIPO_CORRECTO,e,new MensajeVista(Constantes.RENDERIZAR, Constantes.MSJ_OPERACION_FALLIDA,Constantes.WARN));
		}
	}

	public CapsulaOperacion actualizarEstadoCuenta(Fincuentas cuenta) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));			
		try {
			cuenta.setFechaUltimaModificacion(new Date());	
			if(cuenta.getEstado()==Constantes.ESTADO_ACTIVO)
				cuenta.setEstado(Constantes.ESTADO_BLOQUEADO);			
			else if(cuenta.getEstado()==Constantes.ESTADO_BLOQUEADO){
				cuenta.setEstado(Constantes.ESTADO_ACTIVO);			
			}else {
				cuenta.setEstado(Constantes.ESTADO_ACTIVO);			
			}
			this.genericoService.updateObject(cuenta);
			return new CapsulaOperacion(true, Constantes.CODIGO_OPERACION_EXITOSA, Constantes.CODIGO_OPERACION_ERRO_TIPO_CORRECTO,new MensajeVista(Constantes.RENDERIZAR, Constantes.MSJ_OPERACION_EXITOSA.replace(Constantes.KEY_OPERACION, Constantes.OPERACION_ACTUALIZAR),Constantes.CORRECTO));
		}catch (Exception e) {
			Log.getError(logger, e);			
			return new CapsulaOperacion(false, Constantes.CODIGO_OPERACION_EXITOSA, Constantes.CODIGO_OPERACION_ERRO_TIPO_CORRECTO,e,new MensajeVista(Constantes.NO_RENDERIZAR, Constantes.MSJ_OPERACION_FALLIDA,Constantes.WARN));
		}
	}

	@ModelAttribute("userLogin")
	public UsuarioLogin getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioLogin usuario) {
		this.usuario = usuario;
	}

	@ModelAttribute("cuentas")
	public List<Fincuentas> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Fincuentas> cuentas) {
		this.cuentas = cuentas;
	}

	@ModelAttribute("cuenta")
	public Fincuentas getCuenta() {
		return cuenta;
	}

	public void setCuenta(Fincuentas cuenta) {
		this.cuenta = cuenta;
	}	

	@ModelAttribute("editMode")
	public String getEditMode() {
		return editMode;
	}

	public void setEditMode(String editMode) {
		this.editMode = editMode;
	}	

	@ModelAttribute("filtroBusqueda")
	public Fincuentas getCuentaBusqueda() {
		return cuentaBusqueda;
	}

	public void setCuentaBusqueda(Fincuentas cuentaBusqueda) {
		this.cuentaBusqueda = cuentaBusqueda;
	}

	@ModelAttribute("message")
	public MensajeVista getMensaje() {
		return mensaje;
	}

	public void setMensaje(MensajeVista mensaje) {
		this.mensaje = mensaje;
	}  	

}
