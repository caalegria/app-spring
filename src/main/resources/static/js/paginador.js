/**
 * Paginador Generico por JS
 * la tabla debe tener el id dataTable
 */
var resultados_por_pagina = 30;
var pagina_actual = 1;
var pagina_ultima = 0;
$(window).ready(function(){
	//contar todos los registros que tenemos
	var total_registros = $("#dataTable>tbody>tr").length;
	//saber cuantas paginas vamos a mostrar
	pagina_ultima = Math.round(total_registros / resultados_por_pagina);	
	console.log('Total Data: '+total_registros+',pagina_ultima: '+pagina_ultima);	
	//cargar la primera pagina
	cargarPagina(pagina_actual);
	//eventos para los botones
	$("#cargar_primera_pagina").click(function(){
		cargarPagina(1);
	});
	$("#cargar_anterior_pagina").click(function(){
		cargarPagina(pagina_actual - 1);
	});
	$("#cargar_siguiente_pagina").click(function(){
		cargarPagina(pagina_actual + 1);
	});
	$("#cargar_ultima_pagina").click(function(){
		cargarPagina(pagina_ultima);
	});
});
var cargarPagina = function(intPaginaP){
	//evaluar si la pagina a cargar es mayor que el numero de paginas o es menor que 1
	if(intPaginaP < 1){intPaginaP = 1;}
	if(intPaginaP > pagina_ultima){intPaginaP = pagina_ultima;}
	//ocultar todas las lineas
	$("#dataTable>tbody>tr").addClass("linea-oculta");
	var primer_registro = (intPaginaP - 1) * resultados_por_pagina;
	for (var i = primer_registro; i < (primer_registro + resultados_por_pagina); i++){
		$("#dataTable>tbody>tr").eq(i).removeClass("linea-oculta");
	}
	//indicar en qué pagina estamos
	pagina_actual = intPaginaP;
	$("#indicador_paginas").html("Página: " + pagina_actual + " / " + pagina_ultima);
}