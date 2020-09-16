package com.segurosbolivar.finita.aplicacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.segurosbolivar.finita.aplicacion.dao.IComunidadDAO;
import com.segurosbolivar.finita.aplicacion.dto.Catalogo;
import com.segurosbolivar.finita.aplicacion.dto.SaldoBeneficiario;
import com.segurosbolivar.finita.aplicacion.entity.Accionista;
import com.segurosbolivar.finita.aplicacion.entity.Beneficiario;
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

}
