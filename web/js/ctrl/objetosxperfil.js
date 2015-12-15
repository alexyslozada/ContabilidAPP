/* global _ */
(function(){
    
    var objetosxperfilCtrl = {
        cargaPermisos: function(){
            var id = _.getSingleton().idPerfil,
                data = new FormData();
            data.append('id', id);
            _.ajax({
                url: 'SObjXPerfilListar',
                datos: data,
                funcion: cargarDatos
            });
        }
    };
    
    function cargarDatos(){
        var data = JSON.parse(this.responseText),
            campos = ['id', 'oxp_objeto', 'oxp_insertar', 'oxp_modificar', 'oxp_borrar', 'oxp_consultar'],
            acciones = {'actualizar': 
                            {
                                clase: '.actualizar',
                                funcion: function(e){
                                    e.preventDefault();
                                    console.log("Actualizando registro");
                                }
                            }
                        };
        if(data.tipo === _.MSG_CORRECTO){
            _.getID('perfil').setValue(data.objeto.nombre);
            _.getID('activo').get().checked = data.objeto.activo;
            _.llenarFilas('cuerpoTabla', 'plantilla', data.objeto.oxp, campos, acciones);
        } else {
            _.getID('mensaje').delClass('no-mostrar').innerHTML(data.mensaje);
        }
    };
    
    _.controlador('objetosxperfil', objetosxperfilCtrl);
    
})();