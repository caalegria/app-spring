package com.segurosbolivar.finita.aplicacion.entity;

import java.math.BigDecimal;
import java.util.Date;

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
@Table(name="FIN_REG_PRINCIPAL")
public class RegistroPrincipal extends Persistente{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String REF="RegistroPrincipal";
	public static final String PROP_ID="id";
	public static final String PROP_COD_EMISOR="cod_emisor";
	public static final String PROP_COD_ISIN="cod_isin";
	public static final String PROP_FECHA_CARGUE="fecha_cargue";
	public static final String PROP_USUARIO_CARGUE="usuario_cargue";

	@Id
	@Column(name="ID")
	private BigDecimal id;	
	
	@Column(name="COD_EMISOR",length =5)
	private  String cod_emisor;
	
	@Column(name="COD_ISIN",length =12)
	private String cod_isin;
	
	@Column(name="COD_DEPOSITO")
	private BigDecimal cod_deposito;
	
	@Column(name="COD_TIPO_DERECHO")
	private BigDecimal cod_tipo_derecho;
	
	@Column(name="FECHA_VENCIMIENTO",length =0)
	private Date fecha_vencimiento;
	
	@Column(name="CUENTA_INVERSIONISTA",length =0)
	private BigDecimal cuenta_inversionista;
	
	@Column(name="TIP_DOC_TITULAR",length =2)
	private String tip_doc_titular;
	
	@Column(name="NUM_DOC_TITULAR",length =15)
	private String num_doc_titular;
	
	@Column(name="NOMBRE_INVERSIONISTA",length =50)
	private String nombre_inversionista;
	
	@Column(name="SALDO_CONTABLE",columnDefinition="Number(20,4)")
	private BigDecimal saldo_contable;
	
	@Column(name="COBRO_DIVIDENDOS",columnDefinition="Number(20,4)")
	private  BigDecimal cobro_dividendos;
	
	@Column(name="COBRO_DIVIDENDOS_ACCIONES",columnDefinition="Number(20,4)")
	private  BigDecimal cobro_dividendos_acciones;
	
	@Column(name="COBRO_CAPITAL",columnDefinition="Number(20,4)")
	private BigDecimal  cobro_capital;
	
	@Column(name="COBRO_RENDIMIENTOS",columnDefinition="Number(20,4)")
	private  BigDecimal cobro_rendimientos;
	
	@Column(name="REINVERSIONES",columnDefinition="Number(20,4)")
	private  BigDecimal reinversiones;
	
	@Column(name="RECAUDO_CAPITAL",columnDefinition="Number(20,4)")
	private  BigDecimal recaudo_capital;
	
	@Column(name="RECAUDO_DIVIDENDOS_ACCIONES",columnDefinition="Number(20,4)")
	private  BigDecimal recaudo_dividendos_acciones;
	
	@Column(name="RECAUDO_RENDIMIENTOS",columnDefinition="Number(20,4)")
	private  BigDecimal recaudo_rendimientos;
	
	@Column(name="RETENCION_FUENTE",columnDefinition="Number(20,4)")
	private BigDecimal  retencion_fuente;
	
	@Column(name="ENAJENACION",columnDefinition="Number(20,4)")
	private  BigDecimal enajenacion;
	
	@Column(name="IND_CUENTA_ADMON_DECEVAL",length =2)
	private String ind_cuenta_admon_deceval;
	
	@Column(name="FECHA_INI_PERIODO")
	private  Date fecha_ini_periodo;
	
	@Column(name="FECHA_FIN_PERIODO")
	private  Date fecha_fin_periodo;
	
	@Column(name="TASA_EFECTIVA",columnDefinition="Number(6,4)")
	private  BigDecimal tasa_efectiva;
	
	@Column(name="CALCULO_TASA",columnDefinition="Number(8,6)")
	private  BigDecimal calculo_tasa;
	
	@Column(name="IMPUESTO_ICA",columnDefinition="Number(20,4)")
	private  BigDecimal impuesto_ica;
	
	@Column(name="IMPUESTO_CREE",columnDefinition="Number(20,4)")
	private BigDecimal  impuesto_cree;
	
	@Column(name="IMPUESTO_ADICIONAL",columnDefinition="Number(20,4)")
	private  BigDecimal impuesto_adicional;
	
	@Column(name="FECHA_CARGUE")
	private  Date fecha_cargue;
	
	@Column(name="USUARIO_CARGUE",length =50)
	private  String usuario_cargue;
	
	public RegistroPrincipal() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getCod_emisor() {
		return cod_emisor;
	}

	public void setCod_emisor(String cod_emisor) {
		this.cod_emisor = cod_emisor;
	}

	public String getCod_isin() {
		return cod_isin;
	}

	public void setCod_isin(String cod_isin) {
		this.cod_isin = cod_isin;
	}

	public BigDecimal getCod_deposito() {
		return cod_deposito;
	}

	public void setCod_deposito(BigDecimal cod_deposito) {
		this.cod_deposito = cod_deposito;
	}

	public BigDecimal getCod_tipo_derecho() {
		return cod_tipo_derecho;
	}

	public void setCod_tipo_derecho(BigDecimal cod_tipo_derecho) {
		this.cod_tipo_derecho = cod_tipo_derecho;
	}

	public Date getFecha_vencimiento() {
		return fecha_vencimiento;
	}

	public void setFecha_vencimiento(Date fecha_vencimiento) {
		this.fecha_vencimiento = fecha_vencimiento;
	}

	public BigDecimal getCuenta_inversionista() {
		return cuenta_inversionista;
	}

	public void setCuenta_inversionista(BigDecimal cuenta_inversionista) {
		this.cuenta_inversionista = cuenta_inversionista;
	}

	public String getTip_doc_titular() {
		return tip_doc_titular;
	}

	public void setTip_doc_titular(String tip_doc_titular) {
		this.tip_doc_titular = tip_doc_titular;
	}

	public String getNum_doc_titular() {
		return num_doc_titular;
	}

	public void setNum_doc_titular(String num_doc_titular) {
		this.num_doc_titular = num_doc_titular;
	}

	public String getNombre_inversionista() {
		return nombre_inversionista;
	}

	public void setNombre_inversionista(String nombre_inversionista) {
		this.nombre_inversionista = nombre_inversionista;
	}

	public BigDecimal getSaldo_contable() {
		return saldo_contable;
	}

	public void setSaldo_contable(BigDecimal saldo_contable) {
		this.saldo_contable = saldo_contable;
	}

	public BigDecimal getCobro_dividendos() {
		return cobro_dividendos;
	}

	public void setCobro_dividendos(BigDecimal cobro_dividendos) {
		this.cobro_dividendos = cobro_dividendos;
	}

	public BigDecimal getCobro_dividendos_acciones() {
		return cobro_dividendos_acciones;
	}

	public void setCobro_dividendos_acciones(BigDecimal cobro_dividendos_acciones) {
		this.cobro_dividendos_acciones = cobro_dividendos_acciones;
	}

	public BigDecimal getCobro_capital() {
		return cobro_capital;
	}

	public void setCobro_capital(BigDecimal cobro_capital) {
		this.cobro_capital = cobro_capital;
	}

	public BigDecimal getCobro_rendimientos() {
		return cobro_rendimientos;
	}

	public void setCobro_rendimientos(BigDecimal cobro_rendimientos) {
		this.cobro_rendimientos = cobro_rendimientos;
	}

	public BigDecimal getReinversiones() {
		return reinversiones;
	}

	public void setReinversiones(BigDecimal reinversiones) {
		this.reinversiones = reinversiones;
	}

	public BigDecimal getRecaudo_capital() {
		return recaudo_capital;
	}

	public void setRecaudo_capital(BigDecimal recaudo_capital) {
		this.recaudo_capital = recaudo_capital;
	}

	public BigDecimal getRecaudo_dividendos_acciones() {
		return recaudo_dividendos_acciones;
	}

	public void setRecaudo_dividendos_acciones(BigDecimal recaudo_dividendos_acciones) {
		this.recaudo_dividendos_acciones = recaudo_dividendos_acciones;
	}

	public BigDecimal getRecaudo_rendimientos() {
		return recaudo_rendimientos;
	}

	public void setRecaudo_rendimientos(BigDecimal recaudo_rendimientos) {
		this.recaudo_rendimientos = recaudo_rendimientos;
	}

	public BigDecimal getRetencion_fuente() {
		return retencion_fuente;
	}

	public void setRetencion_fuente(BigDecimal retencion_fuente) {
		this.retencion_fuente = retencion_fuente;
	}

	public BigDecimal getEnajenacion() {
		return enajenacion;
	}

	public void setEnajenacion(BigDecimal enajenacion) {
		this.enajenacion = enajenacion;
	}

	public String getInd_cuenta_admon_deceval() {
		return ind_cuenta_admon_deceval;
	}

	public void setInd_cuenta_admon_deceval(String ind_cuenta_admon_deceval) {
		this.ind_cuenta_admon_deceval = ind_cuenta_admon_deceval;
	}

	public Date getFecha_ini_periodo() {
		return fecha_ini_periodo;
	}

	public void setFecha_ini_periodo(Date fecha_ini_periodo) {
		this.fecha_ini_periodo = fecha_ini_periodo;
	}

	public Date getFecha_fin_periodo() {
		return fecha_fin_periodo;
	}

	public void setFecha_fin_periodo(Date fecha_fin_periodo) {
		this.fecha_fin_periodo = fecha_fin_periodo;
	}

	public BigDecimal getTasa_efectiva() {
		return tasa_efectiva;
	}

	public void setTasa_efectiva(BigDecimal tasa_efectiva) {
		this.tasa_efectiva = tasa_efectiva;
	}

	public BigDecimal getCalculo_tasa() {
		return calculo_tasa;
	}

	public void setCalculo_tasa(BigDecimal calculo_tasa) {
		this.calculo_tasa = calculo_tasa;
	}

	public BigDecimal getImpuesto_ica() {
		return impuesto_ica;
	}

	public void setImpuesto_ica(BigDecimal impuesto_ica) {
		this.impuesto_ica = impuesto_ica;
	}

	public BigDecimal getImpuesto_cree() {
		return impuesto_cree;
	}

	public void setImpuesto_cree(BigDecimal impuesto_cree) {
		this.impuesto_cree = impuesto_cree;
	}

	public BigDecimal getImpuesto_adicional() {
		return impuesto_adicional;
	}

	public void setImpuesto_adicional(BigDecimal impuesto_adicional) {
		this.impuesto_adicional = impuesto_adicional;
	}

	public Date getFecha_cargue() {
		return fecha_cargue;
	}

	public void setFecha_cargue(Date fecha_cargue) {
		this.fecha_cargue = fecha_cargue;
	}

	public String getUsuario_cargue() {
		return usuario_cargue;
	}

	public void setUsuario_cargue(String usuario_cargue) {
		this.usuario_cargue = usuario_cargue;
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
		if (!(obj instanceof RegistroPrincipal))
			return false;
		RegistroPrincipal other = (RegistroPrincipal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  id + "," + cod_emisor + "," + cod_isin
				+ "," + cod_deposito + "," + cod_tipo_derecho + ","
				+ fecha_vencimiento + "," + cuenta_inversionista + ","
				+ tip_doc_titular + "," + num_doc_titular + ","
				+ nombre_inversionista + "," + saldo_contable + "," + cobro_dividendos
				+ "," + cobro_dividendos_acciones + "," + cobro_capital
				+ "," + cobro_rendimientos + "," + reinversiones
				+ "," + recaudo_capital + ","
				+ recaudo_dividendos_acciones + "," + recaudo_rendimientos + ","
				+ retencion_fuente + "," + enajenacion + ","
				+ ind_cuenta_admon_deceval + "," + fecha_ini_periodo + ","
				+ fecha_fin_periodo + "," + tasa_efectiva + "," + calculo_tasa
				+ "," + impuesto_ica + "," + impuesto_cree + ","
				+ impuesto_adicional + "," + fecha_cargue + "," + usuario_cargue ;
	}	
}
