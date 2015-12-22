/* global libreria */
/* global _ */
(function(window, document, _){
    _.enrutar('vista')
        .noEncontrado('404.html')
        .ruta('/', 'vistas/index.html')
        .ruta('/perfiles',
              'vistas/perfiles/listar.html',
              'perfiles',
              function(){
                  var ctrl = _.getCtrl();
                  ctrl.listar(ctrl.cargaLista);
              })
        .ruta('/perfiles-crear',
              'vistas/perfiles/crear.html',
              'perfiles',
              function(){
                  _.getID('frmCrearPerfil').noSubmit();
              })
        .ruta('/perfiles-actualizar',
              'vistas/perfiles/actualizar.html',
              'perfiles',
              function(){
                  _.getID('frmActualizarPerfil').noSubmit();
              })
        .ruta('/perfiles-permisos',
              'vistas/perfiles/permisos.html',
              'objetosxperfil',
              function(){
                  _.getCtrl().cargaPermisos();
              })
        .ruta('/usuarios',
              'vistas/usuarios/listar.html',
              'usuarios',
              function(){
                  var ctrl = _.getCtrl();
                  ctrl.listar(ctrl.cargaLista);
              })
        .ruta('/usuarios-crear',
              'vistas/usuarios/crear.html',
              'usuarios',
              function(){
                  _.getID('frmCrearUsuario').noSubmit();
                  _.getCtrl('perfiles').listar(_.getCtrl().poblarPerfiles);
              })
        .ruta('/usuarios-actualizar',
              'vistas/usuarios/actualizar.html',
              'usuarios',
              function(){
                  _.getID('frmActualizarUsuario').noSubmit();
                  _.getCtrl('perfiles').listar(_.getCtrl().poblarPerfiles);
              });

    window.addEventListener('load', _.manejadorRutas, false);
    window.addEventListener('hashchange', _.manejadorRutas, false);

})(window, document, _);