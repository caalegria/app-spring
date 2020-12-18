package com.segurosbolivar.finita.aplicacion.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@Table(name="FINACCIONISTAS")
public class Accionista extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String REF="Accionista";
	public static final String PROP_ID="id";
	public static final String PROP_SEC_CODIGO	="accSecCodigo";
	public static final String PROP_EMP_CODIGO	="accEmpCodigo";
	public static final String PROP_ACC_NOMBRE	="accNombre";

	@EmbeddedId
	private AccionistaPK id= new AccionistaPK();

	@Column(name="ACC_SEC_CODIGO",length = 6)
	private String accSecCodigo;
	
	@Transient 
	private Referencia accSecCodigoRef;

	@Column(name="ACC_ESTADO",length = 1)
	private String accEstado;

	@Column(name="ACC_FORMA_PAGO",length = 3)
	private String accFormaPago;
	
	@Transient 
	private Referencia accFormaPagoRef;

	@Column(name="ACC_NACIONALIDAD",length =3 )
	private String accNacionalidad;
	
	@Transient 
	private Referencia accNacionalidadRef;

	@Column(name="ACC_PAGO_DIVIDENDO",length = 1)
	private String accPagoDividendo;

	@Column(name="ACC_CUENTA",length = 15)
	private String accCuenta;

	@Column(name="ACC_COD_BANCO",length = 3)
	private String accCodBanco;

	//	@Column(name="TOTAL_ACCIONES",length = 1)
	//	private char totalAcciones;

	@Column(name="ACC_OFI_CODIGO", length = 4)
	private String accOfiCodigo;
	
	@Transient 
	private Referencia accEmpCodigoRef;

	@Column(name="ACC_NUM_DEPOSITO",length = 30)
	private String accNumDeposito;

	//@Column(name="ACC_POR_CODIGO",length = 1)
	//private String accPorCodigo;

	@Column(name="ACC_NOMBRE",length = 120)
	private String accNombre;

	@Column(name="ACC_DIRECCION",length = 50)
	private String accDireccion;

	@Column(name="ACC_DIRECCION_2",length = 50)
	private String accDireccion2;

	@Column(name="ACC_TELEFONO",length = 15)
	private String accTelefono;

	@Column(name="ACC_TELEFONO_2",length = 15)
	private String accTelefono2;

	@Column(name="ACC_CIU_CODIGO",length = 3)
	private String accCiuCodigo;
	
	@Transient 
	private Referencia accCiuCodigoRef;

	@Column(name="ACC_CIU_CONSEC",length = 3)
	private String accCiuConsec;

	@Column(name="ACC_CIU_DPT_PAIS_CODIGO")
	private Integer accCiuDptPaisCodigo;
	
	@Transient 
	private Referencia accCiuDptPaisCodigoRef;

	@Column(name="ACC_CIU_DPT_CODIGO",length = 2)
	private String accCiuDptCodigo;
	
	@Transient 
	private Referencia accCiuDptCodigoRef;

	@Column(name="ACC_E_MAIL",length = 50)
	private String accEmail;

	@Column(name="ACC_FAX",length = 15)
	private String accFax;

	@Column(name="ACC_AUT_IDENT",length =20 )
	private String accAutIdent;

	@Column(name="ACC_AUT_NOMBRE",length = 120)
	private String accAutNombre;

	@Column(name="ACC_IND_EMP",length = 1)
	private String accIndEmp;

	@Column(name="ACC_F_INGRESO")
	private Date accFIngreso;

	@Column(name="ACC_F_RETIRO")
	private Date accFRetiro;

	@Column(name="ACC_DIGITO_CONTROL")
	private Integer accDigitoControl;

	@Column(name="ACC_TIPO_CUENTA",length = 3)
	private String accTipoCuenta="";
	
	@Transient 
	private Referencia accTipoCuentaRef;

	@Column(name="ACC_MANEJO_CUENTA",length = 3)
	private String accManejoCuenta;

	@Column(name="ACC_TIPO_CAPITAL",length = 3)
	private String accTipoCapital;
	
	@Transient 
	private Referencia accTipoCapitalRef;

	@Column(name="ACC_APLICAR_DCTO",length = 1)
	private String accAplicarDcto;		

	@Column(name="ACC_COD_ACCIONISTA",length = 8)
	private String accCodAccionista;

	@Column(name="ACC_RESIDENTE",length = 1)
	private String accResidente;

	@Transient	
	private Persona finPersona;
	
	
	@Column(name="ACC_PERIODICIDAD")
	private String periodicidad;
	
	@Transient
	private Referencia referenciaPeriodicidad;
	
	@Column(name="ACC_PAGO")
	private String pago;
	
	@Transient
	private Referencia referenciaPago;
	
	@Transient
	private List<Beneficiario> beneficiarios= new ArrayList<Beneficiario>();

	public Accionista() {
		// TODO Auto-generated constructor stub
	}	

	public Accionista(AccionistaPK id) {
		super();
		this.id = id;
	}

	public AccionistaPK getId() {
		return id;
	}

	public void setId(AccionistaPK id) {
		this.id = id;
	}


	public String getAccSecCodigo() {
		return accSecCodigo;
	}


	public void setAccSecCodigo(String accSecCodigo) {
		this.accSecCodigo = accSecCodigo;
	}


	public String getAccEstado() {
		return accEstado;
	}


	public void setAccEstado(String accEstado) {
		this.accEstado = accEstado;
	}


	public String getAccFormaPago() {
		return accFormaPago;
	}


	public void setAccFormaPago(String accFormaPago) {
		this.accFormaPago = accFormaPago;
	}


	public String getAccNacionalidad() {
		return accNacionalidad;
	}


	public void setAccNacionalidad(String accNacionalidad) {
		this.accNacionalidad = accNacionalidad;
	}


	public String getAccPagoDividendo() {
		return accPagoDividendo;
	}


	public void setAccPagoDividendo(String accPagoDividendo) {
		this.accPagoDividendo = accPagoDividendo;
	}


	public String getAccCuenta() {
		return accCuenta;
	}


	public void setAccCuenta(String accCuenta) {
		this.accCuenta = accCuenta;
	}


	public String getAccCodBanco() {
		return accCodBanco;
	}


	public void setAccCodBanco(String accCodBanco) {
		this.accCodBanco = accCodBanco;
	}


	//	public char getTotalAcciones() {
	//		return totalAcciones;
	//	}
	//
	//
	//	public void setTotalAcciones(char totalAcciones) {
	//		this.totalAcciones = totalAcciones;
	//	}


	public String getAccOfiCodigo() {
		return accOfiCodigo;
	}

	public void setAccOfiCodigo(String accOfiCodigo) {
		this.accOfiCodigo = accOfiCodigo;
	}

	public String getAccNumDeposito() {
		return accNumDeposito;
	}


	public void setAccNumDeposito(String accNumDeposito) {
		this.accNumDeposito = accNumDeposito;
	}


	//	public String getAccPorCodigo() {
	//		return accPorCodigo;
	//	}
	//
	//
	//	public void setAccPorCodigo(String accPorCodigo) {
	//		this.accPorCodigo = accPorCodigo;
	//	}


	public String getAccNombre() {
		return accNombre;
	}


	public void setAccNombre(String accNombre) {
		this.accNombre = accNombre;
	}


	public String getAccDireccion() {
		return accDireccion;
	}


	public void setAccDireccion(String accDireccion) {
		this.accDireccion = accDireccion;
	}


	public String getAccDireccion2() {
		return accDireccion2;
	}


	public void setAccDireccion2(String accDireccion2) {
		this.accDireccion2 = accDireccion2;
	}


	public String getAccTelefono() {
		return accTelefono;
	}


	public void setAccTelefono(String accTelefono) {
		this.accTelefono = accTelefono;
	}


	public String getAccTelefono2() {
		return accTelefono2;
	}


	public void setAccTelefono2(String accTelefono2) {
		this.accTelefono2 = accTelefono2;
	}


	public String getAccCiuCodigo() {
		return accCiuCodigo;
	}


	public void setAccCiuCodigo(String accCiuCodigo) {
		this.accCiuCodigo = accCiuCodigo;
	}


	public String getAccCiuConsec() {
		return accCiuConsec;
	}


	public void setAccCiuConsec(String accCiuConsec) {
		this.accCiuConsec = accCiuConsec;
	}


	public Integer getAccCiuDptPaisCodigo() {
		return accCiuDptPaisCodigo;
	}


	public void setAccCiuDptPaisCodigo(Integer accCiuDptPaisCodigo) {
		this.accCiuDptPaisCodigo = accCiuDptPaisCodigo;
	}


	public String getAccCiuDptCodigo() {
		return accCiuDptCodigo;
	}


	public void setAccCiuDptCodigo(String accCiuDptCodigo) {
		this.accCiuDptCodigo = accCiuDptCodigo;
	}


	public String getAccEmail() {
		return accEmail;
	}


	public void setAccEmail(String accEmail) {
		this.accEmail = accEmail;
	}


	public String getAccFax() {
		return accFax;
	}


	public void setAccFax(String accFax) {
		this.accFax = accFax;
	}


	public String getAccAutIdent() {
		return accAutIdent;
	}


	public void setAccAutIdent(String accAutIdent) {
		this.accAutIdent = accAutIdent;
	}


	public String getAccAutNombre() {
		return accAutNombre;
	}


	public void setAccAutNombre(String accAutNombre) {
		this.accAutNombre = accAutNombre;
	}


	public String getAccIndEmp() {
		return accIndEmp;
	}


	public void setAccIndEmp(String accIndEmp) {
		this.accIndEmp = accIndEmp;
	}


	public Date getAccFIngreso() {
		return accFIngreso;
	}


	public void setAccFIngreso(Date accFIngreso) {
		this.accFIngreso = accFIngreso;
	}


	public Date getAccFRetiro() {
		return accFRetiro;
	}


	public void setAccFRetiro(Date accFRetiro) {
		this.accFRetiro = accFRetiro;
	}


	public Integer getAccDigitoControl() {
		return accDigitoControl;
	}


	public void setAccDigitoControl(Integer accDigitoControl) {
		this.accDigitoControl = accDigitoControl;
	}


	public String getAccTipoCuenta() {
		return accTipoCuenta;
	}


	public void setAccTipoCuenta(String accTipoCuenta) {
		this.accTipoCuenta = accTipoCuenta;
	}


	public String getAccManejoCuenta() {
		return accManejoCuenta;
	}


	public void setAccManejoCuenta(String accManejoCuenta) {
		this.accManejoCuenta = accManejoCuenta;
	}


	public String getAccTipoCapital() {
		return accTipoCapital;
	}


	public void setAccTipoCapital(String accTipoCapital) {
		this.accTipoCapital = accTipoCapital;
	}


	public String getAccAplicarDcto() {
		return accAplicarDcto;
	}


	public void setAccAplicarDcto(String accAplicarDcto) {
		this.accAplicarDcto = accAplicarDcto;
	}	

	public String getAccCodAccionista() {
		return accCodAccionista;
	}

	public void setAccCodAccionista(String accCodAccionista) {
		this.accCodAccionista = accCodAccionista;
	}

	public String getAccResidente() {
		return accResidente;
	}

	public void setAccResidente(String accResidente) {
		this.accResidente = accResidente;
	}	

	public Persona getFinPersona() {
		return finPersona;
	}

	public void setFinPersona(Persona finPersona) {
		this.finPersona = finPersona;
	}
	
	

	public Referencia getAccSecCodigoRef() {
		return accSecCodigoRef;
	}


	public void setAccSecCodigoRef(Referencia accSecCodigoRef) {
		this.accSecCodigoRef = accSecCodigoRef;
	}


	public Referencia getAccFormaPagoRef() {
		return accFormaPagoRef;
	}


	public void setAccFormaPagoRef(Referencia accFormaPagoRef) {
		this.accFormaPagoRef = accFormaPagoRef;
	}


	public Referencia getAccNacionalidadRef() {
		return accNacionalidadRef;
	}


	public void setAccNacionalidadRef(Referencia accNacionalidadRef) {
		this.accNacionalidadRef = accNacionalidadRef;
	}


	public Referencia getAccEmpCodigoRef() {
		return accEmpCodigoRef;
	}


	public void setAccEmpCodigoRef(Referencia accEmpCodigoRef) {
		this.accEmpCodigoRef = accEmpCodigoRef;
	}


	public Referencia getAccCiuCodigoRef() {
		return accCiuCodigoRef;
	}


	public void setAccCiuCodigoRef(Referencia accCiuCodigoRef) {
		this.accCiuCodigoRef = accCiuCodigoRef;
	}


	public Referencia getAccCiuDptPaisCodigoRef() {
		return accCiuDptPaisCodigoRef;
	}


	public void setAccCiuDptPaisCodigoRef(Referencia accCiuDptPaisCodigoRef) {
		this.accCiuDptPaisCodigoRef = accCiuDptPaisCodigoRef;
	}


	public Referencia getAccCiuDptCodigoRef() {
		return accCiuDptCodigoRef;
	}


	public void setAccCiuDptCodigoRef(Referencia accCiuDptCodigoRef) {
		this.accCiuDptCodigoRef = accCiuDptCodigoRef;
	}


	public Referencia getAccTipoCuentaRef() {
		return accTipoCuentaRef;
	}


	public void setAccTipoCuentaRef(Referencia accTipoCuentaRef) {
		this.accTipoCuentaRef = accTipoCuentaRef;
	}


	public Referencia getAccTipoCapitalRef() {
		return accTipoCapitalRef;
	}


	public void setAccTipoCapitalRef(Referencia accTipoCapitalRef) {
		this.accTipoCapitalRef = accTipoCapitalRef;
	}

	public List<Beneficiario> getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(List<Beneficiario> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}	

	public String getPeriodicidad() {
		return periodicidad;
	}

	public void setPeriodicidad(String periodicidad) {
		this.periodicidad = periodicidad;
	}

	public Referencia getReferenciaPeriodicidad() {
		return referenciaPeriodicidad;
	}

	public void setReferenciaPeriodicidad(Referencia referenciaPeriodicidad) {
		this.referenciaPeriodicidad = referenciaPeriodicidad;
	}

	public String getPago() {
		return pago;
	}

	public void setPago(String pago) {
		this.pago = pago;
	}

	public Referencia getReferenciaPago() {
		return referenciaPago;
	}

	public void setReferenciaPago(Referencia referenciaPago) {
		this.referenciaPago = referenciaPago;
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Accionista other = (Accionista) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Accionista [id=" + id.toString() + ", accSecCodigo=" + accSecCodigo + ", accEstado=" + accEstado
				+ ", accFormaPago=" + accFormaPago + ", accNacionalidad=" + accNacionalidad + ", accPagoDividendo="
				+ accPagoDividendo + ", accCuenta=" + accCuenta + ", accCodBanco=" + accCodBanco + ", accEmpCodigo="
				+ id.getAccEmpCodigo() + ", accNumDeposito=" + accNumDeposito + ", accNombre=" + accNombre + ", accDireccion="
				+ accDireccion + ", accDireccion2=" + accDireccion2 + ", accTelefono=" + accTelefono + ", accTelefono2="
				+ accTelefono2 + ", accCiuCodigo=" + accCiuCodigo + ", accCiuConsec=" + accCiuConsec
				+ ", accCiuDptPaisCodigo=" + accCiuDptPaisCodigo + ", accCiuDptCodigo=" + accCiuDptCodigo
				+ ", accEmail=" + accEmail + ", accFax=" + accFax + ", accAutIdent=" + accAutIdent + ", accAutNombre="
				+ accAutNombre + ", accIndEmp=" + accIndEmp + ", accFIngreso=" + accFIngreso + ", accFRetiro="
				+ accFRetiro + ", accDigitoControl=" + accDigitoControl + ", accTipoCuenta=" + accTipoCuenta
				+ ", accManejoCuenta=" + accManejoCuenta + ", accTipoCapital=" + accTipoCapital + ", accAplicarDcto="
				+ accAplicarDcto + ", accCodAccionista=" + accCodAccionista + ", accResidente=" + accResidente
				+ ", finPersona=" + finPersona + "]";
	}	
	
	
}
