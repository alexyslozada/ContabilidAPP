/* global libreria */

'use strict';
(function(window, document){
    var conectar = function(){
        var data = JSON.parse(this.responseText),
            div  = libreria.getID('conectar');
        if(data.tipo === 2){
            div.text(data.mensaje);
        } else if(data.tipo === 1 || data.tipo === 3){
            div.text(data.mensaje);
        }
    };
    
    libreria.ajax({
        metodo:'post',
        url:'SContexto',
        funcion: conectar
    });
})(window, document);