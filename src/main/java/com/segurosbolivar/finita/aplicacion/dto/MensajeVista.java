package com.segurosbolivar.finita.aplicacion.dto;

import com.segurosbolivar.finita.aplicacion.util.Constantes;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
public class MensajeVista {
	
	public String render=Constantes.RENDERIZAR;
	public String tipo=Constantes.INFO;
	public String mensaje="";
	
	public MensajeVista() {
		// TODO Auto-generated constructor stub
	}
	
	public MensajeVista(String render, String mensaje,String tipo) {	
		this.render = render;
		this.mensaje = mensaje;
		this.tipo=tipo;
	}		

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getRender() {
		return render;
	}

	public void setRender(String render) {
		this.render = render;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}	

}
