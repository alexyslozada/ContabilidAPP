/* global _ */
'use strict';
(function(){
    var menuppal = _.getID('menu-ppal'),
        menuprincipal = _.getID('menu-principal'),
        links = menuppal.get().getElementsByClassName('menu-ppal__link'),
        i = 0, max = links.length, link = null;

    /* Clic en el menú principal */
    menuprincipal.click(function(){
        menuppal.toggleClass('mostrar-menu-ppal');
    });

    // Se asigna la función de clic a cada item del menú
    for(; i < max; i++){
        link = links[i];
        _.getElement(link).click(function(){
            menuppal.toggleClass('mostrar-menu-ppal');
        });
    }
})();