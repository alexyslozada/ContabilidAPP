package com.ingenio.servlets.contabilidad;

import com.ingenio.dao.DAORegistroContable;
import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.RegistroContableDetalle;
import com.ingenio.objetos.RegistroContableEncabezado;
import com.ingenio.objetos.Usuario;
import com.ingenio.utilidades.Constantes;
import com.ingenio.utilidades.Utilidades;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(name = "SRegContableGuardarDocumento", urlPatterns = {"/SRegContableGuardarDocumento"})
public class SRegContableGuardarDocumento extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(SRegContableGuardarDocumento.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        HttpSession sesion = request.getSession();
        byte tipo;
        String mensaje;
        String objeto = "";
        Utilidades utilidades = Utilidades.get();

        if (utilidades.autenticado(sesion)) {
            DAORegistroContable dao = new DAORegistroContable();

            Usuario usuario = (Usuario) sesion.getAttribute("credencial");
            String accion = request.getParameter("accion");
            boolean tienePermiso = false;

            if (accion.equalsIgnoreCase("insertar")) {
                tienePermiso = dao.tienePermiso(usuario.getPerfil(), dao.OBJETO, Constantes.INSERTAR);
            } else if (accion.equalsIgnoreCase("editar")) {
                tienePermiso = dao.tienePermiso(usuario.getPerfil(), dao.OBJETO, Constantes.MODIFICAR);
            }

            if (tienePermiso) {

                RegistroContableEncabezado encabezado = (RegistroContableEncabezado) sesion.getAttribute(Constantes.REGISTROCONTABLEENCABEZADO);
                List<RegistroContableDetalle> detalle = (List<RegistroContableDetalle>) sesion.getAttribute(Constantes.REGISTROCONTABLEDETALLE);

                if (encabezado != null) {
                    encabezado.setUsuario(usuario);
                    // Validación de detalle cuadrado
                    if (detalle != null) {
                        int diferencia = 0;
                        for (RegistroContableDetalle registro : detalle) {
                            diferencia += registro.getDebito() - registro.getCredito();
                        }
                        if (diferencia == 0) {
                            try {
                                if (accion.equalsIgnoreCase("insertar")) {
                                    dao.registrarDocumentoCompleto(encabezado, detalle);
                                    if (encabezado.getId_reg_con_enc() > 0) {
                                        tipo = Constantes.MSG_CORRECTO;
                                        mensaje = Constantes.MSG_CREADO_TEXT + encabezado.getDocumento().getConsecutivo();
                                        objeto = encabezado.toJSON();
                                    } else {
                                        tipo = Constantes.MSG_ERROR;
                                        mensaje = "No se pudo registrar el documento";
                                    }
                                } else {
                                    dao.modificarDocumentoCompleto(encabezado, detalle);
                                    if (encabezado.getFechaUpdate() != null) {
                                        tipo = Constantes.MSG_CORRECTO;
                                        mensaje = Constantes.MSG_ACTUALIZADO_TEXT;
                                        objeto = encabezado.toJSON();
                                    } else {
                                        tipo = Constantes.MSG_ERROR;
                                        mensaje = "No se pudo modificar el documento";
                                    }
                                }
                                sesion.removeAttribute(Constantes.REGISTROCONTABLEENCABEZADO);
                                sesion.removeAttribute(Constantes.REGISTROCONTABLEDETALLE);
                            } catch (ExcepcionGeneral eg) {
                                utilidades.generaLogServer(LOG, Level.SEVERE, "Error en SRegContableGuardarDocumento {0}", new Object[]{eg.getMessage()});
                                tipo = Constantes.MSG_ERROR;
                                mensaje = eg.getMessage();
                            }
                        } else {
                            tipo = Constantes.MSG_ERROR;
                            mensaje = "El documento tiene diferencias entre débitos y créditos "+diferencia;
                        }
                    } else {
                        tipo = Constantes.MSG_ERROR;
                        mensaje = "No se encontraron registros del documento a guardar";
                    }
                } else {
                    tipo = Constantes.MSG_ERROR;
                    mensaje = "No se encontró el encabezado del registro a guardar";
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
