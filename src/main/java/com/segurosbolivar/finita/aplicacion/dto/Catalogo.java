package com.segurosbolivar.finita.aplicacion.dto;

public class Catalogo {

	private String codigo;
	private String label;
	
	public Catalogo() {
		// TODO Auto-generated constructor stub
	}

	public Catalogo(String codigo, String label) {
		super();
		this.codigo = codigo;
		this.label = label;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Catalogo))
			return false;
		Catalogo other = (Catalogo) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
