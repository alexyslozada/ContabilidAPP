package com.ingenio.dao;

import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.utilidades.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAODepartamentos extends DAOGenerales {
    
    public final String OBJETO ="DEPARTAMENTO";
    
    private Connection conexion = null;
    private PreparedStatement sentencia = null;
    private ResultSet resultado = null;
    private static final Logger LOG = Logger.getLogger(DAODepartamentos.class.getName());
    
    public String listar() throws ExcepcionGeneral {
        String respuesta = "";
        try{
            setConsulta("select fn_departamentos_sel_json()");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getString(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAODepartamentos.listar {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
}
