package com.segurosbolivar.finita.aplicacion.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.segurosbolivar.finita.aplicacion.entity.Dividendo;
import com.segurosbolivar.finita.aplicacion.entity.Persona;

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
	private String beneficiario;
	private BigInteger diviendo;
	private String moneda;
	private String grupo;
	private BigDecimal saldo;
	private BigDecimal retencion;
	
	private Persona persona;
	private Dividendo dividendo;
	
	public SaldoBeneficiario() {
		// TODO Auto-generated constructor stub
	}
	
	public SaldoBeneficiario(String empresa, String beneficiario, BigInteger diviendo, String moneda, String grupo,	BigDecimal saldo, BigDecimal retencion) {
		super();
		this.empresa = empresa;
		this.beneficiario = beneficiario;
		this.diviendo = diviendo;
		this.moneda = moneda;
		this.grupo = grupo;
		this.saldo = saldo;
		this.retencion = retencion;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
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

	@Override
	public String toString() {
		return "SaldoBeneficiario [select=" + select + ", empresa=" + empresa + ", beneficiario=" + beneficiario
				+ ", diviendo=" + diviendo + ", moneda=" + moneda + ", grupo=" + grupo + ", saldo=" + saldo
				+ ", retencion=" + retencion + ", persona=" + persona + ", dividendo=" + dividendo + "]";
	}
	
	
}
