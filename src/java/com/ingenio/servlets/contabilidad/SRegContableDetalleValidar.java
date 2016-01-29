package com.ingenio.servlets.contabilidad;

import com.ingenio.dao.DAORegistroContable;
import com.ingenio.objetos.CentroCosto;
import com.ingenio.objetos.CuentasPuc;
import com.ingenio.objetos.RegistroContableDetalle;
import com.ingenio.objetos.Tercero;
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
@WebServlet(name = "SRegContableDetalleValidar", urlPatterns = {"/SRegContableDetalleValidar"})
public class SRegContableDetalleValidar extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(SRegContableDetalleValidar.class.getName());

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

            Usuario usuario = (Usuario) sesion.getAttribute("credencial");

            if (dao.tienePermiso(usuario.getPerfil(), dao.OBJETO, Constantes.INSERTAR)) {

                String idCuentaPuc = request.getParameter("id_cuenta_puc");
                String nombreCuenta = request.getParameter("cuenta");
                String codigoCuenta = request.getParameter("cuentapuc");
                String debito = request.getParameter("debito");
                String credito = request.getParameter("credito");
                String idTercero = request.getParameter("id_tercero");
                String nombreTercero = request.getParameter("nombre_tercero");
                String numeroTercero = request.getParameter("tercero");
                String idCCosto = request.getParameter("id_centro_costo");
                String nombreCCosto = request.getParameter("nombre_ccosto");
                String codigoCCosto = request.getParameter("centrocosto");
                
                short iIdCuentaPuc = utilidades.parseShort(idCuentaPuc, LOG, true),
                        iIdTercero = utilidades.parseShort(idTercero, LOG, true),
                        iIdCCosto = utilidades.parseShort(idCCosto, LOG, true);

                int iDebito = utilidades.parseInt(debito, LOG, true),
                        iCredito = utilidades.parseInt(credito, LOG, true);

                // Validación
                
                if (iIdCuentaPuc == 0 || nombreCuenta == null || nombreCuenta.length() == 0 || codigoCuenta == null || codigoCuenta.length() == 0) {
                    mensaje = "La cuenta puc no es válida";
                } else if (iIdTercero == 0 || nombreTercero == null || nombreTercero.length() == 0 || numeroTercero == null || numeroTercero.length() == 0) {
                    mensaje = "El tercero no es válido";
                } else if (iIdCCosto == 0 || nombreCCosto == null || nombreCCosto.length() == 0 || codigoCCosto == null || codigoCCosto.length() == 0) {
                    mensaje = "El centro de costo no es válido";
                } else if (iDebito < 0 || iCredito < 0) {
                    mensaje = "Los valores débito y/o crédito no pueden ser negativos";
                } else if (iDebito == 0 && iCredito == 0) {
                    mensaje = "Debe registrar un valor débito o crédito.";
                } else {
                    mensaje = "";
                    registroValido = true;
                }

                if (registroValido) {
                    CuentasPuc cuentaPuc = new CuentasPuc();
                    Tercero tercero = new Tercero();
                    CentroCosto centroCosto = new CentroCosto();
                    RegistroContableDetalle registro = new RegistroContableDetalle();
                    
                    cuentaPuc.setIdcuenta(iIdCuentaPuc);
                    cuentaPuc.setCuenta(codigoCuenta);
                    cuentaPuc.setNombre(nombreCuenta);
                    tercero.setId_tercero(iIdTercero);
                    tercero.setNumero_identificacion(numeroTercero);
                    tercero.setRazon_social(nombreTercero);
                    centroCosto.setId_centro_costo(iIdCCosto);
                    centroCosto.setCodigo(codigoCCosto);
                    centroCosto.setNombre(nombreCCosto);
                    registro.setCuentaPuc(cuentaPuc);
                    registro.setTercero(tercero);
                    registro.setCentroCosto(centroCosto);
                    registro.setDebito(iDebito);
                    registro.setCredito(iCredito);
                    
                    List<RegistroContableDetalle> lista;
                    lista = (ArrayList<RegistroContableDetalle>) sesion.getAttribute(Constantes.REGISTROCONTABLEDETALLE);
                    
                    if (lista != null) {
                        lista.add(registro);
                    } else {
                        lista = new ArrayList<>();
                        lista.add(registro);
                    }
                    sesion.setAttribute(Constantes.REGISTROCONTABLEDETALLE, lista);
                    tipo = Constantes.MSG_CORRECTO;
                    mensaje = "Registro agregado a la lista correctamente";
                    objeto = registro.toJSON();
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
