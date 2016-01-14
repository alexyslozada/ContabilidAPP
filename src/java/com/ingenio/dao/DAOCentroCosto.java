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
import com.ingenio.objetos.CentroCosto;

public class DAOCentroCosto extends DAOGenerales {

    public final String OBJETO = "CENTROCOSTO";
    private Connection conexion = null;
    private PreparedStatement sentencia = null;
    private ResultSet resultado = null;
    private static final Logger LOG = Logger.getLogger(DAOCentroCosto.class.getName());

    public short crear(CentroCosto objeto) throws ExcepcionGeneral {
        short respuesta = 0;
        try {
            setConsulta("select fn_centros_costo_ins(?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setString(1, objeto.getCodigo());
            sentencia.setString(2, objeto.getNombre());
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getShort(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCentroCosto.crear: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public boolean eliminar(short id) throws ExcepcionGeneral {
        boolean respuesta = false;
        try {
            setConsulta("select fn_centros_costo_del(?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, id);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCentroCosto.eliminar: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public boolean actualizar(CentroCosto objeto) throws ExcepcionGeneral {
        boolean respuesta = false;
        try {
            setConsulta("select fn_centros_costo_upd(?,?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, objeto.getId_centro_costo());
            sentencia.setString(2, objeto.getCodigo());
            sentencia.setString(3, objeto.getNombre());
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCentroCosto.actualizar: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
    
    public String getCentroPorCodigoOId(short tipo, CentroCosto objeto) throws ExcepcionGeneral {
        String respuesta = "";
        try{
            setConsulta("SELECT fn_centros_costo_sel_xcodigoxid(?,?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, tipo);
            sentencia.setString(2, objeto.getCodigo());
            sentencia.setShort(3, objeto.getId_centro_costo());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getString(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCentroCosto.getCentroPorCodigoOId {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        }
        return respuesta;
    }

    public String listar(short tipo, CentroCosto objeto, Paginacion paginacion) throws ExcepcionGeneral {
        String respuesta = "";
        try {
            setConsulta("select fn_centros_costo_sel_json(?,?,?,?,?,?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, tipo);
            sentencia.setString(2, objeto.getCodigo());
            sentencia.setString(3, objeto.getNombre());
            sentencia.setShort(4, paginacion.getPagina());
            sentencia.setShort(5, paginacion.getLimite());
            sentencia.setShort(6, paginacion.getColumna_orden());
            sentencia.setString(7, paginacion.getTipo_orden());
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getString(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCentroCosto.listar {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
}
