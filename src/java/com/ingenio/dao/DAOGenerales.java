package com.ingenio.dao;

import com.ingenio.objetos.Perfil;
import com.ingenio.utilidades.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOGenerales {
    private final DAO dao = DAO.getDAO();
    private String consulta;
    private static final Logger LOG = Logger.getLogger(DAOGenerales.class.getName());

    protected Connection getConexion() throws SQLException{
        return dao.getConexion();
    }

    protected void setConsulta(String consulta){
        this.consulta = consulta;
    }

    protected String getConsulta(){
        return this.consulta;
    }

    public boolean tienePermiso(Perfil perfil, String objeto, String accion) {
        
        boolean respuesta = false;
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            consulta = "select oxp_" + accion + " from objetosxperfil where oxp_perfil = ? and oxp_objeto = ?";
            conexion = getConexion();
            sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, perfil.getIdperfil());
            sentencia.setString(2, objeto);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error al consultar si tiene permiso. DAOGenerales {0}", new Object[]{sqle.getMessage()});
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    protected void cierraConexion(Connection conexion, PreparedStatement sentencia, ResultSet resultado){
        try{
            if(resultado != null){
                resultado.close();
            }
            if(sentencia != null){
                sentencia.close();
            }
            if(conexion != null){
                conexion.close();
            }
        } catch (SQLException sqle){}
    }
}
