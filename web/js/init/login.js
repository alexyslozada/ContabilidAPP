/* global CryptoJS */
/* global _ */
'use strict';
(function(window, _, CryptoJS){

  _.getID('frmLogin').noSubmit();
  
  var conexion = function(datos){
    var data = JSON.parse(datos),
        div  = _.getID('mensaje');
        div.text(data.mensaje);
    if(data.tipo === 2){
      window.location.href = 'sistema';
    } else {
      div.delClass('no-mostrar');
      console.log("Error", data.mensaje);
    }
  };
  
  _.getID('btnLogin').click(function(){
    var data = new FormData(),
        usr  = _.getID('usr').value(),
        pwd  = CryptoJS.SHA3(_.getID('pwd').value());
    data.append('usr', usr);
    data.append('pwd', pwd);
    _.ajax({url: 'SAutenticar', datos: data}).then(function(datos){conexion(datos);}, function(error){console.log(error);});
  });
    
})(window, _, CryptoJS);