package com.segurosbolivar.finita.aplicacion.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.segurosbolivar.finita.aplicacion.entity.rol.Finsgusuarios;
import com.segurosbolivar.finita.aplicacion.repository.FinsgusuariosRepository;

@Service
@Transactional
public class FinsgusuariosDetailsServiceImpl implements UserDetailsService {

	@Autowired
    FinsgusuariosRepository userRepository;

	//Autenticacion 0
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Finsgusuarios appUser = userRepository.findByUsuCodigo(username).orElseThrow(() -> new UsernameNotFoundException("Login Username Invalido."));
		
		Set<GrantedAuthority> grantList = new HashSet<GrantedAuthority>(); 
		
		/*Validar conla tabla FinsgusuariosRoles
		 * for (Role role: appUser.getRoles()) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getDescription());
            grantList.add(grantedAuthority);
		}*/
		
		UserDetails user = (UserDetails) new User(username,appUser.getUsuPasswd(),grantList);
		
		return user;
	}

}
