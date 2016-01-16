package com.ingenio.servlets.tercero;

import com.ingenio.dao.DAOTerceros;
import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.Tercero;
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
@WebServlet(name = "STerceroGetXId", urlPatterns = {"/STerceroGetXId"})
public class STerceroGetXId extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(STerceroGetXId.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        HttpSession sesion = request.getSession();
        byte tipo;
        String mensaje;
        String objeto = "";

        if (Utilidades.get().autenticado(sesion)) {
            DAOTerceros dao = new DAOTerceros();
            Usuario usuario = (Usuario) sesion.getAttribute("credencial");

            if (dao.tienePermiso(usuario.getPerfil(), dao.OBJETO, Constantes.CONSULTAR)) {
                String tipoConsulta = request.getParameter("tipoConsulta");
                
                short iTipoConsulta = Utilidades.get().parseShort(tipoConsulta, LOG, true);
                
                Tercero tercero = new Tercero();
                
                tipo = Constantes.MSG_CORRECTO;
                mensaje = Constantes.MSG_CONSULTA_REALIZADA_TEXT;
                switch(iTipoConsulta){
                    case 1:
                        String numero = request.getParameter("numero_identificacion");
                        tercero.setNumero_identificacion(numero);
                        break;
                    case 2:
                        String id = request.getParameter("id");
                        short iId = Utilidades.get().parseShort(id, LOG, true);
                        tercero.setId_tercero(iId);
                        break;
                    default:
                        tipo = Constantes.MSG_ADVERTENCIA;
                        mensaje = Constantes.MSG_CONSULTA_NO_VALIDA_TEXT;
                }
                try{
                    objeto = dao.getXId(iTipoConsulta, tercero);
                } catch (ExcepcionGeneral eg){
                    Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en STerceroGetXId {0}", new Object[]{eg.getMessage()});
                    tipo = Constantes.MSG_ERROR;
                    mensaje = eg.getMessage();
                }
            } else {
                tipo = Constantes.MSG_ADVERTENCIA;
                mensaje = Constantes.MSG_SIN_PERMISO_TEXT;
            }
        } else {
            tipo = Constantes.MSG_NO_AUTENTICADO;
            mensaje = Constantes.MSG_NO_AUTENTICADO_TEXT;
        }
        try (PrintWriter out = response.getWriter()) {
            out.println(Utilidades.get().respuestaJSON(tipo, mensaje, objeto));
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
