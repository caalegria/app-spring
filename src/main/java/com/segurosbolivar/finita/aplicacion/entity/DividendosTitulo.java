package com.segurosbolivar.finita.aplicacion.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
@Entity
@Table(name="FINDIVIDENDOS_TITULO")
public class DividendosTitulo extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String REF="DividendosTitulo";
	public static final String PROP_ID="id";
	public static final String PROP_ACC_PER_IDENT="accPerIdent";
	
	
	@Id
	@Column(name="DIT_CONSEC")
	private BigDecimal  id;	
	
	@Column(name="DIT_ACC_PER_IDENT",length = 20)
	private String accPerIdent;
	
	@Column(name="DIT_VALOR_DIVIDENDO")
	private BigDecimal valorDividendo;
	
	@Column(name="DIT_FECHA_PAGO")
	private Date fechaPago;
	
	@Column(name="DIT_VALOR_PAGADO")
	private BigDecimal valorPagado;
	
	@Column(name="DIT_PAQUETE")
	private BigDecimal paquete;

	@Column(name="DIT_ESTADO_TRAMITE",length = 3)
	private String estadoTramite;
	
	@Transient
	private Referencia estadoTram;
	
	public DividendosTitulo() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getAccPerIdent() {
		return accPerIdent;
	}

	public void setAccPerIdent(String accPerIdent) {
		this.accPerIdent = accPerIdent;
	}

	public BigDecimal getValorDividendo() {
		return valorDividendo;
	}

	public void setValorDividendo(BigDecimal valorDividendo) {
		this.valorDividendo = valorDividendo;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public BigDecimal getValorPagado() {
		return valorPagado;
	}

	public void setValorPagado(BigDecimal valorPagado) {
		this.valorPagado = valorPagado;
	}

	public BigDecimal getPaquete() {
		return paquete;
	}

	public void setPaquete(BigDecimal paquete) {
		this.paquete = paquete;
	}

	public String getEstadoTramite() {
		return estadoTramite;
	}

	public void setEstadoTramite(String estadoTramite) {
		this.estadoTramite = estadoTramite;
	}

	public Referencia getEstadoTram() {
		return estadoTram;
	}

	public void setEstadoTram(Referencia estadoTram) {
		this.estadoTram = estadoTram;
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
		if (!(obj instanceof DividendosTitulo))
			return false;
		DividendosTitulo other = (DividendosTitulo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DividendosTitulo [id=" + id + ", accPerIdent=" + accPerIdent + ", valorDividendo=" + valorDividendo
				+ ", fechaPago=" + fechaPago + ", valorPagado=" + valorPagado + ", paquete=" + paquete
				+ ", estadoTramite=" + estadoTramite + ", estadoTram=" + estadoTram + "]";
	}

}
