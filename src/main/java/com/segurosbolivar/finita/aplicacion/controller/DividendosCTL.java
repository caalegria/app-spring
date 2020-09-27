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

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
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

	@PostConstruct
	private void init() {
		logger.info(Log.logStartBeans(this.getClass().getName()));
	}
	
	@GetMapping("/cargueDividendos")
	public String userForm(Model model ) {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		bandPost = false;
		this.usuario.setUsername(userDetails.getUsername());
		
		
		model.addAttribute("OPC_CARGUE",Constantes.OPC_CARGUE);
		model.addAttribute("OPC_VALIDACION",Constantes.OPC_VALIDACION);
		
		if( !bandLoadData ) {
			this.loadDataCargues();
			model.addAttribute("ERROR","");
			bandPost = true;
			
		}else {
			bandLoadData = false;
		}

		FinsgusuariosRoles usersRoles = usersRolesRepository.findByUxrUsuario(usuario.getUsername());
		String rol = usersRoles.getFinsgroles().getRolCodigo();
		Utilidades.datosDeLogin(model,usuario);

		
		if(rol.equalsIgnoreCase("SUPERUSUARIO")) {
			usuario.setRol("SuperUsuario");
			model.addAttribute("RolAut","SuperUsuario");
			return "user-superusuario/home";
			
			
		}if (rol.equalsIgnoreCase("CONTADOR")){
			usuario.setRol("SuperUsuario");
			model.addAttribute("RolAut","Contador");
			return "user-contador/home";
			
		}else {
			//Rol consultas no esta dentro del alcance
			return "/";
		}
		
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
					this.setERROR("Busquedad sin resultados");
				
			}else {
				this.setERROR("Ingrese un rango de fechas");
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
		String nombFile = "cargueDividendos_"+fecha.toString()+".csv";
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
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName()).contentType(mediaType).contentLength(file.length()).body(resource);
	}
	
	/**
	 * Util
	 **/
	public void loadDataCargues() {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		this.logCargues.clear();
		if(this.logCargues.isEmpty()) {
			this.logCargues.addAll(this.comunidadService.getLogCargues());
		}
	}
	
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
	
	@ModelAttribute("userLogin")
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


}
