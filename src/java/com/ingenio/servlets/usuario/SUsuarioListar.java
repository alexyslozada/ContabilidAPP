package com.ingenio.servlets.usuario;

import com.ingenio.dao.DAOUsuarios;
import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.Paginacion;
import com.ingenio.objetos.Perfil;
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
@WebServlet(name = "SUsuarioListar", urlPatterns = {"/SUsuarioListar"})
public class SUsuarioListar extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(SUsuarioListar.class.getName());
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        HttpSession sesion = request.getSession();
        byte tipo;
        String mensaje;
        String objeto = "";
        Paginacion paginacion = new Paginacion();
        
        if(Utilidades.get().autenticado(sesion)){
            DAOUsuarios dao = new DAOUsuarios();
            Usuario usuario = (Usuario) sesion.getAttribute("credencial");

            if(dao.tienePermiso(usuario.getPerfil(), "USUARIOS", "consultar")){

                String tipoConsulta   = request.getParameter("tipo_consulta");

                String pagina         = request.getParameter("pagina");
                String limite         = request.getParameter("limite");
                String columna_orden  = request.getParameter("columna_orden");
                String tipo_orden     = request.getParameter("tipo_orden");

                short sTipoConsulta, sPagina, sLimite, sColumna_orden;

                sTipoConsulta  = Utilidades.get().parseShort(tipoConsulta, LOG);
                sPagina        = Utilidades.get().parseShort(pagina, LOG);
                sLimite        = Utilidades.get().parseShort(limite, LOG);
                sColumna_orden = Utilidades.get().parseShort(columna_orden, LOG);

                Usuario userConsulta = new Usuario();
                Perfil perfilConsulta = new Perfil();
                userConsulta.setPerfil(perfilConsulta);

                paginacion = new Paginacion(sPagina, sLimite, sColumna_orden, tipo_orden);
                
                try{
                    tipo = Constantes.MSG_CORRECTO;
                    mensaje = "Consulta realizada";
                    switch(sTipoConsulta){
                        case 1:
                            break;
                        case 2:
                            String id_usuario = request.getParameter("id");
                            short sId_usuario = Utilidades.get().parseShort(id_usuario, LOG);
                            userConsulta.setIdusuario(sId_usuario);
                            break;
                        case 3:
                            String identificacion = request.getParameter("identificacion");
                            userConsulta.setIdentificacion(identificacion);
                            break;
                        case 4:
                            String nombre = request.getParameter("nombre");
                            userConsulta.setNombre(nombre);
                            break;
                        case 5:
                            String perfil = request.getParameter("perfil");
                            short sPerfil = Utilidades.get().parseShort(perfil, LOG);
                            perfilConsulta.setIdperfil(sPerfil);
                            break;
                        case 6:
                            String activo   = request.getParameter("activo");
                            boolean bActivo = Utilidades.get().parseBoolean(activo, LOG);
                            userConsulta.setActivo(bActivo);
                            break;
                        default:
                            tipo = Constantes.MSG_ERROR;
                            mensaje = "El tipo de consulta no es válido";
                    }
                    objeto = dao.listar(sTipoConsulta, userConsulta, paginacion);
                } catch (ExcepcionGeneral eg){
                    Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en SUsuarioListar {0}", new Object[]{eg.getMessage()});
                    tipo = Constantes.MSG_ERROR;
                    mensaje = eg.getMessage();
                }
            } else {
                tipo = Constantes.MSG_ADVERTENCIA;
                mensaje = Constantes.MSG_SIN_PERMISO_TEXT;
            }
        } else {
            tipo = Constantes.MSG_NO_AUTENTICADO;
            mensaje = "Usuario no autenticado";
        }
        
        try (PrintWriter out = response.getWriter()) {
            out.println(Utilidades.get().respuestaJSON(tipo, mensaje, objeto, paginacion));
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
