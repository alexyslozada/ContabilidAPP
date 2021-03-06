package com.ingenio.servlets.empresa;

import com.ingenio.dao.DAOEmpresa;
import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.Ciudad;
import com.ingenio.objetos.Departamento;
import com.ingenio.objetos.Empresa;
import com.ingenio.objetos.TipoIdentificacion;
import com.ingenio.objetos.TipoPersona;
import com.ingenio.objetos.TipoRegimen;
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
@WebServlet(name = "SEmpresaUpsert", urlPatterns = {"/SEmpresaUpsert"})
public class SEmpresaUpsert extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(SEmpresaUpsert.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        HttpSession sesion = request.getSession();
        byte tipo;
        String mensaje;
        String objeto = "";

        if(Utilidades.get().autenticado(sesion)){
            DAOEmpresa dao = new DAOEmpresa();
            Usuario usuario = (Usuario) sesion.getAttribute("credencial");

            if(dao.tienePermiso(usuario.getPerfil(), dao.OBJETO, Constantes.INSERTAR)){
                String sTipoIdentificacion = request.getParameter("tipo_identificacion");
                String numeroIdentificacion = request.getParameter("numero_identificacion");
                String digitoVerificacion = request.getParameter("digito_verificacion");
                String nombre = request.getParameter("nombre");
                String direccion = request.getParameter("direccion");
                String telefono = request.getParameter("telefono");
                String idDepto  = request.getParameter("departamento");
                String idCiudad = request.getParameter("ciudad");
                String direccionWeb = request.getParameter("direccion_web");
                String correo = request.getParameter("correo");
                String actividad = request.getParameter("actividad");
                String autorretenedor = request.getParameter("autorretenedor");
                String sTipoPersona = request.getParameter("tipo_persona");
                String sTipoRegimen = request.getParameter("tipo_regimen");
                // FALTA PODER SUBIR EL LOGO

                TipoIdentificacion tipoIdentificacion = new TipoIdentificacion();
                tipoIdentificacion.setIdtipodocumento(Utilidades.get().parseShort(sTipoIdentificacion, LOG, true));
                Ciudad ciudad = new Ciudad();
                ciudad.setIdciudad(Utilidades.get().parseShort(idCiudad, LOG, true));
                Departamento departamento = new Departamento();
                departamento.setCodigo(idDepto);
                departamento.addCiudad(ciudad);
                TipoPersona tipoPersona = new TipoPersona();
                tipoPersona.setId_tipo_persona(Utilidades.get().parseShort(sTipoPersona, LOG, true));
                TipoRegimen tipoRegimen = new TipoRegimen();
                tipoRegimen.setId_regimen(Utilidades.get().parseShort(sTipoRegimen, LOG, true));
                
                Empresa empresa = new Empresa(
                        tipoIdentificacion,
                        numeroIdentificacion,
                        digitoVerificacion,
                        nombre,
                        direccion,
                        telefono,
                        departamento,
                        direccionWeb,
                        correo,
                        actividad,
                        Utilidades.get().parseBoolean(autorretenedor, LOG),
                        tipoPersona,
                        tipoRegimen,
                        null);
                try{
                    boolean respuesta = dao.upsert(empresa);
                    if(respuesta){
                        tipo = Constantes.MSG_CORRECTO;
                        mensaje = Constantes.MSG_ACTUALIZADO_TEXT;
                    } else {
                        tipo = Constantes.MSG_ADVERTENCIA;
                        mensaje = Constantes.MSG_ACTUALIZADO_NO_TEXT;
                    }
                } catch (ExcepcionGeneral eg){
                    Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en SEmpresaUpsert {0}", new Object[]{eg.getMessage()});
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
