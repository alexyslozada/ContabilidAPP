package com.ingenio.dao;

import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.Perfil;
import com.ingenio.utilidades.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOPerfiles extends DAOGenerales{
    
    private Connection conexion = null;
    private PreparedStatement sentencia = null;
    private ResultSet resultado = null;
    private static final Logger LOG = Logger.getLogger(DAOPerfiles.class.getName());
    
    public final String OBJETO = "PERFILES";

    public short crear(String nombre) throws ExcepcionGeneral{
        short respuesta = 0;
        try{
            setConsulta("select fn_perfiles_ins(?)");
            conexion  = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setString(1, nombre);
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getShort(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOPerfiles.crear: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public boolean eliminar(Short id) throws ExcepcionGeneral{
        boolean respuesta = false;
        try{
            setConsulta("select fn_perfiles_del(?)");
            conexion  = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, id);
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOPerfiles.eliminar: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public boolean actualizar(Perfil perfil) throws ExcepcionGeneral{
        boolean respuesta = false;
        try{
            setConsulta("select fn_perfiles_upd(?,?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, perfil.getIdperfil());
            sentencia.setString(2, perfil.getNombre());
            sentencia.setBoolean(3, perfil.isActivo());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOPerfiles.actualizar: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());            
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public String consultaPorId(short id) throws ExcepcionGeneral{
        String respuesta = "";
        try{
            setConsulta("select fn_perfiles_sel_json(?, ?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, new Integer(2).shortValue());
            sentencia.setShort(2, id);
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getString(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOPerfiles.consultaPorId: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public String listaPerfilJSON() throws ExcepcionGeneral{
        String respuesta = "";
        try{
            setConsulta("select fn_perfiles_sel_json(?)");
            conexion  = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, new Integer(1).shortValue());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getString(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOPerfiles.listaPerfilJSON: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
}
