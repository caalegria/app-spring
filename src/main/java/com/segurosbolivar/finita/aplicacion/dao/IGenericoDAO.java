package com.segurosbolivar.finita.aplicacion.dao;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.segurosbolivar.finita.aplicacion.dto.Parametro;
import com.segurosbolivar.finita.aplicacion.dto.RespuestaCallPL;
import com.segurosbolivar.finita.aplicacion.dto.SaldoBeneficiario;
import com.segurosbolivar.finita.aplicacion.entity.Persistente;
import com.segurosbolivar.finita.aplicacion.service.ConditionMap;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
public interface IGenericoDAO {		
	Persistente updateObject(Persistente object);
	Persistente saveObject(Persistente object);
	boolean deleteObject(Class<? extends Persistente> object, Integer id);
	Persistente getObjetctById(Class<? extends Persistente> classz, Object idObject);		
	List<? extends Persistente> findObjectsByFields(Class<? extends Persistente> classz, ConditionMap conditions,boolean embebedId);
	List<? extends Persistente> findObjects(Class<? extends Persistente> classz, String conditionField,Serializable conditionValue, Map<String, Boolean> ordering);
	List<? extends Persistente> getObjects(Class<? extends Persistente> classz);
	HashMap<String, Object> callProcedimientoPl(String nombrePL, List<Parametro> pametros,List<Class<?>> typeSalidaSalida, boolean tieneCursor);	
	RespuestaCallPL callProcedimientoGenerarOrdenPago(List<SaldoBeneficiario> saldos, RespuestaCallPL respuesta);   
}
 