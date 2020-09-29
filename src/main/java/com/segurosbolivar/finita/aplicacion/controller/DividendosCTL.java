package com.segurosbolivar.finita.aplicacion.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.segurosbolivar.finita.aplicacion.Exception.CustomeFieldValidationException;
import com.segurosbolivar.finita.aplicacion.dto.Deceval;
import com.segurosbolivar.finita.aplicacion.dto.Parametro;
import com.segurosbolivar.finita.aplicacion.dto.UsuarioLogin;
import com.segurosbolivar.finita.aplicacion.entity.rol.Finsgusuarios;
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
import com.segurosbolivar.finita.aplicacion.entity.LogCargues;

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
	private boolean bandLoadData = false;
	private boolean bandPost = false;
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
	
	/**
	 * Util: consulta log cargues
	 **/
	public void loadDataCargues() {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		this.logCargues.clear();
		if(this.logCargues.isEmpty()) {
			this.logCargues.addAll(this.comunidadService.getLogCargues());
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

			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			String username = userDetails.getUsername();
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



}
