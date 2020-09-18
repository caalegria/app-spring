package com.segurosbolivar.finita.aplicacion.util;

/**
 * 
 */
import com.segurosbolivar.finita.aplicacion.entity.Persona;
import com.segurosbolivar.objetos.json.InfoGeneralTercero;
import com.segurosbolivar.objetos.json.Rol;
import com.segurosbolivar.objetos.json.Sexo;

public class FuncionesComunes {


	public InfoGeneralTercero prepararTerceroJuridico() {
		InfoGeneralTercero infoGenTerJur = new InfoGeneralTercero();
		infoGenTerJur.setRol(new Rol("1"));
		infoGenTerJur.setRazonSocial("TUTU 99 SOFTWARE SA");
		infoGenTerJur.setDireccionOficina("CARRERA 44 A # 44 A - 44");
		infoGenTerJur.setCiudadOficina("11001");
		infoGenTerJur.setTelefonoOficina("6407444");
		infoGenTerJur.setCorreoElectronico("RH44@TUTUSESOFTWARE.COM");
		infoGenTerJur.setAutorizaCompartirInf("S");
		infoGenTerJur.setAutorizaRecibirInf("S");	

		return infoGenTerJur;
	}

	public InfoGeneralTercero prepararTerceroNatural(Persona persona) {
		InfoGeneralTercero infoGenTerNatu = new InfoGeneralTercero();		
		infoGenTerNatu.setPrimerNombre(persona.getPerDetalleNombre());		
		infoGenTerNatu.setPrimerApellido(persona.getPerPrimerApellido());
		infoGenTerNatu.setSegundoApellido(persona.getPerSegundoApellido());
		infoGenTerNatu.setSexo(new Sexo(persona.getGenero().toUpperCase()));
		infoGenTerNatu.setDireccionResidencia(persona.getPerDireccion());
		infoGenTerNatu.setCiudadResidencia("5001");
		infoGenTerNatu.setTelefonoResidencia(persona.getPerTelefono());
		infoGenTerNatu.setCelular(persona.getPerTelefono2());
		infoGenTerNatu.setCorreoElectronico(persona.getEmail());
		infoGenTerNatu.setFechaNacimiento(persona.getPerFNacimiento().toString());
		infoGenTerNatu.setAutorizaCompartirInf("N");
		infoGenTerNatu.setAutorizaRecibirInf("N");	

		return  infoGenTerNatu;
	}
}
