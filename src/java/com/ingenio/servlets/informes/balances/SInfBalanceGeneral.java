package com.ingenio.servlets.informes.balances;

import com.ingenio.dao.DAOPeriodo;
import com.ingenio.dao.informes.balances.DAOBalanceGeneral;
import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.Periodo;
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
@WebServlet(name = "SInfBalanceGeneral", urlPatterns = {"/inf-balance-general"})
public class SInfBalanceGeneral extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(SInfBalanceGeneral.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        HttpSession sesion = request.getSession();
        byte tipo;
        String mensaje;
        String objeto = "";
        Utilidades u = Utilidades.get();
        
        if (u.autenticado(sesion)) {
            DAOBalanceGeneral dao = new DAOBalanceGeneral();
            Usuario usuario = (Usuario) sesion.getAttribute("credencial");
            
            if (dao.tienePermiso(usuario.getPerfil(), dao.OBJETO, Constantes.CONSULTAR)) {
                
                String sTypeReport = request.getParameter("type-report"),
                       sClassLevel = request.getParameter("class-level"),
                       sPeriodMonth = request.getParameter("period-month"),
                       sPeriodYear  = request.getParameter("period-year");
                
                short iTypeReport = u.parseShort(sTypeReport, LOG, true),
                      iClassLevel = u.parseShort(sClassLevel, LOG, true),
                      iPeriodMonth = u.parseShort(sPeriodMonth, LOG, true),
                      iPeriodYear = u.parseShort(sPeriodYear, LOG, true);
                
                // Get id of period
                DAOPeriodo daoPeriodo = new DAOPeriodo();
                Periodo periodo = new Periodo(iPeriodYear, iPeriodMonth);
                daoPeriodo.getPeriodoXFecha(periodo);
                
                // Get query
                try {
                    objeto = dao.getBalance(iTypeReport, periodo.getId_periodo_contable(), iClassLevel);
                    tipo = Constantes.MSG_CORRECTO;
                    mensaje = Constantes.MSG_CONSULTA_REALIZADA_TEXT;
                } catch (ExcepcionGeneral eg) {
                    tipo = Constantes.MSG_ERROR;
                    mensaje = eg.getMessage();
                    u.generaLogServer(LOG, Level.WARNING, "Error al generar el informe {0}", new Object[]{eg.getMessage()});
                }

            } else {
                tipo = Constantes.MSG_ERROR;
                mensaje = "Su perfil no está autorizado para consultar los permisos";
            }            
        } else {
            tipo = Constantes.MSG_NO_AUTENTICADO;
            mensaje = Constantes.MSG_NO_AUTENTICADO_TEXT;
        }
        
        try (PrintWriter out = response.getWriter()) {
            out.println(u.respuestaJSON(tipo, mensaje, objeto));
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
