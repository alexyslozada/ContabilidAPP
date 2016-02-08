/* global _ */
'use strict';
(function(window, document, JSON, _){
    var registroCtrl = {
        divMensaje: null,
        formulario: null,
        registroContable: {},
        valoresTotales:{
            debito: 0,
            credito: 0
        },
        inicio_registro: function(){
            var self = this;
            this.formulario = _.getID('frmRegistroContable').noSubmit().get();
            this.divMensaje = _.getID('mensaje');
            _.getID('abreviatura').get()
                    .addEventListener('blur', function(e){
                        self.buscarDocumento(e.target.value, self.poblarDocumento);}, false);
        },
        buscarDocumento: function(abr, callback){
            var data = new FormData(),
                obj = {
                    url: 'SDocumentoContableGetXId',
                    callback: callback,
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
            var self = this,
                campos = ['cuentapuc', 'debito', 'credito', 'tercero', 'centrocosto'],
                i = 0, max = campos.length, btns_cancelar;
        
            this.formulario = _.getID('frmRegistroContableDetalle').noSubmit().get();
            this.divMensaje = _.getID('mensaje');
            this.poblarEncabezadoDetalle();
            this.valoresTotales.debito = 0;
            this.valoresTotales.credito = 0;
            btns_cancelar = document.getElementsByClassName('boton_cancelar');
            
            for(i = 0; i < btns_cancelar.length; i = i + 1){
                btns_cancelar[i].addEventListener('click', registroCtrl.confirmaCancelar, false);
            }
            for(i = 0; i < max; i = i + 1){
                _.getID(campos[i]).onEnterSiguiente();
            }
            
            _.getID('cuentapuc').get().addEventListener('blur', function(e){self.buscarCuenta(e.target.value);}, false);
            _.getID('tercero').get().addEventListener('blur', function(e){self.buscarTercero(e.target.value);}, false);
            _.getID('centrocosto').get().addEventListener('blur', function(e){self.buscarCCosto(e.target.value);}, false);
            _.getID('btnRegistrar').click(function(e){
                e.preventDefault();
                self.registrarDetalle();
            });
            _.getID('btnGuardarDocumento').click(function(e){
                e.preventDefault();
                self.guardarDocumento();
            });
        },
        confirmaCancelar: function(e){
            var obj = {
                url: 'SRegContableCancelar',
                callback: function(){window.location.hash = '#/contabilidad/registro';}
            };
            e.preventDefault();
            if(confirm('Está seguro que desea cancelar el registro?')){
                _.ejecutar(obj);
            } else {
                return false;
            }
        },
        poblarEncabezadoDetalle: function(){
            _.getID('documento').setValue(this.registroContable.documento.documento);
            _.getID('fecha').setValue(this.registroContable.fechaMovimiento);
            _.getID('comentario').setValue(this.registroContable.comentario);
        },
        buscarCuenta: function(cuenta){
            var ctrlCuentas = _.getCtrl('cuentaspuc');
            if(ctrlCuentas.validaCuentaDetalle(cuenta)){
                registroCtrl.divMensaje.addClass('no-mostrar');
                ctrlCuentas.buscarXCuentaOId(1, cuenta, 0, registroCtrl.poblarCuenta);
            } else {
                registroCtrl.limpiaCamposCuenta();
                registroCtrl.divMensaje.delClass('no-mostrar').text('La longitud de la cuenta debe ser de 8 caracteres');
            }
        },
        poblarCuenta: function(datos){
            var data = JSON.parse(datos);
            if(data.tipo === _.MSG_CORRECTO){
                registroCtrl.divMensaje.addClass('no-mostrar');
                _.getID('id_cuenta_puc').setValue(data.objeto.id);
                _.getID('cuenta').setValue(data.objeto.nombre);
                _.getID('debito').get().focus();
            } else {
                registroCtrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
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
        },
        registrarDetalle: function(){
            var self = this,
                data = new FormData(this.formulario),
                obj = {
                    url: 'SRegContableDetalleValidar',
                    datos: data,
                    callback: self.mostrarRegistro
                };
            _.ejecutar(obj);
        },
        mostrarRegistro: function(datos){
            var data = JSON.parse(datos);
            if(data.tipo === _.MSG_CORRECTO){
                registroCtrl.divMensaje.addClass('no-mostrar');
                registroCtrl.agregaTablaDetalle(data.objeto);
            } else {
                registroCtrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
            }
        },
        agregaTablaDetalle: function(data){
            var cuerpo = _.getID('registros_detalle').get(),
                fila   = _.getID('tmpl_registro').get(),
                clon = null, cuenta_codigo = null, cuenta_nombre = null,
                tercero_nit = null, tercero_nombre = null,
                ccosto_codigo = null, ccosto_nombre = null,
                valor_debito = null, valor_credito = null,
                boton_eliminar = null, diferencia = 0;
        
            clon = fila.content.cloneNode(true);
            cuenta_codigo = clon.querySelector('.cuenta_codigo');
            cuenta_nombre = clon.querySelector('.cuenta_nombre');
            tercero_nit = clon.querySelector('.tercero_nit');
            tercero_nombre = clon.querySelector('.tercero_nombre');
            ccosto_codigo = clon.querySelector('.ccosto_codigo');
            ccosto_nombre = clon.querySelector('.ccosto_nombre');
            valor_debito = clon.querySelector('.valor_debito');
            valor_credito = clon.querySelector('.valor_credito');
            boton_eliminar = clon.querySelector('.boton_eliminar');
            
            cuenta_codigo.textContent = data.cuentaPuc.cuenta;
            cuenta_nombre.textContent = data.cuentaPuc.nombre;
            tercero_nit.textContent = data.tercero.numero_identificacion;
            tercero_nombre.textContent = data.tercero.razon_social;
            ccosto_codigo.textContent = data.centroCosto.codigo;
            ccosto_nombre.textContent = data.centroCosto.nombre;
            valor_debito.textContent = data.debito.formatNumero();
            valor_credito.textContent = data.credito.formatNumero();
            boton_eliminar.dataset.id = data.id;
            boton_eliminar.addEventListener('click', function(e){
                e.preventDefault();
                registroCtrl.eliminarRegistroDetalle(e.target.dataset.id);
            }, false);
            
            cuerpo.appendChild(clon);
            
            registroCtrl.valoresTotales.debito += data.debito;
            registroCtrl.valoresTotales.credito += data.credito;
            diferencia = registroCtrl.valoresTotales.debito - registroCtrl.valoresTotales.credito;
            _.getID('total_debitos').text(registroCtrl.valoresTotales.debito.formatNumero());
            _.getID('total_creditos').text(registroCtrl.valoresTotales.credito.formatNumero());
            _.getID('total_diferencia').text(diferencia.formatNumero());
            
            registroCtrl.limpiaCamposCuenta();
            _.getID('debito').setValue('0');
            _.getID('credito').setValue('0');
            _.getID('cuentapuc').setValue('').get().focus();
        },
        eliminarRegistroDetalle: function(id){
            var data = new FormData(),
                obj = {
                    url: 'SRegContableDetalleEliminarDeSesion',
                    datos: data,
                    callback: registroCtrl.retiraDeTablaDetalle
                };
            data.append('id', id);
            _.ejecutar(obj);
        },
        retiraDeTablaDetalle: function(datos){
            var data = JSON.parse(datos),
                detalle = _.getID('registros_detalle').get(),
                fila, valDebito = 0, valCredito = 0, diferencia = 0;
            if (data.tipo === _.MSG_CORRECTO){
                registroCtrl.divMensaje.addClass('no-mostrar');
                fila = detalle.querySelector('[data-id="'+data.objeto.id+'"]').parentNode.parentNode;
                
                valDebito = parseInt(fila.querySelector('.valor_debito').textContent.replace(',', ''), 10);
                valCredito = parseInt(fila.querySelector('.valor_credito').textContent.replace(',', ''), 10);
                registroCtrl.valoresTotales.debito -= valDebito;
                registroCtrl.valoresTotales.credito -= valCredito;
                
                detalle.removeChild(fila);

                diferencia = registroCtrl.valoresTotales.debito - registroCtrl.valoresTotales.credito;
                _.getID('total_debitos').text(registroCtrl.valoresTotales.debito.formatNumero());
                _.getID('total_creditos').text(registroCtrl.valoresTotales.credito.formatNumero());
                _.getID('total_diferencia').text(diferencia.formatNumero());
            } else {
                registroCtrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
            }
        },
        guardarDocumento: function(){
            var obj = {
                url: 'SRegContableGuardarDocumento',
                callback: registroCtrl.documentoGuardado
            };
            _.ejecutar(obj);
        },
        documentoGuardado: function(datos){
            var data = JSON.parse(datos);
            if (data.tipo === _.MSG_CORRECTO){
                alert('Documento guardado con el Consecutivo: '+data.objeto.documento.consecutivo);
                window.location.hash ="#/contabilidad/registro";
            } else {
                alert(data.mensaje);
                registroCtrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
            }
        },
        inicio_consultar: function(){
            var self = this;
            this.formulario = _.getID('frmConsutarDocumento').noSubmit().get();
            this.divMensaje = _.getID('mensaje');
            _.getID('abreviatura').onEnterSiguiente().get()
                    .addEventListener('blur', function(e){
                        self.buscarDocumento(e.target.value, self.poblarDocumentoBusqueda);}, false);
            _.getID('btn_buscar').click(function(e){
                e.preventDefault();
                self.buscarDocumentoDetallado();
            });
        },
        poblarDocumentoBusqueda: function(datos){
            var data = JSON.parse(datos),
                objeto = {};
            switch(data.tipo){
                case _.MSG_CORRECTO:
                    registroCtrl.divMensaje.addClass('no-mostrar');
                    objeto = data.objeto;
                    _.getID('idDocumento').setValue(objeto.id);
                    _.getID('nombre_documento').setValue(objeto.documento);
                    _.getID('numero_documento').get().focus();
                    break;
                default:
                    _.getID('idDocumento').setValue('');
                    _.getID('nombre_documento').setValue('');
                    registroCtrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
            }
        },
        buscarDocumentoDetallado: function(){
            var self = this,
                data = new FormData(self.formulario),
                obj = {
                    url: 'SRegContableConsultar',
                    datos: data,
                    callback: self.llenaDocumentoDetallado
                };
            _.ejecutar(obj);
        },
        llenaDocumentoDetallado: function(datos){
            var data = JSON.parse(datos),
                objeto = data.objeto,
                msg = _.getID('mensaje');
            switch (data.tipo) {
                case _.MSG_CORRECTO:
                    msg.addClass('no-mostrar');
                    registroCtrl.poblarDocumentoDetallado(objeto);
                    break;
                default:
                    msg.delClass('no-mostrar').text(data.mensaje);
                    registroCtrl.limpiarDocumentoDetallado();
            }
        },
        poblarDocumentoDetallado: function(data){
            var cuerpo = _.getID('registros_detalle').get(),
                fila   = _.getID('tmpl_registro').get(),
                clon = null, cuenta_codigo = null, cuenta_nombre = null,
                tercero_nit = null, tercero_nombre = null,
                ccosto_codigo = null, ccosto_nombre = null,
                valor_debito = null, valor_credito = null,
                valDebito = 0, valCredito = 0, diferencia = 0, i = 0,
                detalles = data.detalles, max = 0,
                fragmento = document.createDocumentFragment();
        
            _.getID('idPrincipal').setValue(data.id_reg_con_enc);
            _.getID('documento').setValue(data.documento);
            _.getID('consecutivo').setValue(data.consecutivo);
            _.getID('fecha').setValue(data.fecha_movimiento);
            _.getID('comentario').setValue(data.comentario);
            _.getID('usuario_digita').setValue(data.usuario_digita);
            _.getID('fecha_digita').setValue(data.fecha_creacion);
            if(data.usuario_actualiza){
                _.getID('usuario_actualiza').setValue(data.usuario_actualiza);
            }
            if(data.fecha_update){
                _.getID('fecha_actualiza').setValue(data.fecha_update);
            }
            _.getID('documento_anulado').get().checked = data.anulado;

            // Poblar el detalle
            cuerpo.innerHTML = '';
            if (detalles){
                max = detalles.length;
            }
            for(; i < max; i = i + 1){
                clon = fila.content.cloneNode(true);
                cuenta_codigo = clon.querySelector('.cuenta_codigo');
                cuenta_nombre = clon.querySelector('.cuenta_nombre');
                tercero_nit = clon.querySelector('.tercero_nit');
                tercero_nombre = clon.querySelector('.tercero_nombre');
                ccosto_codigo = clon.querySelector('.ccosto_codigo');
                ccosto_nombre = clon.querySelector('.ccosto_nombre');
                valor_debito = clon.querySelector('.valor_debito');
                valor_credito = clon.querySelector('.valor_credito');

                cuenta_codigo.textContent = detalles[i].cuenta;
                cuenta_nombre.textContent = detalles[i].nombre;
                tercero_nit.textContent = detalles[i].numero_identificacion;
                tercero_nombre.textContent = detalles[i].razon_social;
                ccosto_codigo.textContent = detalles[i].codigo;
                ccosto_nombre.textContent = detalles[i].centrocosto;
                valor_debito.textContent = detalles[i].debito.formatNumero();
                valor_credito.textContent = detalles[i].credito.formatNumero();
                fragmento.appendChild(clon);

                valDebito += detalles[i].debito;
                valCredito += detalles[i].credito;
            }
            
            cuerpo.appendChild(fragmento);
            
            diferencia = valDebito - valCredito;
            _.getID('total_debitos').text(valDebito.formatNumero());
            _.getID('total_creditos').text(valCredito.formatNumero());
            _.getID('total_diferencia').text(diferencia.formatNumero());
        },
        limpiarDocumentoDetallado: function(){
            _.getID('idPrincipal').setValue('');
            _.getID('documento').setValue('');
            _.getID('consecutivo').setValue('');
            _.getID('fecha').setValue('');
            _.getID('comentario').setValue('');
            _.getID('usuario_digita').setValue('');
            _.getID('fecha_digita').setValue('');
            _.getID('usuario_actualiza').setValue('');
            _.getID('fecha_actualiza').setValue('');
            _.getID('documento_anulado').get().checked = false;
            _.getID('registros_detalle').innerHTML('');
        }
    };
    
    _.controlador('registroContable', registroCtrl);
})(window, document, JSON, _);