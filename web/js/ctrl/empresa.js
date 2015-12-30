/* global _ */
(function(window, document, _, JSON){
    var empresaCtrl = {
        formulario: null,
        inicio: function(){
            this.formulario = _.getID('frmAdminEmpresa');
            this.formulario.noSubmit();
            _.getCtrl('tipoIdentificacion')
                    .listar(function(datos){
                                poblarSelect(datos, 'tipo_identificacion', 'id', 'documento', 'tipo_identificacion', true);
                            });
            _.getCtrl('departamentos')
                    .listar(function(datos){
                        poblarSelect(datos, 'objeto', 'codigo', 'nombre', 'departamento', false);
                    });
            _.getCtrl('tipoPersona')
                    .listar(function(datos){
                        poblarSelect(datos, 'objeto', 'id_tipo_persona', 'nombre', 'tipo_persona', false);
                    });
            _.getCtrl('tipoRegimen')
                    .listar(function(datos){
                        poblarSelect(datos, 'objeto', 'id_regimen', 'nombre', 'tipo_regimen', false);
            });
            // Cuando el departamento pierda el foco se cargan las ciudades
            _.getID('departamento').get().addEventListener('blur', function(e){
                var departamento = e.target.value;
                _.getID('ciudad').get().innerHTML = '';
                _.getCtrl('ciudades')
                    .listar(function(datos){
                        poblarSelect(datos, 'objeto', 'idciudad', 'nombre', 'ciudad', false);
                    }, 1, departamento);
                }, false);
            // Por último se carga la información de la empresa (si existe)
            setTimeout(function(){
                empresaCtrl.consultar();
            },500);
        },
        consultar: function(){
            _.ajax({url: 'SEmpresaListar'})
                    .then(function(data){poblar(data);}, function(error){console.log(error);});
        },
        upsert: function(){
            var data = new FormData(this.formulario.get());
            _.ajax({url: 'SEmpresaUpsert', datos: data})
                    .then(function(datos){actualizado(datos);}, function(error){console.log(error);});
        }
    };

    function actualizado(datos){
        var data = JSON.parse(datos);
        _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
        if(data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    };
    
    function poblar(datos){
        var data = JSON.parse(datos),
            form = empresaCtrl.formulario.get();
        if(data.tipo === _.MSG_CORRECTO && Object.keys(data.objeto).length > 0){
            
            form.tipo_identificacion.value = data.objeto.tipo_identificacion;
            form.numero_identificacion.value = data.objeto.numero_identificacion;
            form.digito_verificacion.value = data.objeto.digito_verificacion;
            form.nombre.value = data.objeto.nombre;
            form.direccion.value = data.objeto.direccion;
            form.telefono.value = data.objeto.telefono;
            form.departamento.value = data.objeto.id_departamento;
            form.direccion_web.value = data.objeto.direccion_web;
            form.correo.value = data.objeto.correo;
            form.actividad.value = data.objeto.actividad;
            form.autorretenedor.checked = data.objeto.autorretenedor;
            form.tipo_persona.value = data.objeto.tipo_persona;
            form.tipo_regimen.value = data.objeto.tipo_regimen;
            form.departamento.focus();
            form.departamento.blur();
            setTimeout(function(){
                form.ciudad.value = data.objeto.id_ciudad;
            },200);
        } else if(data.tipo !== _.MSG_NO_AUTENTICADO) {
            _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
        } else {
            window.location.href = 'index.html';
        }
    };

    function poblarSelect(datos, tabla, id, campo, select, esInterno){
        var data = JSON.parse(datos), fragmento = document.createDocumentFragment(),
            lista = null, i = 0, max = 0, opcion = null;
        if(data.tipo === _.MSG_CORRECTO){
            if(esInterno){
                lista = data.objeto[tabla];
            } else {
                lista = data.objeto;
            }
            max = lista.length;
            for(; i < max; i = i + 1){
                opcion = document.createElement('option');
                opcion.setAttribute('value', lista[i][id]);
                opcion.textContent = lista[i][campo];
                fragmento.appendChild(opcion);
            }
            _.getID(select).get().appendChild(fragmento);
        }
    };
    
    _.controlador('empresa', empresaCtrl);
})(window, document, _, JSON);