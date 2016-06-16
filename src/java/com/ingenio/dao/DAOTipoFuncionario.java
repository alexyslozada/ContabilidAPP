package com.ingenio.dao;

import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.Paginacion;
import com.ingenio.utilidades.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.ingenio.objetos.TipoFuncionario;

public class DAOTipoFuncionario extends DAOGenerales {

    public final String OBJETO ="TIPOFUNCIONARIO";
    
    private Connection conexion = null;
    private PreparedStatement sentencia = null;
    private ResultSet resultado = null;
    private static final Logger LOG = Logger.getLogger(DAOTipoFuncionario.class.getName());
    
    public short crear(TipoFuncionario objeto) throws ExcepcionGeneral {
        short respuesta = 0;
        try{
            setConsulta("select fn_tipo_funcionario_ins(?)");
            conexion  = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setString(1, objeto.getNombre());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getShort(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOTipoFuncionario.crear: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public boolean eliminar(short id) throws ExcepcionGeneral{
        boolean respuesta = false;
        try{
            setConsulta("select fn_tipo_funcionario_del(?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, id);
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOTipoFuncionario.eliminar: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public boolean actualizar(TipoFuncionario objeto) throws ExcepcionGeneral {
        boolean respuesta = false;
        try{
            setConsulta("select fn_tipo_funcionario_upd(?,?)");
            conexion  = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, objeto.getId_tipo_funcionario());
            sentencia.setString(2, objeto.getNombre());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOTipoFuncionario.actualizar: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public String listar(short tipo, TipoFuncionario objeto, Paginacion paginacion) throws ExcepcionGeneral {
        String respuesta = "";
        try{
            setConsulta("select fn_tipo_funcionario_sel_json(?,?,?,?,?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, tipo);
            sentencia.setShort(2, objeto.getId_tipo_funcionario());
            sentencia.setShort(3, paginacion.getPagina());
            sentencia.setShort(4, paginacion.getLimite());
            sentencia.setShort(5, paginacion.getColumna_orden());
            sentencia.setString(6, paginacion.getTipo_orden());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getString(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOTipoFuncionario.listar {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
}
