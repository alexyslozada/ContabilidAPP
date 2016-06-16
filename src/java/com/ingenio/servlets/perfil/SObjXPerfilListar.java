package com.ingenio.servlets.perfil;

import com.ingenio.dao.DAOObjetosXPerfil;
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
@WebServlet(name = "SObjXPerfilListar", urlPatterns = {"/SObjXPerfilListar"})
public class SObjXPerfilListar extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(SObjXPerfilListar.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        HttpSession sesion = request.getSession();
        byte tipo;
        String mensaje;
        String objeto = "";

        if(Utilidades.get().autenticado(sesion)){
            DAOObjetosXPerfil dao = new DAOObjetosXPerfil();
            Usuario usuario = (Usuario) sesion.getAttribute("credencial");

            if(dao.tienePermiso(usuario.getPerfil(), dao.OBJETO, Constantes.CONSULTAR)){

                String idPerfil = request.getParameter("id");
                short sIdPerfil = 0;
                try{
                    sIdPerfil = Short.parseShort(idPerfil);
                    tipo = Constantes.MSG_CORRECTO;
                    mensaje = "Consulta realizada";
                } catch (NumberFormatException nfe){
                    tipo = Constantes.MSG_ERROR;
                    mensaje = "El perfil debe ser numérico";
                    Utilidades.get().generaLogServer(LOG, Level.WARNING, "Error al hacer parseShort de {0}", new Object[]{idPerfil});
                }

                objeto = dao.consultarXPerfil(sIdPerfil);

            } else {
                tipo = Constantes.MSG_ADVERTENCIA;
                mensaje = "Su perfil no está autorizado para consultar los permisos";
            }
        } else {
            tipo = Constantes.MSG_NO_AUTENTICADO;
            mensaje = "Usted no se encuentra autenticado.";
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
