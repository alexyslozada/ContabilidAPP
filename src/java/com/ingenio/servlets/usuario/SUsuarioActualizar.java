package com.ingenio.servlets.usuario;

import com.ingenio.dao.DAOUsuarios;
import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.Perfil;
import com.ingenio.objetos.Usuario;
import com.ingenio.utilidades.Constantes;
import com.ingenio.utilidades.Utilidades;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@MultipartConfig
@WebServlet(name = "SUsuarioActualizar", urlPatterns = {"/SUsuarioActualizar"})
public class SUsuarioActualizar extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(SUsuarioActualizar.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        HttpSession sesion = request.getSession();
        byte tipo;
        String mensaje;
        String objeto = "";

        if(Utilidades.get().autenticado(sesion)){
            DAOUsuarios dao = new DAOUsuarios();
            Usuario usuario = (Usuario) sesion.getAttribute("credencial");
            if(dao.tienePermiso(usuario.getPerfil(), "USUARIOS", "modificar")){
                String idUsuario      = request.getParameter("ide");
                String identificacion = request.getParameter("identificacion");
                String nombre         = request.getParameter("nombre");
                String correo         = request.getParameter("correo");
                String perfil         = request.getParameter("perfil");
                String activo         = request.getParameter("activo");
                short idPerfil   = Utilidades.get().parseShort(perfil, LOG),
                      sIdUsuario = Utilidades.get().parseShort(idUsuario, LOG);
                boolean bActivo  = Utilidades.get().parseBoolean(activo, LOG);

                Usuario user = new Usuario();
                Perfil  perf = new Perfil();
                perf.setIdperfil(idPerfil);

                user.setIdusuario(sIdUsuario);
                user.setIdentificacion(identificacion);
                user.setNombre(nombre);
                user.setCorreo(correo);
                user.setPerfil(perf);
                user.setActivo(bActivo);

                try{
                    boolean respuesta = dao.actualizar(user);
                    tipo = Constantes.MSG_CORRECTO;
                    if(respuesta){
                        mensaje = Constantes.MSG_ACTUALIZADO_TEXT;
                    } else {
                        mensaje = Constantes.MSG_ACTUALIZADO_NO_TEXT;
                    }
                } catch (ExcepcionGeneral eg){
                    tipo = Constantes.MSG_ERROR;
                    mensaje = Constantes.MSG_ERROR_GENERAL_TEXT + eg.getMessage();
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
