package com.segurosbolivar.finita.aplicacion.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
@Entity
@Table(name="FINBENEFICIARIOS")
public class Beneficiario extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String REF="Beneficiario";
	public static final String PROP_ID="id";
	public static final String PROP_ESTADO="benestado";

	@EmbeddedId
	private BeneficiarioPK id;
	
	@Column(name="BEN_ESTADO")
	private String benestado;
	
	@Column(name="BEN_VALOR")
	private BigInteger benValor;
	
	@Column(name="NIT")
	private String benNit;
	
	@Transient
	private Persona persona;
	
	public Beneficiario() {
		// TODO Auto-generated constructor stub
	}	
	
	public Beneficiario(BeneficiarioPK id) {
		super();
		this.id = id;
	}
	
	public BeneficiarioPK getId() {
		return id;
	}

	public void setId(BeneficiarioPK id) {
		this.id = id;
	}

	public String getBenestado() {
		return benestado;
	}

	public void setBenestado(String benestado) {
		this.benestado = benestado;
	}

	public BigInteger getBenValor() {
		return benValor;
	}

	public void setBenValor(BigInteger benValor) {
		this.benValor = benValor;
	}
	
	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}	

	public String getBenNit() {
		return benNit;
	}

	public void setBenNit(String benNit) {
		this.benNit = benNit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Beneficiario))
			return false;
		Beneficiario other = (Beneficiario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Beneficiario [id=" + id + ", benestado=" + benestado + ", benValor=" + benValor + "]";
	}
	
	
}
