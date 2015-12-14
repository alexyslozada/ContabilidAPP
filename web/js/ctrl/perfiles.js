/* global _ */
'use strict';
(function(window){
    
    var dato = {},
        datos = [],
        perfilesCtrl = {
            actualizar: function(id){
                console.log("Actualizando: ", id);
            },
            crear: function(){
                var formulario = _.getID('frmCrearPerfil').get();
                _.ajax({
                    url: 'SPerfilCrear',
                    datos: new FormData(formulario),
                    funcion: creado
                });
            },
            eliminar: function(id){
                var data = new FormData();
                if(confirm("Está seguro que desea eliminar este registro?")){
                    data.append('id', id);
                    _.ajax({
                        url: 'SPerfilEliminar',
                        datos: data,
                        funcion: eliminado
                    });
                }
            },
            listar: function(){
                _.ajax({
                    url: 'SPerfilListar',
                    funcion: cargaLista
                });
            }
        };
    
    function creado(){
        var data = JSON.parse(this.responseText),
            formulario = _.getID('frmCrearPerfil').get();

        _.getID('mensaje').delClass('no-mostrar').innerHTML(data.mensaje);

        if(data.tipo === _.MSG_CORRECTO){
            formulario.reset();
        } else if(data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    };
    
    function cargaLista(){
        var data = JSON.parse(this.responseText),
            campos = ['id','nombre','activo'];

        _.getID('mensaje').delClass('no-mostrar').innerHTML(data.mensaje);

        if(data.tipo === _.MSG_CORRECTO){
            datos = data.objeto;
            _.llenarFilas('cuerpoTabla', 'plantilla', data.objeto, campos);
        } else if(data.tipo === _.MSG_NO_AUTENTICADO) {
            window.location.href = "index.html";
        }
    };

    function eliminado(){
        var data = JSON.parse(this.responseText);

        _.getID('mensaje').delClass('no-mostrar').innerHTML(data.mensaje);

        if(data.tipo === _.MSG_CORRECTO){
            _.getCtrl().listar();
        }
    };
    
    _.controlador('perfiles', perfilesCtrl);

})(window);