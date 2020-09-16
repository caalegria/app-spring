package com.segurosbolivar.finita.aplicacion.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AccionistaPK implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	public static final String PROP_ACC_PER_IDENT="accPerIdent";
	public static final String PROP_ACC_OFI_CODIGO="accOfiCodigo";	
	
	@Column(name="ACC_PER_IDENT", length = 20)
	private String accPerIdent="";	
	
	@Column(name="ACC_OFI_CODIGO", length = 4)
	private String accOfiCodigo="";
	
	public AccionistaPK() {
		// TODO Auto-generated constructor stub
	}

	public AccionistaPK(String accPerIdent, String accOfiCodigo) {
		super();
		this.accPerIdent = accPerIdent;
		this.accOfiCodigo = accOfiCodigo;
	}

	public String getAccPerIdent() {
		return accPerIdent;
	}

	public void setAccPerIdent(String accPerIdent) {
		this.accPerIdent = accPerIdent;
	}

	public String getAccOfiCodigo() {
		return accOfiCodigo;
	}

	public void setAccOfiCodigo(String accOfiCodigo) {
		this.accOfiCodigo = accOfiCodigo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accOfiCodigo == null) ? 0 : accOfiCodigo.hashCode());
		result = prime * result + ((accPerIdent == null) ? 0 : accPerIdent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccionistaPK other = (AccionistaPK) obj;
		if (accOfiCodigo == null) {
			if (other.accOfiCodigo != null)
				return false;
		} else if (!accOfiCodigo.equals(other.accOfiCodigo))
			return false;
		if (accPerIdent == null) {
			if (other.accPerIdent != null)
				return false;
		} else if (!accPerIdent.equals(other.accPerIdent))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AccionistaPK [accPerIdent=" + accPerIdent + ", accOfiCodigo=" + accOfiCodigo + "]";
	}		

}
