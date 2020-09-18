package com.segurosbolivar.finita.aplicacion.dto;

import com.segurosbolivar.finita.aplicacion.util.Constantes;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
public class CapsulaOperacion {

	public boolean estadoOperacion=false; 
	public String codigoOperacion=Constantes.CODIGO_OPERACION_ERROR;
	public String codigoOperacion_origen=Constantes.CODIGO_OPERACION_ERRO_TIPO_INTERNO;
	public Exception e;
	public MensajeVista mensaje;

	public CapsulaOperacion() {
		// TODO Auto-generated constructor stub
	}

	public CapsulaOperacion(boolean estadoOperacion, String codigoOperacion, String codigoOperacion_origen, Exception e,
			MensajeVista mensaje) {
		super();
		this.estadoOperacion = estadoOperacion;
		this.codigoOperacion = codigoOperacion;
		this.codigoOperacion_origen = codigoOperacion_origen;
		this.e = e;
		this.mensaje = mensaje;
	}

	public CapsulaOperacion(boolean estadoOperacion, String codigoOperacion, String codigoOperacion_origen,
			MensajeVista mensaje) {
		super();
		this.estadoOperacion = estadoOperacion;
		this.codigoOperacion = codigoOperacion;
		this.codigoOperacion_origen = codigoOperacion_origen;
		this.mensaje = mensaje;
	}

	public boolean isEstadoOperacion() {
		return estadoOperacion;
	}

	public void setEstadoOperacion(boolean estadoOperacion) {
		this.estadoOperacion = estadoOperacion;
	}

	public String getCodigoOperacion() {
		return codigoOperacion;
	}

	public void setCodigoOperacion(String codigoOperacion) {
		this.codigoOperacion = codigoOperacion;
	}

	public String getCodigoOperacion_origen() {
		return codigoOperacion_origen;
	}

	public void setCodigoOperacion_origen(String codigoOperacion_origen) {
		this.codigoOperacion_origen = codigoOperacion_origen;
	}

	public Exception getE() {
		return e;
	}

	public void setE(Exception e) {
		this.e = e;
	}

	public MensajeVista getMensaje() {
		return mensaje;
	}

	public void setMensaje(MensajeVista mensaje) {
		this.mensaje = mensaje;
	}
	
}
