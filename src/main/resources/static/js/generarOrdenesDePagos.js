/**
 * 
 */

$(document).ready(function() {
	$('input[type="checkbox"]').click(function() {
		if ($(this).prop("checked") == true) {								
			$(this).val('true');					
			console.log("Checkbox is checked.Id:"+$(this).prop("id")+" valor: "+ $(this).val()+" "+$(this).prop("checked"));
			$(this).load('/finita/generarOrdenesDePagos/eventoSeleccionar?posicion='+$(this).prop("id")+"&estado="+$(this).prop("value"), $('#form:input[type="checkbox"]').serialize());
		} else if ($(this).prop("checked") == false) {
			$(this).val('false');					
			console.log("Checkbox is unchecked.Id:"+$(this).prop("id")+" valor: "+ $(this).val());
			$(this).load('/finita/generarOrdenesDePagos/eventoSeleccionar?posicion='+$(this).prop("id")+"&estado="+$(this).prop("value"), $('#form:input[type="checkbox"]').serialize());
		}			    
	});
	
	
    let x = document.querySelectorAll(".moneyValue");     
    for (let i = 0, len = x.length; i < len; i++) {     	
        let num = Number(x[i].innerHTML).toLocaleString('en'); 
        x[i].innerHTML = num; 
        x[i].classList.add("currSign"); 
    } 
});		