package com.segurosbolivar.finita.aplicacion.controller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.segurosbolivar.finita.aplicacion.dto.Catalogo;
import com.segurosbolivar.finita.aplicacion.dto.MensajeVista;
import com.segurosbolivar.finita.aplicacion.dto.UsuarioLogin;
import com.segurosbolivar.finita.aplicacion.entity.Beneficiario;
import com.segurosbolivar.finita.aplicacion.entity.BeneficiarioPK;
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
import com.segurosbolivar.objetos.json.InfoGeneralTercero;
import com.segurosbolivar.objetos.json.Sexo;
import com.segurosbolivar.objetos.json.TercerosNaturalInfo;
import com.segurosbolivar.objetos.request.RequestTerceros;
import com.segurosbolivar.objetos.respuesta.RespuestaTercerosREST;
import com.segurosbolivar.service.TercerosREST;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */

@Controller
public class AgregarBeneficiarioAccionistaCTL {

	public static final Logger logger = Logger.getLogger(AgregarBeneficiarioAccionistaCTL.class);

	@Resource
	ConfigurarBeneficiariosCTL configurarBeneficiario;
	
	@Resource
	MessageSource messages;
	
	@Autowired
	Environment env;

	@Autowired
	IGenericoService genericoService;

	@Autowired
	IComunidadService comunidadService;
	
	TercerosREST tercero= new TercerosRESTImplementacion();
	private UsuarioLogin usuario= new UsuarioLogin();
	private Persona persona= new Persona();	
	private Beneficiario beneficiario;
	private List<Persona> personas= new ArrayList<Persona>();
	private List<Catalogo> catalogoNit= new ArrayList<Catalogo>();
	private String viewState=Constantes.INICIANDO;	
	private MensajeVista mensaje= new MensajeVista();

	@PostConstruct
	private void init() {
		logger.info(Log.logStartBeans(this.getClass().getName()));

	}

	@GetMapping(value = "/agregarBeneficiariosAccionista")	
	public String irAgregarBeneficiarosAccionista(Model model,@SessionAttribute("usuarioLogin") UsuarioLogin user,@RequestParam(value = "viewState",defaultValue = "0")String viewState) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));	
		try {
			this.setUsuario(user);		
			Utilidades.datosDeLogin(model,user);	
			this.loadData();			
			this.setViewState(viewState);				
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return Constantes.NOMBRE_FOLDER_CONTADOR+"/"+Constantes.NOMBRE_FOLDER_CONTADOR_OPCIONES +"/"+Constantes.NOMBRE_URL_CONTADOR_2_1_2;
	}
	
	@GetMapping(value = "/agregarBeneficiariosAccionista/regresar")
	public String regresar(Model model) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		if(this.getViewState().contentEquals("1") || this.getViewState().contentEquals("2")) {		
			this.setViewState("0");
			this.setPersona(new Persona());
			return Constantes.URL_HOME_AGREGAR_BENEFICARIOS_ACCIONISTA+"?viewSate=0";
		}else if(this.getViewState().contentEquals("3")){
			this.setViewState("0");
			return Constantes.URL_HOME_CONFIGURAR_BENEFICARIOS_ACCIONISTA+"?accPerIdent="+this.configurarBeneficiario.getAccionista().getId().getAccPerIdent()+"&accEmpCodigo="+this.configurarBeneficiario.getAccionista().getId().getAccEmpCodigo();
		}
		return Constantes.URL_HOME_AGREGAR_BENEFICARIOS_ACCIONISTA+"?viewSate=0";		
	}

	@GetMapping(value = "/agregarBeneficiariosAccionista/nuevo")
	public String irAgregarBeneficiarosAccionistaNuevo(Model model) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		this.setPersona(new Persona());
		this.setViewState("1");
		return Constantes.URL_HOME_AGREGAR_BENEFICARIOS_ACCIONISTA;
	}
	
	@PostMapping("/agregarBeneficiariosAccionista/guardar")
	public String savPersona(Model model,@ModelAttribute("persona")Persona persona) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));	
		logger.info("Persona data "+ persona.toString());
		Beneficiario ben= new Beneficiario();		
		this.validarTerceros(persona,ben,true);
		this.setViewState("0");		
		model.addAttribute(this.configurarBeneficiario.getAccionista());
		return Constantes.URL_HOME_CONFIGURAR_BENEFICARIOS_ACCIONISTA+"?accPerIdent="+this.configurarBeneficiario.getAccionista().getId().getAccPerIdent()+"&accEmpCodigo="+this.configurarBeneficiario.getAccionista().getId().getAccEmpCodigo();
	}

	@PostMapping("/agregarBeneficiariosAccionista/buscar")	
	public String buscarPersona(Model model,@ModelAttribute("persona")Persona persona,BindingResult result) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));	
		logger.info("Filtro: "+ persona);		
		ConditionMap condiciones= new ConditionMap();
		if(!persona.getId().trim().contentEquals(""))
			condiciones.addContition(Persona.PROP_ID, persona.getId(),ConditionMap.CRITERIO_OPER_IGUAL);
		if(persona.getPerDetalleNombre().trim().contentEquals(""))
			condiciones.addContition(Persona.PROP_ID, persona.getPerDetalleNombre(),ConditionMap.CRITERIO_OPER_LIKE);
		if(persona.getPerPrimerApellido().trim().contentEquals(""))
			condiciones.addContition(Persona.PROP_ID, persona.getPerPrimerApellido(),ConditionMap.CRITERIO_OPER_LIKE);
		this.personas.clear();
		return Constantes.URL_HOME_AGREGAR_BENEFICARIOS_ACCIONISTA;
	}	
	
	/*
	 * Asigna un registro de tipo persona 
	 * como relacion al accionista como beneficiario
	 */
	@GetMapping("/agregarBeneficiariosAccionista/configurar")
	public String irConfigurarBeneficiario(Model model,@RequestParam("idPersona")String idPersona) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		try {
			Persona tmp= new Persona(idPersona);
			tmp= this.personas.get(this.personas.indexOf(tmp));
			this.setPersona(tmp);			
			this.setViewState("2");
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return Constantes.URL_HOME_AGREGAR_BENEFICARIOS_ACCIONISTA+"?viewState=2";		
	}
	
	/*
	 * Asigna un registro de tipo persona 
	 * como relacion al accionista como beneficiario
	 */
	@GetMapping("/agregarBeneficiariosAccionista/editar")
	public String irEditarBeneficiario(Model model,@RequestParam("idBeneficiario")String idBeneficiario) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		try {
			this.beneficiario = new Beneficiario(new BeneficiarioPK(this.configurarBeneficiario.getAccionista().getId().getAccPerIdent(), this.configurarBeneficiario.getAccionista().getId().getAccEmpCodigo(), idBeneficiario));
			this.setBeneficiario(this.configurarBeneficiario.getAccionista().getBeneficiarios().get(this.configurarBeneficiario.getAccionista().getBeneficiarios().indexOf(this.beneficiario)));		
			this.setPersona(this.beneficiario.getPersona());
			this.persona.setBenValor(this.beneficiario.getBenValor().toString());
			this.persona.setBenNit(this.beneficiario.getBenNit());			
			this.setViewState("3");
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return Constantes.URL_HOME_AGREGAR_BENEFICARIOS_ACCIONISTA+"?viewState=3";
	}
	
	/*
	 * Asigna un registro de tipo persona 
	 * como relacion al accionista como beneficiario
	 */
	@PostMapping("/agregarBeneficiariosAccionista/agregar")
	public String agregarBeneficiario(Model model,@ModelAttribute("persona")Persona persona) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		try {
			beneficiario = new Beneficiario();
			boolean process = this.validarTerceros(persona,this.beneficiario,false);
			if(process) {
				this.agregarBeneficiario(persona,this.beneficiario);				
			}
			this.setViewState("0");
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		model.addAttribute(this.configurarBeneficiario.getAccionista());		
		return Constantes.URL_HOME_CONFIGURAR_BENEFICARIOS_ACCIONISTA+"?accPerIdent="+this.configurarBeneficiario.getAccionista().getId().getAccPerIdent()+"&accOfiCodigo="+this.configurarBeneficiario.getAccionista().getId().getAccEmpCodigo();
	}
	
	/*
	 * Asigna un registro de tipo persona 
	 * como relacion al accionista como beneficiario
	 */
	@PostMapping("/agregarBeneficiariosAccionista/actualizar")
	public String actualizarBenficiario(Model model,@ModelAttribute("persona")Persona persona) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		try {				
			this.beneficiario.setBenValor(new BigInteger(persona.getBenValor()));
			this.beneficiario.setBenNit(persona.getBenNit());
			this.genericoService.updateObject(this.getBeneficiario());
			this.setViewState("1");
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		model.addAttribute(this.configurarBeneficiario.getAccionista());		
		return Constantes.URL_HOME_CONFIGURAR_BENEFICARIOS_ACCIONISTA+"?accPerIdent="+this.configurarBeneficiario.getAccionista().getId().getAccPerIdent()+"&accOfiCodigo="+this.configurarBeneficiario.getAccionista().getId().getAccEmpCodigo();
	}
	
	@SuppressWarnings("unused")
	private boolean validarTerceros(Persona tmp,Beneficiario beneficiario, boolean crearNuevo) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		try {
			Dataheader dtheader= new Dataheader("79467856",9056, "CO", "10.0.12.13", "N");
			Referencia TipoIdent = (Referencia) this.genericoService.getObjetctById(Referencia.class, tmp.getPerTipoIdent());
			RespuestaTercerosREST respuesta = null;
			
			if(!tmp.getPerTipoIdent().contentEquals("PNI")) {
				logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), "Validando Natural"));
				DatosTercero dterc= new DatosTercero("CC", tmp.getId());
				Data data = new Data(dterc);
				RequestTerceros request = new RequestTerceros(dtheader,data);
				respuesta=tercero.consultaNatural(env.getProperty(Constantes.CONSULTA_NATURAL), request);
			}else if(tmp.getPerTipoIdent().contentEquals("PNI")){
				logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), "Validando Juridico"));
				logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), "Validando Natural"));
				DatosTercero dterc= new DatosTercero("NT", tmp.getId());
				Data data = new Data(dterc);
				RequestTerceros request = new RequestTerceros(dtheader,data);
				respuesta=tercero.consultaNatural(env.getProperty(Constantes.CONSULTA_JURIDICO), request);				
			}
			
			if(respuesta!=null)
				if(respuesta.getResponse().getDataHeader().getCodRespuesta().contentEquals(Constantes.CODIGO_OPERACION_EXITOSA)) {
					logger.info("Tercero Existe");
					if(crearNuevo)
						return this.crearPersona(tmp,beneficiario);
					return true;
				}else {					
					logger.info("Tercero No Existe");
					boolean continuar= this.crearTercero(tmp);
					if(continuar)
						this.crearPersona(tmp,beneficiario);
				}
			
		}catch (Exception e) {
			Log.getError(logger, e);
		}		
		return false;		
	}
	
	private boolean crearTercero(Persona persona) {
		Dataheader dtheader= new Dataheader("79467856",9056, "CO", "10.0.12.13", "N");
		RespuestaTercerosREST respuesta = null;
		DatosTercero dterc = null;
		if(persona.getPerTipoIdent().contentEquals("PCC")) {
			dterc= new DatosTercero("CC", persona.getId());
		}else if(persona.getPerTipoIdent().contentEquals("PNI")) {
			dterc= new DatosTercero("NT", persona.getId());
		}
		InfoGeneralTercero infoGenTerNatu = new InfoGeneralTercero();
		infoGenTerNatu.setPrimerNombre(persona.getPerDetalleNombre());		
		infoGenTerNatu.setPrimerApellido(persona.getPerPrimerApellido());
		infoGenTerNatu.setSegundoApellido(persona.getPerSegundoApellido());
		infoGenTerNatu.setSexo(new Sexo(persona.getGenero()));
		infoGenTerNatu.setDireccionResidencia(persona.getPerDireccion());
		infoGenTerNatu.setCiudadResidencia("5001");
		infoGenTerNatu.setTelefonoResidencia(persona.getPerTelefono());
		infoGenTerNatu.setCelular(persona.getPerTelefono2());
		infoGenTerNatu.setCorreoElectronico(persona.getEmail());
		infoGenTerNatu.setFechaNacimiento(new SimpleDateFormat(Utilidades.pattern_2).format(persona.getPerFNacimiento()));
		infoGenTerNatu.setAutorizaCompartirInf(persona.getAutorizaCompartirInf());
		infoGenTerNatu.setAutorizaRecibirInf(persona.getAutorizaRecibirInf());			
		TercerosNaturalInfo terNatInfo= new TercerosNaturalInfo(infoGenTerNatu);			
		Data data = new Data(dterc,terNatInfo);			
		RequestTerceros request = new RequestTerceros(dtheader,data);
		respuesta=tercero.consultaNatural(env.getProperty(Constantes.CREAR_NATURAL), request);
		
		if(respuesta!=null)
			if(respuesta.getResponse().getDataHeader().getCodRespuesta().contentEquals(Constantes.CODIGO_OPERACION_EXITOSA)) {
				return true;
			}
		
		return false;
	}
	
	/*
	 * Cargar finPersonas desde la BD
	 */
	public void loadData() {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));		
		if(this.personas.isEmpty())
			this.personas.addAll(this.comunidadService.getPersonasNoBeneficiarioAccionista(this.configurarBeneficiario.getAccionista()));
		if(this.catalogoNit.isEmpty())
			this.catalogoNit.addAll(this.comunidadService.catalogoNit());
	}

	/*
	 * Crear beneficiario pero 
	 * creando el finpersona desde cero, creando 
	 * el tercero y el finbeneficiario
	 */
	private boolean crearPersona(Persona persona,Beneficiario beneficiario){
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));		
		boolean respuesta= false;		
		Persona personaNueva= new Persona();
		personaNueva.setId(persona.getId());		
		personaNueva.setPerFCreacion(new Date());
		personaNueva.setPerTipoIdent(persona.getPerTipoIdent());
		personaNueva.setPerNaturaleza(persona.getPerNaturaleza());
		personaNueva.setPerDetalleNombre(persona.getPerDetalleNombre());
		personaNueva.setPerPrimerApellido(persona.getPerPrimerApellido());
		personaNueva.setPerSegundoApellido(persona.getPerSegundoApellido());		
		personaNueva.setPerDireccion(persona.getPerDireccion());		
		//Numero de telefono fijo
		personaNueva.setPerTelefono(persona.getPerTelefono());
		//Numero de celular
		personaNueva.setPerTelefono2(persona.getPerTelefono2());		
		personaNueva.setPerFNacimiento(persona.getPerFNacimiento());		
		personaNueva.setPerNombre(personaNueva.getPerDetalleNombre().concat(" ").concat(personaNueva.getPerPrimerApellido()).concat(" ").concat(personaNueva.getPerSegundoApellido()));		
		try {
			if(!this.personas.contains(personaNueva)) {		
				if(!this.comunidadService.existePersona(personaNueva)) {
					this.genericoService.saveObject(personaNueva);
					this.setMensaje(new MensajeVista(Constantes.RENDERIZAR, messages.getMessage("msg.persona.save.ok",null, null),Constantes.CORRECTO));
				}
			}
			respuesta=true;
		}catch (Exception e) {
			respuesta=false;
			Log.getError(logger, e);
		}
		
		/*
		 * agregar el FINBENEFICIARIOS
		 */
		if(respuesta)			
			 respuesta&=this.agregarBeneficiario(persona,beneficiario);		
		
		/*
		 * Preperar tercero para registro
		 */
		
		
		
		return respuesta;
		
	}		
	
	
	/*
	 * agreagar beneficiario pero 
	 * sin crear el finpersona desde cero
	 */
	private boolean agregarBeneficiario(Persona persona,Beneficiario beneficiario){
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));		
		BeneficiarioPK id= new BeneficiarioPK(this.configurarBeneficiario.getAccionista().getId().getAccPerIdent(), this.configurarBeneficiario.getAccionista().getId().getAccEmpCodigo(),persona.getId());
		beneficiario.setBenestado("A");			
		beneficiario.setId(id);
		beneficiario.setBenValor(new BigInteger(persona.getBenValor()));
		beneficiario.setBenNit(persona.getBenNit());
		try {
			if(!this.configurarBeneficiario.getAccionista().getBeneficiarios().contains(beneficiario)) {				
				this.genericoService.saveObject(beneficiario);
				this.configurarBeneficiario.getAccionista().getBeneficiarios().add(beneficiario);
				logger.info("Beneficiario agregago al accionista.");
			}
			return true;
		}catch (Exception e) {
			Log.getError(logger, e);
			return false;
		}		
	}	

	@ModelAttribute("usuarioLogin")
	public UsuarioLogin getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioLogin usuario) {
		this.usuario = usuario;
	}

	@ModelAttribute("personas")
	public List<Persona> getPersonas() {
		return personas;
	}

	@ModelAttribute("persona")
	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
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
	
	@ModelAttribute("catalogoNit")
	public List<Catalogo> getCatalogoNit() {
		return catalogoNit;
	}

	public void setCatalogoNit(List<Catalogo> catalogoNit) {
		this.catalogoNit = catalogoNit;
	}

	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

}
