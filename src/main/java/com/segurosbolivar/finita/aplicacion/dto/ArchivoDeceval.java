package com.segurosbolivar.finita.aplicacion.dto;

public class ArchivoDeceval {

	private String empresa;
	private String oficina;
	private String fechaApunte;
	private String claseAsiento;
	private String numeroAsiento;
	private String numeroApunte;
	private String cuenta;
	private String codigoAgrupa;
	private String conceptoMovto;
	private String ajusteInfla;
	private String numeroDocto;
	private String fechaVcto;
	private String codigoRamo;
	private String valorDebito;
	private String valorCredito;
	private String benefProd;
	private String claveAgente;
	private String codigoAct_pro;
	private String nit;
	private String consecTercero;

	public ArchivoDeceval() {
		// TODO Auto-generated constructor stub
	}


	public ArchivoDeceval(String empresa, String oficina, String fechaApunte, String claseAsiento, String numeroAsiento,
			String numeroApunte, String cuenta, String codigoAgrupa, String conceptoMovto, String ajusteInfla,
			String numeroDocto, String fechaVcto, String codigoRamo, String valorDebito, String valorCredito,
			String benefProd, String claveAgente, String codigoAct_pro, String nit, String consecTercero) {
		super();
		this.empresa = empresa;
		this.oficina = oficina;
		this.fechaApunte = fechaApunte;
		this.claseAsiento = claseAsiento;
		this.numeroAsiento = numeroAsiento;
		this.numeroApunte = numeroApunte;
		this.cuenta = cuenta;
		this.codigoAgrupa = codigoAgrupa;
		this.conceptoMovto = conceptoMovto;
		this.ajusteInfla = ajusteInfla;
		this.numeroDocto = numeroDocto;
		this.fechaVcto = fechaVcto;
		this.codigoRamo = codigoRamo;
		this.valorDebito = valorDebito;
		this.valorCredito = valorCredito;
		this.benefProd = benefProd;
		this.claveAgente = claveAgente;
		this.codigoAct_pro = codigoAct_pro;
		this.nit = nit;
		this.consecTercero = consecTercero;
	}



	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getOficina() {
		return oficina;
	}

	public void setOficina(String oficina) {
		this.oficina = oficina;
	}

	public String getFechaApunte() {
		return fechaApunte;
	}

	public void setFechaApunte(String fechaApunte) {
		this.fechaApunte = fechaApunte;
	}

	public String getClaseAsiento() {
		return claseAsiento;
	}

	public void setClaseAsiento(String claseAsiento) {
		this.claseAsiento = claseAsiento;
	}

	public String getNumeroAsiento() {
		return numeroAsiento;
	}

	public void setNumeroAsiento(String numeroAsiento) {
		this.numeroAsiento = numeroAsiento;
	}

	public String getNumeroApunte() {
		return numeroApunte;
	}

	public void setNumeroApunte(String numeroApunte) {
		this.numeroApunte = numeroApunte;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getCodigoAgrupa() {
		return codigoAgrupa;
	}

	public void setCodigoAgrupa(String codigoAgrupa) {
		this.codigoAgrupa = codigoAgrupa;
	}

	public String getConceptoMovto() {
		return conceptoMovto;
	}

	public void setConceptoMovto(String conceptoMovto) {
		this.conceptoMovto = conceptoMovto;
	}

	public String getAjusteInfla() {
		return ajusteInfla;
	}

	public void setAjusteInfla(String ajusteInfla) {
		this.ajusteInfla = ajusteInfla;
	}

	public String getNumeroDocto() {
		return numeroDocto;
	}

	public void setNumeroDocto(String numeroDocto) {
		this.numeroDocto = numeroDocto;
	}

	public String getFechaVcto() {
		return fechaVcto;
	}

	public void setFechaVcto(String fechaVcto) {
		this.fechaVcto = fechaVcto;
	}

	public String getCodigoRamo() {
		return codigoRamo;
	}

	public void setCodigoRamo(String codigoRamo) {
		this.codigoRamo = codigoRamo;
	}

	public String getValorDebito() {
		return valorDebito;
	}

	public void setValorDebito(String valorDebito) {
		this.valorDebito = valorDebito;
	}

	public String getValorCredito() {
		return valorCredito;
	}

	public void setValorCredito(String valorCredito) {
		this.valorCredito = valorCredito;
	}

	public String getBenefProd() {
		return benefProd;
	}

	public void setBenefProd(String benefProd) {
		this.benefProd = benefProd;
	}

	public String getClaveAgente() {
		return claveAgente;
	}

	public void setClaveAgente(String claveAgente) {
		this.claveAgente = claveAgente;
	}

	public String getCodigoAct_pro() {
		return codigoAct_pro;
	}

	public void setCodigoAct_pro(String codigoAct_pro) {
		this.codigoAct_pro = codigoAct_pro;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getConsecTercero() {
		return consecTercero;
	}

	public void setConsecTercero(String consecTercero) {
		this.consecTercero = consecTercero;
	}



	public static final String cabezera() {
		StringBuffer decevalCabezera= new StringBuffer();
		decevalCabezera.append("empresa").append(";");
		decevalCabezera.append("oficina").append(";");
		decevalCabezera.append("fechaApunte").append(";");
		decevalCabezera.append("claseAsiento").append(";");
		decevalCabezera.append("numeroAsiento").append(";");
		decevalCabezera.append("numeroApunte").append(";");
		decevalCabezera.append("cuenta").append(";");
		decevalCabezera.append("codigoAgrupa").append(";");
		decevalCabezera.append("conceptoMovto").append(";");
		decevalCabezera.append("ajusteInfla").append(";");
		decevalCabezera.append("numeroDocto").append(";");
		decevalCabezera.append("fechaVcto").append(";");
		decevalCabezera.append("codigoRamo").append(";");
		decevalCabezera.append("valorDebito").append(";");
		decevalCabezera.append("valorCredito").append(";");
		decevalCabezera.append("benefProd").append(";");
		decevalCabezera.append("claveAgente").append(";");
		decevalCabezera.append("codigoAct_pro").append(";");		
		decevalCabezera.append("nit").append(";");
		decevalCabezera.append("consecTercero");
		return decevalCabezera.toString();
	}


	@Override
	public String toString() {
		StringBuffer deceval= new StringBuffer();
		deceval.append(empresa!=null?empresa:"").append(";");
		deceval.append(oficina!=null?oficina:"").append(";");
		deceval.append(fechaApunte!=null?fechaApunte:"").append(";");
		deceval.append(claseAsiento!=null?claseAsiento:"").append(";");
		deceval.append(numeroAsiento!=null?numeroAsiento:"").append(";");
		deceval.append(numeroApunte!=null?numeroApunte:"").append(";");
		deceval.append(cuenta!=null?cuenta:"").append(";");
		deceval.append(codigoAgrupa!=null?codigoAgrupa:"").append(";");
		deceval.append(conceptoMovto!=null?conceptoMovto:"").append(";");
		deceval.append(ajusteInfla!=null?ajusteInfla:"").append(";");
		deceval.append(numeroDocto!=null?numeroDocto:"").append(";");
		deceval.append(fechaVcto!=null?fechaVcto:"").append(";");
		deceval.append(codigoRamo!=null?codigoRamo:"").append(";");
		deceval.append(valorDebito!=null?valorDebito:"").append(";");
		deceval.append(valorCredito!=null?valorCredito:"").append(";");
		deceval.append(benefProd!=null?benefProd:"").append(";");
		deceval.append(claveAgente!=null?claveAgente:"").append(";");
		deceval.append(codigoAct_pro!=null?codigoAct_pro:"").append(";");		
		deceval.append(nit!=null?nit:"").append(";");
		deceval.append(consecTercero!=null?consecTercero:"");

		return deceval.toString();		
	}	
}
