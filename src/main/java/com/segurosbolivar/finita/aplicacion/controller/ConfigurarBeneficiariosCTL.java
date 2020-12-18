package com.segurosbolivar.finita.aplicacion.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.segurosbolivar.finita.aplicacion.dto.MensajeVista;
import com.segurosbolivar.finita.aplicacion.dto.ObjetoBasico;
import com.segurosbolivar.finita.aplicacion.dto.UsuarioLogin;
import com.segurosbolivar.finita.aplicacion.entity.Accionista;
import com.segurosbolivar.finita.aplicacion.entity.AccionistaPK;
import com.segurosbolivar.finita.aplicacion.entity.Beneficiario;
import com.segurosbolivar.finita.aplicacion.entity.DividendosTitulo;
import com.segurosbolivar.finita.aplicacion.entity.Persona;
import com.segurosbolivar.finita.aplicacion.entity.Referencia;
import com.segurosbolivar.finita.aplicacion.service.ConditionMap;
import com.segurosbolivar.finita.aplicacion.service.IComunidadService;
import com.segurosbolivar.finita.aplicacion.service.IGenericoService;
import com.segurosbolivar.finita.aplicacion.util.Constantes;
import com.segurosbolivar.finita.aplicacion.util.Log;
import com.segurosbolivar.finita.aplicacion.util.Utilidades;
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

	@Resource
	CatalogosCTL catalogosCTL;

	private UsuarioLogin usuario= new UsuarioLogin();
	private Accionista accionista= new Accionista();
	private ObjetoBasico filtro=new ObjetoBasico();
	private List<Accionista> accionistas= new ArrayList<Accionista>();
	private String viewState=Constantes.INICIANDO;	
	private MensajeVista mensaje= new MensajeVista();
	
	ConditionMap condiciones= new ConditionMap();

	@PostConstruct
	private void init() {
		logger.info(Log.logStartBeans(this.getClass().getName()));

	}

	@GetMapping("/configurarBeneficiarios")
	public String irConfiguracionBeneficiarios(Model model,@SessionAttribute("usuarioLogin") UsuarioLogin user,@RequestParam(value = "viewState",defaultValue = "redirect")String editMode) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));	
		try {
			this.setUsuario(user);
			Utilidades.datosDeLogin(model,user);
			if(editMode.contentEquals("0")) {
				this.accionistas.clear();
				this.loadData();
			}
			
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return Constantes.NOMBRE_FOLDER_CONTADOR+"/"+Constantes.NOMBRE_FOLDER_CONTADOR_OPCIONES +"/"+Constantes.NOMBRE_URL_CONTADOR_2_1;
	}

	@GetMapping(value = "/configurarBeneficiariosAccionista")	
	public String irBeneficiarosAccionista(ModelMap model,@RequestParam String accPerIdent,@RequestParam String accEmpCodigo) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));	
		try {			
			this.setAccionista(new Accionista(new AccionistaPK(accPerIdent, accEmpCodigo)));
			this.setAccionista(this.accionistas.get(this.accionistas.indexOf(this.accionista)));						
			this.loadDataBeneficiarisAccionista(this.accionista);
			logger.info("Accionista cargado: "+this.accionista.toString());
			model.addAttribute(accionista);
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return Constantes.NOMBRE_FOLDER_CONTADOR+"/"+Constantes.NOMBRE_FOLDER_CONTADOR_OPCIONES +"/"+Constantes.NOMBRE_URL_CONTADOR_2_1_1;
	}	

	@GetMapping(value = "/irAccionista/editar")	
	public String irAccionista(ModelMap model,@RequestParam String accPerIdent,@RequestParam String accEmpCodigo) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));	
		try {			
			this.setAccionista(new Accionista(new AccionistaPK(accPerIdent, accEmpCodigo)));
			this.setAccionista(this.accionistas.get(this.accionistas.indexOf(this.accionista)));						
			this.loadDataBeneficiarisAccionista(this.accionista);			
			logger.info("Accionista cargado: "+this.accionista.toString());
			model.addAttribute(accionista);			
			model.addAttribute("catalogoPeriodicidad",this.catalogosCTL.devolverDataPorCodigo("PP"));
			model.addAttribute("catalogoTipoPago",this.catalogosCTL.devolverDataPorCodigo("PA"));					
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return Constantes.NOMBRE_FOLDER_CONTADOR+"/"+Constantes.NOMBRE_FOLDER_CONTADOR_OPCIONES +"/"+Constantes.NOMBRE_URL_CONTADOR_2_1_3;
	}	
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/irAccionista/historicoPagos")	
	public String consultarHistoricoPagos(ModelMap model,@RequestParam String accPerIdent,@RequestParam String accEmpCodigo) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));	
		try {			
			this.setAccionista(new Accionista(new AccionistaPK(accPerIdent, accEmpCodigo)));
			this.setAccionista(this.accionistas.get(this.accionistas.indexOf(this.accionista)));						
			this.loadDataBeneficiarisAccionista(this.accionista);			
			logger.info("Accionista cargado: "+this.accionista.toString());
			model.addAttribute(accionista);			
			List<DividendosTitulo> historicoPagos= (List<DividendosTitulo>) this.genericoService.findObjects(DividendosTitulo.class, DividendosTitulo.PROP_ACC_PER_IDENT,accPerIdent, null);	
			for(DividendosTitulo div: historicoPagos) {
				try {
				div.setEstadoTram(this.catalogosCTL.getMapaReferencias().get(div.getEstadoTramite()));
				}catch (Exception e) {
					Log.getError(logger, e);
				}
			}
			model.addAttribute("historicoPagos",historicoPagos);	
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return Constantes.NOMBRE_FOLDER_CONTADOR+"/"+Constantes.NOMBRE_FOLDER_CONTADOR_OPCIONES +"/"+Constantes.NOMBRE_URL_CONTADOR_2_1_4;
	}	

	/*
	 * 
	 * 
	 */
	@PostMapping("/irAccionista/actualizar")
	public String actualizarAccionista(ModelMap model,@ModelAttribute("accionista") Accionista accionista,BindingResult result,RedirectAttributes redirAttrs) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		try {			
			this.genericoService.updateObject(accionista);
			redirAttrs.addFlashAttribute("success", "El accionista se ha actualizado con exito.");			
		}catch (Exception e) {
			Log.getError(logger, e);
			redirAttrs.addFlashAttribute("error", "El accionista no se ha actualizado con exito.");
		}				
		return Constantes.URL_HOME_CONFIGURAR_BENEFICARIOS;
	}

	@SuppressWarnings("unchecked")	
	@PostMapping("/configurarBeneficiarios/buscar")	
	public String buscarUsuario(Model model,@ModelAttribute("filtroBusqueda")  ObjetoBasico filtroBusqueda,BindingResult result,RedirectAttributes redirAttrs) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), "Filtro: "+filtroBusqueda.getValue()));		
		this.accionistas.clear();
		this.condiciones.cleanAll();
		try {
			if(!filtroBusqueda.getValue().trim().contentEquals("")) {
				StringBuffer sb = new StringBuffer();
				sb.append("SELECT sb FROM ").append(Accionista.REF).append(" sb")
				.append(" WHERE sb.").append(Accionista.PROP_ACC_NOMBRE).append(" like '%").append(filtroBusqueda.getValue()).append("%'")
				.append(" AND sb.").append(Accionista.PROP_ID).append(".").append(Accionista.PROP_EMP_CODIGO).append(" = ").append(Constantes.CODIGO_EMPRESA_ACCIONISTA);
				this.accionistas.addAll((Collection<? extends Accionista>) this.genericoService.findObjectsByQuery(sb.toString(), 0));
				for(Accionista accionista:this.accionistas) {
					propiedadesAccionista(accionista);
				}
				redirAttrs.addFlashAttribute("success", "Se han encontrado "+this.accionistas.size()+" resultados de busqueda");
			}else {
				redirAttrs.addFlashAttribute("error", "No se han encontrado resultados de busqueda");
				this.loadData();				
			}	
		}catch (Exception e) {
			Log.getError(logger, e);
			redirAttrs.addFlashAttribute("error",e.getMessage());
		}
		model.addAttribute("accionistas", this.getAccionistas());
		return Constantes.URL_HOME_CONFIGURAR_BENEFICARIOS; 
	}


	/*
	 * Cargar accionistas desde la BD
	 */
	@SuppressWarnings({ "unchecked"})
	public void loadData() {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		try {
			if(accionistas.isEmpty()) {
				this.accionistas.addAll((Collection<? extends Accionista>) this.genericoService.findObjects(Accionista.class,Accionista.PROP_ID+"."+Accionista.PROP_EMP_CODIGO, Constantes.CODIGO_EMPRESA_ACCIONISTA	, null));				
				List<Accionista> accionistasTmp= new ArrayList<Accionista>();
				for(Accionista accionista:this.accionistas) {
					propiedadesAccionista(accionista);
					if(accionista.getFinPersona()!=null)
						accionistasTmp.add(accionista);
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

	private void propiedadesAccionista(Accionista accionista) {
		if(accionista!=null ) {
			if(accionista.getId().getAccPerIdent()!=null) {
				try {

					if(!catalogosCTL.getMapaPersonas().containsKey(accionista.getId().getAccPerIdent()))
						catalogosCTL.getMapaPersonas().put(accionista.getId().getAccPerIdent(), (Persona) this.genericoService.getObjetctById(Persona.class, accionista.getId().getAccPerIdent()));

					accionista.setFinPersona(catalogosCTL.getMapaPersonas().get(accionista.getId().getAccPerIdent()));	
					accionista.getFinPersona().setPerTipoIdentRef(catalogosCTL.getMapaReferencias().get(accionista.getFinPersona().getPerTipoIdent()));
					accionista.getFinPersona().setPerNaturalezaRef(catalogosCTL.getMapaReferencias().get(accionista.getFinPersona().getPerNaturaleza()));
					accionista.setAccSecCodigoRef(catalogosCTL.getMapaReferencias().get(accionista.getAccTipoCapital()));								
					accionista.setAccFormaPagoRef(accionista.getAccFormaPago()!=null?catalogosCTL.getMapaReferencias().get(accionista.getAccFormaPago()):new Referencia());
					accionista.setAccNacionalidadRef(accionista.getAccNacionalidad()!=null?catalogosCTL.getMapaReferencias().get(accionista.getAccNacionalidad()):new Referencia());
					accionista.setAccEmpCodigoRef(accionista.getId().getAccEmpCodigo()!=null?catalogosCTL.getMapaReferencias().get(accionista.getId().getAccEmpCodigo()):new Referencia());								
					accionista.setAccCiuCodigoRef(accionista.getAccCiuCodigo()!=null?catalogosCTL.getMapaReferencias().get(accionista.getAccCiuCodigo()):new Referencia());
					accionista.setAccCiuDptPaisCodigoRef(accionista.getAccCiuDptPaisCodigo()!=null?catalogosCTL.getMapaReferencias().get(accionista.getAccCiuDptPaisCodigo().toString()):new Referencia());
					accionista.setAccCiuDptCodigoRef(accionista.getAccCiuDptCodigo()!=null?catalogosCTL.getMapaReferencias().get(accionista.getAccCiuDptCodigo()):new Referencia());
					accionista.setAccTipoCuentaRef(accionista.getAccTipoCuenta()!=null?catalogosCTL.getMapaReferencias().get(accionista.getAccTipoCuenta()):new Referencia());
					accionista.setAccTipoCapitalRef(accionista.getAccTipoCapital()!=null?catalogosCTL.getMapaReferencias().get(accionista.getAccTipoCapital()):new Referencia());

					
				}catch (Exception e) {
					Log.getError(logger, e);
				}
			}					
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
					ben.getPersona().setPerTipoIdentRef(catalogosCTL.getMapaReferencias().get(ben.getPersona().getPerTipoIdent()));					
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

	

	@ModelAttribute("usuarioLogin")
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
	
	@ModelAttribute("filtro")
	public ObjetoBasico getFiltro() {
		return filtro;
	}

	public void setFiltro(ObjetoBasico filtro) {
		this.filtro = filtro;
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
