/* global libreria */
/* global _ */
(function(window){
    _.enrutar('vista')
        .noEncontrado('404.html')
        .ruta('/', 'vistas/index.html')
        .ruta('/perfiles',
              'vistas/perfiles/listar.html',
              'perfiles',
              function(){
                  _.getCtrl().listar();
              })
        .ruta('/perfiles-crear',
              'vistas/perfiles/crear.html',
              'perfiles',
              function(){
                  _.getID('frmCrearPerfil').noSubmit();
              });

    window.addEventListener('load', _.manejadorRutas, false);
    window.addEventListener('hashchange', _.manejadorRutas, false);

})(window);