package com.segurosbolivar.finita.aplicacion.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.segurosbolivar.finita.aplicacion.entity.rol.Finsgusuarios;


@Repository
public interface FinsgusuariosRepository extends CrudRepository<Finsgusuarios, String> {

	public Optional<Finsgusuarios> findByUsuCodigo(String usuCodigo);
 }
