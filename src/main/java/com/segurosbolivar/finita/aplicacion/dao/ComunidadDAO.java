package com.segurosbolivar.finita.aplicacion.dao;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.segurosbolivar.finita.aplicacion.dto.ArchivoDeceval;
import com.segurosbolivar.finita.aplicacion.dto.Catalogo;
import com.segurosbolivar.finita.aplicacion.dto.SaldoBeneficiario;
import com.segurosbolivar.finita.aplicacion.entity.Accionista;
import com.segurosbolivar.finita.aplicacion.entity.AccionistaPK;
import com.segurosbolivar.finita.aplicacion.entity.Beneficiario;
import com.segurosbolivar.finita.aplicacion.entity.BeneficiarioPK;
import com.segurosbolivar.finita.aplicacion.entity.LogCargues;
import com.segurosbolivar.finita.aplicacion.entity.Persona;
import com.segurosbolivar.finita.aplicacion.entity.Referencia;
import com.segurosbolivar.finita.aplicacion.service.IGenericoService;
import com.segurosbolivar.finita.aplicacion.util.Constantes;
import com.segurosbolivar.finita.aplicacion.util.Log;
import com.segurosbolivar.finita.aplicacion.util.Utilidades;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
@Transactional
@Repository
public class ComunidadDAO  implements IComunidadDAO {
	
	public static final Logger logger = Logger.getLogger(ComunidadDAO.class);

	@PersistenceContext	
	private EntityManager entityManager;	
	
	@Autowired
	IGenericoService genericoService;		
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SaldoBeneficiario> getSaldosBeneficiario(){
		List<SaldoBeneficiario> saldosData=new ArrayList<SaldoBeneficiario>();
		try {
			StringBuffer hqlNative= new StringBuffer();			
			hqlNative.append("SELECT * FROM  FINVIEW_SALDOS_BENEFICIARIOS order by DIVIDENDO asc");		
			Query query= entityManager.createNativeQuery(hqlNative.toString());			
			List<Object []> saldos= query.getResultList();
			for(Object[] obj:saldos) {
				SaldoBeneficiario saldobeneficiario = new SaldoBeneficiario();
				saldobeneficiario.setEmpresa(obj[0].toString());
				saldobeneficiario.setAccionista(obj[1].toString());
				saldobeneficiario.setBeneficiario(obj[2].toString());
				saldobeneficiario.setDiviendo(new BigInteger(obj[3].toString()));
				saldobeneficiario.setMoneda(obj[4].toString());
				saldobeneficiario.setGrupo(obj[5].toString());
				saldobeneficiario.setSaldo(new BigDecimal(obj[6].toString()));
				saldobeneficiario.setRetencion(new BigDecimal(obj[7].toString()));	
				try{
					saldobeneficiario.setCuentaContable(new BigDecimal(obj[8].toString()));
				}catch (Exception e) {
					saldobeneficiario.setCuentaContable(new BigDecimal("0"));
				}
				saldobeneficiario.setEstadoTramite(obj[9].toString());
				saldosData.add(saldobeneficiario);
				
				try {
					saldobeneficiario.setPersona((Persona) this.genericoService.getObjetctById(Persona.class, saldobeneficiario.getBeneficiario()));
				}catch (Exception e) {
					Log.getError(logger, e);
					
				}

				try{
					saldobeneficiario.setAccionistaObj(new Accionista(new AccionistaPK(saldobeneficiario.getAccionista(), Constantes.CODIGO_EMPRESA_ACCIONISTA)));
					saldobeneficiario.setAccionistaObj((Accionista) this.genericoService.getObjetctById(Accionista.class, saldobeneficiario.getAccionistaObj().getId()));
				}catch (Exception e) {
					Log.getError(logger, e);
					
				}

				try{
					saldobeneficiario.setPeriodicidad(new Referencia(saldobeneficiario.getAccionistaObj().getPeriodicidad()));
					saldobeneficiario.setPeriodicidad((Referencia) this.genericoService.getObjetctById(Referencia.class,saldobeneficiario.getPeriodicidad().getRefCodigo()));
				}catch (Exception e) {
					Log.getError(logger, e);
					saldobeneficiario.setPeriodicidad(new Referencia());					
				}

				try{
					saldobeneficiario.setTipoPago(new Referencia(saldobeneficiario.getAccionistaObj().getPago()));
					saldobeneficiario.setTipoPago((Referencia) this.genericoService.getObjetctById(Referencia.class,saldobeneficiario.getTipoPago().getRefCodigo()));
				}catch (Exception e) {
					Log.getError(logger, e);
					saldobeneficiario.setTipoPago(new Referencia());					
				}
				
				try{
					saldobeneficiario.setEstadoTram(new Referencia(saldobeneficiario.getEstadoTramite()));
					saldobeneficiario.setEstadoTram((Referencia) this.genericoService.getObjetctById(Referencia.class,saldobeneficiario.getEstadoTram().getRefCodigo()));
				}catch (Exception e) {
					Log.getError(logger, e);
					saldobeneficiario.setTipoPago(new Referencia());					
				}
				
			}
			return saldosData;
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return saldosData;		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Beneficiario> getBeneficiariosPorAccionista(Accionista accionista){
		try {
			StringBuffer hql= new StringBuffer();
			hql.append("SELECT ben");
			hql.append(" FROM ").append(Beneficiario.REF).append(" ben");
			hql.append(" WHERE ben.").append(Beneficiario.PROP_ID).append(".").append(BeneficiarioPK.PROP_ACC_PER_IDENT).append("= :accPerIdent");
			hql.append(" AND ben.").append(Beneficiario.PROP_ID).append(".").append(BeneficiarioPK.PROP_ACC_EMP_CODIGO).append("= :accEmpCodigo");
			Query query= entityManager.createQuery(hql.toString());
			query.setParameter("accPerIdent", accionista.getId().getAccPerIdent());
			query.setParameter("accEmpCodigo", accionista.getId().getAccEmpCodigo());			
			List<Beneficiario> beneficiarios= query.getResultList();
			return beneficiarios;
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		
		return new ArrayList<Beneficiario>();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Persona> getPersonasNoBeneficiarioAccionista(Accionista accionista){
		try {
			StringBuffer hql= new StringBuffer();
			hql.append("SELECT per");
			hql.append(" FROM ").append(Persona.REF).append(" per");
			hql.append(" WHERE per.").append(Persona.PROP_ID).append(" not in (");			
			hql.append(" SELECT ben.").append(Beneficiario.PROP_ID).append(".").append(BeneficiarioPK.PROP_BEN_PER_IDENT);
			hql.append(" FROM ").append(Beneficiario.REF).append(" ben");
			hql.append(" WHERE ben.").append(Beneficiario.PROP_ID).append(".").append(BeneficiarioPK.PROP_ACC_PER_IDENT).append(" = :accPerIdent").append(")");			
			Query query= entityManager.createQuery(hql.toString());
			query.setParameter("accPerIdent", accionista.getId().getAccPerIdent());				
			List<Persona> personas= query.getResultList();
			return personas;
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return new ArrayList<Persona>();
	}
	

	@Override
	public boolean existePersona(Persona persona){
		try {
			StringBuffer hql= new StringBuffer();
			hql.append("SELECT per");
			hql.append(" FROM ").append(Persona.REF).append(" per");
			hql.append(" WHERE per.").append(Persona.PROP_ID).append("= :idPersona"); 			
			Query query= entityManager.createQuery(hql.toString());
			query.setParameter("idPersona", persona.getId());				
			Persona personaRespuesta= (Persona) query.getSingleResult();
			if(personaRespuesta!=null)
				if(personaRespuesta.getId().contentEquals(persona.getId()))
					return true;			
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Catalogo> catalogoNit(){
		try {
			StringBuffer hql= new StringBuffer();
			hql.append("SELECT new com.segurosbolivar.finita.aplicacion.dto.Catalogo(per.").append(Persona.PROP_ID);
			hql.append(",per.").append(Persona.PROP_NOMBRE).append(")");
			hql.append(" FROM ").append(Persona.REF).append(" per");			 			
			Query query= entityManager.createQuery(hql.toString());							
			List<Catalogo> catalogo= query.getResultList();
			return catalogo;			
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return new ArrayList<Catalogo>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean getArchivoDeceval() {		
		try {
			StringBuffer hqlNative= new StringBuffer();			
			hqlNative.append("SELECT empresa"); 
			hqlNative.append(",oficina"); 
			hqlNative.append(",fecha_apunte"); 
			hqlNative.append(",clase_asiento"); 
			hqlNative.append(",numero_asiento"); 
			hqlNative.append(",lpad(to_char(numero_apunte),8,' ') numero_apunte"); 
			hqlNative.append(",cuenta"); 
			hqlNative.append(",codigo_agrupa"); 
			hqlNative.append(",concepto_movto"); 
			hqlNative.append(",ajuste_infla"); 
			hqlNative.append(",rpad(numero_docto,10,' ') numero_docto"); 
			hqlNative.append(",fecha_vcto"); 
			hqlNative.append(",codigo_ramo"); 
			hqlNative.append(",lpad(replace(to_char(valor_debito ,'999999999999.00'),'.',null),15,' ') valor_debito"); 
			hqlNative.append(",lpad(replace(to_char(valor_credito,'999999999999.00'),'.',null),15,' ') valor_credito"); 
			hqlNative.append(",benef_prod"); 
			hqlNative.append(",clave_agente"); 
			hqlNative.append(",codigo_act_pro"); 
			hqlNative.append(",lpad(nit,16,'0') nit"); 
			hqlNative.append(",lpad(to_char(consec_tercero),16,'0')"); 
			hqlNative.append(" FROM finico010");

			Query query= entityManager.createNativeQuery(hqlNative.toString());			
			List<Object []> dataDeceval= query.getResultList();
			List<ArchivoDeceval> archivoData= new ArrayList<ArchivoDeceval>();
			for(Object[] obj:dataDeceval) {
				archivoData.add(new ArchivoDeceval(obj[0].toString(), obj[1].toString(), obj[2].toString(), obj[3].toString(), obj[4].toString(), obj[5].toString(), obj[6].toString(), obj[7].toString(), obj[8].toString(), obj[9].toString(), obj[10].toString(), obj[11].toString(), obj[12].toString(), obj[13].toString(), obj[14].toString(), obj[15].toString(), obj[16].toString(), obj[17].toString(), obj[18].toString(), obj[19].toString()));
			}
			return Utilidades.escribirArchivoPlano(archivoData);
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		
		return false;
	}

	public boolean relacionAccionistaBeneficiario(Persona persona,Beneficiario beneficiario) {

		try {
			entityManager.getTransaction().begin();
			if(persona!=null)
				this.entityManager.persist(persona);

			this.entityManager.persist(beneficiario);

			entityManager.getTransaction().commit();
			return true;
		}catch (Exception e) {
			entityManager.getTransaction().rollback();
			return false;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LogCargues> getLogCargues(){
		try {
			List<LogCargues> logCargue = new ArrayList<LogCargues>();
			StringBuffer hql= new StringBuffer("SELECT c FROM LogCargues c order by c.fechaCargue desc");			
			Query query= entityManager.createQuery(hql.toString());
			logCargue= query.getResultList();
			
		 
			return logCargue;
			
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return new ArrayList<LogCargues>();
	}

	/*Sustituir cuando se conozca la tabla de cargues de dividendos*/
	@SuppressWarnings("unchecked")
	@Override
	public List<LogCargues> exportarCargue(java.sql.Date fecCargue) {
		try {
			List<LogCargues> logCargue = new ArrayList<LogCargues>();
			StringBuffer hql= new StringBuffer("SELECT cargue FROM LogCargues cargue WHERE cargue.fechaCargue = :fechCargue");			
			Query query= entityManager.createQuery(hql.toString()).setParameter("fechCargue", fecCargue);
			
			 if(entityManager == null) {
		            System.out.println("null entityManager");
			 }else {
				 logCargue= query.getResultList();
			 }
		 
			return logCargue;
			
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return new ArrayList<LogCargues>();
	}
	
}
