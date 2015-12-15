package com.ingenio.servlets;

import com.ingenio.dao.DAOObjetosXPerfil;
import com.ingenio.objetos.Usuario;
import com.ingenio.utilidades.Constantes;
import com.ingenio.utilidades.Utilidades;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
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
@WebServlet(name = "SObjXPerfilActualizar", urlPatterns = {"/SObjXPerfilActualizar"})
public class SObjXPerfilActualizar extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(SObjXPerfilActualizar.class.getName());

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

            if(dao.tienePermiso(usuario.getPerfil(), "PERMISOS", "modificar")){

                String idPermiso = request.getParameter("id");
                String insertar  = request.getParameter("insertar");
                String modificar  = request.getParameter("modificar");
                String borrar  = request.getParameter("borrar");
                String consultar  = request.getParameter("consultar");

                short sIdPermiso = 0;
                boolean bInsertar = false,
                        bModificar = false,
                        bBorrar = false,
                        bConsultar = false,
                        respuesta;

                if(insertar != null && insertar.equals("true")){
                    bInsertar = true;
                }
                if(modificar != null && modificar.equals("true")){
                    bModificar = true;
                }
                if(borrar != null && borrar.equals("true")){
                    bBorrar = true;
                }
                if(consultar != null && consultar.equals("true")){
                    bConsultar = true;
                }
                
                try{
                    sIdPermiso = Short.parseShort(idPermiso);
                    tipo = Constantes.MSG_CORRECTO;
                } catch (NumberFormatException nfe){
                    tipo = Constantes.MSG_ERROR;
                    Utilidades.get().generaLogServer(LOG, Level.WARNING, "Error al hacer parseShort de {0}", new Object[]{idPermiso});
                }

                respuesta = dao.actualizaPermisoXPerfil(sIdPermiso, bInsertar, bModificar, bBorrar, bConsultar);

                objeto = "{\"actualizado\":"+respuesta+"}";
                if(respuesta){
                    mensaje = "Permisos actualizados correctamente";
                } else {
                    mensaje = "No se actualizaron los permisos";
                }
            } else {
                tipo = Constantes.MSG_ADVERTENCIA;
                mensaje = "Su perfil no está autorizado para modificar los permisos";
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
