package com.segurosbolivar.finita.aplicacion.dao;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.segurosbolivar.finita.aplicacion.entity.Persistente;


@Transactional
@Repository
public class GenericoDAO implements IGenericoDAO {

	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public Persistente getObjetctById(Class<? extends Persistente> classz, int idObject) {
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
	
	
}
