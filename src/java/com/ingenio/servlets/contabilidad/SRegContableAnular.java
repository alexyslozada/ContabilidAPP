package com.ingenio.servlets.contabilidad;

import com.ingenio.dao.DAORegistroContable;
import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.RegistroContableEncabezado;
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
@WebServlet(name = "SRegContableAnular", urlPatterns = {"/SRegContableAnular"})
public class SRegContableAnular extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(SRegContableAnular.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        HttpSession sesion = request.getSession();
        byte tipo;
        String mensaje;
        String objeto = "";
        Utilidades utilidades = Utilidades.get();

        if (utilidades.autenticado(sesion)) {

            DAORegistroContable dao = new DAORegistroContable();
            Usuario usuario = (Usuario) sesion.getAttribute("credencial");

            if (dao.tienePermiso(usuario.getPerfil(), dao.OBJETO, Constantes.MODIFICAR)) {

                String id_registro = request.getParameter("idPrincipal");
                int iId_registro = utilidades.parseInt(id_registro, LOG, true);
                RegistroContableEncabezado rce = new RegistroContableEncabezado();
                rce.setId_reg_con_enc(iId_registro);
                rce.setUsuario(usuario);

                try {
                    boolean respuesta = dao.anularDocumento(rce);
                    if (respuesta) {
                        tipo = Constantes.MSG_CORRECTO;
                        mensaje = Constantes.MSG_REGISTRO_ANULADO_TEXT;
                    } else {
                        tipo = Constantes.MSG_ERROR;
                        mensaje = Constantes.MSG_REGISTRO_NO_ANULADO_TEXT;
                    }
                } catch (ExcepcionGeneral eg) {
                    tipo = Constantes.MSG_ADVERTENCIA;
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
