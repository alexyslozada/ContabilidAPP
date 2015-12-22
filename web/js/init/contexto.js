/* global _ */
'use strict';
(function(_){
  var conectar = function(datos){
    var data = JSON.parse(datos),
        div  = _.getID('conectar');
    div.text(data.mensaje);
  };

  _.ajax({url:'SContexto'}).then(function(data){conectar(data);}, function(error){console.log(error);});
})(_);