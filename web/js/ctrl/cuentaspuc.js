/* global _ */
(function(window, JSON, _){
    
    var cuentasPucCtrl = {
        pagina: 1,
        total_paginas: 0,
        limite: 30,
        columna_orden: 2,
        tipo_orden: 'acs',
        tipo_consulta: 1,
        formulario: null,
        divMensaje: null,
        inicio: function(){
            this.divMensaje = _.getID('mensaje');
        },
        inicio_crear: function(){
            this.formulario = _.getID('frmCrearCuentasPuc').noSubmit().get();
            this.inicio();
        },
        inicio_actualizar: function(){
            this.formulario = _.getID('frmActualizarCuentasPuc').noSubmit().get();
            this.inicio();
        },
        confirmaActualizar: function(id){
            if(confirm('Desea actualizar esta cuenta?')){
                preparaActualizar(id);
            }
        },
        confirmaEliminar: function(id){
            if(confirm('Desea eliminar esta cuenta?')){
                eliminar(id);
            }
        },
        actualizar: function(){
            var data = new FormData(this.formulario);
            if(this.validaCuenta(this.formulario.cuenta.value)){
                _.ajax({url:'SCuentasPucActualizar', datos: data})
                        .then(function(datos){actualizado(datos);}, function(error){console.log(error);});
            } else {
                this.divMensaje.delClass('no-mostrar').text('La longitud de la cuenta no es válida.');
            }
        },
        crear: function(){
            var data = new FormData(this.formulario);
            
            if(this.validaCuenta(this.formulario.cuenta.value)){
                _.ajax({url:'SCuentasPucCrear', datos: data})
                        .then(function(datos){creado(datos);}, function(error){console.log(error);});
            } else {
                this.divMensaje.delClass('no-mostrar').text('La longitud de la cuenta no es válida.');
            }
        },
        validaCuenta: function(cuenta){
            var enviar = false;
            switch(cuenta.length){
                case 1:
                case 2:
                case 4:
                case 6:
                case 8:
                    enviar = true;
            }
            return enviar;
        },
        listar: function(callback){
            var data = _.paginacion(this.pagina, this.limite, this.columna_orden, this.tipo_orden);
            data.append('tipoConsulta', 1);
            _.ajax({url: 'SCuentasPucListar', datos:data})
                    .then(function(datos){callback(datos);}, function(error){console.log(error);});
        },
        cargarTabla: function(datos){
            var data = JSON.parse(datos), campos = [], acciones = {};
            if(data.tipo === _.MSG_CORRECTO){
                campos = ['cuenta', 'nombre', 'maneja_centrocosto'],
                acciones = {eliminar: {clase: '.eliminar',
                                        funcion: function(e){
                                            e.preventDefault();
                                            cuentasPucCtrl.confirmaEliminar(e.target.dataset.idu);
                                        }
                            },
                            actualizar: {clase: '.actualizar',
                                        funcion: function(e){
                                            e.preventDefault();
                                            cuentasPucCtrl.confirmaActualizar(e.target.dataset.idu);
                                        }
                            }
                };
                
                cuentasPucCtrl.total_paginas = Math.ceil(data.objeto.registros / cuentasPucCtrl.limite);
                _.getID('pagina').get().setAttribute('max', cuentasPucCtrl.total_paginas);
                _.getID('total_paginas').text('de '+cuentasPucCtrl.total_paginas);
                _.llenarFilas('cuerpoTabla', 'plantilla', data.objeto.cuentas_puc, campos, acciones);
            } else if (data.tipo === _.MSG_ADVERTENCIA || data.tipo === _.MSG_ERROR){
                _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
            } else if (data.tipo === _.MSG_NO_AUTENTICADO){
                window.location.href = 'index.html';
            }
        },
        paginar: function(){
            _.paginar();
            this.listar(this.cargarTabla);
        },
        paginar_paginas: function(accion){
            _.paginar_paginas(accion);
            this.paginar();
        }
    };
    
    function actualizado(datos){
        var data = JSON.parse(datos),
            ctrl = _.getCtrl();
        _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
        if(data.tipo === _.MSG_CORRECTO){
            ctrl.formulario.reset();
            setTimeout(function(){
                window.location.hash = '#/cuentas-puc';
            }, 3000);
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    }
    
    function creado(datos){
        var data = JSON.parse(datos),
            ctrl = _.getCtrl();
        ctrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
        if(data.tipo === _.MSG_CORRECTO){
            ctrl.formulario.reset();
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    }
    
    function eliminar(id){
        var data = new FormData();
        data.append('id', id);
        _.ajax({url: 'SCuentasPucEliminar', datos: data})
                .then(function(datos){eliminado(datos);}, function(error){console.log(error);});
    }
    
    function eliminado(datos){
        var data = JSON.parse(datos);
        _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
        if(data.tipo === _.MSG_CORRECTO){
            _.getCtrl().listar(cuentasPucCtrl.cargarTabla);
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    }
    
    function preparaActualizar(id){
        var data = new FormData();
        data.append('tipoConsulta', 2);
        data.append('id', id);
        _.ajax({url: 'SCuentasPucConsultaXCuentaOId', datos: data})
                .then(function(datos){muestraActualizar(datos);}, function(error){console.log(error);});
    }
    
    function muestraActualizar(datos){
        var data   = JSON.parse(datos),
            objeto = data.objeto;
        if (data.tipo === _.MSG_CORRECTO){
            window.location.hash = '#/cuentas-puc-actualizar';
            setTimeout(function(){
                _.getID('id').setValue(objeto.id);
                _.getID('cuenta').setValue(objeto.cuenta);
                _.getID('nombre').setValue(objeto.nombre);
                _.getID('maneja_centrocosto').get().checked = objeto.maneja_centrocosto;
            }, 300);
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    }
    
    _.controlador('cuentaspuc', cuentasPucCtrl);
})(window, JSON, _);