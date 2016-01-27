package com.ingenio.dao;

import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.RegistroContableEncabezado;
import com.ingenio.utilidades.Utilidades;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAORegistroContableEncabezado extends DAOGenerales {

    public final String OBJETO = "REGISTROCONTABLE";
    private Connection conexion = null;
    private PreparedStatement sentencia = null;
    private ResultSet resultado = null;
    private static final Logger LOG = Logger.getLogger(DAORegistroContableEncabezado.class.getName());
    
    public short crear(RegistroContableEncabezado registro) throws ExcepcionGeneral {
        short respuesta = 0;
        try{
            setConsulta("select fn_regconenc_ins(?,?,?,?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setShort(1, registro.getDocumento().getId_documento());
            sentencia.setDate(2, (Date) registro.getFechaMovimiento().getTime());
            sentencia.setString(3, registro.getComentario());
            sentencia.setShort(4, registro.getPeriodo().getId_periodo_contable());
            sentencia.setShort(5, registro.getUsuario().getIdusuario());
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                respuesta = resultado.getShort(1);
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAORegistroContableEncabezado.crear: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
        
    }
    
}
