package com.segurosbolivar.finita.aplicacion.dao;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.segurosbolivar.finita.aplicacion.dto.Parametro;
import com.segurosbolivar.finita.aplicacion.entity.Persistente;
import com.segurosbolivar.finita.aplicacion.util.Constantes;
import com.segurosbolivar.finita.aplicacion.util.Log;


@Transactional
@Repository
public class GenericoDAO implements IGenericoDAO {
	
	public static final Logger logger = Logger.getLogger(GenericoDAO.class);

	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public Persistente getObjetctById(Class<? extends Persistente> classz, Object idObject) {
		return entityManager.find(classz, idObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<? extends Persistente> getObjects(Class<? extends Persistente> classz) {
		String hql = "FROM "+classz.getName()+ " as atcl";
		return (List<Persistente>) entityManager.createQuery(hql).getResultList();
	}	

	@Override
	public Persistente saveObject(Persistente object){
		entityManager.persist(object);
		return object;
	}

	@Override
	public Persistente updateObject(Persistente object){
		entityManager.merge(object);
		return object;
	}	

	@Override
	@Transactional
	public boolean deleteObject(Class<? extends Persistente> classz,Integer id) {
		try {
			Query q= entityManager.createQuery("delete from " +classz.getName() +" a where a.id= :id");
			q.setParameter("id", id);			
			q.executeUpdate();			
			return true;
		}catch (Exception e) {
			return false;
		}
	}	
	
	public HashMap<String, Object> callProcedimientoPl(String nombrePL,List<Parametro> pametros,List<Class<?>> typeSalidaSalida,boolean tieneCursor) {		
		logger.info(Log.getCurrentClassAndMethodNames(getClass().getName(), "Llamado al PL "+nombrePL));
		HashMap<String, Object> dataRespuesta= new HashMap<String, Object>();
		try{			 
			StoredProcedureQuery query=entityManager.createStoredProcedureQuery(nombrePL);			
			/*
			 * Agregando los parametros de entrada del PL
			 */
			try {
				int i=1;
				if(pametros!=null)
					for(Parametro par:pametros) {									
						if(par.getValorParametro() instanceof String) {
							query.registerStoredProcedureParameter(i, String.class, ParameterMode.IN);
							i++;
						}else if(par.getValorParametro() instanceof Integer) {
							query.registerStoredProcedureParameter(i,Integer.class,ParameterMode.IN);
							i++;
						}else if(par.getValorParametro() instanceof Date) {
							query.registerStoredProcedureParameter(i,Date.class,ParameterMode.IN);
							i++;
						}					
					}
				try {	
					int j=i;
					for(Class<?> type:typeSalidaSalida) {
						query.registerStoredProcedureParameter(j, type, ParameterMode.OUT);
						j++;
					}
					if(tieneCursor)
						query.registerStoredProcedureParameter(j, ResultSet.class, ParameterMode.REF_CURSOR);
					
				}catch (Exception e) {
					Log.getError(logger,e);
				}
			}catch (Exception e) {
				Log.getError(logger,e);
			}

			/*
			 * Setiando los valores de los parametros de entrada definido para
			 * el llamado del PL 
			 */	
			try {
				int i=1;
				if(pametros!=null)
					for(Parametro par:pametros) {
						logger.debug("Valor: "+par.getValorParametro());					
						if(par.getValorParametro() instanceof String) {
							query.setParameter(i, par.getValorParametro());
							i++;
						}else if(par.getValorParametro() instanceof Integer) {
							query.setParameter(i, par.getValorParametro());
							i++;
						}else if(par.getValorParametro() instanceof Date) {
							query.setParameter(i, par.getValorParametro());
							i++;
						}					
					}				
			}catch (Exception e) {
				Log.getError(logger,e);
			}
			try {
				query.execute();		
				try{
					List<?> data= (List<?>) query.getResultList();
					dataRespuesta.put(Constantes.CLAVE_LISTA, data);
					logger.debug(Log.getCurrentClassAndMethodNames(getClass().getName(),"Tamaño de respuesta: "+ data.size()));
				}catch (Exception e) {

				}

				try{								
					int tamano= pametros!=null?pametros.size()+1:1;
					String nombreArchivoFinal= (String) query.getOutputParameterValue(tamano);
					dataRespuesta.put(Constantes.CLAVE_NAME, nombreArchivoFinal);
					logger.debug("Nombre del archivo: "+ nombreArchivoFinal);
				}catch (Exception e) {

				}			
			}catch (Exception e) {
				Log.getError(logger,e);				
			}
			return dataRespuesta;	

		}catch (Exception e) {
			logger.error("Problemas de llamado del procedimiento."+e.getMessage());
		}
		return null;
	}
	
}
