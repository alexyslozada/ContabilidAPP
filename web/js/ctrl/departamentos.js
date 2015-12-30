/* global _ */
(function(_){
    var departamentosCtrl = {
        listar: function(callback){
            _.ajax({url: 'SDepartamentoListar'})
                    .then(function(data){callback(data);}, function(error){console.log(error);});
        }
    };
    _.controlador('departamentos', departamentosCtrl);
})(_);