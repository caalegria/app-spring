package com.segurosbolivar.finita.aplicacion.dto;

public class Parametro {

	
	private Class<?> tipoParametro;
	private Object valorParametro;
	
	public Parametro() {
		// TODO Auto-generated constructor stub
	}	

	public Parametro(Object valorParametro) {
		super();
		this.valorParametro = valorParametro;
	}

	public Parametro(Class<?> tipoParametro, Object valorParametro) {
		super();
		this.tipoParametro = tipoParametro;
		this.valorParametro = valorParametro;
	}

	public Class<?> getTipoParametro() {
		return tipoParametro;
	}

	public void setTipoParametro(Class<?> tipoParametro) {
		this.tipoParametro = tipoParametro;
	}

	public Object getValorParametro() {
		return valorParametro;
	}

	public void setValorParametro(Object valorParametro) {
		this.valorParametro = valorParametro;
	}
	
	
}
