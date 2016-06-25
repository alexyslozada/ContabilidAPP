/* global _ */
(function (window, document, JSON, _){

  var ctrl = {
    form: null,
    divMessage: null,
    init: function () {
      this.divMessage = _.getID('mensaje');
      this.form = _.getID('frmInfMovCuenta').noSubmit().get();
    },
    list: function () {
      var self = this,
          data = new FormData(self.form),
          obj = {
            url: 'inf-mov-cuenta',
            datos: data,
            callback: self.showList
          };
      _.ejecutar(obj);
    },
    showList: function (datos) {
      var data = JSON.parse(datos),
          obj = data.objeto;
      if (data.tipo === _.MSG_CORRECTO) {
        ctrl.showData(obj);
      } else if (data.tipo === _.MSG_NO_AUTENTICADO) {
        window.location.href = 'index.html';
      }
    },
    showData: function (datos) {
      var i = 0,
          max = datos.length,
          cuenta = null,
          table = _.getID('cuerpoTabla').get(),
          header = _.getID('template-header').get(),
          clonHeader = null,
          fragmentHeader = document.createDocumentFragment(),
          fragmentDetail = null,
          num_cuenta = null,
          nom_cuenta = null,
          san_cuenta = null;
  
      // Clear list
      table.innerHTML = '';

      for (; i < max; i++) {
        cuenta = datos[i];
        clonHeader = header.content.cloneNode(true);
        fragmentDetail = document.createDocumentFragment();

        // Get the DOM elements on header of list
        num_cuenta = clonHeader.querySelector('.num-cuenta');
        nom_cuenta = clonHeader.querySelector('.nom-cuenta');
        san_cuenta = clonHeader.querySelector('.san-cuenta');

        // Set text values to elements on header of list.
        num_cuenta.textContent = cuenta.cuenta;
        nom_cuenta.textContent = cuenta.nombre;
        san_cuenta.textContent = cuenta.saldo_anterior.formatNumero();
        
        // Add to fragment
        fragmentHeader.appendChild(clonHeader);
        
        // Add details of movement
        ctrl.showDataDetails(fragmentDetail, cuenta.movimientos, cuenta.saldo_anterior, cuenta.naturaleza);
        fragmentHeader.appendChild(fragmentDetail);
      }
      
      table.appendChild(fragmentHeader);
    },
    showDataDetails: function (fragment, data, saldo, naturaleza) {
      var i = 0,
          max = data.length,
          movimiento = null,
          nuevoSaldo = saldo,
          clonBody = null,
          clonAggregate = null,
          detail = _.getID('template-detail').get(),
          aggregate = _.getID('template-aggregate').get(),
          movFecha = null,
          movCCosto = null,
          movDocumento = null,
          movComentario = null,
          movTercero = null,
          movDebito = null,
          movCredito = null,
          movSaldo = null,
          totalDebitos = 0,
          totalCreditos = 0,
          aggregateAnterior = null,
          aggregateDebitos = null,
          aggregateCreditos = null,
          aggregateSaldo = null;

      for(; i < max; i++) {
        movimiento = data[i];
        clonBody = detail.content.cloneNode(true);
        totalDebitos += movimiento.debito;
        totalCreditos += movimiento.credito;
        
        if (naturaleza === 'D') {
          nuevoSaldo += movimiento.debito - movimiento.credito;
        } else {
          nuevoSaldo += movimiento.credito - movimiento.debito;
        }
        // Get the DOM elements
        movFecha = clonBody.querySelector('.mov-fecha');
        movCCosto = clonBody.querySelector('.mov-centrocosto');
        movDocumento = clonBody.querySelector('.mov-documento');
        movComentario = clonBody.querySelector('.mov-comentario');
        movTercero = clonBody.querySelector('.mov-tercero');
        movDebito = clonBody.querySelector('.mov-debito');
        movCredito = clonBody.querySelector('.mov-credito');
        movSaldo = clonBody.querySelector('.mov-saldo');
        
        // Set text values to elements.
        movFecha.textContent = movimiento.fecha;
        movCCosto.textContent = movimiento.ccosto;
        movDocumento.textContent = movimiento.consecutivo;
        movComentario.textContent = movimiento.comentario;
        movTercero.textContent = movimiento.tercero;
        movDebito.textContent = movimiento.debito.formatNumero();
        movCredito.textContent = movimiento.credito.formatNumero();
        movSaldo.textContent = nuevoSaldo.formatNumero();
        
        fragment.appendChild(clonBody);
      }
      
      // Aggregate information
      clonAggregate = aggregate.content.cloneNode(true);
      aggregateAnterior = clonAggregate.querySelector('.total-anterior');
      aggregateDebitos = clonAggregate.querySelector('.total-debitos');
      aggregateCreditos = clonAggregate.querySelector('.total-creditos');
      aggregateSaldo = clonAggregate.querySelector('.total-saldo');
      
      aggregateAnterior.textContent = saldo.formatNumero();
      aggregateDebitos.textContent = totalDebitos.formatNumero();
      aggregateCreditos.textContent = totalCreditos.formatNumero();
      aggregateSaldo.textContent = nuevoSaldo.formatNumero();
      
      fragment.appendChild(clonAggregate);
      
    }
  };
  
  _.controlador('inf-mov-cuenta', ctrl);
  
})(window, document, JSON, _);