/* global _ */
(function(_){
    var tipoRegimenCtrl = {
        listar: function(callback){
            _.ajax({url:'STipoRegimenListar'})
                    .then(function(data){callback(data);}, function(error){console.log(error);});
        }
    };
    _.controlador('tipoRegimen', tipoRegimenCtrl);
})(_);