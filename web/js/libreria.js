'use strict';
(function(window, document){
    function inicio(){
        var elemento = null,
            marco    = null,
            rutas    = {},
            controladores = {},
            ctrl     = null,
            libreria = {
                getID: function(id){
                    elemento = document.getElementById(id);
                    return this;
                },
                click: function(funcion){
                    elemento.addEventListener('click', funcion, false);
                    return this;
                },
                text: function(contenido){
                    elemento.textContent = contenido;
                    return this;
                },
                innerHTML: function(contenido){
                    elemento.innerHTML = contenido;
                    return this;
                },
                get: function(){
                    return elemento;
                },
                noSubmit: function(){
                    elemento.addEventListener('submit', function(e){e.preventDefault();}, false);
                    return this;
                },
                controlador: function(nombre, controller){
                    controladores[nombre] = {'controlador': controller};
                },
                getCtrl: function(){
                    return ctrl;
                },
                enrutar: function(){
                    marco = elemento;
                    return this;
                },
                ruta: function(url, plantilla, controller, carga){
                    rutas[url] = {'plantilla': plantilla, 'controlador': controller, 'carga': carga};
                    return this;
                },
                manejadorRutas: function(){
                    var hash = window.location.hash.substring(1) || '/',
                        destino = rutas[hash],
                        xhr = new XMLHttpRequest();

                    if(destino && destino.plantilla){

                            if(destino.controlador){
                                    ctrl = controladores[destino.controlador].controlador;
                            }

                            this.ajax({metodo: 'get', url: destino.plantilla, funcion: function(){
                                    marco.innerHTML = this.responseText;
                                    setTimeout(function(){
                                        if(typeof(destino.carga) === 'function'){
                                            destino.carga();
                                        }
                                    }, 300);
                            }});
                        /*
                            xhr.addEventListener('load', function(){
                                    marco.innerHTML = this.responseText;
                                    setTimeout(function(){
                                        if(typeof(destino.carga) === 'function'){
                                            destino.carga();
                                        }
                                    }, 300);
                            }, false);

                            xhr.open('get', destino.plantilla, true);
                            xhr.send(null);
                        */
                    } else {
                            window.location.hash = '#/';
                    }
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
                }
            };
        return libreria;
    };
    
    if(typeof window.libreria === "undefined"){
        window.libreria = inicio();
    } else {
        console.log("Ya está llamada");
    }
})(window, document);