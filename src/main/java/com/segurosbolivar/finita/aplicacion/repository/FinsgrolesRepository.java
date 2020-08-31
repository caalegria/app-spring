package com.segurosbolivar.finita.aplicacion.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.segurosbolivar.finita.aplicacion.entity.rol.Finsgroles;

@Repository
public interface FinsgrolesRepository extends CrudRepository<Finsgroles, String>{
	
	public Finsgroles findByRolCodigo(String rolCodigo);
	
	
}
