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
import com.ingenio.objetos.Tercero;

public class DAOTerceros extends DAOGenerales {

    public final String OBJETO = "TERCEROS";
    private Connection conexion = null;
    private PreparedStatement sentencia = null;
    private ResultSet resultado = null;
    private static final Logger LOG = Logger.getLogger(DAOTerceros.class.getName());

    public short crear(Tercero objeto) throws ExcepcionGeneral {
        short respuesta = 0;
        try {
            setConsulta("select fn_terceros_ins(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, objeto.getTipo_identificacion());
            sentencia.setString(2, objeto.getNumero_identificacion());
            sentencia.setString(3, objeto.getDigito_verificacion());
            sentencia.setShort(4, objeto.getTipo_persona());
            sentencia.setString(5, objeto.getRazon_social());
            sentencia.setString(6, objeto.getPrimer_apellido());
            sentencia.setString(7, objeto.getSegundo_apellido());
            sentencia.setString(8, objeto.getPrimer_nombre());
            sentencia.setString(9, objeto.getSegundo_nombre());
            sentencia.setString(10, objeto.getDireccion());
            sentencia.setString(11, objeto.getTelefono());
            sentencia.setString(12, objeto.getDepto_residencia());
            sentencia.setShort(13, objeto.getCiudad_residencia());
            sentencia.setString(14, objeto.getCorreo());
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getShort(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOTerceros.crear: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public boolean eliminar(short id) throws ExcepcionGeneral {
        boolean respuesta = false;
        try {
            setConsulta("select fn_terceros_del(?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, id);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOTerceros.eliminar: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public boolean actualizar(Tercero objeto) throws ExcepcionGeneral {
        boolean respuesta = false;
        try {
            setConsulta("select fn_terceros_upd(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, objeto.getId_tercero());
            sentencia.setShort(2, objeto.getTipo_identificacion());
            sentencia.setString(3, objeto.getNumero_identificacion());
            sentencia.setString(4, objeto.getDigito_verificacion());
            sentencia.setShort(5, objeto.getTipo_persona());
            sentencia.setString(6, objeto.getRazon_social());
            sentencia.setString(7, objeto.getPrimer_apellido());
            sentencia.setString(8, objeto.getSegundo_apellido());
            sentencia.setString(9, objeto.getPrimer_nombre());
            sentencia.setString(10, objeto.getSegundo_nombre());
            sentencia.setString(11, objeto.getDireccion());
            sentencia.setString(12, objeto.getTelefono());
            sentencia.setString(13, objeto.getDepto_residencia());
            sentencia.setShort(14, objeto.getCiudad_residencia());
            sentencia.setString(15, objeto.getCorreo());
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOTerceros.actualizar: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
    
    public String getXId(short tipo, Tercero objeto) throws ExcepcionGeneral {
        String respuesta = "";
        try{
            setConsulta("select fn_terceros_sel_xide(?,?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, tipo);
            sentencia.setString(2, objeto.getNumero_identificacion());
            sentencia.setShort(3, objeto.getId_tercero());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getString(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOTerceros.listar {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public String listar(short tipo, Tercero objeto, Paginacion paginacion) throws ExcepcionGeneral {
        String respuesta = "";
        try {
            setConsulta("select fn_terceros_sel_json(?,?,?,?,?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, tipo);
            sentencia.setString(2, objeto.getRazon_social());
            sentencia.setShort(3, paginacion.getPagina());
            sentencia.setShort(4, paginacion.getLimite());
            sentencia.setShort(5, paginacion.getColumna_orden());
            sentencia.setString(6, paginacion.getTipo_orden());
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getString(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOTerceros.listar {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
}
