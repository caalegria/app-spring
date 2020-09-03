package com.segurosbolivar.finita.aplicacion.util;

public final class Constantes {

	
	/*
	 * Claves de respuesta de PL y Funciones 
	 */
	public static String CLAVE_NAME="name";
	public static String CLAVE_LISTA="list";
	public static String CLAVE_RESPUSTA="respuesta";

	/*
	 * Constantes de capsula de errores
	 */
	public static String CODIGO_OPERACION_EXITOSA="0";
	public static String CODIGO_OPERACION_ERROR="-1";
	public static String CODIGO_OPERACION_ERRO_TIPO_CORRECTO="0";		
	public static String CODIGO_OPERACION_ERRO_TIPO_INTERNO="2";
	public static String CODIGO_OPERACION_ERRO_TIPO_BD="3";	

	/*
	 * Ruta de opciones para contador
	 */	
	public static String NOMBRE_FOLDER_CONTADOR="user-contador";
	public static String NOMBRE_FOLDER_CONTADOR_OPCIONES="opciones";
	public static String NOMBRE_URL_CONTADOR_1="plaCuentas";
	public static String NOMBRE_URL_CONTADOR_2="genOrdPagos";
	public static String NOMBRE_URL_CONTADOR_3="aplicacion";
	public static String URL_HOME_PLAN_CUENTA="redirect:/plaCuentas";


	/*
	 * Constantes de estado para la tabla FINCUENTAS
	 */
	public static char ESTADO_ACTIVO='A';
	public static char ESTADO_BLOQUEADO='B';
	
	
	/*
	 * Constantes de modos de CRUD
	 */
	public static String CRUD_CREAR="1";
	public static String CRUD_ACTUALIZAR="2";
	public static String CRUD_ELIMINAR="3";
	
	/*
	 * Constantes de Mensaje de Vista
	 */
	public static String NO_RENDERIZAR="0";
	public static String RENDERIZAR="1";
	
	
	/*
	 * Constantes de Tipo Mensaje
	 */
	public static String INFO="1";
	public static String CORRECTO="2";
	public static String WARN="3";
	
	
	/*
	 * Constantes estado direccionamiento
	 */
	public static String INICIANDO="0";
	public static String REDIRECT="1";
	
	/*
	 * Constante CRUD  
	 */
	public static String OPERACION_GUARDAR="guardar";
	public static String OPERACION_ACTUALIZAR="actualizar";
	public static String OPERACION_ELIMINAR="eliminar";

	/*
	 * Constante mensajes
	 * de operaciones 
	 */
	public static String KEY_COD="<cod>";
	public static String KEY_ERROR="<error>";
	public static String KEY_OPERACION="<operacion>";
	public static String MSJ_OPERACION_EXITOSA="La operacion <operacion> se ha completado con exito.";
	public static String MSJ_OPERACION_FALLIDA="La operación <operacion> no se ha completado con exito.ERROR: <cod> <error>";
}
