package com.ingenio.dao;

import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.utilidades.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOCierreContable extends DAOGenerales {
    
    public final String OBJETO = "CIERRECONTABLE";
    private Connection conexion = null;
    private PreparedStatement sentencia = null;
    private ResultSet resultado = null;
    private static final Logger LOG = Logger.getLogger(DAOCierreContable.class.getName());
    
    
    
    public boolean periodoCerrado(short periodo) throws ExcepcionGeneral {
        boolean cerrado = true; // inicia en el peor escenario
        try {
            setConsulta("select fn_cierre_enc_isclose(?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, periodo);
            resultado = sentencia.executeQuery();
            if (resultado.next()){
                cerrado = resultado.getBoolean(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCierreContable.periodoCerrado: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return cerrado;
    }
    
    public boolean movimientosBalanceados(short periodo) throws ExcepcionGeneral {
        boolean balanceado = false; // inicia en el peor escenario
        try {
            setConsulta("select fn_cierre_det_isbalanced(?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, periodo);
            resultado = sentencia.executeQuery();
            if (resultado.next()){
                balanceado = resultado.getBoolean(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCierreContable.movimientosBalanceados: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return balanceado;
    }
    
    public short insertarCierreEncabezado(short idusuario, short periodo) throws ExcepcionGeneral {
        short respuesta = 0;
        try {
            setConsulta("select fn_cierre_enc_ins(?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, idusuario);
            sentencia.setShort(2, periodo);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getShort(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCierreContable.insertarCierreEncabezado: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
    
    public boolean insertaCuentasDetalle(short encabezado, short periodo) throws ExcepcionGeneral {
        boolean respuesta = false;
        try {
            setConsulta("select fn_cierre_det_ins_niv5(?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, encabezado);
            sentencia.setShort(2, periodo);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCierreContable.insertarCuentasDetalle: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
    
    public boolean insertaCuentasNivel(short encabezado, short nivel){
        boolean respuesta = false;
        try {
            setConsulta("select fn_cierre_det_ins_niv(?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, encabezado);
            sentencia.setShort(2, nivel);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCierreContable.insertaCuentasNivel: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
    
    public boolean cierreBalanceado(short encabezado){
        boolean respuesta = false;
        try {
            setConsulta("select fn_cierre_contable_isbalanced(?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, encabezado);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCierreContable.insertaCuentasNivel: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
    
    public boolean poblarSaldoAnterior(short encabezado, short periodo){
        boolean respuesta = false;
        try {
            setConsulta("select fn_cierre_det_ins_saldoanterior(?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, encabezado);
            sentencia.setShort(2, periodo);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCierreContable.poblarSaldoAnterior: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
    
    public boolean calcularSaldoNuevo(short encabezado){
        boolean respuesta = false;
        try {
            setConsulta("select fn_cierre_det_ins_saldonuevo(?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, encabezado);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCierreContable.calcularSaldoNuevo: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
    
}
