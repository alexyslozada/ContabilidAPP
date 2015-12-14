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
    
    function creado(){
        var data = JSON.parse(this.responseText),
            formulario = _.getID('frmCrearPerfil').get();

        _.getID('mensaje').delClass('no-mostrar').innerHTML(data.mensaje);

        if(data.tipo === _.MSG_CORRECTO){
            formulario.reset();
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
        }
    };

    function cargaLista(){
        var data = JSON.parse(this.responseText),
            campos = ['id','nombre','activo'];

        if(data.tipo === _.MSG_CORRECTO){
            datos = data.objeto;
            _.llenarFilas('cuerpoTabla', 'plantilla', data.objeto, campos);
        } else if(data.tipo === _.MSG_ADVERTENCIA){
            _.getID('mensaje').delClass('no-mostrar').innerHTML(data.mensaje);
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