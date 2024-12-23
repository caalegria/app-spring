package com.segurosbolivar.finita.aplicacion.util;

import org.apache.log4j.Logger;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
public class Log {
	public static final Logger logger = Logger.getLogger(Log.class);


	public static String logStartBeans(String nameClass) {
		final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
		Thread.currentThread().getStackTrace()[1].getLineNumber();		
		return "[Iniciando el controlador metodo: "+ e.getMethodName()+" ]............." ;	
	}

	public static String getCurrentClassAndMethodNames(String nameClass,String mensaje) {
		final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
		Thread.currentThread().getStackTrace()[1].getLineNumber();		
		return "[Llamado al metodo "+e.getMethodName()+" ] --> "+mensaje ;
	}

	public static void getError(Logger logger,Exception e) {
		final StackTraceElement exce = Thread.currentThread().getStackTrace()[2];		
		Thread.currentThread().getStackTrace()[1].getLineNumber();		
		logger.error("[Error FINITA "+ exce.getMethodName()+" ] --> "+e.getMessage());		
		logger.error("[Iniciando trace de Error ..............");
		e.printStackTrace();		
		logger.warn("[Finalizo trace de Error ..............");
	}

	public static void getWarn(Logger logger, Exception e) {
		final StackTraceElement exce = Thread.currentThread().getStackTrace()[2];		
		Thread.currentThread().getStackTrace()[1].getLineNumber();		
		logger.warn("[Warning FINITA "+ exce.getMethodName()+" ] --> "+e.getMessage());
		logger.warn("[Iniciando trace de Warning ..............");
		e.printStackTrace();		
		logger.warn("[Finalizo trace de Warning ..............");		
	}	
}