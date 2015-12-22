/* global _ */

'use strict';
(function(window){
    function validar(datos){
        var data = JSON.parse(datos);
        if(data.tipo === 2){
            _.getID('mensaje-inicial').text(data.mensaje);
        } else {
            window.location.href = 'index.html';
        }
    };

    _.ajax({url: 'SAutenticado'}).then(function(datos){validar(datos);}, function(error){console.log(error);});

})(window, _);