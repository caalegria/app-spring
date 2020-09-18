package com.segurosbolivar.finita.aplicacion.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.segurosbolivar.finita.aplicacion.dao.IGenericoDAO;
import com.segurosbolivar.finita.aplicacion.dto.Parametro;
import com.segurosbolivar.finita.aplicacion.dto.RespuestaCallPL;
import com.segurosbolivar.finita.aplicacion.dto.SaldoBeneficiario;
import com.segurosbolivar.finita.aplicacion.entity.Persistente;


@Service
public class GenericoService implements IGenericoService {
	@Autowired
	private IGenericoDAO iGenericoDAO;
	
	@Override
	public Persistente getObjetctById(Class<? extends Persistente> classz,Object idObject) {
		return this.iGenericoDAO.getObjetctById(classz,idObject);
	}
	
	@Override
	public List<? extends Persistente> getObjects(Class<? extends Persistente> classz){
		return this.iGenericoDAO.getObjects(classz);
	}
	
	@Override
	public Persistente updateObject(Persistente object) {
		return this.iGenericoDAO.updateObject(object);
	}
	
	@Override
	public Persistente saveObject(Persistente object) {
		return this.iGenericoDAO.saveObject(object);
	}
	
	@Override
	public boolean deleteObject(Class<? extends Persistente> object,Integer id) {
		return this.iGenericoDAO.deleteObject(object, id);		
	}
	
	@Override
	public HashMap<String, Object> callProcedimientoPl(String nombrePL, List<Parametro> pametros,List<Class<?>> typeSalidaSalida, boolean tieneCursor){
		return this.iGenericoDAO.callProcedimientoPl(nombrePL, pametros, typeSalidaSalida, tieneCursor);
	}
	
	@Override
	public List<? extends Persistente> findObjectsByFields(Class<? extends Persistente> classz, ConditionMap conditions,boolean embebedId){
		return this.iGenericoDAO.findObjectsByFields(classz, conditions,embebedId);
	}
	
	@Override
	public List<? extends Persistente> findObjects(Class<? extends Persistente> classz, String conditionField,Serializable conditionValue, Map<String, Boolean> ordering) {	
		return this.iGenericoDAO.findObjects(classz, conditionField, conditionValue, ordering);
	}
	
	@Override
	public RespuestaCallPL callProcedimientoGenerarOrdenPago(List<SaldoBeneficiario> saldos,RespuestaCallPL respuesta) {
		return this.iGenericoDAO.callProcedimientoGenerarOrdenPago(saldos,respuesta);
}
}
