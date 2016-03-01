/* global _ */
(function(window, JSON, _){
    'use strict';
    
    var cierreContableCtrl = {
        formulario: null,
        inicio_cierre: function(){
            var self = this;
            self.formulario = _.getID('frmCierreContable').noSubmit().get();
            _.getID('btnCierreContable').click(self.cierre);
        },
        cierre: function(){
            var data = new FormData(cierreContableCtrl.formulario);
            _.ejecutar({
                url: 'SCierreContable',
                datos: data,
                callback: cierreContableCtrl.respuestaCierre
            });
        },
        respuestaCierre: function(datos){
            var data = JSON.parse(datos);
            _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
            if (data.tipo === _.MSG_NO_AUTENTICADO) {
                window.location.hash = '#/';
            }
        }
        
    };
    
    _.controlador('cierreContable', cierreContableCtrl);
})(window, JSON, _);