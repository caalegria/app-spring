package com.segurosbolivar.finita.aplicacion.service;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.segurosbolivar.finita.aplicacion.dto.Parametro;
import com.segurosbolivar.finita.aplicacion.dto.RespuestaCallPL;
import com.segurosbolivar.finita.aplicacion.dto.SaldoBeneficiario;
import com.segurosbolivar.finita.aplicacion.entity.Persistente;

public interface IGenericoService {	
	Persistente updateObject(Persistente object);
	Persistente saveObject(Persistente object);	
	List<? extends Persistente> getObjects(Class<? extends Persistente> classz);
	Persistente getObjetctById(Class<? extends Persistente> classz, Object idObject);
	boolean deleteObject(Class<? extends Persistente> object, Integer id);
	HashMap<String, Object> callProcedimientoPl(String nombrePL, List<Parametro> pametros,List<Class<?>> typeSalidaSalida, boolean tieneCursor);
	List<? extends Persistente> findObjectsByFields(Class<? extends Persistente> classz, ConditionMap conditions,boolean embebedId);
	List<? extends Persistente> findObjects(Class<? extends Persistente> classz, String conditionField, Serializable conditionValue, Map<String, Boolean> ordering);
	RespuestaCallPL callProcedimientoGenerarOrdenPago(List<SaldoBeneficiario> saldos, String usuario, RespuestaCallPL respuesta);
	List<? extends Persistente> findObjectsByQuery(String query, Integer limit) throws Exception;
}