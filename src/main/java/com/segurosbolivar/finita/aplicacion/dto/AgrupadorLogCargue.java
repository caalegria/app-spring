package com.segurosbolivar.finita.aplicacion.dto;

import java.util.ArrayList;
import java.util.List;

import com.segurosbolivar.finita.aplicacion.entity.LogCargues;
import com.segurosbolivar.finita.aplicacion.util.Constantes;


/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
public class AgrupadorLogCargue extends Agrupador{
	
	
	private List<LogCargues> logCargues = new ArrayList<LogCargues>();		

	public AgrupadorLogCargue(String fechaCargue) {
		super(fechaCargue);
		// TODO Auto-generated constructor stub
	}

	public List<LogCargues> getLogCargues() {
		return logCargues;
	}

	public void setLogCargues(List<LogCargues> logCargues) {
		this.logCargues = logCargues;
	}

	public void renombarArchivo() {
		try {			
			this.setNombreArchivo(Constantes.DECEVAL_LOG_CARGUE.concat(this.getFechaCargue().replace("/", "_")));
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof AgrupadorLogCargue))
			return false;
		AgrupadorLogCargue other = (AgrupadorLogCargue) obj;
		if (this.getFechaCargue() == null) {
			if (other.getFechaCargue() != null)
				return false;
		} else if (!getFechaCargue().equals(other.getFechaCargue()))
			return false;
		return true;
	}	
}
