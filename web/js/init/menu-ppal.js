/* global _ */
'use strict';
(function(){
    var menuppal = _.getID('menu-ppal'),
        menuprincipal = _.getID('menu-principal'),
        menuppal_ele = menuppal.get(),
        i = 0, max = menuppal_ele.childNodes.length,
        hijo = null, link = null;

    /* Clic en el menú principal */
    menuprincipal.click(function(){
        menuppal.toggleClass('mostrar-menu-ppal');
    });

    // Se asigna la función de clic a cada item del menú
    for(; i < max; i++){
        hijo = menuppal_ele.childNodes[i];
        if(hijo.nodeName.toLowerCase() === 'li' && hijo.hasChildNodes()){
            link = hijo.childNodes[0];
            if(link.nodeName.toLowerCase() === 'a'){
                _.getElement(link).click(function(){
                    menuppal.toggleClass('mostrar-menu-ppal');
                });
            }
        }
    }
})();