package com.ingenio.utilidades;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public final class Utilidades {
    
    private final static Utilidades utilidades = new Utilidades();
    private ServletContext contexto = null;
    private boolean contextoCreado = false;

    private Utilidades(){}

    public static Utilidades get(){
        return utilidades;
    }

    public void setContexto(ServletContext contexto){
        this.contexto = contexto;
    }

    public boolean isContextoCreado(){
        return contextoCreado;
    }
    
    public void setContextoCreado(){
        this.contextoCreado = true;
    }

    public ServletContext getContexto(){
        return this.contexto;
    }

    public void irAPagina(String direccion, HttpServletRequest solicitud,
                            HttpServletResponse respuesta, ServletContext contexto)
            throws ServletException, IOException, IllegalStateException{
        direccion = respuesta.encodeURL(direccion);
        RequestDispatcher despachador = contexto.getRequestDispatcher(direccion);
        despachador.forward(solicitud, respuesta);
    }

    public boolean autenticado(HttpSession sesion)
            throws ServletException, IOException{
        if (sesion == null || sesion.getAttribute("credencial") == null){ /* No autenticado */
          return (false);
        }
        return (true);
    }

    /**
     * Devuelve falso o verdadero si se activó el log en el web.xml
     * @return true si está activado, false si no.
     */
    public boolean isLogServerActivo() {
        if (contexto.getInitParameter("activar_log_server").equals("1")) {
            return true;
        }
        return false;
    }

    public void generaLogServer(Logger logger, Level level, String mensaje, Object[] data) {
        if (isLogServerActivo()) {
            logger.log(level, mensaje, data);
        }
    }
    
    public String respuestaJSON(byte tipo, String mensaje, String objeto){
        StringBuilder sb = new StringBuilder();
        sb.append("{\"tipo\":");
        sb.append(tipo);
        sb.append(", \"mensaje\": \"");
        sb.append(mensaje);
        sb.append("\", \"objeto\": ");
        if(objeto == null || objeto.length() == 0){
            sb.append("\"\"");
        } else {
            sb.append(objeto);
        }
        sb.append("}");
        return sb.toString();
    }
}
