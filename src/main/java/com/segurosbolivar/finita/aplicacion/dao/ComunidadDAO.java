package com.segurosbolivar.finita.aplicacion.dao;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.segurosbolivar.finita.aplicacion.dto.Catalogo;
import com.segurosbolivar.finita.aplicacion.dto.SaldoBeneficiario;
import com.segurosbolivar.finita.aplicacion.entity.Accionista;
import com.segurosbolivar.finita.aplicacion.entity.Beneficiario;
import com.segurosbolivar.finita.aplicacion.entity.BeneficiarioPK;
import com.segurosbolivar.finita.aplicacion.entity.Persona;
import com.segurosbolivar.finita.aplicacion.util.Log;


@Transactional
@Repository
public class ComunidadDAO  implements IComunidadDAO {
	
	public static final Logger logger = Logger.getLogger(ComunidadDAO.class);

	@PersistenceContext	
	private EntityManager entityManager;	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SaldoBeneficiario> getSaldosBeneficiario(){
		try {
			StringBuffer hqlNative= new StringBuffer();
			hqlNative.append("SELECT sb.* from FINVIEW_SALDOS_BENEFICIARIOS");
			Query query= entityManager.createNativeQuery(hqlNative.toString(), "SaldoBeneficiarioMapping");
			List<SaldoBeneficiario> saldos= query.getResultList();
			return saldos;
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		return new ArrayList<SaldoBeneficiario>();		
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
			query.setParameter("accEmpCodigo", accionista.getAccEmpCodigo());			
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
}
