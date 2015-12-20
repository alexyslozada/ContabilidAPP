/* global _ */
(function(){
    
    var filaBackup = null,
        objetosxperfilCtrl = {
        cargaPermisos: function(){
            var id = _.getSingleton().idPerfil,
                data = new FormData();
            data.append('id', id);
            _.ajax({
                url: 'SObjXPerfilListar',
                datos: data,
                funcion: cargarDatos
            });
        }
    };
    
    function actualizado(){
        var data = JSON.parse(this.responseText);
        _.getID('mensaje').delClass('no-mostrar').text(data.mensaje);
        if(data.tipo === _.MSG_CORRECTO){
            actualizaTabla();
        } else if(data.tipo === _.MSG_NO_AUTENTICADO){
            window.location.href = 'index.html';
        }
    };

    function actualizaTabla(){
        var campos = filaBackup.cells,
            i = 2, max = 5;
        for(; i <= max; i = i + 1){
            campos[i].innerHTML = campos[i].querySelector('input').checked?'Si':'No';
        }
    };

    function cargarDatos(){
        var data = JSON.parse(this.responseText),
            campos = ['id', 'oxp_objeto', 'oxp_insertar', 'oxp_modificar', 'oxp_borrar', 'oxp_consultar'],
            acciones = {'editar': 
                            {
                                clase: '.editar',
                                funcion: function(e){
                                            var btn = e.target;
                                            e.preventDefault();
                                            if(btn.dataset.accion === 'editar'){
                                                activaCheckbox(btn);
                                            } else {
                                                grabaCambios(btn);
                                            }
                                        }
                            },
                        'cancelar':
                            {
                                clase: '.cancelar',
                                funcion: deshacer
                            }
                        };
        if(data.tipo === _.MSG_CORRECTO){
            _.getID('perfil').setValue(data.objeto.nombre);
            _.getID('activo').get().checked = data.objeto.activo;
            _.llenarFilas('cuerpoTabla', 'plantilla', data.objeto.oxp, campos, acciones);
        } else {
            _.getID('mensaje').delClass('no-mostrar').innerHTML(data.mensaje);
        }
    };

    function activaCheckbox(btn){
        var btnX  = btn.nextSibling.nextSibling,
            fila  = btn.parentNode.parentNode,
            campos = fila.cells, i = 2, max = campos.length,
            check = null, label = null, activo = false;
        filaBackup = fila.cloneNode(true);
        btn.dataset.accion = 'grabar';
        btn.textContent    = 'Grabar';
        btn.style.color    = 'green';
        btnX.classList.remove('no-mostrar');
        for(; i < max - 1; i = i + 1){
            activo = campos[i].textContent === 'Si'?true:false;
            check = document.createElement('input');
            check.setAttribute('type', 'checkbox');
            check.classList.add('no-mostrar');
            check.setAttribute('name', campos[i].className);
            check.setAttribute('id', campos[i].className);
            label = document.createElement('label');
            label.setAttribute('for', campos[i].className);
            label.classList.add('label-check');
            check.checked = activo;
            campos[i].innerHTML = '';
            campos[i].appendChild(check);
            campos[i].appendChild(label);
        }
    };

    function deshacer(e){
        var btn = e.target,
            btnGr = btn.previousElementSibling,
            filaActual = btn.parentNode.parentNode,
            campos = filaActual.cells,
            camposBackup = filaBackup.cells,
            i = 2, max = campos.length;
        e.preventDefault();
        btn.classList.add('no-mostrar');
        cambiaBoton(btnGr);
        for(; i < max - 1; i = i + 1){
            campos[i].innerHTML = camposBackup[i].innerHTML;
        }
    };
    
    function grabaCambios(btn){
        var btnX  = btn.nextSibling.nextSibling,
            fila  = btn.parentNode.parentNode,
            campos = fila.cells,
            data  = new FormData();
    
        btnX.classList.add('no-mostrar');
        cambiaBoton(btn);
        /* 
         * Para no volver a listar la tabla, se guarda la fila
         * y no se clona para que se pueda trabajar.
         */
        filaBackup = fila;
        data.append('id', campos[0].textContent);
        data.append('insertar', campos[2].querySelector('#oxp_insertar').checked);
        data.append('modificar', campos[3].querySelector('#oxp_modificar').checked);
        data.append('borrar', campos[4].querySelector('#oxp_borrar').checked);
        data.append('consultar', campos[5].querySelector('#oxp_consultar').checked);
        _.ajax({
            url: 'SObjXPerfilActualizar',
            datos: data,
            funcion: actualizado
        });
    };
    
    function cambiaBoton(btn){
        btn.dataset.accion = 'editar';
        btn.textContent = 'Editar';
        btn.style.color = 'steelblue';
    };

    _.controlador('objetosxperfil', objetosxperfilCtrl);

})();