package com.ingenio.servlets.centrocosto;

import com.ingenio.dao.DAOCentroCosto;
import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.CentroCosto;
import com.ingenio.objetos.Paginacion;
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
@WebServlet(name = "SCentroCostoListar", urlPatterns = {"/SCentroCostoListar"})
public class SCentroCostoListar extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(SCentroCostoListar.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        HttpSession sesion = request.getSession();
        byte tipo;
        String mensaje;
        String objeto = "";
        Paginacion paginacion;

        if (Utilidades.get().autenticado(sesion)) {
            DAOCentroCosto dao = new DAOCentroCosto();
            Usuario usuario = (Usuario) sesion.getAttribute("credencial");

            if (dao.tienePermiso(usuario.getPerfil(), dao.OBJETO, Constantes.CONSULTAR)) {

                String tipoConsulta = request.getParameter("tipoConsulta"),
                       pagina       = request.getParameter("pagina"),
                       limite       = request.getParameter("limite"),
                       columna_orden = request.getParameter("columna_orden"),
                       tipo_orden   = request.getParameter("tipo_orden");
                
                short iTipoConsulta = Utilidades.get().parseShort(tipoConsulta, LOG, true),
                      iPagina       = Utilidades.get().parseShort(pagina, LOG, false),
                      iLimite       = Utilidades.get().parseShort(limite, LOG, false),
                      iColumnaOrden = Utilidades.get().parseShort(columna_orden, LOG, false);
                
                paginacion = new Paginacion(iPagina, iLimite, iColumnaOrden, tipo_orden);
                CentroCosto centrocosto = new CentroCosto();
                
                tipo = Constantes.MSG_CORRECTO;
                mensaje = Constantes.MSG_CONSULTA_REALIZADA_TEXT;
                
                switch(iTipoConsulta){
                    case 1:
                        break;
                    case 2:
                        String codigo = request.getParameter("codigo");
                        centrocosto.setCodigo(codigo);
                        break;
                    case 3:
                        String nombre = request.getParameter("nombre");
                        centrocosto.setNombre(nombre);
                        break;
                    default:
                        tipo = Constantes.MSG_ADVERTENCIA;
                        mensaje = Constantes.MSG_CONSULTA_NO_VALIDA_TEXT;
                }
                
                try{
                    objeto = dao.listar(iTipoConsulta, centrocosto, paginacion);
                } catch (ExcepcionGeneral eg){
                    Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en SCentroCostoListar {0}", new Object[]{eg.getMessage()});
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
