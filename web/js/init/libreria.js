'use strict';
(function (window, document) {
    function inicio() {
        var marco = null,
            vistaNoEncontrado = null,
            rutas = {},
            controladores = {},
            ctrl = null,
            singleton = {},
            libreria = {
                MSG_CORRECTO: 2,
                MSG_ERROR: 1,
                MSG_ADVERTENCIA: 3,
                MSG_NO_AUTENTICADO: 4,
                getID: function (id) {
                    var clone = {elemento: document.getElementById(id)};
                    clone = this.extender(clone, this);
                    return clone;
                },
                getElement: function (ele) {
                    var clone = {elemento: ele};
                    clone = this.extender(clone, this);
                    return clone;
                },
                get: function () {
                    return this.elemento;
                },
                addClass: function (clase) {
                    this.elemento.classList.add(clase);
                    return this;
                },
                click: function (funcion) {
                    this.elemento.addEventListener('click', funcion, false);
                    return this;
                },
                delClass: function (clase) {
                    this.elemento.classList.remove(clase);
                    return this;
                },
                noSubmit: function () {
                    this.elemento.addEventListener('submit', function (e) {
                        e.preventDefault();
                    }, false);
                    return this;
                },
                innerHTML: function (contenido) {
                    this.elemento.innerHTML = contenido;
                    return this;
                },
                setValue: function (valor) {
                    this.elemento.value = valor;
                    return this;
                },
                text: function (contenido) {
                    this.elemento.textContent = contenido;
                    return this;
                },
                toggleClass: function (clase) {
                    this.elemento.classList.toggle(clase);
                    return this;
                },
                value: function () {
                    return this.elemento.value;
                },
                getSingleton: function () {
                    return singleton;
                },
                setSingleton: function (objeto) {
                    singleton = objeto;
                },
                onEnterSiguiente: function(){
                    this.elemento.addEventListener('keypress', function(e){
                        var indice = parseInt(e.target.getAttribute('tabindex'),10),
                            siguiente;
                        if (e.keyCode === 13){
                            e.preventDefault();
                            siguiente = e.target.parentNode.parentNode.querySelector('[tabindex="'+(indice+1)+'"]');
                            siguiente.focus();
                            if(siguiente.select){
                                siguiente.select();
                            }
                        }
                    }, false);
                    return this;
                },
                llenarFilas: function (cuerpoTabla, template, datos, campos, acciones) {
                    var cuerpo = document.getElementById(cuerpoTabla),
                            fila = document.getElementById(template),
                            frag = document.createDocumentFragment(),
                            i = 0, j = 0, maxDatos = datos.length, registro = {},
                            clon = null, maxCampos = campos.length, campo = null,
                            accion = null, btnAccion = null;

                    cuerpo.textContent = '';
                    for (; i < maxDatos; i = i + 1) {
                        registro = datos[i];
                        clon = fila.content.cloneNode(true);
                        for (; j < maxCampos; j = j + 1) {
                            campo = clon.querySelector('.' + campos[j]);
                            if (typeof registro[campos[j]] !== 'boolean') {
                                campo.textContent = registro[campos[j]];
                            } else {
                                campo.textContent = registro[campos[j]] ? 'Si' : 'No';
                            }
                        }
                        j = 0;

                        /**
                         * Accines a realizar
                         * El objeto debe tener la siguiente estructura
                         * {'nombre': {'clase', 'funcion'}}
                         */
                        for (accion in acciones) {
                            btnAccion = clon.querySelector(acciones[accion].clase);
                            btnAccion.dataset.idu = registro['id'];
                            btnAccion.addEventListener('click', acciones[accion].funcion, false);
                        }

                        frag.appendChild(clon);
                    }
                    cuerpo.appendChild(frag);
                },
                paginar: function () {
                    var input = this.getID('pagina'),
                            pag = parseInt(input.value(), 10),
                            lim = parseInt(this.getID('limite').value(), 10);
                    if (pag <= this.getCtrl().total_paginas) {
                        if (pag > 0) {
                            this.getCtrl().pagina = pag;
                        } else {
                            this.getCtrl().pagina = 1;
                        }
                    } else {
                        this.getCtrl().pagina = this.getCtrl().total_paginas;
                    }
                    input.setValue(this.getCtrl().pagina);
                    if (lim > 0) {
                        this.getCtrl().limite = lim;
                    } else {
                        this.getCtrl().limite = 1;
                        this.getID('limite').setValue(1);
                    }
                    this.getCtrl().columna_orden = parseInt(this.getID('columna_orden').value(), 10);
                    this.getCtrl().tipo_orden = this.getID('tipo_orden').value();
                },
                paginar_paginas: function (accion) {
                    switch (accion) {
                        case 'siguiente':
                            this.getID('pagina').setValue(this.getCtrl().pagina + 1);
                            break;
                        case 'anterior':
                            this.getID('pagina').setValue(this.getCtrl().pagina - 1);
                            break;
                        case 'primer':
                            this.getID('pagina').setValue(1);
                            break;
                        case 'ultimo':
                            this.getID('pagina').setValue(this.getCtrl().total_paginas);
                    }
                },
                paginacion: function (pagina, limite, columna_orden, tipo_orden) {
                    var data = new FormData();
                    data.append("pagina", pagina);
                    data.append("limite", limite);
                    data.append("columna_orden", columna_orden);
                    data.append("tipo_orden", tipo_orden);
                    return data;
                },
                /**
                 * poblarSelect permite poblar la información de un select
                 * @param {JSON} datos Objeto JSON que contiene los datos
                 * @param {string} tabla Nombre de la tabla que contiene los datos. Es el nombre que está en el select como AS ...
                 * @param {string} id Nombre de la columna que identifica el ID de la tabla y será el value del select
                 * @param {string} campo Nombre del campo que se desea mostrar en el select
                 * @param {string} select ID del select que se va a poblar
                 * @param {boolean} esInterno Identifica si la información que viene en el JSON tiene paginación o no.
                 * @returns {void} Pobla el select con la información obtenida.
                 */
                poblarSelect: function (datos, tabla, id, campo, select, esInterno) {
                    var data = JSON.parse(datos),
                            fragmento = document.createDocumentFragment(),
                            lista = null, i = 0, max = 0, opcion = null;
                    if (data.tipo === this.MSG_CORRECTO) {
                        if (esInterno) {
                            lista = data.objeto[tabla];
                        } else {
                            lista = data.objeto;
                        }
                        max = lista.length;
                        for (; i < max; i = i + 1) {
                            opcion = document.createElement('option');
                            opcion.setAttribute('value', lista[i][id]);
                            opcion.textContent = lista[i][campo];
                            fragmento.appendChild(opcion);
                        }
                        this.getID(select).get().appendChild(fragmento);
                    }
                },
                controlador: function (nombre, controller) {
                    controladores[nombre] = {'controlador': controller};
                },
                getCtrl: function () {
                    if (arguments.length === 0) {
                        return ctrl;
                    } else {
                        return controladores[arguments[0]].controlador;
                    }
                },
                enrutar: function (id) {
                    marco = document.getElementById(id);
                    return this;
                },
                ruta: function (url, plantilla, controller, carga) {
                    rutas[url] = {'plantilla': plantilla,
                        'controlador': controller,
                        'carga': carga};
                    return this;
                },
                cargaVista: function (destino) {
                    this.ajax({metodo: 'get',
                        url: destino.plantilla
                    }).then(function (data) {
                        marco.innerHTML = data;
                        if (typeof (destino.carga) === 'function') {
                            destino.carga();
                        }
                    }, function (error) {
                        console.log(error);
                    });
                },
                manejadorRutas: function () {
                    var hash = window.location.hash.substring(1) || '/',
                            destino = rutas[hash],
                            _this = window._;

                    if (destino && destino.plantilla) {

                        if (destino.controlador) {
                            ctrl = controladores[destino.controlador].controlador;
                        }

                        _this.cargaVista(destino);
                    } else {
                        destino = {};
                        destino.plantilla = vistaNoEncontrado;
                        _this.cargaVista(destino);
                    }
                },
                noEncontrado: function (archivo) {
                    vistaNoEncontrado = archivo;
                    return this;
                },
                ajax: function (objeto) {
                    return new Promise(function (resolver, rechazar) {
                        var metodo = objeto.metodo || 'post',
                                url = objeto.url || '',
                                datos = objeto.datos || null,
                                xhr = new XMLHttpRequest();

                        xhr.open(metodo, url, true);
                        xhr.addEventListener('load', function () {
                            if (this.status === 200) {
                                resolver(this.responseText);
                            } else {
                                rechazar(Error('Error al cargar la información: ' + this.statusText));
                            }
                        }, false);
                        xhr.addEventListener('error', function () {
                            rechazar(Error('Hubo un error en la red'));
                        }, false);
                        xhr.send(datos);
                    });
                },
                /* Es un FACADE del ajax */
                ejecutar: function (obj) {
                    this.ajax({url: obj.url, datos: obj.datos})
                            .then(function (datos) {
                                obj.callback(datos);
                            }, function (error) {
                                console.log(error);
                            });
                },
                extender: function (out) {
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

        /**
         * Da formato de moneda a los números
         * @param {int} decimales Cantidad de decimales que tiene el número
         * @param {char} digitos Separador de decimales
         * @param {char} temporal Separador de miles
         * @param {boolean} negativo Indica si el número negativo se envuelve (false) o se muestra el signo menos (true)
         * @returns {String} Numero en tipo Cadena de texto formateada
         */
        Number.prototype.formatNumero = function (decimales, negativo, digitos, temporal) {
            var n = this,
                c = isNaN(decimales = Math.abs(decimales)) ? 0 : decimales,
                d = typeof digitos === 'undefined' ? "." : digitos,
                t = typeof temporal === 'undefined' ? "," : temporal,
                si = n < 0 ? "-" : "",
                sf = "",
                i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "",
                j = (j = i.length) > 3 ? j % 3 : 0;
            if(!negativo){
                si = n < 0 ? "(" : "";
                sf = n < 0 ? ")" : "";
            }
            return si + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "") + sf;
        };
        return libreria;
    }
    ;

    if (typeof window.libreria === "undefined") {
        window.libreria = window._ = inicio();
    } else {
        console.log("Ya está llamada");
    }
})(window, document);