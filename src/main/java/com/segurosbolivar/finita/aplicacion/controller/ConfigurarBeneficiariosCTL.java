package com.segurosbolivar.finita.aplicacion.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.segurosbolivar.finita.aplicacion.dto.MensajeVista;
import com.segurosbolivar.finita.aplicacion.dto.UsuarioLogin;
import com.segurosbolivar.finita.aplicacion.entity.Accionista;
import com.segurosbolivar.finita.aplicacion.entity.AccionistaPK;
import com.segurosbolivar.finita.aplicacion.entity.Beneficiario;
import com.segurosbolivar.finita.aplicacion.entity.Persona;
import com.segurosbolivar.finita.aplicacion.entity.Referencia;
import com.segurosbolivar.finita.aplicacion.service.IComunidadService;
import com.segurosbolivar.finita.aplicacion.service.IGenericoService;
import com.segurosbolivar.finita.aplicacion.util.Constantes;
import com.segurosbolivar.finita.aplicacion.util.Log;
import com.segurosbolivar.impl.TercerosRESTImplementacion;
import com.segurosbolivar.objeto.Data;
import com.segurosbolivar.objetos.json.Dataheader;
import com.segurosbolivar.objetos.json.DatosTercero;
import com.segurosbolivar.objetos.request.RequestTerceros;
import com.segurosbolivar.objetos.respuesta.RespuestaTercerosREST;
import com.segurosbolivar.service.TercerosREST;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */

@Controller
public class ConfigurarBeneficiariosCTL {

	public static final Logger logger = Logger.getLogger(ConfigurarBeneficiariosCTL.class);

	@Autowired
	private Environment env;

	@Autowired
	IGenericoService genericoService;

	@Autowired
	IComunidadService comunidadService;

	private UsuarioLogin usuario= new UsuarioLogin();
	private Accionista accionista= new Accionista();	
	private List<Accionista> accionistas= new ArrayList<Accionista>();
	private String viewState=Constantes.INICIANDO;	
	private MensajeVista mensaje= new MensajeVista();
	private HashMap<String, Persona> mapaPersonas= new HashMap<String, Persona>();
	private HashMap<String, Referencia> mapaReferencias= new HashMap<String, Referencia>();

	@PostConstruct
	private void init() {
		logger.info(Log.logStartBeans(this.getClass().getName()));

	}

	@GetMapping("/configurarBeneficiarios")
	public String irConfiguracionBeneficiarios(Model model,@SessionAttribute("usuarioLogin") UsuarioLogin user,@RequestParam(value = "viewState",defaultValue = "redirect")String editMode) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));	
		try {
			this.setUsuario(user);
			this.loadMapaReferencias();
			this.loadData();			
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return Constantes.NOMBRE_FOLDER_CONTADOR+"/"+Constantes.NOMBRE_FOLDER_CONTADOR_OPCIONES +"/"+Constantes.NOMBRE_URL_CONTADOR_2_1;
	}

	@GetMapping(value = "/configurarBeneficiariosAccionista")	
	public String irBeneficiarosAccionista(ModelMap model,@RequestParam String accPerIdent,@RequestParam String accOfiCodigo) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));	
		try {			
			this.setAccionista(new Accionista(new AccionistaPK(accPerIdent, accOfiCodigo)));
			this.setAccionista(this.accionistas.get(this.accionistas.indexOf(this.accionista)));						
			this.loadDataBeneficiarisAccionista(this.accionista);
			logger.info("Accionista cargado: "+this.accionista.toString());
			model.addAttribute(accionista);
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return Constantes.NOMBRE_FOLDER_CONTADOR+"/"+Constantes.NOMBRE_FOLDER_CONTADOR_OPCIONES +"/"+Constantes.NOMBRE_URL_CONTADOR_2_1_1;
	}	

	@PostMapping("/configurarBeneficiarios/buscar")	
	public String busPlaCuenta(ModelMap model,@ModelAttribute("accionista")Accionista filtroBusqueda,BindingResult result) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));	
		logger.info("Filtro: "+ filtroBusqueda);
		this.accionistas.clear();
		if(!filtroBusqueda.getAccNombre().trim().contentEquals(""))
			this.accionistas.add((Accionista) this.genericoService.getObjetctById(Accionista.class, filtroBusqueda.getId()));
		else {
			this.loadData();
		}		
		return Constantes.URL_HOME_PLAN_CUENTA;
	}


	/*
	 * Cargar accionistas desde la BD
	 */
	@SuppressWarnings({ "unchecked"})
	public void loadData() {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		try {
			if(accionistas.isEmpty()) {
				this.accionistas.addAll((Collection<? extends Accionista>) this.genericoService.findObjects(Accionista.class, Accionista.PROP_EMP_CODIGO, Constantes.CODIGO_EMPRESA_ACCIONISTA	, null));
				//this.accionistas.addAll((Collection<? extends Accionista>) genericoService.getObjects(Accionista.class));
				List<Accionista> accionistasTmp= new ArrayList<Accionista>();
				for(Accionista accionista:this.accionistas) {
					if(accionista!=null ) {
						if(accionista.getId().getAccPerIdent()!=null) {
							try {

								if(!mapaPersonas.containsKey(accionista.getId().getAccPerIdent()))
									mapaPersonas.put(accionista.getId().getAccPerIdent(), (Persona) this.genericoService.getObjetctById(Persona.class, accionista.getId().getAccPerIdent()));

								accionista.setFinPersona(mapaPersonas.get(accionista.getId().getAccPerIdent()));	
								accionista.setAccSecCodigoRef(mapaReferencias.get(accionista.getAccTipoCapital()));
								if(accionista.getAccFormaPago()!=null)
									accionista.setAccFormaPagoRef(mapaReferencias.get(accionista.getAccFormaPago()));
								if(accionista.getAccNacionalidad()!=null)
									accionista.setAccNacionalidadRef(mapaReferencias.get(accionista.getAccNacionalidad()));
								if(accionista.getAccEmpCodigo()!=null)
									accionista.setAccEmpCodigoRef(mapaReferencias.get(accionista.getAccEmpCodigo()));
								if(accionista.getAccCiuCodigo()!=null)
									accionista.setAccCiuCodigoRef(mapaReferencias.get(accionista.getAccCiuCodigo()));
								if(accionista.getAccCiuDptPaisCodigo()!=null)
									accionista.setAccCiuDptPaisCodigoRef(mapaReferencias.get(accionista.getAccCiuDptPaisCodigo().toString()));
								if(accionista.getAccCiuDptCodigo()!=null)
									accionista.setAccCiuDptCodigoRef(mapaReferencias.get(accionista.getAccCiuDptCodigo()));
								if(accionista.getAccTipoCuenta()!=null)
									accionista.setAccTipoCuentaRef(mapaReferencias.get(accionista.getAccTipoCuenta()));
								if(accionista.getAccTipoCapital()!=null)
									accionista.setAccTipoCapitalRef(mapaReferencias.get(accionista.getAccTipoCapital()));

								if(accionista.getFinPersona()!=null)
									accionistasTmp.add(accionista);
							}catch (Exception e) {
								Log.getError(logger, e);
							}
						}					
					}
				}
				if(!accionistasTmp.isEmpty()) {
					this.accionistas.clear();
					this.accionistas.addAll(accionistasTmp);
				}

			}		
		}catch (Exception e) {
			Log.getError(logger, e);
		}	
	}

	/*
	 * Cargar beneficiarios del accionista desde la BD
	 */	
	public void loadDataBeneficiarisAccionista(Accionista accionista) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		this.accionista.getBeneficiarios().clear();	
		try {			
			this.accionista.getBeneficiarios().addAll(this.comunidadService.getBeneficiariosPorAccionista(accionista));
			for(Beneficiario ben:this.accionista.getBeneficiarios()) {
				try {				
					ben.setPersona((Persona) this.genericoService.getObjetctById(Persona.class, ben.getId().getBenPerIdent()));
				}catch (Exception e) {
					Log.getError(logger, e);
				}
			}

		}catch (Exception e) {
			Log.getError(logger, e);
		}
	}

	/*
	 *Validar si e beneficiario existe en terceros 
	 */

	public void validarTercero() {
		TercerosREST tercero= new TercerosRESTImplementacion();		
		Dataheader dtheader= new Dataheader("79467856", 9056, "CO", "10.0.12.13", "N");
		DatosTercero dterc= new DatosTercero("CC", "1120360010");
		Data data = new Data(dterc);
		RequestTerceros request = new RequestTerceros(dtheader,data);
		RespuestaTercerosREST respuesta= tercero.consultaNatural(env.getProperty(Constantes.CONSULTA_NATURAL), request);
		logger.info(respuesta.getJsonSend());
		logger.info(respuesta.getJsonReviced());
	}

	@SuppressWarnings("unchecked")
	protected void loadMapaReferencias() {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		if(mapaReferencias.isEmpty()) {			
			List<Referencia> referencias= (List<Referencia>) this.genericoService.getObjects(Referencia.class);		
			for(Referencia ref:referencias) {
				this.mapaReferencias.put(ref.getRefCodigo(), ref);
			}
		}
	}

	@ModelAttribute("userLogin")
	public UsuarioLogin getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioLogin usuario) {
		this.usuario = usuario;
	}

	@ModelAttribute("accionista")
	public Accionista getAccionista() {
		return accionista;
	}

	public void setAccionista(Accionista accionista) {
		this.accionista = accionista;
	}

	@ModelAttribute("accionistas")
	public List<Accionista> getAccionistas() {
		return accionistas;
	}

	public void setAccionistas(List<Accionista> accionistas) {
		this.accionistas = accionistas;
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
