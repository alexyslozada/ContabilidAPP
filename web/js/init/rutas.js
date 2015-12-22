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
                  _.getCtrl().listar(false);
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
                  _.getCtrl().listar();
              })
        .ruta('/usuarios-crear',
              'vistas/usuarios/crear.html',
              'usuarios',
              function(){
                  var select   = _.getID('perfil').get();
                  _.getCtrl('perfiles').listar(true);
                  setTimeout(function(){
                    var opcion = null, perfiles = [],
                        fragmento = document.createDocumentFragment(),
                        i = 0, max = 0;
                    perfiles = _.getSingleton();
                    max = perfiles.length;
                    for(; i < max; i = i + 1){
                        opcion = document.createElement('option');
                        opcion.setAttribute('value', perfiles[i].id);
                        opcion.textContent = perfiles[i].nombre;
                        fragmento.appendChild(opcion);
                    }
                    select.appendChild(fragmento);
                }, 300);
              });

    window.addEventListener('load', _.manejadorRutas, false);
    window.addEventListener('hashchange', _.manejadorRutas, false);

})(window, document, _);