package com.ingenio.servlets.empresafuncionario;

import com.ingenio.dao.DAOEmpresaFuncionarios;
import com.ingenio.objetos.EmpresaFuncionario;
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
@WebServlet(name = "SEmpresaFuncionarioListar", urlPatterns = {"/SEmpresaFuncionarioListar"})
public class SEmpresaFuncionarioListar extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(SEmpresaFuncionarioListar.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        HttpSession sesion = request.getSession();
        byte tipo;
        String mensaje;
        String objeto = "";

        if(Utilidades.get().autenticado(sesion)){
            DAOEmpresaFuncionarios dao = new DAOEmpresaFuncionarios();
            Usuario usuario = (Usuario) sesion.getAttribute("credencial");
            
            String sTipo = request.getParameter("tipoConsulta");
            short iTipo  = Utilidades.get().parseShort(sTipo, LOG, true);
            EmpresaFuncionario funcionario = new EmpresaFuncionario();
            
            tipo    = Constantes.MSG_CORRECTO;
            mensaje = Constantes.MSG_CONSULTA_REALIZADA_TEXT;
            
            switch(iTipo){
                case 1:
                    break;
                case 2:
                    String sIdFuncionario = request.getParameter("id");
                    short  iIdFuncionario = Utilidades.get().parseShort(sIdFuncionario, LOG, true);
                    funcionario.setId_funcionario(iIdFuncionario);
                    break;
                case 3:
                    String sIdentificacion = request.getParameter("identificacion");
                    funcionario.setIdentificacion(sIdentificacion);
                    break;
                default:
                    tipo = Constantes.MSG_ERROR;
                    mensaje = Constantes.MSG_CONSULTA_NO_VALIDA_TEXT;
            }
            objeto = dao.listar(iTipo, funcionario);
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
