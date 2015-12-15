'use strict';
(function(window, document){
  function inicio(){
    var marco    = null,
        vistaNoEncontrado = null,
        rutas    = {},
        controladores = {},
        ctrl     = null,
        singleton = {},
        libreria = {
          MSG_CORRECTO: 2,
          MSG_ERROR: 1,
          MSG_ADVERTENCIA: 3,
          MSG_NO_AUTENTICADO: 4,
          getID: function(id){
            var clone = {elemento: document.getElementById(id)};
            clone = this.extender(clone, this);
            return clone;
          },
          getElement: function(ele){
            var clone = {elemento: ele};
            clone = this.extender(clone, this);
            return clone;
          },
          get: function(){
            return this.elemento;
          },
          addClass: function(clase){
            this.elemento.classList.add(clase);
            return this;
          },
          click: function(funcion){
            this.elemento.addEventListener('click', funcion, false);
            return this;
          },
          delClass: function(clase){
            this.elemento.classList.remove(clase);
            return this;
          },
          innerHTML: function(contenido){
            this.elemento.innerHTML = contenido;
            return this;
          },
          noSubmit: function(){
            this.elemento.addEventListener('submit', function(e){e.preventDefault();}, false);
            return this;
          },
          setValue: function(valor){
              this.elemento.value = valor;
              return this;
          },
          text: function(contenido){
            this.elemento.textContent = contenido;
            return this;
          },
          toggleClass: function(clase){
            this.elemento.classList.toggle(clase);
            return this;
          },
          value: function(){
            return this.elemento.value;
          },
          getSingleton: function(){
              return singleton;
          },
          setSingleton: function(objeto){
              singleton = objeto;
          },
          llenarFilas: function(cuerpoTabla, template, datos, campos, acciones){
            var cuerpo = document.getElementById(cuerpoTabla),
                fila = document.getElementById(template),
                frag = document.createDocumentFragment(),
                i = 0, j = 0, maxDatos = datos.length, registro = {},
                clon = null, maxCampos = campos.length, campo = null,
                eliminar = null, //actualizar = null,
                self = this, accion = null, btnAccion = null;

            cuerpo.innerHTML = '';
            for(; i < maxDatos; i = i + 1){
                registro = datos[i];
                clon = fila.content.cloneNode(true);
                for(; j < maxCampos; j = j + 1){
                    campo = clon.querySelector('.'+campos[j]);
                    if(typeof registro[campos[j]] !== 'boolean'){
                        campo.textContent = registro[campos[j]];
                    } else {
                        campo.textContent = registro[campos[j]]?'Si':'No';
                    }
                }
                j = 0;

                /**
                 * Accines a realizar
                 * El objeto debe tener la siguiente estructura
                 * {'nombre': {'clase', 'funcion'}}
                 */ 
                for(accion in acciones){
                    btnAccion = clon.querySelector(acciones[accion].clase);
                    btnAccion.dataset.idu = registro['id'];
                    btnAccion.addEventListener('click', acciones[accion].funcion, false);
                }

                frag.appendChild(clon);
            }
            cuerpo.appendChild(frag);
          },
          controlador: function(nombre, controller){
            controladores[nombre] = {'controlador': controller};
          },
          getCtrl: function(){
            return ctrl;
          },
          enrutar: function(id){
            marco = document.getElementById(id);
            return this;
          },
          ruta: function(url, plantilla, controller, carga){
            rutas[url] = {'plantilla': plantilla,
                          'controlador': controller,
                          'carga': carga};
            return this;
          },
          cargaVista: function(destino){
            var _this = this;
            _this.ajax({metodo: 'get',
                        url: destino.plantilla,
                        funcion: function(){
                          marco.innerHTML = this.responseText;
                          setTimeout(function(){
                              if(typeof(destino.carga) === 'function'){
                                destino.carga();
                              }
                          }, 300);
                        }
            });
          },
          manejadorRutas: function(){
            var hash = window.location.hash.substring(1) || '/',
                destino = rutas[hash],
                _this = window._;

            if(destino && destino.plantilla){

              if(destino.controlador){
                ctrl = controladores[destino.controlador].controlador;
              }

              _this.cargaVista(destino);
            } else {
              destino = {};
              destino.plantilla = vistaNoEncontrado;
              _this.cargaVista(destino);
            }
          },
          noEncontrado: function(archivo){
            vistaNoEncontrado = archivo;
            return this;
          },
          ajax: function(objeto){
            var metodo = objeto.metodo || 'post',
                url    = objeto.url || '',
                datos  = objeto.datos || null,
                callback = objeto.funcion,
                xhr    = new XMLHttpRequest();

            xhr.addEventListener('load', callback, false);
            xhr.open(metodo, url, true);
            xhr.send(datos);
          },
          extender: function(out) {
            out = out || {};

            for (var i = 1; i < arguments.length; i++) {
              var obj = arguments[i];

              if (!obj)
                continue;

              for (var key in obj) {
                if (obj.hasOwnProperty(key)) {
                  if (typeof obj[key] === 'object')
                    this.extender(out[key], obj[key]);
                  else
                    out[key] = obj[key];
                }
              }
            }
            return out;
          }
      };
    return libreria;
  };
    
  if(typeof window.libreria === "undefined"){
    window.libreria = window._ = inicio();
  } else {
    console.log("Ya está llamada");
  }
})(window, document);