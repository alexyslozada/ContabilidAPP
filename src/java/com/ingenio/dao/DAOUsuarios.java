package com.ingenio.dao;

import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.Paginacion;
import com.ingenio.objetos.Usuario;
import com.ingenio.utilidades.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOUsuarios extends DAOGenerales{
    private Connection conexion = null;
    private PreparedStatement sentencia = null;
    private ResultSet resultado = null;
    private static final Logger LOG = Logger.getLogger(DAOUsuarios.class.getName());
    
    public short crear(Usuario usuario) throws ExcepcionGeneral{
        short respuesta = 0;
        try{
            setConsulta("select fn_usuarios_ins(?,?,?,?,?)");
            conexion  = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setString(1, usuario.getIdentificacion());
            sentencia.setString(2, usuario.getNombre());
            sentencia.setString(3, usuario.getCorreo());
            sentencia.setString(4, usuario.getClave());
            sentencia.setShort(5, usuario.getPerfil().getIdperfil());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getShort(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOUsuarios.crear: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public String listar(short tipo, Usuario usuario, Paginacion paginacion) throws ExcepcionGeneral{
        String respuesta = "";
        try{
            setConsulta("select fn_usuarios_sel_json(?,?,?,?,?,?,?,?,?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, tipo);
            sentencia.setShort(2, usuario.getIdusuario());
            sentencia.setString(3, usuario.getIdentificacion());
            sentencia.setString(4, usuario.getNombre());
            sentencia.setShort(5, usuario.getPerfil().getIdperfil());
            sentencia.setBoolean(6, usuario.isActivo());
            sentencia.setShort(7, paginacion.getPagina());
            sentencia.setShort(8, paginacion.getLimite());
            sentencia.setShort(9, paginacion.getColumna_orden());
            sentencia.setString(10, paginacion.getTipo_orden());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getString(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOUsuarios.listar {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
}
