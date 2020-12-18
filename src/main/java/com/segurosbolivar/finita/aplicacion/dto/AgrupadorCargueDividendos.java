package com.segurosbolivar.finita.aplicacion.dto;

import java.util.ArrayList;
import java.util.List;

import com.segurosbolivar.finita.aplicacion.entity.LogCargueFinita;
import com.segurosbolivar.finita.aplicacion.util.Constantes;


/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
public class AgrupadorCargueDividendos extends Agrupador{
		
	private List<LogCargueFinita> logDeCarga = new ArrayList<LogCargueFinita>();	
	

	public AgrupadorCargueDividendos(String fechaCargue) {
		super(fechaCargue);	
	}

	public List<LogCargueFinita> getLogDeCarga() {
		return logDeCarga;
	}

	public void setLogDeCarga(List<LogCargueFinita> logDeCarga) {
		this.logDeCarga = logDeCarga;
	}

	public void renombarArchivo() {
		try {			
			this.setNombreArchivo(Constantes.DECEVAL_ORIGIN.concat(this.getFechaCargue().replace("/", "_")));
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getFechaCargue() == null) ? 0 : getFechaCargue().hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof AgrupadorCargueDividendos))
			return false;
		AgrupadorCargueDividendos other = (AgrupadorCargueDividendos) obj;
		if (getFechaCargue() == null) {
			if (other.getFechaCargue() != null)
				return false;
		} else if (!getFechaCargue().equals(other.getFechaCargue()))
			return false;
		return true;
	}	
}
