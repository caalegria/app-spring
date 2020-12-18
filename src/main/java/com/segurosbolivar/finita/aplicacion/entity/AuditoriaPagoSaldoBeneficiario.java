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
@Table(name="FINPAGOS_BOLIVAR")
public class AuditoriaPagoSaldoBeneficiario extends Persistente{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String REF="AuditoriaPagoSaldoBeneficiario";
	public static final String PROP_ID="id";
	
	@Id
	@Column(name="PBO_CONSEC")
	private BigDecimal id;	
	
	@Column(name="PBO_EMP_CODIGO",length =2)
	private  String cod_emisor;
	
	@Column(name="PBO_BXT_PER_IDENT",length =20)
	private String cod_isin;
	
	@Column(name="PBO_DIV_CONSEC")
	private BigDecimal cod_deposito;
	
	@Column(name="PBO_CNV_CODIGO",length =5)
	private String cod_tipo_derecho;
	
	@Column(name="PBO_VALOR_ENVIADO")
	private BigDecimal fecha_vencimiento;
	
	@Column(name="PBO_VALOR_IMPUESTOS")
	private BigDecimal cuenta_inversionista;
	
	@Column(name="PBO_VALOR_PAGADO")
	private BigDecimal tip_doc_titular;
	
	@Column(name="PBO_ESTADO",length =1)
	private String num_doc_titular;
	
	@Column(name="PBO_FECHA_ESTADO")
	private Date nombre_inversionista;
	
	@Column(name="PBO_FECHA_REGISTRO")
	private Date saldo_contable;
	
	@Column(name="PBO_USUARIO",length =30)
	private  String cobro_dividendos;
	
	@Column(name="PBO_ORDEN_PAGO")
	private  BigDecimal cobro_dividendos_acciones;
	
	@Column(name="PBO_AGENCIA",length =20)
	private String  cobro_capital;
	
	@Column(name="PBO_CAJERO",length =20)
	private  String cobro_rendimientos;
	
	@Column(name="PBO_SOLICITA",length =20)
	private  String reinversiones;
	
	@Column(name="PBO_CONCEPTO",length =20)
	private  String recaudo_capital;
	
	@Column(name="PBO_NRO_CHEQUE",length =20)
	private  String recaudo_dividendos_acciones;
	
	@Column(name="PBO_BANCO",length = 20)
	private  String recaudo_rendimientos;
	
	@Column(name="PBO_FORMA_PAGO",length =20)
	private String  retencion_fuente;
	
	@Column(name="PBO_GRUPO")
	private  BigDecimal enajenacion;
	
	@Column(name="PBO_USUARIO_SISTEMA",length =30)
	private String ind_cuenta_admon_deceval;
	
	
	public AuditoriaPagoSaldoBeneficiario() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getId() {
		return id;
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

	public String getCod_tipo_derecho() {
		return cod_tipo_derecho;
	}

	public void setCod_tipo_derecho(String cod_tipo_derecho) {
		this.cod_tipo_derecho = cod_tipo_derecho;
	}

	public BigDecimal getFecha_vencimiento() {
		return fecha_vencimiento;
	}

	public void setFecha_vencimiento(BigDecimal fecha_vencimiento) {
		this.fecha_vencimiento = fecha_vencimiento;
	}

	public BigDecimal getCuenta_inversionista() {
		return cuenta_inversionista;
	}

	public void setCuenta_inversionista(BigDecimal cuenta_inversionista) {
		this.cuenta_inversionista = cuenta_inversionista;
	}

	public BigDecimal getTip_doc_titular() {
		return tip_doc_titular;
	}

	public void setTip_doc_titular(BigDecimal tip_doc_titular) {
		this.tip_doc_titular = tip_doc_titular;
	}

	public String getNum_doc_titular() {
		return num_doc_titular;
	}

	public void setNum_doc_titular(String num_doc_titular) {
		this.num_doc_titular = num_doc_titular;
	}

	public Date getNombre_inversionista() {
		return nombre_inversionista;
	}

	public void setNombre_inversionista(Date nombre_inversionista) {
		this.nombre_inversionista = nombre_inversionista;
	}

	public Date getSaldo_contable() {
		return saldo_contable;
	}

	public void setSaldo_contable(Date saldo_contable) {
		this.saldo_contable = saldo_contable;
	}

	public String getCobro_dividendos() {
		return cobro_dividendos;
	}

	public void setCobro_dividendos(String cobro_dividendos) {
		this.cobro_dividendos = cobro_dividendos;
	}

	public BigDecimal getCobro_dividendos_acciones() {
		return cobro_dividendos_acciones;
	}

	public void setCobro_dividendos_acciones(BigDecimal cobro_dividendos_acciones) {
		this.cobro_dividendos_acciones = cobro_dividendos_acciones;
	}

	public String getCobro_capital() {
		return cobro_capital;
	}

	public void setCobro_capital(String cobro_capital) {
		this.cobro_capital = cobro_capital;
	}

	public String getCobro_rendimientos() {
		return cobro_rendimientos;
	}

	public void setCobro_rendimientos(String cobro_rendimientos) {
		this.cobro_rendimientos = cobro_rendimientos;
	}

	public String getReinversiones() {
		return reinversiones;
	}

	public void setReinversiones(String reinversiones) {
		this.reinversiones = reinversiones;
	}

	public String getRecaudo_capital() {
		return recaudo_capital;
	}

	public void setRecaudo_capital(String recaudo_capital) {
		this.recaudo_capital = recaudo_capital;
	}

	public String getRecaudo_dividendos_acciones() {
		return recaudo_dividendos_acciones;
	}

	public void setRecaudo_dividendos_acciones(String recaudo_dividendos_acciones) {
		this.recaudo_dividendos_acciones = recaudo_dividendos_acciones;
	}

	public String getRecaudo_rendimientos() {
		return recaudo_rendimientos;
	}

	public void setRecaudo_rendimientos(String recaudo_rendimientos) {
		this.recaudo_rendimientos = recaudo_rendimientos;
	}

	public String getRetencion_fuente() {
		return retencion_fuente;
	}

	public void setRetencion_fuente(String retencion_fuente) {
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

	public void setId(BigDecimal id) {
		this.id = id;
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
		if (!(obj instanceof AuditoriaPagoSaldoBeneficiario))
			return false;
		AuditoriaPagoSaldoBeneficiario other = (AuditoriaPagoSaldoBeneficiario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AuditoriaPagoSaldoBeneficiario [id=" + id + ", cod_emisor=" + cod_emisor + ", cod_isin=" + cod_isin
				+ ", cod_deposito=" + cod_deposito + ", cod_tipo_derecho=" + cod_tipo_derecho + ", fecha_vencimiento="
				+ fecha_vencimiento + ", cuenta_inversionista=" + cuenta_inversionista + ", tip_doc_titular="
				+ tip_doc_titular + ", num_doc_titular=" + num_doc_titular + ", nombre_inversionista="
				+ nombre_inversionista + ", saldo_contable=" + saldo_contable + ", cobro_dividendos=" + cobro_dividendos
				+ ", cobro_dividendos_acciones=" + cobro_dividendos_acciones + ", cobro_capital=" + cobro_capital
				+ ", cobro_rendimientos=" + cobro_rendimientos + ", reinversiones=" + reinversiones
				+ ", recaudo_capital=" + recaudo_capital + ", recaudo_dividendos_acciones="
				+ recaudo_dividendos_acciones + ", recaudo_rendimientos=" + recaudo_rendimientos + ", retencion_fuente="
				+ retencion_fuente + ", enajenacion=" + enajenacion + ", ind_cuenta_admon_deceval="
				+ ind_cuenta_admon_deceval + "]";
	}
	
}
