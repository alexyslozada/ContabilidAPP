'use strict';
(function(window){

  libreria.getID('frmLogin').noSubmit();
  
  var conexion = function(){
    var data = JSON.parse(this.responseText),
        div  = libreria.getID('mensaje');
        div.text(data.mensaje);
    if(data.tipo === 2){
      window.location.href = 'sistema';
    } else {
      div.delClass('no-mostrar');
      console.log("Error", data.mensaje);
    }
  };
  
  libreria.getID('btnLogin').click(function(){
    var data = new FormData(),
        usr  = libreria.getID('usr').value(),
        pwd  = CryptoJS.SHA3(libreria.getID('pwd').value());
    data.append('usr', usr);
    data.append('pwd', pwd);
    libreria.ajax({
      url: 'SAutenticar',
      datos: data,
      funcion: conexion
    });
  });
    
})(window);