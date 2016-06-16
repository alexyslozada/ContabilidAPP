package com.ingenio.servlets.tipoidentificacion;

import com.ingenio.dao.DAOTipoIdentificacion;
import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.Paginacion;
import com.ingenio.objetos.TipoIdentificacion;
import com.ingenio.objetos.Usuario;
import com.ingenio.utilidades.Constantes;
import com.ingenio.utilidades.Utilidades;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@MultipartConfig
@WebServlet(name = "STipoIdentificacionListar", urlPatterns = {"/STipoIdentificacionListar"})
public class STipoIdentificacionListar extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(STipoIdentificacionListar.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        HttpSession sesion = request.getSession();
        byte tipo;
        String mensaje;
        String objeto = "";
        Paginacion paginacion = new Paginacion();

        if(Utilidades.get().autenticado(sesion)){
            DAOTipoIdentificacion dao = new DAOTipoIdentificacion();
            Usuario usuario = (Usuario) sesion.getAttribute("credencial");

            String tipoConsulta   = request.getParameter("tipo_consulta");

            String pagina         = request.getParameter("pagina");
            String limite         = request.getParameter("limite");
            String columna_orden  = request.getParameter("columna_orden");
            String tipo_orden     = request.getParameter("tipo_orden");

            short sTipoConsulta, sPagina, sLimite, sColumna_orden;

            sTipoConsulta  = Utilidades.get().parseShort(tipoConsulta, LOG, true);
            sPagina        = Utilidades.get().parseShort(pagina, LOG, false);
            sLimite        = Utilidades.get().parseShort(limite, LOG, false);
            sColumna_orden = Utilidades.get().parseShort(columna_orden, LOG, false);

            paginacion = new Paginacion(sPagina, sLimite, sColumna_orden, tipo_orden);

            try{
                tipo = Constantes.MSG_CORRECTO;
                mensaje = Constantes.MSG_CONSULTA_REALIZADA_TEXT;
                TipoIdentificacion tipoIdentificacion = new TipoIdentificacion();
                switch(sTipoConsulta){
                    case 1:
                        break;
                    case 2:
                        String id = request.getParameter("id");
                        tipoIdentificacion.setIdtipodocumento(Utilidades.get().parseShort(id, LOG, true));
                        break;
                    default:
                        tipo = Constantes.MSG_ERROR;
                        mensaje = Constantes.MSG_CONSULTA_NO_VALIDA_TEXT;
                }
                objeto = dao.listar(sTipoConsulta, tipoIdentificacion, paginacion);
            } catch (ExcepcionGeneral eg){
                Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en STipoIdentificacionListar {0}", new Object[]{eg.getMessage()});
                tipo = Constantes.MSG_ERROR;
                mensaje = eg.getMessage();
            }
        } else {
            tipo = Constantes.MSG_NO_AUTENTICADO;
            mensaje = Constantes.MSG_NO_AUTENTICADO_TEXT;
        }
        try (PrintWriter out = response.getWriter()) {
            out.println(Utilidades.get().respuestaJSON(tipo, mensaje, objeto, paginacion));
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
