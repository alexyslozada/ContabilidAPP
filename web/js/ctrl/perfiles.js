/* global _ */
'use strict';
(function(window){
    
    var datos = [],
        perfilesCtrl = {
            actualizar: function(){
                var formulario = _.getID('frmActualizarPerfil').get();
                _.ajax({
                    url: 'SPerfilActualizar',
                    datos: new FormData(formulario),
                    funcion: actualizado
                });
            },
            crear: function(){
                var formulario = _.getID('frmCrearPerfil').get();
                _.ajax({
                    url: 'SPerfilCrear',
                    datos: new FormData(formulario),
                    funcion: creado
                });
            },
            confirmaActualizar: function(id){
                if(confirm("Desea actualizar este registro?")){
                    preparaActualizar(id);
                }
            },
            confirmaEliminar: function(id){
                if(confirm("Está seguro que desea eliminar este registro?")){
                    eliminar(id);
                }
            },
            irAPermisos: function(id){
                _.setSingleton({idPerfil: id});
                window.location.hash = '#/perfiles-permisos';
            },
            listar: function(){
                var data = new FormData();
                data.append('tipoConsulta', 1);
                _.ajax({
                    url: 'SPerfilListar',
                    funcion: cargaLista,
                    datos: data
                });
            }
        };

    function actualizado(){
        var data = JSON.parse(this.responseText);
        _.getID('mensaje').delClass('no-mostrar').innerHTML(data.mensaje);
        if(data.tipo === _.MSG_CORRECTO){
            _.getID('frmActualizarPerfil').get().reset();
            setTimeout(function(){
                window.location.hash = '#/perfiles';
            }, 3000);
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    };

    function creado(){
        var data = JSON.parse(this.responseText),
            formulario = _.getID('frmCrearPerfil').get();

        _.getID('mensaje').delClass('no-mostrar').innerHTML(data.mensaje);

        if(data.tipo === _.MSG_CORRECTO){
            formulario.reset();
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    };

    function cargaLista(){
        var data = JSON.parse(this.responseText),
            campos = ['id','nombre','activo'],
            acciones = {eliminar: {clase:'.eliminar',
                                   funcion: function(e){
                                        e.preventDefault();
                                        perfilesCtrl.confirmaEliminar(e.target.dataset.idu);
                                   }
                                  },
                        actualizar: {clase:'.actualizar',
                                     funcion: function(e){
                                         e.preventDefault();
                                         perfilesCtrl.confirmaActualizar(e.target.dataset.idu);
                                     }
                                 },
                        permisos: {clase: '.permisos',
                                    funcion: function(e){
                                        e.preventDefault();
                                        perfilesCtrl.irAPermisos(e.target.dataset.idu);
                                    }
                                }
                        };

        if(data.tipo === _.MSG_CORRECTO){
            datos = data.objeto;
            _.llenarFilas('cuerpoTabla', 'plantilla', data.objeto, campos, acciones);
        } else if(data.tipo === _.MSG_ADVERTENCIA){
            _.getID('mensaje').delClass('no-mostrar').innerHTML(data.mensaje);
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    };

    function eliminar(id){
        var data = new FormData();
            data.append('id', id);
            _.ajax({
                url: 'SPerfilEliminar',
                datos: data,
                funcion: eliminado
            });
    };
    
    function eliminado(){
        var data = JSON.parse(this.responseText);

        _.getID('mensaje').delClass('no-mostrar').innerHTML(data.mensaje);

        if(data.tipo === _.MSG_CORRECTO){
            _.getCtrl().listar();
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    };

    function muestraActualizar(){
        var data = JSON.parse(this.responseText);
        if(data.tipo === _.MSG_CORRECTO){
            window.location.hash = '#/perfiles-actualizar';
            setTimeout(function(){
                _.getID('ide').setValue(data.objeto.idperfil);
                _.getID('nombre').setValue(data.objeto.nombre);
                _.getID('activo').get().checked = data.objeto.activo;
            }, 200);
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        } else {
            alert(data.mensaje);
        }
    };
    
    function preparaActualizar(id){
        var data = new FormData();
        data.append('tipoConsulta', 2);
        data.append('id', id);
        _.ajax({
            url: 'SPerfilListar',
            funcion: muestraActualizar,
            datos: data
        });
    };
    
    _.controlador('perfiles', perfilesCtrl);

})(window);