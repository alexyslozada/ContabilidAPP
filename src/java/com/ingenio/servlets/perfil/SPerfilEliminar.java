package com.ingenio.servlets.perfil;

import com.ingenio.dao.DAOPerfiles;
import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.Usuario;
import com.ingenio.utilidades.Constantes;
import com.ingenio.utilidades.Utilidades;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@MultipartConfig
@WebServlet(name = "SPerfilEliminar", urlPatterns = {"/SPerfilEliminar"})
public class SPerfilEliminar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        HttpSession sesion = request.getSession();
        byte tipo = Constantes.MSG_ADVERTENCIA;
        String mensaje;
        String objeto = "";
        
        String id = request.getParameter("id");
        short sid;
        try{
            sid = Short.parseShort(id);
        } catch (NumberFormatException nfe){
            sid = 0;
        }

        if(Utilidades.get().autenticado(sesion)){
            DAOPerfiles dao = new DAOPerfiles();
            Usuario usuario = (Usuario) sesion.getAttribute("credencial");
            if(dao.tienePermiso(usuario.getPerfil(), dao.OBJETO, Constantes.BORRAR)){
                try{
                    tipo = Constantes.MSG_CORRECTO;
                    boolean eliminado = dao.eliminar(sid);
                    if(eliminado){
                        mensaje = "El registro ha sido eliminado, ID: "+sid;
                    } else {
                        mensaje = "No se eliminó ningún registro";
                    }
                    objeto = "{\"eliminado\":"+eliminado+"}";
                } catch (ExcepcionGeneral eg){
                    mensaje = eg.getMessage();
                }
            } else {
                mensaje = "Su perfil no tiene permiso para realizar esta acción";
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
