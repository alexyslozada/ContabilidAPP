package com.ingenio.servlets.informes.detallados;

import com.ingenio.dao.DAOPeriodo;
import com.ingenio.dao.informes.detallados.DAOMovimientoCuentas;
import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.Periodo;
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
@WebServlet(name = "SInfMovimientosCuenta", urlPatterns = {"/inf-mov-cuenta"})
public class SInfMovimientosCuenta extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(SInfMovimientosCuenta.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        HttpSession sesion = request.getSession();
        byte tipo;
        String mensaje;
        String objeto = "";
        Utilidades u = Utilidades.get();

        if(u.autenticado(sesion)){
            
            DAOMovimientoCuentas dao = new DAOMovimientoCuentas();
            Periodo initialPeriod = new Periodo();
            Periodo finalPeriod   = new Periodo();
            
            Usuario usuario = (Usuario) sesion.getAttribute("credencial");
            
            String sInitialMonth, sInitialYear, sFinalMonth, sFinalYear, sInitialAccount, sFinalAccount;
            sInitialMonth = request.getParameter("initial-month");
            sInitialYear  = request.getParameter("initial-year");
            sFinalMonth   = request.getParameter("final-month");
            sFinalYear    = request.getParameter("final-year");
            sInitialAccount = request.getParameter("initial-account");
            sFinalAccount = request.getParameter("final-account");

            setPeriodo(initialPeriod, sInitialMonth, sInitialYear);
            setPeriodo(finalPeriod, sFinalMonth, sFinalYear);
            
            try {
                objeto  = dao.getMovimientos(initialPeriod.getId_periodo_contable(),
                                                finalPeriod.getId_periodo_contable(),
                                                sInitialAccount, sFinalAccount);
                tipo    = Constantes.MSG_CORRECTO;
                mensaje = Constantes.MSG_CONSULTA_REALIZADA_TEXT;
            } catch (ExcepcionGeneral eg) {
                tipo = Constantes.MSG_ERROR;
                mensaje = eg.getMessage();
            }
            
        } else {
            tipo = Constantes.MSG_NO_AUTENTICADO;
            mensaje = Constantes.MSG_NO_AUTENTICADO_TEXT;
        }

        try (PrintWriter out = response.getWriter()) {
            out.println(u.respuestaJSON(tipo, mensaje, objeto));
        }
    }
    
    private void setPeriodo(Periodo period, String month, String year) {
        Utilidades u = Utilidades.get();
        DAOPeriodo daoPeriodo = new DAOPeriodo();
        
        short m = u.parseShort(month, LOG, true);
        short y  = u.parseShort(year, LOG, true);
        period.setMes(m);
        period.setAnio(y);
        daoPeriodo.getPeriodoXFecha(period);
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
