/* global _ */
'use strict';
(function(window, JSON, _){
    var registroCtrl = {
        divMensaje: null,
        formulario: null,
        registroContable: {},
        inicio_registro: function(){
            var self = this;
            this.formulario = _.getID('frmRegistroContable').noSubmit().get();
            this.divMensaje = _.getID('mensaje');
            _.getID('abreviatura').get().addEventListener('blur', function(e){self.buscarDocumento(e.target.value);}, false);
        },
        buscarDocumento: function(abr){
            var self = this,
                data = new FormData(),
                obj = {
                    url: 'SDocumentoContableGetXId',
                    callback: self.poblarDocumento
                };
            data.append('tipoConsulta', 1);
            data.append('abreviatura', abr);
            obj.datos = data;
            _.ejecutar(obj);
        },
        poblarDocumento: function(datos){
            var data = JSON.parse(datos),
                objeto = {};
            switch(data.tipo){
                case _.MSG_CORRECTO:
                    registroCtrl.divMensaje.addClass('no-mostrar');
                    objeto = data.objeto;
                    _.getID('idDocumento').setValue(objeto.id);
                    _.getID('documento').setValue(objeto.documento);
                    _.getID('fecha').get().focus();
                    break;
                default:
                    _.getID('idDocumento').setValue('');
                    _.getID('documento').setValue('');
                    registroCtrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
            }
        },
        validarEncabezado: function(){
            var self = this,
                data = new FormData(self.formulario),
                obj = {
                    url: 'SRegContableEncabezadoCrear',
                    callback: self.abrirDetalle,
                    datos: data
                };
            data.append('idDocumento', _.getID('idDocumento').value());
            _.ejecutar(obj);
        },
        abrirDetalle: function(datos){
            var data = JSON.parse(datos),
                divm = registroCtrl.divMensaje,
                obje = registroCtrl.registroContable;
            switch(data.tipo){
                case _.MSG_CORRECTO:
                    divm.addClass('no-mostrar');
                    obje = data.objeto;
                    console.log(data.mensaje);
                    break;
                default:
                    divm.delClass('no-mostrar').text(data.mensaje);
            }
        }
    };
    
    _.controlador('registroContable', registroCtrl);
})(window, JSON, _);