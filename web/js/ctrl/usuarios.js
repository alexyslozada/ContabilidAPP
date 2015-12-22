/* global _ */
'use strict';
(function(_, window){
    var usuariosCtrl = {
        pagina: 1,
        total_paginas: 0,
        limite: 10,
        columna_orden: 2,
        tipo_orden: 'acs',
        tipo_consulta: 1,
        listar: function(){
            var data = _.paginacion(this.pagina, this.limite, this.columna_orden, this.tipo_orden);
            data.append("tipo_consulta", usuariosCtrl.tipo_consulta);
            _.ajax({url: 'SUsuarioListar', datos: data}).then(function(datos){cargaLista(datos);}, function(error){console.log(error);});
        }
    };
    
    function cargaLista(datos){
        var data = JSON.parse(datos),
            campos = ['id', 'identificacion', 'nombre', 'correo', 'perfil', 'activo'],
            acciones = {eliminar: {clase: '.eliminar',
                                    funcion: function(e){
                                        e.preventDefault();
                                        usuariosCtrl.confirmaEliminar(e.target.dataset.idu);
                                    }
                        },
                        actualizar: {clase: '.actualizar',
                                    funcion: function(e){
                                        e.preventDefault();
                                        usuariosCtrl.confirmaActualizar(e.target.dataset.idu);
                                    }
                        }
            };
        if(data.tipo === _.MSG_CORRECTO){
            var total_paginas = Math.ceil(data.objeto.registros / usuariosCtrl.limite);
            _.llenarFilas('cuerpoTabla', 'plantilla', data.objeto.usuarios, campos, acciones);
            _.getID('total_paginas').text('de '+total_paginas);
        } else if(data.tipo === _.MSG_ADVERTENCIA){
            _.getID('mensaje').delClass('no-mostrar').innerHTML(data.mensaje);
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    };

    _.controlador('usuarios', usuariosCtrl);
})(_, window);