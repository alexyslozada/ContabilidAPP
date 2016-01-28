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
                    callback: self.poblarDocumento,
                    datos: data
                };
            data.append('tipoConsulta', 1);
            data.append('abreviatura', abr);
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
                    url: 'SRegContableEncabezadoValidar',
                    callback: self.abrirDetalle,
                    datos: data
                };
            data.append('idDocumento', _.getID('idDocumento').value());
            data.append('documento', _.getID('documento').value());
            _.ejecutar(obj);
        },
        abrirDetalle: function(datos){
            var data = JSON.parse(datos),
                divm = registroCtrl.divMensaje;
            switch(data.tipo){
                case _.MSG_CORRECTO:
                    divm.addClass('no-mostrar');
                    registroCtrl.registroContable = data.objeto;
                    window.location.hash = '#/contabilidad/registro/detalle';
                    break;
                default:
                    divm.delClass('no-mostrar').text(data.mensaje);
            }
        },
        inicio_detalle: function(){
            var self = this;
            this.formulario = _.getID('frmRegistroContableDetalle').noSubmit().get();
            this.divMensaje = _.getID('mensaje');
            this.poblarEncabezadoDetalle();
            _.getID('cuentapuc').get().addEventListener('blur', function(e){self.buscarCuenta(e.target.value);}, false);
            _.getID('tercero').get().addEventListener('blur', function(e){self.buscarTercero(e.target.value);}, false);
            _.getID('centrocosto').get().addEventListener('blur', function(e){self.buscarCCosto(e.target.value);}, false);
        },
        poblarEncabezadoDetalle: function(){
            _.getID('documento').setValue(this.registroContable.documento.documento);
            _.getID('fecha').setValue(this.registroContable.fechaMovimiento);
            _.getID('comentario').setValue(this.registroContable.comentario);
        },
        buscarCuenta: function(cuenta){
            var ctrlCuentas = _.getCtrl('cuentaspuc');
            if(ctrlCuentas.validaCuentaDetalle(cuenta)){
                registroCtrl.divMensaje.addClass('no-mostrar')
                ctrlCuentas.buscarXCuentaOId(1, cuenta, 0, registroCtrl.poblarCuenta);
            } else {
                registroCtrl.limpiaCamposCuenta();
                registroCtrl.divMensaje.delClass('no-mostrar').text('La longitud de la cuenta debe ser de 8 caracteres');
            }
        },
        poblarCuenta: function(datos){
            var data = JSON.parse(datos);
            if(data.tipo === _.MSG_CORRECTO){
                _.getID('id_cuenta_puc').setValue(data.objeto.id);
                _.getID('cuenta').setValue(data.objeto.nombre);
                _.getID('debito').get().focus();
            } else {
                registroCtrl.limpiaCamposCuenta();
            }
        },
        limpiaCamposCuenta: function(){
            _.getID('id_cuenta_puc').setValue('');
            _.getID('cuenta').setValue('');
        },
        buscarTercero: function(tercero){
            _.getCtrl('terceros').buscarPorNumeroOId(1, tercero, 0, registroCtrl.poblarTercero);
        },
        poblarTercero: function(datos){
            var data = JSON.parse(datos);
            if(data.tipo === _.MSG_CORRECTO){
                registroCtrl.divMensaje.addClass('no-mostrar');
                _.getID('id_tercero').setValue(data.objeto.id);
                _.getID('nombre_tercero').setValue(data.objeto.razon_social);
            } else {
                registroCtrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
                registroCtrl.limpiarCamposTercero();
            }
        },
        limpiarCamposTercero: function(){
            _.getID('id_tercero').setValue('');
            _.getID('nombre_tercero').setValue('');
        },
        buscarCCosto: function(ccosto){
            _.getCtrl('centroscosto').buscarXCodigoOId(1, ccosto, 0, registroCtrl.poblarCCosto);
        },
        poblarCCosto: function(datos){
            var data = JSON.parse(datos);
            if(data.tipo === _.MSG_CORRECTO){
                registroCtrl.divMensaje.addClass('no-mostrar');
                _.getID('id_centro_costo').setValue(data.objeto.id);
                _.getID('nombre_ccosto').setValue(data.objeto.nombre);
            } else {
                registroCtrl.limpiarCamposCCosto();
                registroCtrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
            }
        },
        limpiarCamposCCosto: function(){
            _.getID('id_centro_costo').setValue('');
            _.getID('nombre_ccosto').setValue('');
        }
    };
    
    _.controlador('registroContable', registroCtrl);
})(window, JSON, _);