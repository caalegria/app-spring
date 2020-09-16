package com.segurosbolivar.finita.aplicacion.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
@Embeddable
public class BeneficiarioPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String PROP_ACC_PER_IDENT="accPerIdent";
	public static final String PROP_ACC_EMP_CODIGO="accEmpCodigo";
	public static final String PROP_BEN_PER_IDENT="benPerIdent";

	/*
	 * Identificacion del accionista
	 */
	@Column(name="ACC_PER_IDENT")
	private String accPerIdent;
	
	@Column(name="ACC_EMP_CODIGO")
	private String accEmpCodigo;
	
	/*
	 * Identifiacion del beneficiario
	 */
	@Column(name="BEN_PER_IDENT")
	private String benPerIdent;
	
	public BeneficiarioPK() {
		// TODO Auto-generated constructor stub
	}

	public BeneficiarioPK(String accPerIdent, String accEmpCodigo, String benPerIdent) {
		super();
		this.accPerIdent = accPerIdent;
		this.accEmpCodigo = accEmpCodigo;
		this.benPerIdent = benPerIdent;
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

	public String getBenPerIdent() {
		return benPerIdent;
	}

	public void setBenPerIdent(String benPerIdent) {
		this.benPerIdent = benPerIdent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accEmpCodigo == null) ? 0 : accEmpCodigo.hashCode());
		result = prime * result + ((accPerIdent == null) ? 0 : accPerIdent.hashCode());
		result = prime * result + ((benPerIdent == null) ? 0 : benPerIdent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof BeneficiarioPK))
			return false;
		BeneficiarioPK other = (BeneficiarioPK) obj;
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
		if (benPerIdent == null) {
			if (other.benPerIdent != null)
				return false;
		} else if (!benPerIdent.equals(other.benPerIdent))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BeneficiarioPK [accPerIdent=" + accPerIdent + ", accEmpCodigo=" + accEmpCodigo + ", benPerIdent="+ benPerIdent + "]";
	}
	
	
}

