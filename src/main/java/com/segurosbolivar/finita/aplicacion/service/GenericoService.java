package com.segurosbolivar.finita.aplicacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.segurosbolivar.finita.aplicacion.dao.IGenericoDAO;
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
	
}
