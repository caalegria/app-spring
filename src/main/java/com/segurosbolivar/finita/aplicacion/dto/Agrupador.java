package com.segurosbolivar.finita.aplicacion.dto;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
public class Agrupador {
	
	private String fechaCargue;	
	private List<String> data = new ArrayList<String>();
	private String rutaArchivoOriginal;
	private String nombreArchivo;
	private String extension;
	
	public Agrupador() {
		// TODO Auto-generated constructor stub
	}	

	public Agrupador(String fechaCargue) {
		super();
		this.fechaCargue = fechaCargue;
	}
	
	public Agrupador(String fechaCargue, String rutaArchivoOriginal) {
		super();
		this.fechaCargue = fechaCargue;		
		this.rutaArchivoOriginal = rutaArchivoOriginal;
	}
	
	public String getFechaCargue() {
		return fechaCargue;
	}

	public void setFechaCargue(String fechaCargue) {
		this.fechaCargue = fechaCargue;
	}

	public String getRutaArchivoOriginal() {
		return rutaArchivoOriginal;
	}

	public void setRutaArchivoOriginal(String rutaArchivoOriginal) {
		this.rutaArchivoOriginal = rutaArchivoOriginal;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}	

	public String getNombreArchivo() {		
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}	

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
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
		if (!(obj instanceof Agrupador))
			return false;
		Agrupador other = (Agrupador) obj;
		if (fechaCargue == null) {
			if (other.fechaCargue != null)
				return false;
		} else if (!fechaCargue.equals(other.fechaCargue))
			return false;
		return true;
	}	
}
