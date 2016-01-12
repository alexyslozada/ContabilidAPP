package com.ingenio.dao;
import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.utilidades.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.ingenio.objetos.EmpresaFuncionario;

public class DAOEmpresaFuncionarios extends DAOGenerales {
    
    public final String OBJETO = "EMP-FUNCIONARIOS";
    private Connection conexion = null;
    private PreparedStatement sentencia = null;
    private ResultSet resultado = null;
    private static final Logger LOG = Logger.getLogger(DAOEmpresaFuncionarios.class.getName());
    
    public short crear(EmpresaFuncionario objeto) throws ExcepcionGeneral {
        short respuesta = 0;
        try{
            setConsulta("select fn_empresa_funcionarios_ins(?,?,?,?,?,?)");
            conexion  = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, objeto.getTipoIdentificacion().getIdtipodocumento());
            sentencia.setString(2, objeto.getIdentificacion());
            sentencia.setString(3, objeto.getDigito_verificacion());
            sentencia.setString(4, objeto.getNombre());
            sentencia.setShort(5, objeto.getTipoFuncionario().getId_tipo_funcionario());
            sentencia.setBoolean(6, objeto.isVigente());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getShort(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOEmpresaFuncionarios.crear: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public boolean eliminar(short id) throws ExcepcionGeneral{
        boolean respuesta = false;
        try{
            setConsulta("select fn_empresa_funcionarios_del(?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, id);
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOEmpresaFuncionarios.eliminar: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public boolean actualizar(EmpresaFuncionario objeto) throws ExcepcionGeneral {
        boolean respuesta = false;
        try{
            setConsulta("select fn_empresa_funcionarios_upd(?,?,?,?,?,?,?)");
            conexion  = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, objeto.getId_funcionario());
            sentencia.setShort(2, objeto.getTipoIdentificacion().getIdtipodocumento());
            sentencia.setString(3, objeto.getIdentificacion());
            sentencia.setString(4, objeto.getDigito_verificacion());
            sentencia.setString(5, objeto.getNombre());
            sentencia.setShort(6, objeto.getTipoFuncionario().getId_tipo_funcionario());
            sentencia.setBoolean(7, objeto.isVigente());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getBoolean(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOEmpresaFuncionarios.actualizar: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }

    public String listar(short tipo, EmpresaFuncionario objeto) throws ExcepcionGeneral {
        String respuesta = "";
        try{
            setConsulta("select fn_empresa_funcionarios_sel_json(?,?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, tipo);
            sentencia.setShort(2, objeto.getId_funcionario());
            sentencia.setString(3, objeto.getIdentificacion());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getString(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOEmpresaFuncionarios.listar {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
}
