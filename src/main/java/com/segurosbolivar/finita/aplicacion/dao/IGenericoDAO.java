package com.segurosbolivar.finita.aplicacion.dao;
import java.util.List;

import com.segurosbolivar.finita.aplicacion.entity.Persistente;


public interface IGenericoDAO {		
	Persistente updateObject(Persistente object);
	Persistente saveObject(Persistente object);	
	List<? extends Persistente> getObjects(Class<? extends Persistente> classz);
	Persistente getObjetctById(Class<? extends Persistente> classz, Object idObject);
	boolean deleteObject(Class<? extends Persistente> object, Integer id);	
   
}
 