package com.ingenio.dao;

import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.utilidades.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOObjetosXPerfil extends DAOGenerales {
    private Connection conexion = null;
    private PreparedStatement sentencia = null;
    private ResultSet resultado = null;
    private static final Logger LOG = Logger.getLogger(DAOObjetosXPerfil.class.getName());
    
    public final String OBJETO = "PERMISOS";

    public boolean actualizaPermisoXPerfil(short idpermiso, boolean insertar, boolean modificar, boolean borrar, boolean consultar) throws ExcepcionGeneral{
        boolean respuesta = false;
        try{
            setConsulta("select fn_objxper_upd(?, ?, ?, ?, ?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, idpermiso);
            sentencia.setBoolean(2, insertar);
            sentencia.setBoolean(3, modificar);
            sentencia.setBoolean(4, borrar);
            sentencia.setBoolean(5, consultar);
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOObjetosXPerfil.actualizaPermisoXPerfil {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public String consultarXPerfil(short idperfil) throws ExcepcionGeneral{
        String respuesta = "";
        try{
            setConsulta("select fn_objxper_sel_json(?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, new Integer(1).shortValue());
            sentencia.setShort(2, idperfil);
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getString(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOObjetosXPerfil.consultarXPerfil {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
}
