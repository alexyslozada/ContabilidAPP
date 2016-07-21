/* global _ */
(function (window, document, JSON, _){
  var ctrl = {
    form: null,
    divMessage: null,
    init: function () {
      this.divMessage = _.getID('mensaje');
      this.form = _.getID('frmBalanceGeneral').noSubmit().get();
    },
    list: function () {
      var self = this,
          data = new FormData(self.form),
          obj = {
            url: 'inf-balance-general',
            datos: data,
            callback: self.showList
          };
      _.ejecutar(obj);
    },
    showList: function (datos) {
      var data = JSON.parse(datos);
      ctrl.divMessage.addClass('no-mostrar');
      switch (data.tipo) {
        case _.MSG_CORRECTO:
          ctrl.showData(data.objeto);
          break;
        case _.MSG_ERROR:
          ctrl.divMessage.delClass('no-mostrar').text(data.mensaje);
          break;
        case _.MSG_NO_AUTENTICADO:
          window.location.href = 'index.html';
          break;
        default:
          ctrl.divMessage.delClass('no-mostrar').text('Error no controlado');
      }
    },
    showData: function (datos) {
      var campos = ['cuenta', 'nombre', 'anterior', 'debitos', 'creditos', 'actual'],
          columnas = ['account-number', 'account-name', 'previus-balance', 'debit-balance', 'credit-balance', 'current-balance'],
          acciones = {};
      _.fillRows('cuerpoTabla', 'template-detail', datos, campos, columnas, acciones);
    }
  };
  
  _.controlador('inf-balance-general', ctrl);
  
})(window, document, JSON, _);