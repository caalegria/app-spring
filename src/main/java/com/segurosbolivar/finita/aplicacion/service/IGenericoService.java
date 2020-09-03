package com.segurosbolivar.finita.aplicacion.service;


import java.util.HashMap;
import java.util.List;

import com.segurosbolivar.finita.aplicacion.dto.Parametro;
import com.segurosbolivar.finita.aplicacion.entity.Persistente;

public interface IGenericoService {	
	Persistente updateObject(Persistente object);
	Persistente saveObject(Persistente object);	
	List<? extends Persistente> getObjects(Class<? extends Persistente> classz);
	Persistente getObjetctById(Class<? extends Persistente> classz, Object idObject);
	boolean deleteObject(Class<? extends Persistente> object, Integer id);
	HashMap<String, Object> callProcedimientoPl(String nombrePL, List<Parametro> pametros,List<Class<?>> typeSalidaSalida, boolean tieneCursor);
}
