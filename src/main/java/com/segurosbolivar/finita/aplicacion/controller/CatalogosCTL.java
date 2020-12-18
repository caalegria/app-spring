package com.segurosbolivar.finita.aplicacion.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.segurosbolivar.finita.aplicacion.entity.Persona;
import com.segurosbolivar.finita.aplicacion.entity.Referencia;
import com.segurosbolivar.finita.aplicacion.service.IGenericoService;
import com.segurosbolivar.finita.aplicacion.util.Log;
/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
@Controller
public class CatalogosCTL {

	public static final Logger logger = Logger.getLogger(CatalogosCTL.class);

	@Autowired
	IGenericoService genericoService;

	private HashMap<String, List<Referencia>> dataRerencias= new HashMap<String, List<Referencia>>();
	
	private List<Referencia> periodicidad=new ArrayList<Referencia>();
	private List<Referencia> tipoPago= new ArrayList<Referencia>();
	private HashMap<String, Persona> mapaPersonas= new HashMap<String, Persona>();
	private HashMap<String, Referencia> mapaReferencias= new HashMap<String, Referencia>();
	
	public CatalogosCTL() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void init() {
		logger.info(Log.logStartBeans(this.getClass().getName()));
		this.loadDataCatalogos();
		this.loadMapaReferencias();
		this.loadMapaPersonas();
	}
	
	public List<Referencia> getPeriodicidad() {		
		return periodicidad;
	}	
	
	public List<Referencia> getTipoPago() {		
		return tipoPago;
	}

	public void setTipoPago(List<Referencia> tipoPago) {
		this.tipoPago = tipoPago;
	}

	public void setPeriodicidad(List<Referencia> periodicidad) {
		this.periodicidad = periodicidad;
	}	
	
	public  void loadDataCatalogos(){
		@SuppressWarnings("unchecked")
		List< Referencia> data = (List<Referencia>) this.genericoService.getObjects(Referencia.class);
		for(Referencia dat:data) {
			String clave=claveSinNumeros(dat.getRefCodigo());
			try {
				if(!dataRerencias.containsKey(clave)) 
					dataRerencias.put(clave, new ArrayList<Referencia>());				

				dataRerencias.get(clave).add(dat);
			}catch (Exception e) {
				Log.getError(logger, e);
				continue ;
			}
		}
	}
	
	private String claveSinNumeros(String codigo) {		
		 char [] data=codigo.toCharArray();
		 String claveFin="";
		for(char objeto: data ) {
			if(Character.isLetter(objeto))
				claveFin=claveFin.concat(String.valueOf(objeto));
			if(Character.isDigit(objeto))
				return claveFin; 
		}		
		return claveFin;	
	}

	public List<Referencia> devolverDataPorCodigo(String codigoIniciales){
		return this.dataRerencias.get(codigoIniciales);
	}
	
	@SuppressWarnings("unchecked")
	protected void loadMapaPersonas() {
		logger.info(Log.getCurrentClassAndMethodNames(this.getClass().getName(), ""));
		List<Persona> dataTemp= (List<Persona>) this.genericoService.getObjects(Persona.class);
		for(Persona per:dataTemp) {
			this.mapaPersonas.put(per.getId(), per);
		}
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

	public HashMap<String, Persona> getMapaPersonas() {
		return mapaPersonas;
	}

	public void setMapaPersonas(HashMap<String, Persona> mapaPersonas) {
		this.mapaPersonas = mapaPersonas;
	}

	public HashMap<String, Referencia> getMapaReferencias() {
		return mapaReferencias;
	}

	public void setMapaReferencias(HashMap<String, Referencia> mapaReferencias) {
		this.mapaReferencias = mapaReferencias;
	}

}
