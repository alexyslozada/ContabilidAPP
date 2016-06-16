package com.ingenio.servlets.contabilidad;

import com.ingenio.dao.DAORegistroContable;
import com.ingenio.objetos.RegistroContableDetalle;
import com.ingenio.objetos.Usuario;
import com.ingenio.utilidades.Constantes;
import com.ingenio.utilidades.Utilidades;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@MultipartConfig
@WebServlet(name = "SRegContableDetalleEliminarDeSesion", urlPatterns = {"/SRegContableDetalleEliminarDeSesion"})
public class SRegContableDetalleEliminarDeSesion extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(SRegContableDetalleEliminarDeSesion.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        HttpSession sesion = request.getSession();
        byte tipo;
        String mensaje;
        String objeto = "";
        boolean borrado = false;
        Utilidades utilidades = Utilidades.get();

        if (utilidades.autenticado(sesion)) {
            DAORegistroContable dao = new DAORegistroContable();

            Usuario usuario = (Usuario) sesion.getAttribute("credencial");
            
            String id = request.getParameter("id");
            int iId = utilidades.parseInt(id, LOG, true);
            
            List<RegistroContableDetalle> lista;
            lista = (ArrayList<RegistroContableDetalle>) sesion.getAttribute(Constantes.REGISTROCONTABLEDETALLE);
            
            if(lista != null){
                for(RegistroContableDetalle registro : lista){
                    if(registro.getId_reg_con_det() == iId){
                        lista.remove(registro);
                        borrado = true;
                        break;
                    }
                }
                if(borrado){
                    tipo = Constantes.MSG_CORRECTO;
                    mensaje = Constantes.MSG_ELIMINADO_TEXT;
                    objeto = "{\"id\":"+id+"}";
                } else {
                    tipo = Constantes.MSG_ADVERTENCIA;
                    mensaje = Constantes.MSG_ELIMINADO_NO_TEXT;
                }
            } else {
                tipo = Constantes.MSG_ERROR;
                mensaje = "No existe lista de detalle";
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
