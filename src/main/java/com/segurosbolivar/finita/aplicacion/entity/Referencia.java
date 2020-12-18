package com.segurosbolivar.finita.aplicacion.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
@Entity
@Table(name="FINREFERENCIAS")
public class Referencia extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String REF="Referencia";
	public static final String PROP_ID="refCodigo";


	@Id
	@Column(name="REF_CODIGO")
	private String refCodigo;


	@Column(name="REF_DESCRIPCION")
	private String refDescripcion;

	@Column(name="REF_VALOR")
	private BigInteger refValor;

	public Referencia() {
		this.refCodigo="NN";
		this.refDescripcion="sin referencia";
		this.refValor=new BigInteger("0"); 
	}
	
	public Referencia(String refCodigo) {
		super();
		this.refCodigo = refCodigo;
	}	

	public String getRefCodigo() {
		return refCodigo;
	}

	public void setRefCodigo(String refCodigo) {
		this.refCodigo = refCodigo;
	}

	public String getRefDescripcion() {
		return refDescripcion;
	}

	public void setRefDescripcion(String refDescripcion) {
		this.refDescripcion = refDescripcion;
	}

	public BigInteger getRefValor() {
		return refValor;
	}

	public void setRefValor(BigInteger refValor) {
		this.refValor = refValor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((refCodigo == null) ? 0 : refCodigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Referencia))
			return false;
		Referencia other = (Referencia) obj;
		if (refCodigo == null) {
			if (other.refCodigo != null)
				return false;
		} else if (!refCodigo.equals(other.refCodigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Finreferencia [refCodigo=" + refCodigo + ", refDescripcion=" + refDescripcion + ", refValor=" + refValor+ "]";
	}	


}
