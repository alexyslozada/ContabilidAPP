/* global _ */
/* global CryptoJS */

'use strict';
(function(_, window, document, CryptoJS){
    var usuariosCtrl = {
        pagina: 1,
        total_paginas: 0,
        limite: 10,
        columna_orden: 2,
        tipo_orden: 'acs',
        tipo_consulta: 1,
        actualizar: function(){
            var formulario = _.getID('frmActualizarUsuario').get();
            _.ajax({url: 'SUsuarioActualizar', datos: new FormData(formulario)})
                    .then(function(datos){actualizado(datos);}, function(error){console.log(error);});
        },
        crear: function(){
            var formulario = _.getID('frmCrearUsuario').get(),
                data = null;
            formulario.clave.value = CryptoJS.SHA3(formulario.clave.value);
            data = new FormData(formulario);
            _.ajax({url: 'SUsuarioCrear', datos: data})
                    .then(function(datos){creado(datos);}, function(error){console.log(error);});
        },
        listar: function(callback){
            var data = _.paginacion(this.pagina, this.limite, this.columna_orden, this.tipo_orden);
            data.append("tipo_consulta", usuariosCtrl.tipo_consulta);
            _.ajax({url: 'SUsuarioListar', datos: data})
                  .then(function(datos){callback(datos);},
                        function(error){console.log(error);});
        },
        confirmaEliminar: function(id){
            if(confirm("Confirma que desea eliminar este registro?")){
                eliminar(id);
            }
        },
        confirmaActualizar: function(id){
            if(confirm("Confirma que desea actualizar este registro?")){
                preparaActualizar(id);
            }
        },
        cargaLista: function(datos){
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
                usuariosCtrl.total_paginas = Math.ceil(data.objeto.registros / usuariosCtrl.limite);
                _.getID('pagina').get().setAttribute('max', usuariosCtrl.total_paginas);
                _.llenarFilas('cuerpoTabla', 'plantilla', data.objeto.usuarios, campos, acciones);
                _.getID('total_paginas').text('de '+usuariosCtrl.total_paginas);
            } else if(data.tipo === _.MSG_ADVERTENCIA){
                _.getID('mensaje').delClass('no-mostrar').innerHTML(data.mensaje);
            } else if (data.tipo === _.MSG_NO_AUTENTICADO){
                window.location.href = 'index.html';
            }
        },
        paginar: function(){
            _.paginar();
            this.listar(this.cargaLista);
        },
        paginar_paginas: function(accion){
            _.paginar_paginas(accion);
            usuariosCtrl.paginar();
        },
        poblarPerfiles: function(datos){
            var data   = JSON.parse(datos),
                select = _.getID('perfil').get(),
                opcion = null, 
                fragmento = document.createDocumentFragment(),
                i = 0, max = 0;

            max = data.objeto.length;
            for(; i < max; i = i + 1){
                opcion = document.createElement('option');
                opcion.setAttribute('value', data.objeto[i].id);
                opcion.textContent = data.objeto[i].nombre;
                fragmento.appendChild(opcion);
            }
            select.appendChild(fragmento);
        }
    };
    
    function actualizado(datos){
        var data = JSON.parse(datos);
        _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
        if(data.tipo === _.MSG_CORRECTO){
            _.getID('frmActualizarUsuario').get().reset();
            setTimeout(function(){
                window.location.hash = '#/usuarios';
            }, 3000);
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    };
    
    function creado(datos){
        var data = JSON.parse(datos);
        _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
        if(data.tipo === _.MSG_CORRECTO){
            _.getID('frmCrearUsuario').get().reset();
        } else if(data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    };

    function eliminar(id){
        var data = new FormData();
        data.append("id", id);
        _.ajax({url: 'SUsuarioEliminar', datos: data})
                .then(function(datos){eliminado(datos);}, function(error){console.log(error);});
    };

    function eliminado(datos){
        var data = JSON.parse(datos);
        _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
        if(data.tipo === _.MSG_CORRECTO){
            usuariosCtrl.listar(usuariosCtrl.cargaLista);
        } else if(data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    }

    function preparaActualizar(id){
        var data = new FormData();
        data.append('tipo_consulta', 2);
        data.append('id', id);
        _.ajax({url: 'SUsuarioListar', datos: data})
                .then(function(datos){muestraActualizar(datos);}, function(error){console.log(error);});
    };
    
    function muestraActualizar(datos){
        var data = JSON.parse(datos),
            usuario = data.objeto.usuarios[0];
        if(data.tipo === _.MSG_CORRECTO){
            window.location.hash = '#/usuarios-actualizar';
            setTimeout(function(){
                _.getID('ide').setValue(usuario.id);
                _.getID('identificacion').setValue(usuario.identificacion);
                _.getID('nombre').setValue(usuario.nombre);
                _.getID('correo').setValue(usuario.correo);
                _.getID('perfil').setValue(usuario.idperfil);
                _.getID('activo').get().checked = usuario.activo;
            }, 200);
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        } else {
            alert(data.mensaje);
        }
    };

    _.controlador('usuarios', usuariosCtrl);
})(_, window, document, CryptoJS);