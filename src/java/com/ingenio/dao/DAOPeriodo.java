package com.ingenio.dao;

import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.Periodo;
import com.ingenio.utilidades.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOPeriodo extends DAOGenerales {

    public final String OBJETO = "PERIODOS";
    private Connection conexion = null;
    private PreparedStatement sentencia = null;
    private ResultSet resultado = null;
    private static final Logger LOG = Logger.getLogger(DAOPeriodo.class.getName());
    
    public short crear(Periodo periodo) throws ExcepcionGeneral {
        short respuesta = 0;
        try{
            setConsulta("select fn_periodo_crear(?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, periodo.getAnio());
            sentencia.setShort(2, periodo.getMes());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getShort(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOPeriodo.crear: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
    
    public Periodo getPeriodoXFecha(Periodo periodo) throws ExcepcionGeneral {
        Periodo respuesta = null;
        try{
            setConsulta("select id_periodo_contable, anio, mes, abierto, fecha_cierre from fn_periodo_get_xfecha(?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, periodo.getAnio());
            sentencia.setShort(2, periodo.getMes());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = new Periodo();
                respuesta.setId_periodo_contable(resultado.getShort(1));
                respuesta.setAnio(resultado.getShort(2));
                respuesta.setMes(resultado.getShort(3));
                respuesta.setAbierto(resultado.getBoolean(4));
                respuesta.setFecha_cierre(resultado.getDate(5));
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOPeriodo.getPeriodoXFecha: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
    
    public boolean cerrarPeriodo(short periodo){
        boolean respuesta = false;
        try {
            setConsulta("select fn_periodo_cerrar(?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, periodo);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCierreContable.cerrarPeriodo: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
    
    public boolean periodoCierreIsValid(short year, short month) throws ExcepcionGeneral {
        boolean respuesta = false;
        try {
            setConsulta("select fn_periodo_cierre_isvalid(?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, year);
            sentencia.setShort(2, month);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOPeriodo.periodoCierreIsValid: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
}
