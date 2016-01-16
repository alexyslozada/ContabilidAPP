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
import com.ingenio.objetos.CuentasPuc;

public class DAOCuentasPuc extends DAOGenerales {

    public final String OBJETO = "CUENTASPUC";
    
    private Connection conexion = null;
    private PreparedStatement sentencia = null;
    private ResultSet resultado = null;
    private static final Logger LOG = Logger.getLogger(DAOCuentasPuc.class.getName());

    public short crear(CuentasPuc objeto) throws ExcepcionGeneral {
        short respuesta = 0;
        try {
            setConsulta("select fn_cuentas_puc_ins(?,?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setString(1, objeto.getCuenta());
            sentencia.setString(2, objeto.getNombre());
            sentencia.setBoolean(3, objeto.isManejaCentroCosto());
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getShort(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCuentasPuc.crear: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public boolean eliminar(short id) throws ExcepcionGeneral {
        boolean respuesta = false;
        try {
            setConsulta("select fn_cuentas_puc_del(?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, id);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCuentasPuc.eliminar: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public boolean actualizar(CuentasPuc objeto) throws ExcepcionGeneral {
        boolean respuesta = false;
        try {
            setConsulta("select fn_cuentas_puc_upd(?,?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, objeto.getIdcuenta());
            sentencia.setString(2, objeto.getNombre());
            sentencia.setBoolean(3, objeto.isManejaCentroCosto());
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCuentasPuc.actualizar: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    /**
     * Consulta un solo registro de las cuentas puc. Puede ser por Id o por Cuenta
     * @param tipo Tipo de consulta 1 = Por cuenta, 2 = Por Id.
     * @param objeto Objeto CuentasPuc de donde se consultará la información.
     * @return Un objeto JSON con la información consultada. O null si no encuentra información.
     * @throws ExcepcionGeneral 
     */
    public String getCuentaXCuentaOId(short tipo, CuentasPuc objeto) throws ExcepcionGeneral {
        String respuesta = "";
        try{
            setConsulta("select fn_cuentas_puc_sel_xcuenta_o_xid_json(?,?,?)");
            conexion  = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, tipo);
            sentencia.setShort(2, objeto.getIdcuenta());
            sentencia.setString(3, objeto.getCuenta());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getString(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCuentasPuc.getCuentaXCuentaOId {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
    
    public String listar(short tipo, CuentasPuc objeto, Paginacion paginacion) throws ExcepcionGeneral {
        String respuesta = "";
        try {
            setConsulta("select fn_cuentas_puc_sel_json(?,?,?,?,?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, tipo);
            sentencia.setString(2, objeto.getNombre());
            sentencia.setShort(3, paginacion.getPagina());
            sentencia.setShort(4, paginacion.getLimite());
            sentencia.setShort(5, paginacion.getColumna_orden());
            sentencia.setString(6, paginacion.getTipo_orden());
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getString(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCuentasPuc.listar {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
}
