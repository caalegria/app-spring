/**
 * 
 */

function buscar() {
	var busqueda =document.getElementById("filtroBusqueda").value;
	console.log("Texto de busqueda: "+busqueda);	
	if(busqueda.trim()!=''){
		$(this).load('/finita/configurarBeneficiarios/buscar?busqueda='+busqueda,$("#filtroBusqueda").serialize());		
	}
	
}