/* global libreria */
/* global _ */

'use strict';
(function(window){
    
	function validar(){
            var data = JSON.parse(this.responseText);
            if(data.tipo === 2){
                    _.getID('mensaje-inicial').text(data.mensaje);
            } else {
                    window.location.href = 'index.html';
            }
	};
	
	libreria.ajax({
            url: 'SAutenticado',
            funcion: validar
	});
})(window);