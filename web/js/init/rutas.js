/* global _ */
'use strict';
(function (window, _) {
    _.enrutar('vista')
            .noEncontrado('404.html')
            .ruta('/', 'vistas/index.html')
            .ruta('/perfiles',
                    'vistas/perfiles/listar.html',
                    'perfiles',
                    function () {
                        var ctrl = _.getCtrl();
                        ctrl.listar(ctrl.cargaLista);
                    })
            .ruta('/perfiles-crear',
                    'vistas/perfiles/crear.html',
                    'perfiles',
                    function () {
                        _.getID('frmCrearPerfil').noSubmit();
                    })
            .ruta('/perfiles-actualizar',
                    'vistas/perfiles/actualizar.html',
                    'perfiles',
                    function () {
                        _.getID('frmActualizarPerfil').noSubmit();
                    })
            .ruta('/perfiles-permisos',
                    'vistas/perfiles/permisos.html',
                    'objetosxperfil',
                    function () {
                        _.getCtrl().cargaPermisos();
                    })
            .ruta('/usuarios',
                    'vistas/usuarios/listar.html',
                    'usuarios',
                    function () {
                        var ctrl = _.getCtrl();
                        ctrl.listar(ctrl.cargaLista);
                    })
            .ruta('/usuarios-crear',
                    'vistas/usuarios/crear.html',
                    'usuarios',
                    function () {
                        var ctrl = _.getCtrl();
                        _.getID('frmCrearUsuario').noSubmit();
                        ctrl('perfiles').listar(ctrl.poblarPerfiles);
                    })
            .ruta('/usuarios-actualizar',
                    'vistas/usuarios/actualizar.html',
                    'usuarios',
                    function () {
                        var ctrl = _.getCtrl();
                        _.getID('frmActualizarUsuario').noSubmit();
                        ctrl('perfiles').listar(ctrl.poblarPerfiles);
                    })
            .ruta('/tipo-identificacion',
                    'vistas/tipo_identificacion/listar.html',
                    'tipoIdentificacion',
                    function () {
                        _.getCtrl().inicio_listar();
                    })
            .ruta('/tipo-identificacion-crear',
                    'vistas/tipo_identificacion/crear.html',
                    'tipoIdentificacion',
                    function () {
                        _.getCtrl().inicio_crear();
                    })
            .ruta('/tipo-identificacion-actualizar',
                    'vistas/tipo_identificacion/actualizar.html',
                    'tipoIdentificacion',
                    function () {
                        _.getCtrl().inicio_actualizar();
                    })
            .ruta('/tipo-funcionario',
                    'vistas/tipo_funcionario/listar.html',
                    'tipoFuncionario',
                    function () {
                        _.getCtrl().inicio_listar();
                    })
            .ruta('/tipo-funcionario-crear',
                    'vistas/tipo_funcionario/crear.html',
                    'tipoFuncionario',
                    function () {
                        _.getCtrl().inicio_crear();
                    })
            .ruta('/tipo-funcionario-actualizar',
                    'vistas/tipo_funcionario/actualizar.html',
                    'tipoFuncionario',
                    function () {
                        _.getCtrl().inicio_actualizar();
                    })
            .ruta('/empresa',
                    'vistas/empresa/admin.html',
                    'empresa',
                    function () {
                        _.getCtrl().inicio();
                    })
            .ruta('/empresa-funcionarios',
                    'vistas/empresa_funcionario/listar.html',
                    'empresaFuncionario',
                    function () {
                        var ctrl = _.getCtrl();
                        ctrl.listar(ctrl.cargarTabla);
                    }
            )
            .ruta('/empresa-funcionarios-crear',
                    'vistas/empresa_funcionario/crear.html',
                    'empresaFuncionario',
                    function () {
                        _.getCtrl().inicio_crear();
                    }
            )
            .ruta('/empresa-funcionarios-actualizar',
                    'vistas/empresa_funcionario/actualizar.html',
                    'empresaFuncionario',
                    function () {
                        _.getCtrl().inicio_actualizar();
                    }
            )
            .ruta('/cuentas-puc',
                  'vistas/cuentaspuc/listar.html',
                  'cuentaspuc',
                  function(){
                      var ctrl = _.getCtrl();
                      ctrl.listar(ctrl.cargarTabla);
                  }
            )
            .ruta('/cuentas-puc-crear',
                  'vistas/cuentaspuc/crear.html',
                  'cuentaspuc',
                  function(){
                      _.getCtrl().inicio_crear();
                  }
            )
            .ruta('/cuentas-puc-actualizar',
                  'vistas/cuentaspuc/actualizar.html',
                  'cuentaspuc',
                  function(){
                      _.getCtrl().inicio_actualizar();
                  }
            )
            .ruta('/centros-costo',
                  'vistas/centroscosto/listar.html',
                  'centroscosto',
                  function(){
                      _.getCtrl().inicio();
                      _.getCtrl().listar(_.getCtrl().cargarTabla);
                  }
            )
            .ruta('/centros-costo/crear',
                  'vistas/centroscosto/crear.html',
                  'centroscosto',
                  function(){
                      _.getCtrl().inicio_crear();
                  }
            )
            .ruta('/centros-costo/actualizar',
                  'vistas/centroscosto/actualizar.html',
                  'centroscosto',
                  function(){
                      _.getCtrl().inicio_actualizar();
                  }
            );

    window.addEventListener('load', _.manejadorRutas, false);
    window.addEventListener('hashchange', _.manejadorRutas, false);

})(window, _);