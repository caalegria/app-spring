package com.segurosbolivar.finita.aplicacion.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.segurosbolivar.finita.aplicacion.dto.Agrupador;
import com.segurosbolivar.finita.aplicacion.dto.AgrupadorCargueDividendos;
import com.segurosbolivar.finita.aplicacion.dto.AgrupadorLogCargue;
import com.segurosbolivar.finita.aplicacion.dto.AgrupadorLogFinita;
import com.segurosbolivar.finita.aplicacion.dto.ArchivoDeceval;
import com.segurosbolivar.finita.aplicacion.dto.Deceval;
import com.segurosbolivar.finita.aplicacion.dto.Parametro;
import com.segurosbolivar.finita.aplicacion.dto.UsuarioLogin;
import com.segurosbolivar.finita.aplicacion.entity.LogCargueFinita;
import com.segurosbolivar.finita.aplicacion.entity.LogCargues;
import com.segurosbolivar.finita.aplicacion.entity.RegistroPrincipal;
import com.segurosbolivar.finita.aplicacion.entity.rol.FinsgusuariosRoles;
import com.segurosbolivar.finita.aplicacion.repository.FinsgrolesRepository;
import com.segurosbolivar.finita.aplicacion.repository.FinsgusuariosRolesRepository;
import com.segurosbolivar.finita.aplicacion.service.FinsgusuariosService;
import com.segurosbolivar.finita.aplicacion.service.IComunidadService;
import com.segurosbolivar.finita.aplicacion.service.IGenericoService;
import com.segurosbolivar.finita.aplicacion.util.Constantes;
import com.segurosbolivar.finita.aplicacion.util.Log;
import com.segurosbolivar.finita.aplicacion.util.MediaTypeUtils;
import com.segurosbolivar.finita.aplicacion.util.Utilidades;

@Controller
@SessionAttributes("usuarioLogin")
public class DividendosCTL {

	public static final Logger logger = Logger.getLogger(DividendosCTL.class);

	@Autowired
	IGenericoService genericoService;

	@Autowired
	FinsgusuariosRolesRepository usersRolesRepository;

	@Autowired
	FinsgrolesRepository roleRepository;

	@Autowired
	IComunidadService comunidadService;

	@Autowired
	FinsgusuariosService userService;

	@Autowired
	private ServletContext servletContext;

	private UsuarioLogin usuario= new UsuarioLogin();
	private Deceval filtroBusqueda= new Deceval();
	private List<LogCargues> logCargues= new ArrayList<LogCargues>();
	private String ERROR ="";	
	private String success ="";
	private String error ="";
	private boolean bandLoadData = false;
	private boolean bandPost = false;
	private List<AgrupadorCargueDividendos> cargueDividendos= new ArrayList<AgrupadorCargueDividendos>();
	private List<AgrupadorLogFinita> logsDeFinita= new ArrayList<AgrupadorLogFinita>();
	private List<AgrupadorLogCargue> logsDeCargue= new ArrayList<AgrupadorLogCargue>();	

	//paginacion
	int paginaActual = 0;
	private boolean bandPag = false;

	@PostConstruct
	private void init() {
		logger.info(Log.logStartBeans(this.getClass().getName()));
	}

	@GetMapping("/cargueDividendos")
	public String userForm(Model model) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		bandPost = false;

		//validacion wizard
		model.addAttribute("OPC_CARGUE",Constantes.OPC_CARGUE);
		model.addAttribute("OPC_VALIDACION",Constantes.OPC_VALIDACION);

		//@generico: validacion filtro busquedad - inicio - paginacion
		if( !bandLoadData &&  !bandPag) {
			this.loadDataCargues();
			this.setERROR("");
			model.addAttribute("ERROR",ERROR);
			paginacionUtil(0,model);
			bandPost = true;

		}else {
			paginacionUtil(paginaActual,model);
			bandLoadData = false;
			bandPag = false;
			setPaginaActual(0);	
		}

		//inicio posterior a logeo unicamente
		/*if(this.usuario.getUsername().isEmpty() || this.usuario.getUsername() ==null) {
			return menuDiferenciado(model);
		} else {
			return Constantes.URL_HOME_SUPERUSER;
		}*/
		return menuDiferenciado(model);

	}

	/**
	 * @Generico:  obtencion numero paginas url - seccion paginacion carguesDividendo.html
	 **/
	@GetMapping("/paginacion")
	public String paginacion ( @RequestParam Map<String, Object> params ) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));

		try {
			int numPag = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
			this.setPaginaActual(numPag);
			bandPag = true;

		}catch(Exception e) {
			logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), e.getMessage().toString()));
		}

		return "redirect:/cargueDividendos";
	}


	@PostMapping("/filtroCargues")
	public String buscarDividendos(Model model, @ModelAttribute("filtroBusqueda") Deceval filtroBusqueda) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		List<LogCargues> resultado = new ArrayList<LogCargues>();
		this.ERROR = "";

		if(!bandPost)
			this.loadDataCargues();

		try {
			if (!this.logCargues.isEmpty() && filtroBusqueda.getFechFinal() != null && filtroBusqueda.getFechInicial() != null) {
				Parametro fecIni= new Parametro(java.sql.Date.class, new java.sql.Date(filtroBusqueda.getFechInicial().getTime()));
				Parametro fecFinal= new Parametro(java.sql.Date.class, new java.sql.Date(filtroBusqueda.getFechFinal().getTime()));		

				for (LogCargues tmp : this.logCargues) {
					if ((tmp.getFechaCargue().compareTo((Date)fecIni.getValorParametro()) > 0 || tmp.getFechaCargue().compareTo((Date)fecIni.getValorParametro()) == 0 ) && 
							(tmp.getFechaCargue().compareTo((Date)fecFinal.getValorParametro()) < 0 || tmp.getFechaCargue().compareTo((Date)fecFinal.getValorParametro()) == 0 ) ) 
						resultado.add(tmp);	
				}

				if(resultado.isEmpty())
					this.setERROR(Constantes.SIN_RESUTADO_DIVIDENDO);

			}else {
				this.setERROR(Constantes.INGRESAR_FECHA);
			}

		} catch (Exception e) {
			logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), e.getMessage().toString()));
		}

		this.setLogCargues(resultado);
		bandLoadData = true;
		return "redirect:/cargueDividendos";
	}

	@GetMapping(value = "/descargaPlano/{fecha}")	
	public ResponseEntity<InputStreamResource>  descargaPlano(Model model, @PathVariable java.sql.Date fecha)  {

		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		String nombFile = Constantes.FILE_DIVIDENDOS+fecha.toString()+".csv";
		MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, nombFile);
		File file = new File(Utilidades.rutaTemporal()+nombFile);
		InputStreamResource resource = null;

		try {

			file.createNewFile();
			loadDescarga(file,fecha);
			resource = new InputStreamResource(new FileInputStream(file)); 
			file.delete();

		}catch (Exception e) {
			Log.getError(logger, e);
		}

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
				.contentType(mediaType).contentLength(file.length()).body(resource);
	}

	@GetMapping(value="/download")
	public ResponseEntity<InputStreamResource> downloadFile1(@RequestParam("index")int index,@RequestParam("idLista")int idLista) throws IOException {		
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));

		Agrupador tmpAgrupador= null;
		MediaType mediaType = null;
		File file = null;
		InputStreamResource resource= null;

		switch (idLista) {
		case 1:
			tmpAgrupador= this.cargueDividendos.get(index);
			mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, tmpAgrupador.getNombreArchivo()); 
			file = new File(tmpAgrupador.getRutaArchivoOriginal());
			resource = new InputStreamResource(new FileInputStream(file));						
			break;
		case 2:
			tmpAgrupador=this.logsDeFinita.get(index);
			mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, tmpAgrupador.getNombreArchivo()); 
			file = new File(tmpAgrupador.getRutaArchivoOriginal());
			resource = new InputStreamResource(new FileInputStream(file));			
			break;
		case 3:
			tmpAgrupador= this.logsDeCargue.get(index);
			mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, tmpAgrupador.getNombreArchivo()); 
			file = new File(tmpAgrupador.getRutaArchivoOriginal());
			resource = new InputStreamResource(new FileInputStream(file));			
			break;

		default:
			break;
		}

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName()).contentType(mediaType).contentLength(file.length()).body(resource);
	}

	/**
	 * Util: consulta log cargues
	 **/
	public void loadDataCargues() {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		this.logCargues.clear();
		if(this.logCargues.isEmpty()) {
			this.logCargues.addAll(this.comunidadService.getLogCargues());
		}

		/**
		 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
		 * Inclusion data de tabla FIN_REG_PRINCIPAL, de la tabla LOG_CARGUES y de la tabla LOG_CARGUE_FINITA
		 * para obtener datos de cargue archivo original dividendos, log de cargue y logs de validacion de archivo deceval
		 * 
		 */
		this.loadDataArchivoDeceval();
		this.loadDataLogFinita();
		this.loadDataLogDeCargues();
	}

	/*
	 * 
	 */
	@SuppressWarnings("unchecked")
	protected void loadDataArchivoDeceval() {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		HashMap<String, AgrupadorCargueDividendos> mapaCargueDividendos= new HashMap<String, AgrupadorCargueDividendos>();
		List<RegistroPrincipal> dataDividendos= new ArrayList<RegistroPrincipal>();
		try {
			dataDividendos.addAll((Collection<? extends RegistroPrincipal>) this.genericoService.findObjects(RegistroPrincipal.class, null, null, null));
			for(RegistroPrincipal registro:dataDividendos) {
				try{
					String formatDateAgrupador= Utilidades.simpleDateFormat_2.format(registro.getFecha_cargue());
					if(!mapaCargueDividendos.containsKey(formatDateAgrupador)) {
						AgrupadorCargueDividendos agrupador= new AgrupadorCargueDividendos(formatDateAgrupador);				
						mapaCargueDividendos.put(formatDateAgrupador, agrupador);
					}			
					mapaCargueDividendos.get(formatDateAgrupador).getData().add(registro.toString());
				}catch (Exception e) {
					Log.getError(logger, e);
				}
			}		

			this.cargueDividendos.clear();
			this.cargueDividendos.addAll(mapaCargueDividendos.values());
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		this.escribirArchivoDeceval(mapaCargueDividendos);		
	}

	/*
	 * 
	 */
	@SuppressWarnings("unchecked")
	protected void loadDataLogFinita() {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		HashMap<String, AgrupadorLogFinita> mapaLogFinita= new HashMap<String, AgrupadorLogFinita>();
		List<LogCargueFinita> dataLogFinita= new ArrayList<LogCargueFinita>();
		try {
			dataLogFinita.addAll((Collection<? extends LogCargueFinita>) this.genericoService.findObjects(LogCargueFinita.class, null, null, null));
			for(LogCargueFinita registro:dataLogFinita) {
				try{
					String formatDateAgrupador= Utilidades.simpleDateFormat_2.format(registro.getFechaProceso());
					if(!mapaLogFinita.containsKey(formatDateAgrupador)) {
						AgrupadorLogFinita agrupador=new AgrupadorLogFinita(formatDateAgrupador);				
						mapaLogFinita.put(formatDateAgrupador, agrupador);
					}			
					mapaLogFinita.get(formatDateAgrupador).getData().add(registro.toString());
				}catch (Exception e) {
					Log.getError(logger, e);
				}
			}	
			this.logsDeFinita.clear();
			this.logsDeFinita.addAll(mapaLogFinita.values());
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		this.escribirLogFinita(mapaLogFinita);		
	}

	/*
	 * 
	 */
	@SuppressWarnings("unchecked")
	protected void loadDataLogDeCargues() {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		HashMap<String, AgrupadorLogCargue> mapaLogDeCargue= new HashMap<String, AgrupadorLogCargue>();
		List<LogCargues> dataLogDeCargue= new ArrayList<LogCargues>();
		try {			
			dataLogDeCargue.addAll((Collection<? extends LogCargues>) this.genericoService.findObjects(LogCargues.class, null, null, null));
			for(LogCargues registro:dataLogDeCargue) {
				try{
					String formatDateAgrupador= Utilidades.simpleDateFormat_2.format(registro.getFechaCargue());
					if(!mapaLogDeCargue.containsKey(formatDateAgrupador)) {
						AgrupadorLogCargue agrupador= new AgrupadorLogCargue(formatDateAgrupador);				
						mapaLogDeCargue.put(formatDateAgrupador, agrupador);
					}			
					mapaLogDeCargue.get(formatDateAgrupador).getData().add(registro.toString());
				}catch (Exception e) {
					Log.getError(logger, e);
				}
			}	
			this.logsDeCargue.clear();
			this.logsDeCargue.addAll(mapaLogDeCargue.values());
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		this.escribirLogCargue(mapaLogDeCargue);		
	}


	/*
	 * Escribir archivos Originales Deceval
	 */
	protected void escribirArchivoDeceval(HashMap<String, AgrupadorCargueDividendos> mapaCargueDividendos) {

		for( AgrupadorCargueDividendos objeto: mapaCargueDividendos.values()) {
			objeto.renombarArchivo();
			objeto.setExtension(Constantes.TXT);
			objeto.setRutaArchivoOriginal(Utilidades.rutaTemporal().concat(objeto.getNombreArchivo()).concat(Constantes.TXT));
			if(Utilidades.escribirArchivoPlano(objeto.getData(), objeto.getNombreArchivo(),Constantes.TXT,ArchivoDeceval.cabezera()))
				logger.info("El archivo "+objeto.getRutaArchivoOriginal()+" se pudo generar");
			else
				logger.info("El archivo "+objeto.getRutaArchivoOriginal()+" no se pudo generar");			
		}		
	}

	/*
	 * Escribir de log de finita
	 */
	protected void escribirLogFinita(HashMap<String, AgrupadorLogFinita> mapaLogFinita) {

		for( AgrupadorLogFinita objeto: mapaLogFinita.values()) {
			objeto.renombarArchivo();
			objeto.setExtension(Constantes.TXT);
			objeto.setRutaArchivoOriginal(Utilidades.rutaTemporal().concat(objeto.getNombreArchivo()).concat(Constantes.TXT));
			if(Utilidades.escribirArchivoPlano(objeto.getData(), objeto.getNombreArchivo(),Constantes.TXT,null))
				logger.info("El archivo "+objeto.getRutaArchivoOriginal()+" se pudo generar");
			else
				logger.info("El archivo "+objeto.getRutaArchivoOriginal()+" no se pudo generar");			
		}		
	}

	/*
	 * Escribir de log de cargue
	 */
	protected void escribirLogCargue(HashMap<String, AgrupadorLogCargue> mapaLogDeCargue) {

		for( AgrupadorLogCargue objeto: mapaLogDeCargue.values()) {
			objeto.renombarArchivo();
			objeto.setExtension(Constantes.TXT);
			objeto.setRutaArchivoOriginal(Utilidades.rutaTemporal().concat(objeto.getNombreArchivo()).concat(Constantes.TXT));
			if(Utilidades.escribirArchivoPlano(objeto.getData(), objeto.getNombreArchivo(),Constantes.TXT,null))
				logger.info("El archivo "+objeto.getRutaArchivoOriginal()+" se pudo generar");
			else
				logger.info("El archivo "+objeto.getRutaArchivoOriginal()+" no se pudo generar");			
		}		
	}

	/**
	 * Util: Descarga archivo plano dividendos
	 **/
	public void loadDescarga(File file, java.sql.Date fecCargue) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));

		// Reemplazar tipo dato, tabla: cargue_archivo_dividendo
		try {
			List<LogCargues> resultado = new ArrayList<>();
			resultado.addAll(this.comunidadService.exportarCargue(fecCargue));

			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);

			if (!resultado.isEmpty()) {
				for (LogCargues temp : resultado) {
					StringBuffer linea = new StringBuffer("");
					linea.append(temp.getFechaCargue().toString()).append(";").append(temp.getTipoCargue()).append(";")
					.append(temp.getDescripcion());
					bw.write(linea.toString());
					bw.newLine();
				}
			}
			bw.close();
			fw.close();

		} catch (Exception e) {
			Log.getError(logger, e);
		}
	}

	/**
	 * Util:  menu diferenciado
	 **/
	public String menuDiferenciado(Model model) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));		
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			this.usuario.setUsername(userDetails.getUsername());
			FinsgusuariosRoles usersRoles = usersRolesRepository.findByUxrUsuario(usuario.getUsername());
			String rol = usersRoles.getFinsgroles().getRolCodigo();
			Utilidades.datosDeLogin(model, usuario);

			if (rol.equalsIgnoreCase("SUPERUSUARIO")) {
				usuario.setRol("SuperUsuario");
				//model.addAttribute("RolAut", "SuperUsuario");
				return Constantes.URL_HOME_SUPERUSER;

			}
			if (rol.equalsIgnoreCase("CONTADOR")) {
				usuario.setRol("Contador");
				//model.addAttribute("RolAut", "Contador");
				//return Constantes.URL_HOME_CONTADOR;
				return Constantes.URL_HOME_SUPERUSER;

			} else {
				// Rol consultas no esta dentro del alcance
				return "redirect:/";
			}
			

		} catch (Exception e) {
			logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), e.getMessage().toString()));
		}
		return "redirect:/logout";
	}

	/**
	 * @Generico: paginacion
	 * numPag: numero pagina actual
	 * model: binding carguesDividnedo.html
	 **/
	public void paginacionUtil(int numPag, Model model) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));

		try {
			PageRequest pageReq = PageRequest.of(numPag, 10);

			int totalPag = logCargues.size() % 10 == 0 ? logCargues.size() / 10 : logCargues.size() / 10 + 1;
			int max = numPag + 1 >= totalPag ? logCargues.size() : 10 * (numPag + 1);
			int min = numPag + 1 > totalPag ? max : 10 * numPag;
			Page<LogCargues> pageLogCargue = new PageImpl<>(this.logCargues.subList(min, max), pageReq,
					logCargues.size());

			if (totalPag > 0) {
				List<Integer> pags = IntStream.range(1, totalPag + 1).boxed().collect(Collectors.toList());
				model.addAttribute("pages", pags);

			}

			model.addAttribute("list", pageLogCargue.getContent());
			model.addAttribute("current", numPag + 1);
			model.addAttribute("next", numPag + 2);
			model.addAttribute("prev", numPag);
			model.addAttribute("last", totalPag);

		} catch (Exception e) {
			logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), e.getMessage().toString()));
		}

	}


	@ModelAttribute("logCargues")
	public List<LogCargues> getLogCargues() {
		return logCargues;
	}

	public void setLogCargues(List<LogCargues> logCargues) {
		this.logCargues = logCargues;
	}

	@ModelAttribute("filtroBusqueda")
	public Deceval getFiltroBusqueda() {
		return filtroBusqueda;
	}

	public void setFiltroBusqueda(Deceval filtro) {
		this.filtroBusqueda = filtro;
	}

	@ModelAttribute("usuarioLogin")
	public UsuarioLogin getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioLogin usuario) {
		this.usuario = usuario;
	}

	@ModelAttribute("ERROR")
	public String getERROR() {
		return ERROR;
	}

	public void setERROR(String eRROR) {
		ERROR = eRROR;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public void setPaginaActual(int paginaActual) {
		this.paginaActual = paginaActual;
	}

	@ModelAttribute("cargueDividendos")
	public List<AgrupadorCargueDividendos> getCargueDividendos() {
		return cargueDividendos;
	}

	public void setCargueDividendos(List<AgrupadorCargueDividendos> cargueDividendos) {
		this.cargueDividendos = cargueDividendos;
	}

	@ModelAttribute("logsDeFinita")
	public List<AgrupadorLogFinita> getLogsDeFinita() {
		return logsDeFinita;
	}

	public void setLogsDeFinita(List<AgrupadorLogFinita> logsDeFinita) {
		this.logsDeFinita = logsDeFinita;
	}

	@ModelAttribute("logsDeCargue")
	public List<AgrupadorLogCargue> getLogsDeCargue() {
		return logsDeCargue;
	}

	public void setLogsDeCargue(List<AgrupadorLogCargue> logsDeCargue) {
		this.logsDeCargue = logsDeCargue;
	}

	@ModelAttribute("success")
	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	@ModelAttribute("error")
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}



}
