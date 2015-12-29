package com.ingenio.servlets.tipofuncionario;

import com.ingenio.dao.DAOTipoFuncionario;
import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.TipoFuncionario;
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
@WebServlet(name = "STipoFuncionarioEliminar", urlPatterns = {"/STipoFuncionarioEliminar"})
public class STipoFuncionarioEliminar extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(STipoFuncionarioEliminar.class.getName());
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        HttpSession sesion = request.getSession();
        byte tipo;
        String mensaje;
        String objeto = "";

        if(Utilidades.get().autenticado(sesion)){
            DAOTipoFuncionario dao = new DAOTipoFuncionario();
            Usuario usuario = (Usuario) sesion.getAttribute("credencial");

            if(dao.tienePermiso(usuario.getPerfil(), dao.OBJETO, Constantes.BORRAR)){
                
                String id = request.getParameter("id");
                short sId = Utilidades.get().parseShort(id, LOG, true);

                try{
                    boolean respuesta = dao.eliminar(sId);
                    tipo = Constantes.MSG_CORRECTO;
                    if(respuesta){
                        mensaje = Constantes.MSG_ELIMINADO_TEXT;
                    } else {
                        mensaje = Constantes.MSG_ELIMINADO_NO_TEXT;
                    }
                } catch (ExcepcionGeneral eg){
                    Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en STipoFuncionarioEliminar {0}", new Object[]{eg.getMessage()});
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
