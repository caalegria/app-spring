package com.segurosbolivar.finita.aplicacion.dto;

public class UsuarioLogin {
	
	private String username="";
	private String rol="";
	
	public UsuarioLogin() {
		// TODO Auto-generated constructor stub
	}
	

	public UsuarioLogin(String username, String rol) {
		super();
		this.username = username;
		this.rol = rol;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}


	@Override
	public String toString() {
		return "UsuarioLogin [username=" + username + ", rol=" + rol + "]";
	}	

}
