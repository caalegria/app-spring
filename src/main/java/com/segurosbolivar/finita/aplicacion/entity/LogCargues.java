package com.segurosbolivar.finita.aplicacion.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "LOG_CARGUES")
public class LogCargues extends Persistente{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "FECHA_CARGUE")
	private Date fechaCargue;
	

	@Column(name = "DESCRIPCION", nullable = false, length = 200)
	private String descripcion;
	
	
	@Column(name = "TIPO_CARGUE", nullable = false, length = 25)
	private String tipoCargue;
	

	@DateTimeFormat(pattern="dd-MMM-YYYY")
	public Date getFechaCargue() {
		return fechaCargue;
	}
	
	public void setFechaCargue(Date fechaCargue) {
		this.fechaCargue = fechaCargue;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTipoCargue() {
		return tipoCargue;
	}
	public void setTipoCargue(String tipoCargue) {
		this.tipoCargue = tipoCargue;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaCargue == null) ? 0 : fechaCargue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Referencia))
			return false;
		LogCargues other = (LogCargues) obj;
		if (fechaCargue == null) {
			if (other.fechaCargue != null)
				return false;
		} else if (!fechaCargue.equals(other.fechaCargue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Finreferencia [fechaCargue=" + fechaCargue + ", descripcion=" + descripcion + ", tipoCargue=" + tipoCargue+ "]";
	}	


}
