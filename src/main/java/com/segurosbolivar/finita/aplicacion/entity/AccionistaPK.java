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
	public static final String PROP_ACC_EMP_CODIGO="accEmpCodigo";	
	
	@Column(name="ACC_PER_IDENT", length = 20)
	private String accPerIdent;	
	
	@Column(name="ACC_EMP_CODIGO",length = 2)
	private String accEmpCodigo;
	
	public AccionistaPK() {
		// TODO Auto-generated constructor stub
	}

	public AccionistaPK(String accPerIdent, String accEmpCodigo) {
		super();
		this.accPerIdent = accPerIdent;
		this.accEmpCodigo = accEmpCodigo;
	}

	public String getAccPerIdent() {
		return accPerIdent;
	}

	public void setAccPerIdent(String accPerIdent) {
		this.accPerIdent = accPerIdent;
	}
	
	public String getAccEmpCodigo() {
		return accEmpCodigo;
	}


	public void setAccEmpCodigo(String accEmpCodigo) {
		this.accEmpCodigo = accEmpCodigo;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accEmpCodigo == null) ? 0 : accEmpCodigo.hashCode());
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
		if (accEmpCodigo == null) {
			if (other.accEmpCodigo != null)
				return false;
		} else if (!accEmpCodigo.equals(other.accEmpCodigo))
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
		return "AccionistaPK [accPerIdent=" + accPerIdent + ", accEmpCodigo=" + accEmpCodigo + "]";
	}		

}
