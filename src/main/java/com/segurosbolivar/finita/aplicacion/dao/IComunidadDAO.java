package com.segurosbolivar.finita.aplicacion.dao;

import java.io.File;
import java.sql.Date;
import java.util.List;

import com.segurosbolivar.finita.aplicacion.dto.Catalogo;
import com.segurosbolivar.finita.aplicacion.dto.SaldoBeneficiario;
import com.segurosbolivar.finita.aplicacion.entity.Accionista;
import com.segurosbolivar.finita.aplicacion.entity.Beneficiario;
import com.segurosbolivar.finita.aplicacion.entity.LogCargues;
import com.segurosbolivar.finita.aplicacion.entity.Persona;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
public interface IComunidadDAO {

	List<SaldoBeneficiario> getSaldosBeneficiario();
	List<Beneficiario> getBeneficiariosPorAccionista(Accionista accionista);
	List<Persona> getPersonasNoBeneficiarioAccionista(Accionista accionista);
	boolean existePersona(Persona persona);
	List<Catalogo> catalogoNit();
	boolean getArchivoDeceval();
	List<LogCargues> getLogCargues();
	List<LogCargues> exportarCargue(java.sql.Date fecCargue);   
}
 