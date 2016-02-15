package com.ingenio.servlets.contabilidad;

import com.ingenio.dao.DAOPeriodo;
import com.ingenio.dao.DAORegistroContable;
import com.ingenio.objetos.DocumentoContable;
import com.ingenio.objetos.Periodo;
import com.ingenio.objetos.RegistroContableEncabezado;
import com.ingenio.objetos.Usuario;
import com.ingenio.utilidades.Constantes;
import com.ingenio.utilidades.Utilidades;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@MultipartConfig
@WebServlet(name = "SRegContableEncabezadoValidar", urlPatterns = {"/SRegContableEncabezadoValidar"})
public class SRegContableEncabezadoValidar extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(SRegContableEncabezadoValidar.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        HttpSession sesion = request.getSession();
        byte tipo;
        String mensaje;
        String objeto = "";
        boolean registroValido = false;
        Utilidades utilidades = Utilidades.get();

        if (utilidades.autenticado(sesion)) {
            DAORegistroContable dao = new DAORegistroContable();
            DAOPeriodo daoPeriodo = new DAOPeriodo();

            Usuario usuario = (Usuario) sesion.getAttribute("credencial");

            if (dao.tienePermiso(usuario.getPerfil(), dao.OBJETO, Constantes.INSERTAR)) {

                String accion = request.getParameter("accion");
                String idRegistro = request.getParameter("idPrincipal"); // Cuando se edita
                String consecutivo = request.getParameter("consecutivo"); // Cuando se edita
                String idDocumento = request.getParameter("idDocumento");
                String documento = request.getParameter("documento");
                String fecha = request.getParameter("fecha");
                String comentario = request.getParameter("comentario");

                Calendar cFecha = utilidades.parseFecha(fecha, LOG, true);
                Periodo periodoContable = new Periodo();
                if (cFecha != null) {
                    int ano = cFecha.get(Calendar.YEAR);
                    int mes = cFecha.get(Calendar.MONTH) + 1;
                    periodoContable.setAnio((short) ano);
                    periodoContable.setMes((short) mes);
                    periodoContable = daoPeriodo.getPeriodoXFecha(periodoContable);
                }

                int iIdRegistro = 0, iConsecutivo = 0;
                short iIdDocumento = utilidades.parseShort(idDocumento, LOG, true);

                if (accion.equalsIgnoreCase("editar")) {
                    iIdRegistro = utilidades.parseInt(idRegistro, LOG, true);
                    iConsecutivo = utilidades.parseInt(consecutivo, LOG, true);
                }

                if (iIdDocumento == 0) {
                    mensaje = "El documento no es válido";
                } else if (comentario == null || comentario.length() == 0) {
                    mensaje = "El comentario no puede estar vacío.";
                } else if (periodoContable == null || periodoContable.getId_periodo_contable() == 0) {
                    mensaje = "La fecha y/o el periodo es válido.";
                } else if (!periodoContable.isAbierto()) {
                    mensaje = "Este periodo contable ya está cerrado. No se pueden hacer movimientos.";
                } else if (accion.equalsIgnoreCase("editar") && iIdRegistro == 0) {
                    mensaje = "El id del registro no es válido.";
                } else {
                    mensaje = "";
                    registroValido = true;
                }

                if (registroValido) {
                    RegistroContableEncabezado rce = new RegistroContableEncabezado();
                    DocumentoContable documentoContable = new DocumentoContable();

                    documentoContable.setId_documento(iIdDocumento);
                    documentoContable.setDocumento(documento);
                    rce.setDocumento(documentoContable);
                    rce.setFechaMovimiento(cFecha);
                    rce.setComentario(comentario);
                    rce.setPeriodo(periodoContable);
                    if (accion.equalsIgnoreCase("editar")) {
                        rce.setId_reg_con_enc(iIdRegistro);
                        documentoContable.setConsecutivo(iConsecutivo);
                    }

                    sesion.setAttribute(Constantes.REGISTROCONTABLEENCABEZADO, rce);
                    sesion.removeAttribute(Constantes.REGISTROCONTABLEDETALLE);
                    tipo = Constantes.MSG_CORRECTO;
                    mensaje = "Registro contable establecido en la sesión";
                    objeto = rce.toJSON();
                } else {
                    tipo = Constantes.MSG_ERROR;
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
            out.println(utilidades.respuestaJSON(tipo, mensaje, objeto));
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
