/* global _ */
(function (window, JSON, _) {
    var centroscostoCtrl = {
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
        inicio_crear: function () {
            this.formulario = _.getID('frmCrearCentrosCosto').noSubmit().get();
            this.inicio();
        },
        inicio_actualizar: function () {
            this.formulario = _.getID('frmActualizarCentrosCosto').noSubmit().get();
            this.inicio();
        },
        confirmaActualizar: function (id) {
            if (confirm('Desea actualizar este centro?')) {
                preparaActualizar(id);
            }
        },
        confirmaEliminar: function (id) {
            if (confirm('Desea eliminar este centro?')) {
                eliminar(id);
            }
        },
        actualizar: function () {
            var data = new FormData(this.formulario);
            _.ajax({url: 'SCentroCostoActualizar', datos: data})
                    .then(function (datos) {
                        actualizado(datos);
                    }, function (error) {
                        console.log(error);
                    });
        },
        crear: function () {
            var data = new FormData(this.formulario);
            _.ajax({url: 'SCentroCostoCrear', datos: data})
                    .then(function (datos) {
                        creado(datos);
                    }, function (error) {
                        console.log(error);
                    });
        },
        listar: function (callback) {
            var data = _.paginacion(this.pagina, this.limite, this.columna_orden, this.tipo_orden);
            data.append('tipoConsulta', 1);
            _.ajax({url: 'SCentroCostoListar', datos: data})
                    .then(function (datos) {
                        callback(datos);
                    }, function (error) {
                        console.log(error);
                    });
        },
        cargarTabla: function (datos) {
            var data = JSON.parse(datos), campos = [], acciones = {};
            if (data.tipo === _.MSG_CORRECTO) {
                campos = ['codigo', 'nombre'],
                        acciones = {eliminar: {clase: '.eliminar',
                                funcion: function (e) {
                                    e.preventDefault();
                                    centroscostoCtrl.confirmaEliminar(e.target.dataset.idu);
                                }
                            },
                            actualizar: {clase: '.actualizar',
                                funcion: function (e) {
                                    e.preventDefault();
                                    centroscostoCtrl.confirmaActualizar(e.target.dataset.idu);
                                }
                            }
                        };

                centroscostoCtrl.total_paginas = Math.ceil(data.objeto.registros / centroscostoCtrl.limite);
                _.getID('pagina').get().setAttribute('max', centroscostoCtrl.total_paginas);
                _.getID('total_paginas').text('de ' + centroscostoCtrl.total_paginas);
                _.llenarFilas('cuerpoTabla', 'plantilla', data.objeto.centros_costo, campos, acciones);
            } else if (data.tipo === _.MSG_ADVERTENCIA || data.tipo === _.MSG_ERROR) {
                centroscostoCtrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
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

    function actualizado(datos) {
        var data = JSON.parse(datos),
            ctrl = _.getCtrl();
        ctrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
        if (data.tipo === _.MSG_CORRECTO) {
            ctrl.formulario.reset();
            setTimeout(function () {
                window.location.hash = '#/centros-costo';
            }, 3000);
        } else if (data.tipo === _.MSG_NO_AUTENTICADO) {
            window.location.href = 'index.html';
        }
    }

    function creado(datos) {
        var data = JSON.parse(datos),
            ctrl = _.getCtrl();
        ctrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
        if (data.tipo === _.MSG_CORRECTO) {
            ctrl.formulario.reset();
        } else if (data.tipo === _.MSG_NO_AUTENTICADO) {
            window.location.href = 'index.html';
        }
    }

    function eliminar(id) {
        var data = new FormData();
        data.append('id', id);
        _.ajax({url: 'SCentroCostoEliminar', datos: data})
                .then(function (datos) {
                    eliminado(datos);
                }, function (error) {
                    console.log(error);
                });
    }

    function eliminado(datos) {
        var data = JSON.parse(datos),
            ctrl = _.getCtrl();
        ctrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
        if (data.tipo === _.MSG_CORRECTO) {
            ctrl.listar(ctrl.cargarTabla);
        } else if (data.tipo === _.MSG_NO_AUTENTICADO) {
            window.location.href = 'index.html';
        }
    }

    function preparaActualizar(id) {
        var data = new FormData();
        data.append('tipoConsulta', 2);
        data.append('id', id);
        _.ajax({url: 'SCentroCostoGetXCuentaXId', datos: data})
                .then(function (datos) {
                    muestraActualizar(datos);
                }, function (error) {
                    console.log(error);
                });
    }

    function muestraActualizar(datos) {
        var data = JSON.parse(datos),
            objeto = data.objeto;
        if (data.tipo === _.MSG_CORRECTO) {
            window.location.hash = '#/centros-costo/actualizar';
            setTimeout(function () {
                _.getID('id').setValue(objeto.id);
                _.getID('codigo').setValue(objeto.codigo);
                _.getID('nombre').setValue(objeto.nombre);
            }, 300);
        } else if (data.tipo === _.MSG_NO_AUTENTICADO) {
            window.location.href = 'index.html';
        }
    }

    _.controlador('centroscosto', centroscostoCtrl);
})(window, JSON, _);