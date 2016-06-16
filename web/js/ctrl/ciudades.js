/* global _ */
(function(_){
    var ciudadesCtrl = {
        listar: function(callback, tipo, departamento){
            var data = new FormData();
            data.append('tipo', tipo);
            data.append('departamento', departamento);
            _.ajax({url: 'SCiudadListar', datos: data})
                    .then(function(datos){callback(datos);}, function(error){console.log(error);});
        }
    };
    _.controlador('ciudades', ciudadesCtrl);
})(_);