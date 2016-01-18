package com.ingenio.servlets;

import com.ingenio.dao.DAO;
import com.ingenio.utilidades.Utilidades;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class SListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext contexto = sce.getServletContext();
        DAO.getDAO().creaPiscina(contexto);
        Utilidades.get().setLogServerActivo(contexto.getInitParameter("activar_log_server").equals("1"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DAO.getDAO().cierraPiscina();
    }
    
}
