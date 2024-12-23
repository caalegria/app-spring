package com.segurosbolivar.finita.aplicacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.segurosbolivar.finita.aplicacion.dao.IComunidadDAO;
import com.segurosbolivar.finita.aplicacion.dto.Catalogo;
import com.segurosbolivar.finita.aplicacion.dto.SaldoBeneficiario;
import com.segurosbolivar.finita.aplicacion.entity.Accionista;
import com.segurosbolivar.finita.aplicacion.entity.Beneficiario;
import com.segurosbolivar.finita.aplicacion.entity.LogCargues;
import com.segurosbolivar.finita.aplicacion.entity.Persona;


@Service
public class ComunidadService implements IComunidadService {

	@Autowired
	private IComunidadDAO iComunidadDAO;
	

	@Override
	public 	List<SaldoBeneficiario> getSaldosBeneficiario(){
		return this.iComunidadDAO.getSaldosBeneficiario();
	}
	
	@Override
	public List<Beneficiario> getBeneficiariosPorAccionista(Accionista accionista){
		return this.iComunidadDAO.getBeneficiariosPorAccionista(accionista);
	}
	
	@Override
	public List<Persona> getPersonasNoBeneficiarioAccionista(Accionista accionista){
		return this.iComunidadDAO.getPersonasNoBeneficiarioAccionista(accionista);
	}

	@Override
	public boolean existePersona(Persona persona) {
		return this.iComunidadDAO.existePersona(persona);
	}
	
	@Override
	public List<Catalogo> catalogoNit(){
		return this.iComunidadDAO.catalogoNit();
	}
	
	@Override
	public boolean getArchivoDeceval(){
		return this.iComunidadDAO.getArchivoDeceval();
	}
	
	@Override
	public List<LogCargues> getLogCargues(){
		return this.iComunidadDAO.getLogCargues();
	}
	
	/*Sustituir cuando se conozca la tabla de cargues de dividendos*/
	@Override
	public List<LogCargues> exportarCargue(java.sql.Date fecCargue){
		return this.iComunidadDAO.exportarCargue(fecCargue);
	}

}
