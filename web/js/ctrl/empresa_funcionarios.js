/* global _ */
(function (window, JSON, _) {
    'use strict';
    var empFuncionarioCtrl = {
        formulario: null,
        inicio: function () {
            _.getCtrl('tipoIdentificacion')
                    .listar(function (datos) {
                        _.poblarSelect(datos, 'tipo_identificacion', 'id', 'documento', 'tipo_identificacion', true);
                    });
            _.getCtrl('tipoFuncionario')
                    .listar(function (datos) {
                        _.poblarSelect(datos, 'tipo_funcionario', 'id', 'nombre', 'tipo_funcionario', true);
                    });
        },
        inicio_crear: function () {
            this.formulario = _.getID('frmCrearFuncionarios').noSubmit().get();
            this.inicio();
        },
        inicio_actualizar: function () {
            this.formulario = _.getID('frmActualizarFuncionarios').noSubmit().get();
            this.inicio();
        },
        actualizar: function(){
            var data = new FormData(this.formulario);
            _.ajax({url: 'SEmpresaFuncionarioActualizar', datos: data})
                    .then(function(datos){actualizado(datos);}, function(error){console.log(error);});
        },
        crear: function () {
            var data = new FormData(this.formulario);
            _.ajax({url: 'SEmpresaFuncionarioCrear', datos: data})
                    .then(function (datos) {
                        creado(datos);
                    }, function (error) {
                        console.log(error);
                    });
        },
        cargarTabla: function (datos) {
            var data = JSON.parse(datos),
                    campos = ['id', 'documento', 'identificacion', 'digito_verificacion', 'nombre', 'tipo_funcionario', 'vigente'],
                    acciones = {eliminar: {clase: '.eliminar',
                            funcion: function (e) {
                                e.preventDefault();
                                empFuncionarioCtrl.confirmaEliminar(e.target.dataset.idu);
                            }
                        },
                        actualizar: {clase: '.actualizar',
                            funcion: function (e) {
                                e.preventDefault();
                                empFuncionarioCtrl.confirmaActualizar(e.target.dataset.idu);
                            }
                        }
                    };
            if (data.tipo === _.MSG_CORRECTO) {
                _.llenarFilas('cuerpoTabla', 'plantilla', data.objeto, campos, acciones);
            } else if (data.tipo === _.MSG_ADVERTENCIA) {
                _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
            } else if (data.tipo === _.MSG_NO_AUTENTICADO) {
                window.location.href = 'index.html';
            }
        },
        confirmaActualizar: function(id){
            if(confirm('Desea actualizar este registro?')){
                preparaActualizar(id);
            }
        },
        confirmaEliminar: function(id){
            if(confirm('Desea eliminar este registro?')){
                eliminar(id);
            }
        },
        listar: function (callback) {
            var data = new FormData();
            data.append('tipoConsulta', 1);
            _.ajax({url: 'SEmpresaFuncionarioListar', datos: data})
                    .then(function (respuesta) {
                        callback(respuesta);
                    }, function (error) {
                        console.log(error);
                    });
        }
    };
    
    function actualizado(datos){
        var data = JSON.parse(datos);
        _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
        if(data.tipo === _.MSG_CORRECTO){
            empFuncionarioCtrl.formulario.reset();
            setTimeout(function(){
                window.location.hash = '#/empresa-funcionarios';
            }, 3000);
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    }

    function creado(datos) {
        var data = JSON.parse(datos);
        _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
        if (data.tipo === _.MSG_CORRECTO) {
            empFuncionarioCtrl.formulario.reset();
        } else if (data.tipo === _.MSG_NO_AUTENTICADO) {
            window.location.href = 'index.html';
        }
    };

    function eliminar(id){
        var data = new FormData();
        data.append('id', id);
        _.ajax({url: 'SEmpresaFuncionarioEliminar', datos: data})
                .then(function(datos){eliminado(datos);}, function(error){console.log(error);});
    }
    
    function eliminado(datos){
        var data = JSON.parse(datos);
        _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
        if(data.tipo === _.MSG_CORRECTO){
            empFuncionarioCtrl.listar(empFuncionarioCtrl.cargarTabla);
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    }
    
    function preparaActualizar(id){
        var data = new FormData();
        data.append('tipoConsulta', 2);
        data.append('id', id);
        _.ajax({url: 'SEmpresaFuncionarioListar', datos: data})
              .then(function(datos){muestraActualizar(datos);}, function(error){console.log(error);});
    }
    
    function muestraActualizar(datos){

        var data = JSON.parse(datos),
            objeto = data.objeto[0];
        _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
        console.log(objeto);
        if(data.tipo === _.MSG_CORRECTO){

            window.location.hash = '#/empresa-funcionarios-actualizar';
            setTimeout(function(){
                empFuncionarioCtrl.inicio_actualizar();
                _.getID('ide').setValue(objeto.id);
                _.getID('tipo_identificacion').setValue(objeto.tipo_identificacion);
                _.getID('identificacion').setValue(objeto.identificacion);
                _.getID('digito_verificacion').setValue(objeto.digito_verificacion);
                _.getID('nombre').setValue(objeto.nombre);
                _.getID('tipo_funcionario').setValue(objeto.idtipofuncionario);
                _.getID('vigente').get().checked = objeto.vigente;
            }, 200);

        } else if(data.tipo === _.MSG_NO_AUTENTICADO) {
            window.location.href = 'index.html';
        }
    }
    
    _.controlador('empresaFuncionario', empFuncionarioCtrl);
})(window, JSON, _);