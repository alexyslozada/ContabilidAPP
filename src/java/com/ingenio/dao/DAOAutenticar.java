package com.ingenio.dao;

import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.Perfil;
import com.ingenio.objetos.Usuario;
import com.ingenio.utilidades.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOAutenticar extends DAOGenerales{
    private static final Logger LOG = Logger.getLogger(DAOAutenticar.class.getName());
    
    public Usuario login(String ide, String pwd) throws ExcepcionGeneral{
        Usuario usuario = new Usuario();
        Perfil perfil   = new Perfil();
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try{
            setConsulta("select * from fn_autenticar(?,?)");
            conexion = getConexion();
            sentencia = conexion.prepareStatement(getConsulta());
            sentencia.setString(1, ide);
            sentencia.setString(2, pwd);
            resultado = sentencia.executeQuery();
            if(resultado.next()){

                perfil.setIdperfil(resultado.getShort("idpe"));
                perfil.setNombre(resultado.getString("perf"));
                perfil.setActivo(true);

                usuario.setIdusuario(resultado.getShort("idus"));
                usuario.setIdentificacion(resultado.getString("iden"));
                usuario.setNombre(resultado.getString("nomb"));
                usuario.setCorreo(resultado.getString("corr"));
                usuario.setPerfil(perfil);
                usuario.setClave("Ninguna");
                usuario.setActivo(true);

            } else {
                throw new ExcepcionGeneral("No se encontró información");
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.WARNING, "Excepcion en DAOAutenticar.login() {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return usuario;
    }
}
