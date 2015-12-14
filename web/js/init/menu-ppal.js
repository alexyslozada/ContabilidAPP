'use strict';
(function(){
	var menuppal     = libreria.getID('menu-ppal'),
			mpl_perfiles = libreria.getID('mpl-perfiles'),
			menuppal_ele = menuppal.get(),
			i = 0, max = menuppal_ele.childNodes.length,
	    hijo = null, link = null;

	/* Clic en el menú principal */
	libreria.getID('menu-principal').click(function(){
		menuppal.toggleClass('mostrar-menu-ppal');
	});

	// Se asigna la función de clic a cada item del menú
	for(; i < max; i++){
		hijo = menuppal_ele.childNodes[i];
		if(hijo.hasChildNodes()){
			link = hijo.childNodes[0];
			if(link.nodeName.toLowerCase() === 'a'){
				libreria.getElement(link).click(function(){
					menuppal.toggleClass('mostrar-menu-ppal');
				});
			}
		}
	}

})();