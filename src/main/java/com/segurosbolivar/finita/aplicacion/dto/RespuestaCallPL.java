package com.segurosbolivar.finita.aplicacion.dto;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
public class RespuestaCallPL {
	
	private String codigo;
	private String mensaje;
	
	public RespuestaCallPL() {
		// TODO Auto-generated constructor stub
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public String toString() {
		return "RespuestaCallPL [codigo=" + codigo + ", mensaje=" + mensaje + "]";
	}	
}
