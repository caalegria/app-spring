package com.segurosbolivar.finita.aplicacion.service;

import com.segurosbolivar.finita.aplicacion.Exception.UsernameOrIdNotFound;
import com.segurosbolivar.finita.aplicacion.dto.ChangePasswordForm;
import com.segurosbolivar.finita.aplicacion.entity.rol.Finsgusuarios;

public interface FinsgusuariosService {

	public Iterable<Finsgusuarios> getAllUsers();

	public Finsgusuarios createUser(Finsgusuarios user) throws Exception;

	public Finsgusuarios getUserById(String usoCodigo) throws Exception;
	
	public Finsgusuarios updateUser(Finsgusuarios user) throws Exception;
	
	public void deleteUser(String usuCodigo) throws UsernameOrIdNotFound;
	
	public Finsgusuarios changePassword(ChangePasswordForm form) throws Exception;
}
