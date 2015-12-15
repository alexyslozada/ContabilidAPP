package com.ingenio.servlets;

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
@WebServlet(name = "SPerfilCrear", urlPatterns = {"/SPerfilCrear"})
public class SPerfilCrear extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        HttpSession sesion = request.getSession();
        byte tipo = Constantes.MSG_ADVERTENCIA;
        String mensaje;
        String objeto = "";

        if(Utilidades.get().autenticado(sesion)){
            String nombre = request.getParameter("nombre");
            DAOPerfiles dao = new DAOPerfiles();
            Usuario usuario = (Usuario) sesion.getAttribute("credencial");
            if(dao.tienePermiso(usuario.getPerfil(), "PERFILES", "insertar")){
                try{
                    short resultado = dao.crear(nombre);
                    tipo = Constantes.MSG_CORRECTO;
                    mensaje = "Se ha creado el perfil con el id: " + resultado;
                } catch (ExcepcionGeneral eg){
                    tipo = Constantes.MSG_ADVERTENCIA;
                    mensaje = eg.getMessage();
                }
            } else {
                mensaje = "Su perfil no tiene autorización para crear perfiles.";
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
