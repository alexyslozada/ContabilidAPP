package com.ingenio.dao;

import com.ingenio.utilidades.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.postgresql.ds.PGPoolingDataSource;

public final class DAO {

    private static final DAO dao = new DAO();
    private PGPoolingDataSource piscina = null;
    private static final Logger LOG = Logger.getLogger(DAO.class.getName());

    private DAO(){}

    public static DAO getDAO(){
        return dao;
    }

    public void creaPiscina(ServletContext ctx){
        String servidor = ctx.getInitParameter("servidor");
        int puertobd = Integer.parseInt(ctx.getInitParameter("puertobd"));
        String basededa = ctx.getInitParameter("basededatos");
        String usuariob = ctx.getInitParameter("usuariobd");
        String clavebda = ctx.getInitParameter("clavebd");
        int conexionesiniciales = Integer.parseInt(ctx.getInitParameter("conexionesiniciales"));
        int conexionesmaximas = Integer.parseInt(ctx.getInitParameter("conexionesmaximas"));
        piscina = new PGPoolingDataSource();
        piscina.setDataSourceName("Conexion a la Base Contabilidad");
        piscina.setServerName(servidor);
        piscina.setPortNumber(puertobd);
        piscina.setDatabaseName(basededa);
        piscina.setUser(usuariob);
        piscina.setPassword(clavebda);
        piscina.setInitialConnections(conexionesiniciales);
        piscina.setMaxConnections(conexionesmaximas);
    }
    
    public void cierraPiscina(){
        piscina.close();
    }

    public Connection getConexion() throws SQLException{
        if (piscina == null) {
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAO. No fue posible cargar la piscina de conexiones", null);
            return null;
        }
        return piscina.getConnection();
    }

    public Calendar getFechaServerBD() throws SQLException{
        Calendar fecha = Calendar.getInstance();
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try{
            String consulta = "select timezone('COT'::text, now())::timestamp";
            conexion = getConexion();
            sentencia = conexion.prepareStatement(consulta);
            resultado = sentencia.executeQuery();
            if(resultado.next()){
                fecha.setTimeInMillis(resultado.getTimestamp(1).getTime());
            }
        } catch (SQLException sqle){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error al consultar fecha servidor. DAOGenerales {0}", new Object[]{sqle.getMessage()});
            throw new SQLException(sqle);
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (sentencia != null) {
                    sentencia.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException sqle) {}
        }
        return fecha;
    }

}
