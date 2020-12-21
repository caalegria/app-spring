package com.segurosbolivar.finita.aplicacion.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.segurosbolivar.finita.aplicacion.entity.Accionista;
import com.segurosbolivar.finita.aplicacion.entity.Dividendo;
import com.segurosbolivar.finita.aplicacion.entity.Persona;
import com.segurosbolivar.finita.aplicacion.entity.Referencia;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
public class SaldoBeneficiario implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6288648792344764837L;
	private boolean select=false;
	private String empresa;
	private String accionista;
	private String beneficiario;
	private BigInteger diviendo;
	private String moneda;
	private String grupo;
	private BigDecimal saldo;
	private BigDecimal retencion;
	private BigDecimal cuentaContable;
	private String estadoTramite;
	
	private BigDecimal consecutivoDividendo;
	private Date fecha;	
	
	private Persona persona;
	private Accionista accionistaObj;
	private Referencia periodicidad;
	private Referencia tipoPago;
	private Referencia estadoTram;
	private Dividendo dividendo;
	
	public SaldoBeneficiario() {
		// TODO Auto-generated constructor stub
	}
	
	public SaldoBeneficiario(String empresa,String accionista, String beneficiario, BigInteger diviendo, String moneda, String grupo,	BigDecimal saldo, BigDecimal retencion,BigDecimal cuentaContable,String estadoTramite,BigDecimal consecutivoDividendo,Date fecha) {
		super();		
		this.empresa = empresa;
		this.accionista=accionista;
		this.beneficiario = beneficiario;
		this.diviendo = diviendo;
		this.moneda = moneda;
		this.grupo = grupo;
		this.saldo = saldo;
		this.retencion = retencion;
		this.cuentaContable=cuentaContable;
		this.estadoTramite=estadoTramite;
		this.consecutivoDividendo=consecutivoDividendo;
		this.fecha=fecha;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}	

	public String getAccionista() {
		return accionista;
	}

	public void setAccionista(String accionista) {
		this.accionista = accionista;
	}

	public String getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}

	public BigInteger getDiviendo() {
		return diviendo;
	}

	public void setDiviendo(BigInteger diviendo) {
		this.diviendo = diviendo;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getRetencion() {
		return retencion;
	}

	public void setRetencion(BigDecimal retencion) {
		this.retencion = retencion;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Dividendo getDividendo() {
		return dividendo;
	}

	public void setDividendo(Dividendo dividendo) {
		this.dividendo = dividendo;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}	

	public Accionista getAccionistaObj() {
		return accionistaObj;
	}

	public void setAccionistaObj(Accionista accionistaObj) {
		this.accionistaObj = accionistaObj;
	}		

	public Referencia getPeriodicidad() {
		return periodicidad;
	}

	public void setPeriodicidad(Referencia periodicidad) {
		this.periodicidad = periodicidad;
	}

	public Referencia getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(Referencia tipoPago) {
		this.tipoPago = tipoPago;
	}	

	public BigDecimal getCuentaContable() {
		return cuentaContable;
	}

	public void setCuentaContable(BigDecimal cuentaContable) {
		this.cuentaContable = cuentaContable;
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

	public BigDecimal getConsecutivoDividendo() {
		return consecutivoDividendo;
	}

	public void setConsecutivoDividendo(BigDecimal consecutivoDividendo) {
		this.consecutivoDividendo = consecutivoDividendo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((consecutivoDividendo == null) ? 0 : consecutivoDividendo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SaldoBeneficiario))
			return false;
		SaldoBeneficiario other = (SaldoBeneficiario) obj;
		if (consecutivoDividendo == null) {
			if (other.consecutivoDividendo != null)
				return false;
		} else if (!consecutivoDividendo.equals(other.consecutivoDividendo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SaldoBeneficiario [select=" + select + ", empresa=" + empresa + ", accionista=" + accionista
				+ ", beneficiario=" + beneficiario + ", diviendo=" + diviendo + ", moneda=" + moneda + ", grupo="
				+ grupo + ", saldo=" + saldo + ", retencion=" + retencion + ", cuentaContable=" + cuentaContable
				+ ", estadoTramite=" + estadoTramite + ", persona=" + persona + ", accionistaObj=" + accionistaObj
				+ ", periodicidad=" + periodicidad + ", tipoPago=" + tipoPago + ", dividendo=" + dividendo + "]";
	}	
}
