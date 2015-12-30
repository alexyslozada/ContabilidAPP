/* global _ */
(function(_){
    var tipoPersonaCtrl = {
        listar: function(callback){
            _.ajax({url: 'STipoPersonaListar'})
                    .then(function(data){callback(data);}, function(error){console.log(error);});
        }
    };
    _.controlador('tipoPersona', tipoPersonaCtrl);
})(_);