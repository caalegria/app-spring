package com.segurosbolivar.finita.aplicacion.dto;

import java.math.BigInteger;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
@SqlResultSetMapping(name = "SaldoBeneficiarioMapping",classes = @ConstructorResult(
		targetClass = SaldoBeneficiario.class,
		columns = {
				@ColumnResult(name="EMPRESA",type=String.class),
				@ColumnResult(name="BENEFICIARIO",type=String.class),
				@ColumnResult(name="DIVIDENDO",type=BigInteger.class),
				@ColumnResult(name="MONEDA",type=String.class),
				@ColumnResult(name="GRUPO",type=String.class),
				@ColumnResult(name="SALDO",type=BigInteger.class),
				@ColumnResult(name="RETENCION",type=BigInteger.class),
		}))
public class SaldoBeneficiario {
	
	private String empresa;
	private String beneficiario;
	private BigInteger diviendo;
	private String moneda;
	private String grupo;
	private BigInteger saldo;
	private BigInteger retencion;
	
	public SaldoBeneficiario() {
		// TODO Auto-generated constructor stub
	}
	
	public SaldoBeneficiario(String empresa, String beneficiario, BigInteger diviendo, String moneda, String grupo,	BigInteger saldo, BigInteger retencion) {
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

	public BigInteger getSaldo() {
		return saldo;
	}

	public void setSaldo(BigInteger saldo) {
		this.saldo = saldo;
	}

	public BigInteger getRetencion() {
		return retencion;
	}

	public void setRetencion(BigInteger retencion) {
		this.retencion = retencion;
	}

}
