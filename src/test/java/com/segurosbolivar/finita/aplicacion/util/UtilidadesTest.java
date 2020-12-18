package com.segurosbolivar.finita.aplicacion.util;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class UtilidadesTest {

	@Test
	public void escribirArchivoCSV() {
		List<String> data= Arrays.asList( "1;2;4;5;6", "1;2;4;5;6", "1;2;4;5;6");
		assertTrue(Utilidades.escribirArchivoPlano(data, "test", Constantes.CSV, null));
	}

}
