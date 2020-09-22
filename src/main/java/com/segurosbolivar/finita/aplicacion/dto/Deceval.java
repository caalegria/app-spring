package com.segurosbolivar.finita.aplicacion.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Deceval {
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechInicial = new Date();
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechFinal = new Date();
	
	public Deceval() {
		// TODO Auto-generated constructor stub
	}

	public Date getFechInicial() {
		return fechInicial;
	}

	public void setFechInicial(Date fechInicial) {
		this.fechInicial = fechInicial;
	}

	public Date getFechFinal() {
		return fechFinal;
	}

	public void setFechFinal(Date fechFinal) {
		this.fechFinal = fechFinal;
	}	

}
