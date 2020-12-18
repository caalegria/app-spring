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
public class AgrupadorLogFinita extends Agrupador {
	
	
	private List<LogCargueFinita> logCargueFinita = new ArrayList<LogCargueFinita>();
	

	public AgrupadorLogFinita(String fechaCargue) {
		super(fechaCargue);
		// TODO Auto-generated constructor stub
	}

	public List<LogCargueFinita> getLogCargueFinita() {
		return logCargueFinita;
	}

	public void setLogCargueFinita(List<LogCargueFinita> logCargueFinita) {
		this.logCargueFinita = logCargueFinita;
	}

	public void renombarArchivo() {
		try {			
			this.setNombreArchivo(Constantes.DECEVAL_LOG_FINITA.concat(this.getFechaCargue().replace("/", "_")));
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
		if (!(obj instanceof AgrupadorLogFinita))
			return false;
		AgrupadorLogFinita other = (AgrupadorLogFinita) obj;
		if (getFechaCargue() == null) {
			if (other.getFechaCargue() != null)
				return false;
		} else if (!getFechaCargue().equals(other.getFechaCargue()))
			return false;
		return true;
	}	
}
