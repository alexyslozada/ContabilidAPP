package com.ingenio.dao;

import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.utilidades.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.ingenio.objetos.DocumentoContable;

public class DAODocumentoContable extends DAOGenerales {

    public final String OBJETO = "DOCUMENTOCONTABLE";
    private Connection conexion = null;
    private PreparedStatement sentencia = null;
    private ResultSet resultado = null;
    private static final Logger LOG = Logger.getLogger(DAODocumentoContable.class.getName());

    public short crear(DocumentoContable objeto) throws ExcepcionGeneral {
        short respuesta = 0;
        try {
            setConsulta("select fn_documento_contable_ins(?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setString(1, objeto.getAbreviatura());
            sentencia.setString(2, objeto.getDocumento());
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getShort(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAODocumentoContable.crear: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public boolean eliminar(short id) throws ExcepcionGeneral {
        boolean respuesta = false;
        try {
            setConsulta("select fn_documento_contable_del(?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, id);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAODocumentoContable.eliminar: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public boolean actualizar(DocumentoContable objeto) throws ExcepcionGeneral {
        boolean respuesta = false;
        try {
            setConsulta("select fn_documento_contable_upd(?,?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, objeto.getId_documento());
            sentencia.setString(2, objeto.getAbreviatura());
            sentencia.setString(3, objeto.getDocumento());
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAODocumentoContable.actualizar: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
    
    public String getXAbrXId(short tipo, DocumentoContable documento) throws ExcepcionGeneral {
        String respuesta = null;
        try{
            setConsulta("select fn_documento_contable_sel_xabrxid_json(?,?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, tipo);
            sentencia.setString(2, documento.getAbreviatura());
            sentencia.setShort(3, documento.getId_documento());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getString(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAODocumentoContable.getXAbrXId {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public String listar() throws ExcepcionGeneral {
        String respuesta = "";
        try {
            setConsulta("select fn_documento_contable_sel_json()");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getString(1);
            }
        } catch (SQLException sqle) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAODocumentoContable.listar {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
}
