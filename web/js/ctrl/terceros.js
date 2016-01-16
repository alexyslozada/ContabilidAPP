/* global _ */
(function (window, document, JSON, _, Math) {
    var tercerosCtrl = {
        pagina: 1,
        total_paginas: 0,
        limite: 10,
        columna_orden: 2,
        tipo_orden: 'acs',
        tipo_consulta: 1,
        formulario: null,
        divMensaje: null,
        inicio: function () {
            this.divMensaje = _.getID('mensaje');
        },
        carga_inicial: function(){
            var tipoIdentificacionCtrl = _.getCtrl('tipoIdentificacion'),
                tipoPersona = _.getCtrl('tipoPersona'),
                departamentos = _.getCtrl('departamentos'),
                self = this;
        
            tipoIdentificacionCtrl.limite = 0; // Para consultar todos los registros
            tipoIdentificacionCtrl.listar(function(datos){
                _.poblarSelect(datos, 'tipo_identificacion', 'id', 'documento', 'tipo_identificacion', true);
            });
            tipoPersona.listar(function(datos){
                _.poblarSelect(datos, 'tipo_persona', 'id_tipo_persona', 'nombre', 'tipo_persona', false);
            });
            departamentos.listar(function(datos){
                _.poblarSelect(datos, 'departamentos', 'codigo', 'nombre', 'depto_residencia', false);
            });
            
            // Se asigna la escucha cuando se cambia de departamento para que cargue las ciudades
            _.getID('depto_residencia').get().addEventListener('blur', function(e){
                var departamento = e.target.value;
                _.getID('ciudad_residencia').get().innerHTML = '';
                _.getCtrl('ciudades')
                    .listar(function(datos){
                        _.poblarSelect(datos, 'objeto', 'idciudad', 'nombre', 'ciudad_residencia', false);
                    }, 1, departamento);
                }, false);
            
            // Cuando se escribe los nombres se pobla la razón social
            _.getID('primer_apellido').get().addEventListener('keyup', self.poblarRazon, false);
            _.getID('segundo_apellido').get().addEventListener('keyup', self.poblarRazon, false);
            _.getID('primer_nombre').get().addEventListener('keyup', self.poblarRazon, false);
            _.getID('segundo_nombre').get().addEventListener('keyup', self.poblarRazon, false);
            
            // Para volver a dejar los límites normales
            // y para habilitar los campos necesarios
            setTimeout(function(){
                self.habilitaCampos(_.getID('tipo_persona').value());
                _.getID('tipo_persona').get().addEventListener('blur', function(){self.habilitaCampos(this.value);}, false);
                    tipoIdentificacionCtrl.limite = 10;
            }, 500);
        },
        inicio_crear: function () {
            this.formulario = _.getID('frmCrearTerceros').noSubmit().get();
            this.inicio();
            this.carga_inicial();
        },
        inicio_actualizar: function () {
            this.formulario = _.getID('frmActualizarTerceros').noSubmit().get();
            this.inicio();
            this.carga_inicial();
        },
        confirmaActualizar: function (id) {
            if (confirm('Desea actualizar este tercero?')) {
                this.preparaActualizar(id);
            }
        },
        confirmaEliminar: function (id) {
            if (confirm('Desea eliminar este tercero?')) {
                this.eliminar(id);
            }
        },
        habilitaCampos: function(tipoDePersona){
            if(tipoDePersona === '1'){
                _.getID('razon_social').get().readOnly = true;
                _.getID('primer_nombre').get().readOnly = false;
                _.getID('segundo_nombre').get().readOnly = false;
                _.getID('primer_apellido').get().readOnly = false;
                _.getID('segundo_apellido').get().readOnly = false;
            } else if (tipoDePersona === '2'){
                _.getID('razon_social').get().readOnly = false;
                _.getID('primer_nombre').setValue('').get().readOnly = true;
                _.getID('segundo_nombre').setValue('').get().readOnly = true;
                _.getID('primer_apellido').setValue('').get().readOnly = true;
                _.getID('segundo_apellido').setValue('').get().readOnly = true;
            }
        },
        poblarRazon: function(){
            if(_.getID('tipo_persona').value() === '1'){
                var razon = _.getID('primer_nombre').value().trim() + ' ' + _.getID('segundo_nombre').value().trim() + ' '+ _.getID('primer_apellido').value().trim() + ' ' + _.getID('segundo_apellido').value().trim();
                _.getID('razon_social').setValue(razon);
            }
        },
        ejecutado: function (datos) {
            var data = JSON.parse(datos);
            
            tercerosCtrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
            if (data.tipo === _.MSG_CORRECTO) {
                tercerosCtrl.formulario.reset();
            } else if (data.tipo === _.MSG_NO_AUTENTICADO) {
                window.location.href = 'index.html';
            }
        },
        actualizar: function () {
            var self = this,
                obj = {
                datos: new FormData(self.formulario),
                url: 'STerceroActualizar',
                callback: self.ejecutado
            };
            _.ejecutar(obj);
        },
        crear: function () {
            var self = this,
                obj = {
                datos: new FormData(self.formulario),
                url: 'STerceroCrear',
                callback: self.ejecutado
            };
            _.ejecutar(obj);
        },
        eliminar: function(id){
            var data = new FormData(), obj = {}, self = this;
            data.append('id', id);
            obj.datos = data;
            obj.url = 'STerceroEliminar';
            obj.callback = this.eliminado;
            _.ejecutar(obj);
        },
        eliminado: function(datos){
            var data = JSON.parse(datos);
            tercerosCtrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
            if (data.tipo === _.MSG_CORRECTO) {
                tercerosCtrl.listar(tercerosCtrl.cargarTabla);
            } else if (data.tipo === _.MSG_NO_AUTENTICADO) {
                window.location.href = 'index.html';
            }
        },
        detalles: function(id){
            var data = new FormData(), obj = {};
            data.append('tipoConsulta', 2);
            data.append('id', id);
            obj.datos = data;
            obj.url = 'STerceroGetXId';
            obj.callback = this.muestraDetalles;
            _.ejecutar(obj);
        },
        muestraDetalles: function(datos){
            var data = JSON.parse(datos),
                objeto = data.objeto;
            if(data.tipo === _.MSG_CORRECTO){
                window.location.hash = '#/terceros/detalle';
                setTimeout(function(){
                    _.getID('id').setValue(objeto.id);
                    _.getID('tipo_identificacion').setValue(objeto.documento);
                    _.getID('numero_identificacion').setValue(objeto.numero_identificacion);
                    _.getID('digito_verificacion').setValue(objeto.digito_verificacion);
                    _.getID('tipo_persona').setValue(objeto.tipopersona);
                    _.getID('razon_social').setValue(objeto.razon_social);
                    _.getID('primer_apellido').setValue(objeto.primer_apellido);
                    _.getID('segundo_apellido').setValue(objeto.segundo_apellido);
                    _.getID('primer_nombre').setValue(objeto.primer_nombre);
                    _.getID('segundo_nombre').setValue(objeto.segundo_nombre);
                    _.getID('direccion').setValue(objeto.direccion);
                    _.getID('telefono').setValue(objeto.telefono);
                    _.getID('depto_residencia').setValue(objeto.departamento);
                    _.getID('ciudad_residencia').setValue(objeto.ciudad);
                    _.getID('correo').setValue(objeto.correo);
                }, 200);
            } else if(data.tipo === _.MSG_ADVERTENCIA || data.tipo === _.MSG_ERROR){
                tercerosCtrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
            } else if (data.tipo === _.MSG_NO_AUTENTICADO){
                window.location.href = 'index.html';
            }
        },
        listar: function (callback) {
            var data = _.paginacion(this.pagina, this.limite, this.columna_orden, this.tipo_orden),
                obj = {};

            data.append('tipoConsulta', 1);
            obj.datos = data;
            obj.url = 'STerceroListar';
            obj.callback = callback;
            _.ejecutar(obj);
        },
        preparaActualizar: function (id) {
            var data = new FormData(), obj = {};
            
            data.append('tipoConsulta', 2);
            data.append('id', id);
            obj.datos = data;
            obj.url = 'STerceroGetXId';
            obj.callback = this.muestraActualizar;
            _.ejecutar(obj);
        },
        muestraActualizar: function(datos) {
            var data = JSON.parse(datos),
                objeto = data.objeto,
                departamentos;
            if (data.tipo === _.MSG_CORRECTO) {
                window.location.hash = '#/terceros/actualizar';
                setTimeout(function () {
                    departamentos = _.getID('depto_residencia').get();
                    _.getID('id').setValue(objeto.id);
                    _.getID('tipo_identificacion').setValue(objeto.idtipoidentificacion);
                    _.getID('numero_identificacion').setValue(objeto.numero_identificacion);
                    _.getID('digito_verificacion').setValue(objeto.digito_verificacion);
                    _.getID('tipo_persona').setValue(objeto.idtipopersona);
                    _.getID('razon_social').setValue(objeto.razon_social);
                    _.getID('primer_apellido').setValue(objeto.primer_apellido);
                    _.getID('segundo_apellido').setValue(objeto.segundo_apellido);
                    _.getID('primer_nombre').setValue(objeto.primer_nombre);
                    _.getID('segundo_nombre').setValue(objeto.segundo_nombre);
                    _.getID('direccion').setValue(objeto.direccion);
                    _.getID('telefono').setValue(objeto.telefono);
                    departamentos.value = objeto.iddepartamento;
                    _.getID('correo').setValue(objeto.correo);
                    departamentos.focus();
                    departamentos.blur();
                    setTimeout(function(){
                        _.getID('ciudad_residencia').setValue(objeto.idciudad);
                    }, 200);
                }, 300);
            } else if (data.tipo === _.MSG_NO_AUTENTICADO) {
                window.location.href = 'index.html';
            }
        },
        cargarTabla: function (datos) {
            var data = JSON.parse(datos), campos = [], acciones = {};
            if (data.tipo === _.MSG_CORRECTO) {
                campos = ['numero_identificacion', 'tipopersona', 'razon_social', 'telefono', 'correo'],
                        acciones = {
                            eliminar: {clase: '.eliminar',
                                funcion: function (e) {
                                    e.preventDefault();
                                    tercerosCtrl.confirmaEliminar(e.target.dataset.idu);
                                }
                            },
                            actualizar: {clase: '.actualizar',
                                funcion: function (e) {
                                    e.preventDefault();
                                    tercerosCtrl.confirmaActualizar(e.target.dataset.idu);
                                }
                            },
                            detalles: {clase: '.detalles',
                                funcion: function (e) {
                                    e.preventDefault();
                                    tercerosCtrl.detalles(e.target.dataset.idu);
                                }
                            }
                        };

                tercerosCtrl.total_paginas = Math.ceil(data.objeto.registros / tercerosCtrl.limite);
                _.getID('pagina').get().setAttribute('max', tercerosCtrl.total_paginas);
                _.getID('total_paginas').text('de ' + tercerosCtrl.total_paginas);
                _.llenarFilas('cuerpoTabla', 'plantilla', data.objeto.terceros, campos, acciones);
            } else if (data.tipo === _.MSG_ADVERTENCIA || data.tipo === _.MSG_ERROR) {
                tercerosCtrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
            } else if (data.tipo === _.MSG_NO_AUTENTICADO) {
                window.location.href = 'index.html';
            }
        },
        paginar: function () {
            _.paginar();
            this.listar(this.cargarTabla);
        },
        paginar_paginas: function (accion) {
            _.paginar_paginas(accion);
            this.paginar();
        }
    };
    
    _.controlador('terceros', tercerosCtrl);
})(window, document, JSON, _, Math);