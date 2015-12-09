/* global libreria*/
'use strict';
(function(){

    libreria.getID('frmLogin').noSubmit();
    
    var conexion = function(){
        var data = JSON.parse(this.responseText);
        if(data.tipo === 2){
            console.log("Correcto", data.mensaje);
            console.log(data.objeto);
        } else {
            console.log("Error", data.mensaje);
        }
    };
    
    libreria.getID('btnLogin').click(function(){
        libreria.ajax({
            url: 'SAutenticar',
            datos: new FormData(libreria.getID('frmLogin').get()),
            funcion: conexion
        });
    });
    
})();