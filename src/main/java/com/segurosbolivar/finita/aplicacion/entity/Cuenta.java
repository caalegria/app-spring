package com.segurosbolivar.finita.aplicacion.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
@Entity
@Table(name="FINCUENTAS")
public class Cuenta extends Persistente {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CTA_CUENTA",length = 16)
	private String id="";

	@Column(name="CTA_SINONIMO",length = 60)
	private String descripcion;

	@Column(name="CTA_FECHA_CREACION")	
	private Date fechaCreacion;

	@Column(name="CTA_FECHA_MODIFICACION")
	private Date fechaUltimaModificacion;

	@Column(name="CTA_ESTADO")
	private char estado;


	public Cuenta() {
		// TODO Auto-generated constructor stub
	}	
	
	public Cuenta(String id) {
		super();
		this.id = id;
	}

	public Cuenta(String id, String descripcion, Date fechaCreacion, Date fechaUltimaModificacion, char estado) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaModificacion = fechaUltimaModificacion;
		this.estado = estado;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@DateTimeFormat(pattern="dd-MMM-YYYY")
	public Date getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@DateTimeFormat(pattern="dd-MMM-YYYY")
	public Date getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}


	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}


	public char getEstado() {
		return estado;
	}


	public void setEstado(char estado) {
		this.estado = estado;
	}



	@Override
	public String toString() {
		return "Fincuentas [id=" + id + ", descripcion=" + descripcion + ", fechaCreacion=" + fechaCreacion
				+ ", fechaUltimaModificacion=" + fechaUltimaModificacion + ", estado=" + estado + "]";
	}


	@Override
	public boolean equals(Object obj) {
		if(null == obj)return false;
		if(!(obj instanceof Cuenta))return false;
		else {
			Cuenta objeto= (Cuenta) obj;
			if(null == this.getId()|| null== objeto.getId())return false;
			else return (this.getId().equals(objeto.getId()));
		}
	}

}
