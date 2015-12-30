package com.ingenio.dao;

import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.utilidades.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.ingenio.objetos.Empresa;

public class DAOEmpresa extends DAOGenerales {
    
    public final String OBJETO ="EMPRESA";
    
    private Connection conexion = null;
    private PreparedStatement sentencia = null;
    private ResultSet resultado = null;
    private static final Logger LOG = Logger.getLogger(DAOEmpresa.class.getName());
    
    public boolean upsert(Empresa objeto) throws ExcepcionGeneral {
        boolean respuesta = false;
        try{
            setConsulta("select fn_empresa_upsert(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            conexion  = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, objeto.getTipo_identificacion().getIdtipodocumento());
            sentencia.setString(2, objeto.getNumero_identificacion());
            sentencia.setString(3, objeto.getDigito_verificacion());
            sentencia.setString(4, objeto.getNombre());
            sentencia.setString(5, objeto.getDireccion());
            sentencia.setString(6, objeto.getTelefono());
            sentencia.setString(7, objeto.getDepartamento().getCodigo());
            sentencia.setShort(8, objeto.getDepartamento().getCiudades().get(0).getIdciudad());
            sentencia.setString(9, objeto.getDireccion_web());
            sentencia.setString(10, objeto.getCorreo());
            sentencia.setString(11, objeto.getActividad());
            sentencia.setBoolean(12, objeto.isAutorretenedor());
            sentencia.setShort(13, objeto.getTipo_persona().getId_tipo_persona());
            sentencia.setShort(14, objeto.getTipo_regimen().getId_regimen());
            sentencia.setString(15, objeto.getLogo());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOEmpresa.upsert: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public String listar() throws ExcepcionGeneral {
        String respuesta = "";
        try{
            setConsulta("select fn_empresa_sel_json()");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getString(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOEmpresa.listar {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
}
