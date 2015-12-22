/* global _ */
'use strict';
(function(window){
    
    var perfilesCtrl = {
        actualizar: function(){
            var formulario = _.getID('frmActualizarPerfil').get();
            _.ajax({
                url: 'SPerfilActualizar',
                datos: new FormData(formulario)
            }).then(function(datos){actualizado(datos);}, function(error){console.log(error);});
        },
        crear: function(){
            var formulario = _.getID('frmCrearPerfil').get();
            _.ajax({url: 'SPerfilCrear', datos: new FormData(formulario)}).then(function(datos){creado(datos);}, function(error){console.log(error);});
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
        /**
         * Listar: consulta la lista de perfiles.
         * Si no se pasan parámetros, se carga la tabla nativa.
         * De lo contrario, se devuelve el objeto json del listado
         * @param boolean True: Significa que solo devuelve el json.
         * @returns Si True JSON, de lo contrario carga la tabla nativa.
         */
        listar: function(callback){
            var data = new FormData();
            data.append('tipoConsulta', 1);
            _.ajax({
                    url: 'SPerfilListar',
                    datos: data
                  }).then(function(respuesta){
                      callback(respuesta);
                  }, function(error){
                      console.log(error);
                  });
        },
        cargaLista: function(datos){
            var data = JSON.parse(datos),
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
                _.llenarFilas('cuerpoTabla', 'plantilla', data.objeto, campos, acciones);
            } else if(data.tipo === _.MSG_ADVERTENCIA){
                _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
            } else if (data.tipo === _.MSG_NO_AUTENTICADO){
                window.location.href = 'index.html';
            }
        }
    };

    function actualizado(datos){
        var data = JSON.parse(datos);
        _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
        if(data.tipo === _.MSG_CORRECTO){
            _.getID('frmActualizarPerfil').get().reset();
            setTimeout(function(){
                window.location.hash = '#/perfiles';
            }, 3000);
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    };

    function creado(datos){
        var data = JSON.parse(datos),
            formulario = _.getID('frmCrearPerfil').get();

        _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);

        if(data.tipo === _.MSG_CORRECTO){
            formulario.reset();
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    };

    function eliminar(id){
        var data = new FormData();
            data.append('id', id);
            _.ajax({url: 'SPerfilEliminar', datos: data}).then(function(datos){eliminado(datos);}, function(error){console.log(error);});
    };
    
    function eliminado(datos){
        var data = JSON.parse(datos),
            ctrl = _.getCtrl();

        _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);

        if(data.tipo === _.MSG_CORRECTO){
            ctrl.listar(ctrl.cargaLista);
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    };

    function muestraActualizar(datos){
        var data = JSON.parse(datos);
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
        _.ajax({url: 'SPerfilListar', datos: data}).then(function(datos){muestraActualizar(datos);}, function(error){console.log(error);});
    };

    _.controlador('perfiles', perfilesCtrl);

})(window);