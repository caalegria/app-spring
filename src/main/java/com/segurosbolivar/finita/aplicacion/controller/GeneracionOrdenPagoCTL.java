package com.segurosbolivar.finita.aplicacion.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.segurosbolivar.finita.aplicacion.dto.MensajeVista;
import com.segurosbolivar.finita.aplicacion.dto.RespuestaCallPL;
import com.segurosbolivar.finita.aplicacion.dto.SaldoBeneficiario;
import com.segurosbolivar.finita.aplicacion.dto.UsuarioLogin;
import com.segurosbolivar.finita.aplicacion.service.IComunidadService;
import com.segurosbolivar.finita.aplicacion.service.IGenericoService;
import com.segurosbolivar.finita.aplicacion.util.Constantes;
import com.segurosbolivar.finita.aplicacion.util.Log;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
@Controller
public class GeneracionOrdenPagoCTL {

	public static final Logger logger = Logger.getLogger(GeneracionOrdenPagoCTL.class);

	@Autowired
	IGenericoService genericoService;
	
	@Autowired
	IComunidadService comunidadService;
	
	private UsuarioLogin usuario;
	private List<SaldoBeneficiario> saldos= new ArrayList<SaldoBeneficiario>();
	protected List<SaldoBeneficiario> saldosApagar= new ArrayList<SaldoBeneficiario>();
	private String viewState=Constantes.INICIANDO;	
	private MensajeVista mensaje= new MensajeVista();
	
	@PostConstruct
	private void init() {
		logger.info(Log.logStartBeans(this.getClass().getName()));
	}	

	@GetMapping("/generarOrdenesDePagos")
	public String irGenOrdPagos(Model model,@SessionAttribute("usuarioLogin") UsuarioLogin user,@RequestParam(value = "viewState",defaultValue = "0")String viewState,@RequestParam(value = "reload",defaultValue = "0")String reload) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		try {
			this.setUsuario(user);
			model.addAttribute("userLogin",user);
			if(reload.contentEquals("1")) {				
				this.loadData();
			}
		}catch (Exception e) {
			Log.getError(logger, e);
		}	
		
		return Constantes.NOMBRE_FOLDER_CONTADOR+"/"+Constantes.NOMBRE_FOLDER_CONTADOR_OPCIONES +"/"+Constantes.NOMBRE_URL_CONTADOR_2_2;
	}
	
	/*
	 * Este metodo se llama a traves de AJAX
	 * enviando la posicion de el dato en la tabla y el estado
	 * del chexbox
	 */
	@GetMapping("/generarOrdenesDePagos/eventoSeleccionar")
	public String selecionarOrden(Model model,@RequestParam("posicion")int posicion,@RequestParam("estado")boolean estado) {
		try {
			SaldoBeneficiario tmp= this.saldos.get(posicion);
			tmp.setSelect(estado);			
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return Constantes.URL_HOME_GENERAR_ORDEN_PAGOS;
	}
	
	@PostMapping("/generarOrdenesDePagos/generar")
	public String generarOrdenesDePago(Model model) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		try {
			this.saldosApagar.clear();
			for(SaldoBeneficiario saldo:saldos) {
				if(saldo.isSelect()) {					
					logger.info("Saldo Selecionado para pagar: "+saldo);					
					this.saldosApagar.add(saldo);
				}
			}			
			RespuestaCallPL respuesta=this.genericoService.callProcedimientoGenerarOrdenPago(saldosApagar,new RespuestaCallPL());
			logger.info("Respuesta de PRC --> "+respuesta.getCodigo());			
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		
		return Constantes.URL_HOME_GENERAR_ORDEN_PAGOS; 
	}
	
	/*
	 * Cargar accionistas desde la BD
	 */	
	public void loadData() {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		try {
			this.saldos.clear();
			this.saldos.addAll(this.comunidadService.getSaldosBeneficiario());
		}catch (Exception e) {
			Log.getError(logger, e);
		}
	}
	
	@ModelAttribute("userLogin")
	public UsuarioLogin getUsuario() {
		return usuario;
	}


	public void setUsuario(UsuarioLogin usuario) {
		this.usuario = usuario;
	}

	@ModelAttribute("saldos")
	public List<SaldoBeneficiario> getSaldos() {
		return saldos;
	}

	public void setSaldos(List<SaldoBeneficiario> saldos) {
		this.saldos = saldos;
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

}
