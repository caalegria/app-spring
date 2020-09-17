package com.segurosbolivar.finita.aplicacion.entity;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
@Entity
@Table(name="Finpersonas")
public class Persona extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String REF="Persona";
	public static final String PROP_ID="id";
	public static final String PROP_NOMBRES_APELLIDOS="perDetalleNombre";
	public static final String PROP_NOMBRE="perNombre";
	public static final String PROP_PRIMER_APELLIDO="perPrimerApellido";
	public static final String PROP_SEGUNDO_APELLIDO="perSegundoApellido";

	@Id
	@Column(name="PER_IDENT",length = 20)
	private String id;

	@Column(name="PER_NOMBRE",length = 120)
	private String perNombre; 

	@Column(name="PER_F_CREACION")
	private Date perFCreacion;

	@Column(name="PER_DIRECCION",length = 50)
	private String perDireccion;

	@Column(name="PER_TELEFONO",length = 15)
	private String perTelefono;

	@Column(name="PER_TELEFONO_2",length = 15)
	private String perTelefono2;

	@Column(name="PER_FAX",length = 15)
	private String perFax;

	@Column(name="PER_TIPO_IDENT",length = 3)	
	private String perTipoIdent;
	
	@Transient
	private Referencia perTipoIdentRef;

	@Column(name="PER_CIU_CODIGO",length = 3)
	private String perCiuCodigo;

	@Column(name="PER_CIU_CONSEC",length = 3)
	private String perCiuConsec;

	@Column(name="PER_CIU_DPT_PAIS_CODIGO")
	private BigInteger perCiuDptPaisCodigo;

	@Column(name="PER_CIU_DPT_CODIGO",length = 2)
	private String perCiuDptCodigo;

	@Column(name="PER_NATURALEZA",length = 3)
	private String perNaturaleza;
	
	@Transient
	private Referencia perNaturalezaRef;

	@Column(name="PER_DIRECCION_2",length = 50)
	private String perDireccion2;

	@Column(name="PER_CIU_CODIGO_2",length = 3)
	private String perCiuCodigo2;

	@Column(name="PER_CIU_CONSEC_2",length = 3)
	private String perCiuConsec2;

	@Column(name="PER_CIU_DPT_PAIS_CODIGO_2")
	private BigInteger perCiuDptPaisCodigo2;

	@Column(name="PER_CIU_DPT_CODIGO_2",length = 2)
	private String perCiuDptCodigo2;

	@Column(name="PER_IND_DEPOSITO",length = 1)
	private String perIndDeposito;

	@Column(name="PER_BASICO",length = 1)
	private String perBasico;

	@Column(name="PER_IDENT_2",length = 30)
	private String perIndent2;

	@Column(name="PER_NOMBRE_CORTO",length = 40)
	private String perNombreCorto;

	@Column(name="PER_CLASIF_TRIBUTARIA",length = 3)
	private String perClasifTributaria;

	@Column(name="PER_PRIMER_APELLIDO",length = 30)
	private String perPrimerApellido;

	@Column(name="PER_SEGUNDO_APELLIDO",length = 30)	
	private String perSegundoApellido;

	@Column(name="PER_DETALLE_NOMBRE",length = 60)
	private String perDetalleNombre;

	@Column(name="PER_FICHA_REGISTRAL",length = 30)
	private String perFichaResgistral;

	@Column(name="PER_NAT_BKP",length = 3)
	private String perNatBkp;

	@Column(name="PER_F_NACIMIENTO")	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date perFNacimiento;	
	
	@Transient
    private List<Accionista> accionistas;	
	
	@Transient
	private String genero;
	
	@Transient
	@Email
	private String email;
	
	@Transient
	private String autorizaCompartirInf;
	
	@Transient
	private String autorizaRecibirInf;
	
	@Transient
	private String benValor;
	
	@Transient
	private String benNit;
	

	public Persona() {
		// TODO Auto-generated constructor stub
	}
	
	public Persona(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPerNombre() {
		return perNombre;
	}

	public void setPerNombre(String perNombre) {
		this.perNombre = perNombre;
	}

	public Date getPerFCreacion() {
		return perFCreacion;
	}

	public void setPerFCreacion(Date perFCreacion) {
		this.perFCreacion = perFCreacion;
	}

	public String getPerDireccion() {
		return perDireccion;
	}

	public void setPerDireccion(String perDireccion) {
		this.perDireccion = perDireccion;
	}

	public String getPerTelefono() {
		return perTelefono;
	}

	public void setPerTelefono(String perTelefono) {
		this.perTelefono = perTelefono;
	}

	public String getPerTelefono2() {
		return perTelefono2;
	}

	public void setPerTelefono2(String perTelefono2) {
		this.perTelefono2 = perTelefono2;
	}

	public String getPerFax() {
		return perFax;
	}

	public void setPerFax(String perFax) {
		this.perFax = perFax;
	}

	public String getPerTipoIdent() {
		return perTipoIdent;
	}

	public void setPerTipoIdent(String perTipoIdent) {
		this.perTipoIdent = perTipoIdent;
	}

	public String getPerCiuCodigo() {
		return perCiuCodigo;
	}

	public void setPerCiuCodigo(String perCiuCodigo) {
		this.perCiuCodigo = perCiuCodigo;
	}

	public String getPerCiuConsec() {
		return perCiuConsec;
	}

	public void setPerCiuConsec(String perCiuConsec) {
		this.perCiuConsec = perCiuConsec;
	}

	public BigInteger getPerCiuDptPaisCodigo() {
		return perCiuDptPaisCodigo;
	}

	public void setPerCiuDptPaisCodigo(BigInteger perCiuDptPaisCodigo) {
		this.perCiuDptPaisCodigo = perCiuDptPaisCodigo;
	}

	public String getPerCiuDptCodigo() {
		return perCiuDptCodigo;
	}

	public void setPerCiuDptCodigo(String perCiuDptCodigo) {
		this.perCiuDptCodigo = perCiuDptCodigo;
	}

	public String getPerNaturaleza() {
		return perNaturaleza;
	}

	public void setPerNaturaleza(String perNaturaleza) {
		this.perNaturaleza = perNaturaleza;
	}

	public String getPerDireccion2() {
		return perDireccion2;
	}

	public void setPerDireccion2(String perDireccion2) {
		this.perDireccion2 = perDireccion2;
	}

	public String getPerCiuCodigo2() {
		return perCiuCodigo2;
	}

	public void setPerCiuCodigo2(String perCiuCodigo2) {
		this.perCiuCodigo2 = perCiuCodigo2;
	}

	public String getPerCiuConsec2() {
		return perCiuConsec2;
	}

	public void setPerCiuConsec2(String perCiuConsec2) {
		this.perCiuConsec2 = perCiuConsec2;
	}

	public BigInteger getPerCiuDptPaisCodigo2() {
		return perCiuDptPaisCodigo2;
	}

	public void setPerCiuDptPaisCodigo2(BigInteger perCiuDptPaisCodigo2) {
		this.perCiuDptPaisCodigo2 = perCiuDptPaisCodigo2;
	}

	public String getPerCiuDptCodigo2() {
		return perCiuDptCodigo2;
	}

	public void setPerCiuDptCodigo2(String perCiuDptCodigo2) {
		this.perCiuDptCodigo2 = perCiuDptCodigo2;
	}

	public String getPerIndDeposito() {
		return perIndDeposito;
	}

	public void setPerIndDeposito(String perIndDeposito) {
		this.perIndDeposito = perIndDeposito;
	}

	public String getPerBasico() {
		return perBasico;
	}

	public void setPerBasico(String perBasico) {
		this.perBasico = perBasico;
	}

	public String getPerIndent2() {
		return perIndent2;
	}

	public void setPerIndent2(String perIndent2) {
		this.perIndent2 = perIndent2;
	}

	public String getPerNombreCorto() {
		return perNombreCorto;
	}

	public void setPerNombreCorto(String perNombreCorto) {
		this.perNombreCorto = perNombreCorto;
	}

	public String getPerClasifTributaria() {
		return perClasifTributaria;
	}

	public void setPerClasifTributaria(String perClasifTributaria) {
		this.perClasifTributaria = perClasifTributaria;
	}

	public String getPerPrimerApellido() {
		return perPrimerApellido;
	}

	public void setPerPrimerApellido(String perPrimerApellido) {
		this.perPrimerApellido = perPrimerApellido;
	}

	public String getPerSegundoApellido() {
		return perSegundoApellido;
	}

	public void setPerSegundoApellido(String perSegundoApellido) {
		this.perSegundoApellido = perSegundoApellido;
	}

	public String getPerDetalleNombre() {
		return perDetalleNombre;
	}

	public void setPerDetalleNombre(String perDetalleNombre) {
		this.perDetalleNombre = perDetalleNombre;
	}

	public String getPerFichaResgistral() {
		return perFichaResgistral;
	}

	public void setPerFichaResgistral(String perFichaResgistral) {
		this.perFichaResgistral = perFichaResgistral;
	}

	public String getPerNatBkp() {
		return perNatBkp;
	}

	public void setPerNatBkp(String perNatBkp) {
		this.perNatBkp = perNatBkp;
	}

	public Date getPerFNacimiento() {
		return perFNacimiento;
	}

	public void setPerFNacimiento(Date perFNacimiento) {
		this.perFNacimiento = perFNacimiento;
	}
	
	public List<Accionista> getAccionistas() {
		return accionistas;
	}

	public void setAccionistas(List<Accionista> accionistas) {
		this.accionistas = accionistas;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	

	public String getAutorizaCompartirInf() {
		return autorizaCompartirInf;
	}

	public void setAutorizaCompartirInf(String autorizaCompartirInf) {
		this.autorizaCompartirInf = autorizaCompartirInf;
	}

	public String getAutorizaRecibirInf() {
		return autorizaRecibirInf;
	}

	public void setAutorizaRecibirInf(String autorizaRecibirInf) {
		this.autorizaRecibirInf = autorizaRecibirInf;
	}	

	public String getBenValor() {
		return benValor;
	}

	public void setBenValor(String benValor) {
		this.benValor = benValor;
	}

	public String getBenNit() {
		return benNit;
	}

	public void setBenNit(String benNit) {
		this.benNit = benNit;
	}
	

	public Referencia getPerTipoIdentRef() {
		return perTipoIdentRef;
	}

	public void setPerTipoIdentRef(Referencia perTipoIdentRef) {
		this.perTipoIdentRef = perTipoIdentRef;
	}	

	public Referencia getPerNaturalezaRef() {
		return perNaturalezaRef;
	}

	public void setPerNaturalezaRef(Referencia perNaturalezaRef) {
		this.perNaturalezaRef = perNaturalezaRef;
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
		Persona other = (Persona) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Finpersona [id=" + id + ", perNombre=" + perNombre + ", perFCreacion=" + perFCreacion
				+ ", perDireccion=" + perDireccion + ", perTelefono=" + perTelefono + ", perTelefono2=" + perTelefono2
				+ ", perFax=" + perFax + ", perTipoIdent=" + perTipoIdent + ", perCiuCodigo=" + perCiuCodigo
				+ ", perCiuConsec=" + perCiuConsec + ", perCiuDptPaisCodigo=" + perCiuDptPaisCodigo
				+ ", perCiuDptCodigo=" + perCiuDptCodigo + ", perNaturaleza=" + perNaturaleza + ", perDireccion2="
				+ perDireccion2 + ", perCiuCodigo2=" + perCiuCodigo2 + ", perCiuConsec2=" + perCiuConsec2
				+ ", perCiuDptPaisCodigo2=" + perCiuDptPaisCodigo2 + ", perCiuDptCodigo2=" + perCiuDptCodigo2
				+ ", perIndDeposito=" + perIndDeposito + ", perBasico=" + perBasico + ", perIndent2=" + perIndent2
				+ ", perNombreCorto=" + perNombreCorto + ", perClasifTributaria=" + perClasifTributaria
				+ ", perPrimerApellido=" + perPrimerApellido + ", perSegundoApellido=" + perSegundoApellido
				+ ", perDetalleNombre=" + perDetalleNombre + ", perFichaResgistral=" + perFichaResgistral
				+ ", perNatBkp=" + perNatBkp + ", perFNacimiento=" + perFNacimiento + "]";
	}
}
