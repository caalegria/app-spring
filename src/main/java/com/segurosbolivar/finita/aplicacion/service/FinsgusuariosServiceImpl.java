package com.segurosbolivar.finita.aplicacion.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.segurosbolivar.finita.aplicacion.Exception.CustomeFieldValidationException;
import com.segurosbolivar.finita.aplicacion.Exception.UsernameOrIdNotFound;
import com.segurosbolivar.finita.aplicacion.dto.ChangePasswordForm;
import com.segurosbolivar.finita.aplicacion.entity.rol.Finsgusuarios;
import com.segurosbolivar.finita.aplicacion.repository.FinsgusuariosRepository;

@Service
public class FinsgusuariosServiceImpl implements FinsgusuariosService{

	@Autowired
	FinsgusuariosRepository repository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Iterable<Finsgusuarios> getAllUsers() {
		return repository.findAll();
	}
	
	private boolean checkUsernameAvailable(Finsgusuarios user) throws Exception {
		Optional<Finsgusuarios> userFound = repository.findByUsuCodigo(user.getUsuCodigo());
		if (userFound.isPresent()) {
			throw new CustomeFieldValidationException("Username no disponible","username");
		}
		return true;
	}

	private boolean checkPasswordValid(Finsgusuarios user) throws Exception {
		if (user.getUsuPasswd() == null || user.getUsuPasswd().isEmpty()) {
			throw new CustomeFieldValidationException("Confirm Password es obligatorio","confirmPassword");
		}
		
		if ( !user.getUsuPasswd().equals(user.getUsuPasswd())) {
			throw new CustomeFieldValidationException("Password y Confirm Password no son iguales","password");
		}
		return true;
	}


	@Override
	public Finsgusuarios createUser(Finsgusuarios user) throws Exception {
		if (checkUsernameAvailable(user) && checkPasswordValid(user)) {
			String encodedPassword = bCryptPasswordEncoder.encode(user.getUsuPasswd());
			user.setUsuPasswd(encodedPassword);
			user = repository.save(user);
		}
		return user;
	}

	@Override
	public Finsgusuarios getUserById(String usuCodigo) throws UsernameOrIdNotFound {
		return repository.findById(usuCodigo).orElseThrow(() -> new UsernameOrIdNotFound("El Id del usuario no existe."));
	}

	@Override
	public Finsgusuarios updateUser(Finsgusuarios fromUser) throws Exception {
		Finsgusuarios toUser = getUserById(fromUser.getUsuCodigo());
		mapUser(fromUser, toUser);
		return repository.save(toUser);
	}
	
	/**
	 * Map everythin but the password.
	 * @param from
	 * @param to
	 */
	protected void mapUser(Finsgusuarios from,Finsgusuarios to) {
		to.setUsuCodigo(from.getUsuCodigo());
		to.setUsuNombre(from.getUsuNombre());
		to.setUsuUltimoLogin(from.getUsuUltimoLogin());
		//to.setRoles(from.getRoles());
	}

	@Override
	@PreAuthorize("hasAnyRole('SUPERUSUARIO')")
	public void deleteUser(String usuCodigo) throws UsernameOrIdNotFound {
		Finsgusuarios user = getUserById(usuCodigo);
		repository.delete(user);
	}

	
	@Override
	public Finsgusuarios changePassword(ChangePasswordForm form) throws Exception {
		Finsgusuarios user = getUserById(form.getId());
		
		if ( !isLoggedUserADMIN() && !user.getUsuPasswd().equals(form.getCurrentPassword())) {
			throw new Exception ("Current Password invalido.");
		}
		
		if( user.getUsuPasswd().equals(form.getNewPassword())) {
			throw new Exception ("Nuevo debe ser diferente al password actual.");
		}
		
		if( !form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception ("Nuevo Password y Confirm Password no coinciden.");
		}
		
		String encodePassword = bCryptPasswordEncoder.encode(form.getNewPassword());
		user.setUsuPasswd(encodePassword);
		return repository.save(user);
	}
	
	private boolean isLoggedUserADMIN() {
		//Obtener el usuario logeado
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDetails loggedUser = null;
		Object roles = null;

		//Verificar que ese objeto traido de sesion es el usuario
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;

			roles = loggedUser.getAuthorities().stream()
					.filter(x -> "ROLE_ADMIN".equals(x.getAuthority())).findFirst()
					.orElse(null); 
		}
		return roles != null ? true : false;
	}
	
	private Finsgusuarios getLoggedUser() throws Exception {
		//Obtener el usuario logeado
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDetails loggedUser = null;

		//Verificar que ese objeto traido de sesion es el usuario
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
		}
		
		Finsgusuarios myUser = repository
				.findByUsuCodigo(loggedUser.getUsername()).orElseThrow(() -> new Exception("Error obteniendo el usuario logeado desde la sesion."));
		
		return myUser;
	}
}
