/* global _ */
'use strict';
(function(window, JSON, _){
    var tipoFuncionarioCtrl = {
        pagina: 1,
        total_paginas: 0,
        limite: 10,
        columna_orden: 2,
        tipo_orden: 'acs',
        tipo_consulta: 1,
        formulario: null,
        inicio_actualizar: function(){
            this.formulario = _.getID('frmActualizarTipoFuncionario').noSubmit().get();
        },
        inicio_crear: function(){
            this.formulario = _.getID('frmCrearTipoFuncionario').noSubmit().get();
        },
        inicio_listar: function(){
            this.listar(cargaTabla);
        },
        actualizar: function(){
            var data = new FormData(this.formulario);
            _.ajax({url: 'STipoFuncionarioActualizar', datos: data})
                    .then(function(datos){actualizado(datos);}, function(error){console.log(error);});
        },
        crear: function(){
            var data = new FormData(this.formulario);
            _.ajax({url: 'STipoFuncionarioCrear', datos:data})
                    .then(function(datos){creado(datos);}, function(error){console.log(error);});
        },
        confirmaActualizar: function(id){
            if(confirm("Está seguro que desea actualizar este registro?")){
                preparaActualizar(id);
            }
        },
        confirmaEliminar: function(id){
            if(confirm("Está seguro que desea eliminar este registro?")){
                eliminar(id);
            }
        },
        listar: function(callback){
             var data = _.paginacion(this.pagina, this.limite, this.columna_orden, this.tipo_orden);
             data.append("tipo_consulta", this.tipo_consulta);
            _.ajax({url: 'STipoFuncionarioListar', datos: data})
                    .then(function(data){callback(data);}, function(error){console.log(error);});
        },
        paginar: function(){
            _.paginar();
            this.listar(cargaTabla);
        },
        paginar_paginas: function(accion){
            _.paginar_paginas(accion);
            this.paginar();
        }
    };
    
    function cargaTabla(datos){
        var data = JSON.parse(datos),
            campos = ['id', 'nombre'],
            acciones = {eliminar: {clase: '.eliminar',
                                    funcion: function(e){
                                        e.preventDefault();
                                        tipoFuncionarioCtrl.confirmaEliminar(e.target.dataset.idu);
                                    }
                        },
                        actualizar: {clase: '.actualizar',
                                    funcion: function(e){
                                        e.preventDefault();
                                        tipoFuncionarioCtrl.confirmaActualizar(e.target.dataset.idu);
                                    }
                        }
            };
        if(data.tipo === _.MSG_CORRECTO){
            tipoFuncionarioCtrl.total_paginas = Math.ceil(data.objeto.registros / tipoFuncionarioCtrl.limite);
            _.getID('pagina').get().setAttribute('max', tipoFuncionarioCtrl.total_paginas);
            _.llenarFilas('cuerpoTabla', 'plantilla', data.objeto.tipo_funcionario, campos, acciones);
            _.getID('total_paginas').text('de ' + tipoFuncionarioCtrl.total_paginas);
        } else if(data.tipo === _.MSG_ADVERTENCIA || data.tipo === _.MSG_ERROR){
            _.getID('mensaje').delClass('no-mostrar').innerHTML(data.mensaje);
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    };
    
    function actualizado(datos){
        var data = JSON.parse(datos);
        _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
        if(data.tipo === _.MSG_CORRECTO){
            tipoFuncionarioCtrl.formulario.reset();
            setTimeout(function(){
                window.location.hash = '#/tipo-funcionario';
            }, 3000);
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    };
    
    function creado(datos){
        var data = JSON.parse(datos);
        _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
        if(data.tipo === _.MSG_CORRECTO){
            tipoFuncionarioCtrl.formulario.reset();
        }else if(data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    };
    
    function eliminar(id){
        var data = new FormData();
        data.append("id", id);
        _.ajax({url: 'STipoFuncionarioEliminar', datos: data})
                .then(function(datos){eliminado(datos);}, function(error){console.log(error);});
    };

    function eliminado(datos){
        var data = JSON.parse(datos);
        _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
        if(data.tipo === _.MSG_CORRECTO){
            tipoFuncionarioCtrl.listar(cargaTabla);
        } else if(data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    };

    function preparaActualizar(id){
        var data = new FormData();
        data.append('tipo_consulta', 2);
        data.append('id', id);
        _.ajax({url: 'STipoFuncionarioListar', datos: data})
                .then(function(datos){muestraActualizar(datos);}, function(error){console.log(error);});
    };

    function muestraActualizar(datos){
        var data = JSON.parse(datos),
            tipoFuncionario = data.objeto.tipo_funcionario[0];
        if(data.tipo === _.MSG_CORRECTO){
            window.location.hash = '#/tipo-funcionario-actualizar';
            setTimeout(function(){
                _.getID('ide').setValue(tipoFuncionario.id);
                _.getID('nombre').setValue(tipoFuncionario.nombre);
            }, 200);
        } else if (data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        } else {
            alert(data.mensaje);
        }
    };
    
    _.controlador('tipoFuncionario', tipoFuncionarioCtrl);
})(window, JSON, _);