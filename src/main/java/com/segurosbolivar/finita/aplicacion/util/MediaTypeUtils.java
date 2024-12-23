package com.segurosbolivar.finita.aplicacion.util;

import javax.servlet.ServletContext;

import org.springframework.http.MediaType;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
public class MediaTypeUtils {
	public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
		String mineType = servletContext.getMimeType(fileName);
		try {
			MediaType mediaType = MediaType.parseMediaType(mineType);
			return mediaType;
		} catch (Exception e) {
			return MediaType.APPLICATION_OCTET_STREAM;
		}
	}
}
