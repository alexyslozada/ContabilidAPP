/* global _ */
(function (window, JSON, _) {
  var docContableCtrl = {
    formulario: null,
    divMensaje: null,
    inicio: function () {
      this.divMensaje = _.getID('mensaje');
    },
    inicio_crear: function () {
      this.formulario = _.getID('frmCrearDocumento').noSubmit().get();
      this.inicio();
    },
    inicio_actualizar: function () {
      this.formulario = _.getID('frmActualizarDocumento').noSubmit().get();
      this.inicio();
    },
    confirmaActualizar: function (id) {
      if (confirm('Desea actualizar este documento?')) {
        this.preparaActualizar(id);
      }
    },
    confirmaEliminar: function (id) {
      if (confirm('Desea eliminar este documento?')) {
        this.eliminar(id);
      }
    },
    ejecutado: function (datos) {
      var data = JSON.parse(datos);

      docContableCtrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
      if (data.tipo === _.MSG_CORRECTO) {
        docContableCtrl.formulario.reset();
      } else if (data.tipo === _.MSG_NO_AUTENTICADO) {
        window.location.href = 'index.html';
      }
    },
    actualizar: function () {
      var self = this,
          obj = {
            datos: new FormData(self.formulario),
            url: 'SDocumentoContableActualizar',
            callback: self.ejecutado
          };
      _.ejecutar(obj);
    },
    crear: function () {
      var self = this,
              obj = {
                datos: new FormData(self.formulario),
                url: 'SDocumentoContableCrear',
                callback: self.ejecutado
              };
      _.ejecutar(obj);
    },
    eliminar: function (id) {
      var data = new FormData(), obj = {};
      data.append('id', id);
      obj.datos = data;
      obj.url = 'SDocumentoContableEliminar';
      obj.callback = this.eliminado;
      _.ejecutar(obj);
    },
    eliminado: function (datos) {
      var data = JSON.parse(datos);
      docContableCtrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
      if (data.tipo === _.MSG_CORRECTO) {
        docContableCtrl.listar(docContableCtrl.cargarTabla);
      } else if (data.tipo === _.MSG_NO_AUTENTICADO) {
        window.location.href = 'index.html';
      }
    },
    listar: function (callback) {
      var obj = {};

      obj.url = 'SDocumentoContableListar';
      obj.callback = callback;
      _.ejecutar(obj);
    },
    preparaActualizar: function (id) {
      var data = new FormData(), obj = {};

      data.append('tipoConsulta', 2);
      data.append('id', id);
      obj.datos = data;
      obj.url = 'SDocumentoContableGetXId';
      obj.callback = this.muestraActualizar;
      _.ejecutar(obj);
    },
    muestraActualizar: function (datos) {
      var data = JSON.parse(datos),
              objeto = data.objeto,
              departamentos;
      if (data.tipo === _.MSG_CORRECTO) {
        window.location.hash = '#/documento-contable/actualizar';
        setTimeout(function () {
          departamentos = _.getID('depto_residencia').get();
          _.getID('id').setValue(objeto.id);
          _.getID('abreviatura').setValue(objeto.abreviatura);
          _.getID('documento').setValue(objeto.documento);
        }, 300);
      } else if (data.tipo === _.MSG_NO_AUTENTICADO) {
        window.location.href = 'index.html';
      }
    },
    cargarTabla: function (datos) {
      var data = JSON.parse(datos), campos = [], acciones = {};
      if (data.tipo === _.MSG_CORRECTO) {
        campos = ['abreviatura', 'documento', 'consecutivo'],
                acciones = {
                  eliminar: {clase: '.eliminar',
                    funcion: function (e) {
                      e.preventDefault();
                      docContableCtrl.confirmaEliminar(e.target.dataset.idu);
                    }
                  },
                  actualizar: {clase: '.actualizar',
                    funcion: function (e) {
                      e.preventDefault();
                      docContableCtrl.confirmaActualizar(e.target.dataset.idu);
                    }
                  }
                };

        _.llenarFilas('cuerpoTabla', 'plantilla', data.objeto, campos, acciones);
      } else if (data.tipo === _.MSG_ADVERTENCIA || data.tipo === _.MSG_ERROR) {
        docContableCtrl.divMensaje.delClass('no-mostrar').text(data.mensaje);
      } else if (data.tipo === _.MSG_NO_AUTENTICADO) {
        window.location.href = 'index.html';
      }
    }
  };

  _.controlador('documentoContable', docContableCtrl);
})(window, JSON, _);