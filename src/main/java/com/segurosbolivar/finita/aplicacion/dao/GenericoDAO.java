package com.segurosbolivar.finita.aplicacion.dao;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.segurosbolivar.finita.aplicacion.dto.Parametro;
import com.segurosbolivar.finita.aplicacion.dto.RespuestaCallPL;
import com.segurosbolivar.finita.aplicacion.dto.SaldoBeneficiario;
import com.segurosbolivar.finita.aplicacion.entity.Persistente;
import com.segurosbolivar.finita.aplicacion.service.ConditionMap;
import com.segurosbolivar.finita.aplicacion.util.Constantes;
import com.segurosbolivar.finita.aplicacion.util.Log;
import com.segurosbolivar.finita.aplicacion.util.Utilidades;

import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;


/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */

@Transactional
@Repository
public class GenericoDAO implements IGenericoDAO {

	public static final Logger logger = Logger.getLogger(GenericoDAO.class);

	@PersistenceContext	
	private EntityManager entityManager;

	/**
	 * Metodo para persistir(Insert) cualquier entity
	 * que extienda de la clase persistente
	 */
	@Override
	public Persistente saveObject(Persistente object){
		entityManager.persist(object);
		return object;
	}

	/**
	 * Metodo para actualizar(Update) cualquier entity
	 * que extienda de la clase persistente
	 */
	@Override
	public Persistente updateObject(Persistente object){
		entityManager.merge(object);
		return object;
	}	


	/**
	 * Metodo para eliminar(Delete) cualquier entity
	 * que extienda de la clase persistente
	 */
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


	/**
	 * Metodo para obtener un listado de objeto entity 
	 * que extiendan de persistente bajo una sola condicion 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<? extends Persistente> findObjects(Class<? extends Persistente> classz, String conditionField, Serializable conditionValue, Map<String, Boolean> ordering) {
		try {
			StringBuffer sb = new StringBuffer(Constantes.BASIC_SIZE);
			sb.append("select a from ").append(classz.getName()).append(" a ");

			TypedQuery query = null;
			if (conditionField != null && conditionValue != null) {
				sb.append(" where a.").append(conditionField).append(" = ").append(conditionValue);
				Utilidades.buildOrder(sb, ordering);
				query = (TypedQuery) entityManager.createQuery(sb.toString());
			} else {
				Utilidades.buildOrder(sb, ordering);
				query = (TypedQuery) entityManager.createQuery(sb.toString());

			}
			return query.getResultList();
		} catch (Exception e) {
			Log.getError(logger, e);		
		}
		return new ArrayList<Persistente>();
	}



	/*
	 * Consulta generica para cualquier entity que sea mapeado
	 * y que herede de la clase peristente enviando el listado de condiciones
	 * dentro de un map de condiciones
	 */

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<? extends Persistente> findObjectsByFields(Class<? extends Persistente> classz, ConditionMap conditions,boolean embebedId){

		Set<String> keys = null;
		List<? extends Persistente> result = null;
		short oper = 0;

		int VALUE = 0;
		int OPERATION = 1;

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery criteria = builder.createQuery(classz);
		Root root = criteria.from( classz );
		if(conditions!=null && conditions.getProps()!=null) {
			keys = conditions.getProps().keySet();
		}
		if(keys!=null && !keys.isEmpty()) {
			Predicate restriction=null;
			for(String property: keys) {
				if(conditions.getProps().get(property)!=null && (conditions.getProps().get(property).size()==2) 
						&& conditions.getProps().get(property).get(OPERATION)!=null) {

					oper = (Short) conditions.getProps().get(property).get(OPERATION);
					if(oper == ConditionMap.CRITERIO_OPER_IS_NOT_NULL ||
							oper == ConditionMap.CRITERIO_OPER_IS_NULL ||	
							conditions.getProps().get(property).get(VALUE)!=null) {
						restriction = Utilidades.addPredicateCriterio(builder,restriction,root,oper,embebedId,property,conditions.getProps().get(property).get(VALUE));
					}					

				}
			}
			if(restriction != null) {
				criteria.where(restriction);
			}
		}
		result = entityManager.createQuery(criteria).getResultList();
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Persistente findObjectByFields(Class<? extends Persistente> classz, ConditionMap conditions,boolean embebedId){
		Set<String> keys = null;
		short oper = 0;

		int VALUE = 0;
		int OPERATION = 1;

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery criteria = builder.createQuery(classz);
		Root root = criteria.from( classz );
		if(conditions!=null && conditions.getProps()!=null) {
			keys = conditions.getProps().keySet();
		}
		if(keys!=null && !keys.isEmpty()) {
			Predicate restriction=null;
			for(String property: keys) {
				if(conditions.getProps().get(property)!=null && (conditions.getProps().get(property).size()==2) 
						&& conditions.getProps().get(property).get(OPERATION)!=null) {

					oper = (Short) conditions.getProps().get(property).get(OPERATION);
					if(oper == ConditionMap.CRITERIO_OPER_IS_NOT_NULL ||
							oper == ConditionMap.CRITERIO_OPER_IS_NULL ||	
							conditions.getProps().get(property).get(VALUE)!=null) {
						restriction = Utilidades.addPredicateCriterio(builder,restriction,root,oper,embebedId,property,conditions.getProps().get(property).get(VALUE));
					}
				}
			}
			if(restriction != null) {
				criteria.where(restriction);
			}
		}

		TypedQuery query = entityManager.createQuery(criteria);
		query.setMaxResults(1);
		Persistente p = (Persistente) query.getSingleResult();
		return p;
	}

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
					if(typeSalidaSalida!=null)
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



	/**
	 * 
	 * @param saldos
	 */
	@Override
	public RespuestaCallPL callProcedimientoGenerarOrdenPago(List<SaldoBeneficiario> saldos,RespuestaCallPL respuesta) {
		logger.info(Log.getCurrentClassAndMethodNames(getClass().getName(), "Llamado al PL "+Constantes.PKG_FIN_ORDEN_PAGO_ENVIAR_A_PAGO));
		try {			
			Session session = entityManager.unwrap( Session.class );						
			session.doWork(new Work(){				
				public void execute(Connection conn) throws SQLException {
					try {						
						try {
							OracleConnection connection=null;							
							if (conn.isWrapperFor(OracleConnection.class)) {								
								connection = conn.unwrap(OracleConnection.class);
							}

							if(connection!=null) {
								String query = "{call " + Constantes.PKG_FIN_ORDEN_PAGO_ENVIAR_A_PAGO + "(?,?,?)}";
								StructDescriptor descriptorType = StructDescriptor.createDescriptor("TYP_FILA_SALDOS_BENEF", connection);
								Object[] type;			            
								STRUCT[] estructurasTypoArchivo = new STRUCT[saldos.size()];
								int i=0;
								for(SaldoBeneficiario sb:saldos) {
									type = new Object[]{sb.getEmpresa(),sb.getBeneficiario(),sb.getDividendo(),sb.getMoneda(),sb.getGrupo(),sb.getSaldo(),sb.getRetencion()};
									STRUCT estructuraTypoArchivo = new STRUCT(descriptorType, connection, type);
									estructurasTypoArchivo[i] = estructuraTypoArchivo;
									i++;
								}

								ArrayDescriptor descriptorTypeTBL = ArrayDescriptor.createDescriptor("TYP_ARRAY_SALDOS_BENEF", connection);
								ARRAY typoTBLArchivos = new ARRAY(descriptorTypeTBL, connection, estructurasTypoArchivo);

								CallableStatement cs = connection.prepareCall(query);
								cs.setDate(1,Date.valueOf("2020-09-18"));
								cs.setObject(2, (Object) typoTBLArchivos);								
								cs.registerOutParameter(3, Types.VARCHAR);							

								cs.executeUpdate();															
								respuesta.setCodigo(cs.getString(3));			                
								logger.info("Resultado " + respuesta);		
							}else {
								logger.info("La OracleConnection no se pudo castear.");
							}
						}catch (Exception e) {
							Log.getError(logger, e);
						}

					}catch (Exception e) {
						Log.getError(logger, e);
					}
				}
			});
		}catch (Exception e) {
			Log.getError(logger, e);
		}
		
		return respuesta;
	}


}
