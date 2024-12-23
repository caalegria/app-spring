package com.segurosbolivar.finita.aplicacion.util;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
public final class Constantes {

	
	/*
	 * Datos de constante BD
	 */
	public static final int BASIC_SIZE = 400;
	public static final int DBLIST_MAX_SIZE = 500;	
	
	/*
	 * Nombre estandar de archivo generado para
	 * deceval 
	 */
	public static final String DECEVAL="deceval";
	public static final String DECEVAL_ORIGIN="deceval_origen_";
	public static final String DECEVAL_LOG_FINITA="log_finita_";
	public static final String DECEVAL_LOG_CARGUE="log_cargue_";
	
	/*
	 * Extensiones de archivos
	 */
	public static final String TXT=".txt";
	public static final String PDF=".pdf";
	public static final String CSV=".csv";
	
	/*
	 * Claves ENV properties Spring Boot
	 * REST terceros
	 */
	public static String CONSULTA_NATURAL = "rest.terceros.consulta.natural";
	public static String CREAR_NATURAL = "rest.terceros.crear.natural";
	public static String ACTUALIZAR_NATURAL = "rest.terceros.actualizar.natural";

	public static String CONSULTA_JURIDICO = "rest.terceros.consulta.juridico";	
	public static String CREAR_JURIDICO = "rest.terceros.crear.juridico";
	public static String ACTUALIZAR_JURIDICO = "rest.terceros.actualizar.juridico"; 
	
	/*
	 * Claves de respuesta de PL y Funciones 
	 */
	public static String CLAVE_NAME="name";
	public static String CLAVE_LISTA="list";
	public static String CLAVE_RESPUESTA="respuesta_";

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
	public static String NOMBRE_URL_CONTADOR_2_1="configurarBeneficiarios";
	public static String NOMBRE_URL_CONTADOR_2_1_1="configurarBeneficiariosAccionista";
	public static String NOMBRE_URL_CONTADOR_2_1_2="agregarBeneficiariosAccionista";	
	public static String NOMBRE_URL_CONTADOR_2_1_3="editarAccionista";
	public static String NOMBRE_URL_CONTADOR_2_1_4="historicoPagosSaldos";
	public static String NOMBRE_URL_CONTADOR_2_2="generarOrdenesDePagos";	
	public static String NOMBRE_URL_CONTADOR_3="aplicacion";
	public static String NOMBRE_URL_CONTADOR_4="retenciones";
	public static String NOMBRE_URL_CONTADOR_5="estadoOrdenesPago";
	public static String NOMBRE_URL_CONTADOR_6="parametrizacion";	
	
	public static String URL_REDIRECT="redirect:";
	public static String URL_HOME_PLAN_CUENTA="redirect:/plaCuentas";
	public static String URL_HOME_CONFIGURAR_BENEFICARIOS="redirect:/configurarBeneficiarios";
	public static String URL_HOME_CONFIGURAR_BENEFICARIOS_ACCIONISTA="redirect:/configurarBeneficiariosAccionista";
	public static String URL_HOME_AGREGAR_BENEFICARIOS_ACCIONISTA="redirect:/agregarBeneficiariosAccionista";
	public static String URL_HOME_GENERAR_ORDEN_PAGOS="redirect:/generarOrdenesDePagos";
	public static String URL_HOME_APLICACION="redirect:/aplicacion";


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
	public static String CREANDO="2";
	public static String ACTUALIZANDO="3";
	public static String BUSCANDO="4";
	
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
	
	/*
	 * Datos y rectricciones de consultas
	 * sobre BD
	 */
	
	public static String CODIGO_EMPRESA_ACCIONISTA="50";
	public static String CODIGO_ACCIONISTA_DECEVAL="800182091";
	
	/*
	 * Constantes Dividendos
	 * 
	 */
	
	public static String OPC_CARGUE= "Cargue Archivo Plano";
	public static String OPC_VALIDACION= "Validación Cargue Plano";
	public static String SIN_RESUTADO_DIVIDENDO = "Busquedad sin resultados";
	public static String INGRESAR_FECHA = "Ingrese un rango de fechas";
	public static String URL_HOME_SUPERUSER = "user-superusuario/home";
	
	public static String URL_HOME_CONTADOR = "user-contador/home";
	public static String FILE_DIVIDENDOS = "cargueDividendos_";
	
	
	/*
	 * Listado de PRC 
	 */
	public static final String PKG_FIN_ORDEN_PAGO_ENVIAR_A_PAGO="PKG_FIN_ORDEN_PAGO.ENVIAR_A_PAGO";
	public static final String PKG_FIN_ARCHIVO_CONTABLE_PBD_PROCESO_CIERRE="PKG_FIN_ARCHIVO_CONTABLE.PBD_PROCESO_CIERRE";
	public static final String PKG_FIN_ARCHIVO_CONTABLE_PBD_PROCESO_REVERSION="PKG_FIN_ARCHIVO_CONTABLE.PBD_PROCESO_REVERSION";
	public static final String PKG_FIN_ARCHIVO_CONTABLE_PBD_GENERA_ARCHIVO="PKG_FIN_ARCHIVO_CONTABLE.PBD_GENERA_ARCHIVO";
}
