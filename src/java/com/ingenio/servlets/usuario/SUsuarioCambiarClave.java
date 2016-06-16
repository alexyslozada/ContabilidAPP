package com.ingenio.servlets.usuario;

import com.ingenio.dao.DAOUsuarios;
import com.ingenio.excepciones.ExcepcionGeneral;
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
@WebServlet(name = "SUsuarioCambiarClave", urlPatterns = {"/SUsuarioCambiarClave"})
public class SUsuarioCambiarClave extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(SUsuarioCambiarClave.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        HttpSession sesion = request.getSession();
        byte tipo;
        String mensaje;
        String objeto = "";

        if (Utilidades.get().autenticado(sesion)) {
            DAOUsuarios dao = new DAOUsuarios();
            Usuario usuario = (Usuario) sesion.getAttribute("credencial");

            short id = usuario.getIdusuario();
            String anterior = request.getParameter("anterior");
            String nueva = request.getParameter("nueva");

            try {
                boolean resultado = dao.cambiarClave(id, anterior, nueva);
                if(resultado){
                    tipo = Constantes.MSG_CORRECTO;
                    mensaje = "Clave actualizada correctamente";
                } else {
                    tipo = Constantes.MSG_ERROR;
                    mensaje = "No se actualizó la clave";
                }
            } catch (ExcepcionGeneral eg) {
                Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en SUsuarioCambiarClave {0}", new Object[]{eg.getMessage()});
                tipo = Constantes.MSG_ERROR;
                mensaje = eg.getMessage();
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
