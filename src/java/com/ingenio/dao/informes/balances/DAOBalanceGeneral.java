package com.ingenio.dao.informes.balances;

import com.ingenio.dao.DAOGenerales;
import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.utilidades.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOBalanceGeneral extends DAOGenerales {
    
    public final String OBJETO = "INFBALANCEGENERAL";
    private final String consulta = "SELECT * FROM fn_inf_balance_general_json(?,?,?)";
    
    private Connection conexion = null;
    private PreparedStatement sentencia = null;
    private ResultSet resultado = null;
    private Utilidades u = Utilidades.get();
    private static final Logger LOG = Logger.getLogger(DAOBalanceGeneral.class.getName());
    
    public String getBalance(short tipo, short periodo, short nivel) {
        String respuesta = "";
        try {
            conexion = getConexion();
            sentencia = conexion.prepareStatement(consulta);
            sentencia.setShort(1, tipo);
            sentencia.setShort(2, periodo);
            sentencia.setShort(3, nivel);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                respuesta = resultado.getString(1);
            }
        } catch (SQLException sqle) {
            u.generaLogServer(LOG, Level.SEVERE, "Error en DAOBalanceGeneral.getBalance: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return respuesta;
    }
}
