package com.segurosbolivar.finita.aplicacion.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.segurosbolivar.finita.aplicacion.entity.rol.FinsgusuariosRoles;
import com.segurosbolivar.finita.aplicacion.entity.rol.FinsgusuariosRolesPK;


public interface FinsgusuariosRolesRepository extends CrudRepository <FinsgusuariosRoles, FinsgusuariosRolesPK> {

	@Query(value = "SELECT * FROM FINSGUSUARIOS_ROLES WHERE UXR_USU_CODIGO = ?1", nativeQuery = true)
	public FinsgusuariosRoles findByUxrUsuario(String uxrUsuario);
 }