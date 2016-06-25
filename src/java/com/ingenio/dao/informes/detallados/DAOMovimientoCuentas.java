package com.ingenio.dao.informes.detallados;

import com.ingenio.dao.DAOGenerales;
import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.CuentasPuc;
import com.ingenio.objetos.MovimientoCuentaDetalle;
import com.ingenio.utilidades.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOMovimientoCuentas extends DAOGenerales {
    
    public final String OBJETO = "MOVCUENDETALLE";
    private final String ENCABEZADO = "SELECT _idcuenta, _cuenta, _nombre, _saldoanterior, _naturaleza FROM fn_inf_movimiento_cuenta_encabezado(?,?,?,?)";
    private final String DETALLE = "SELECT _idcuenta, _fecha, _ccosto, _consecutivo, _comentario, _tercero, _debito, _credito FROM fn_inf_movimiento_cuenta_detalle(?,?,?,?)";
    
    private Connection conexion = null;
    private PreparedStatement sentencia = null;
    private ResultSet resultado = null;
    private Utilidades u = Utilidades.get();
    private static final Logger LOG = Logger.getLogger(DAOMovimientoCuentas.class.getName());
    
    public String getMovimientos(short perInicial, short perFinal, String ctaInicial, String ctaFinal) throws ExcepcionGeneral {
        StringBuilder respuesta = new StringBuilder();
        CuentasPuc cuenta;
        MovimientoCuentaDetalle movimiento;
        respuesta.append("[");
        try {
            Queue<CuentasPuc> encabezado = getEncabezado(perInicial, perFinal, ctaInicial, ctaFinal);
            Queue<MovimientoCuentaDetalle> detalle = getDetalle(perInicial, perFinal, ctaInicial, ctaFinal);
            while (!encabezado.isEmpty()) {
                cuenta = encabezado.poll();
                do {
                    cuenta.addMovimiento(detalle.poll());
                } while (!detalle.isEmpty() && cuenta.getIdcuenta() == detalle.peek().getIdcuenta());
                respuesta.append(cuenta.toJSON());
                respuesta.append(",");
            }
            removeLastComa(respuesta);
        } catch (ExcepcionGeneral eg) {
            throw eg;
        }
        respuesta.append("]");
        return respuesta.toString();
    }
    
    private Queue<CuentasPuc> getEncabezado(short perInicial, short perFinal, String ctaInicial, String ctaFinal) throws ExcepcionGeneral {
        Queue<CuentasPuc> listado = new LinkedList<>();
        CuentasPuc cuenta;
        try {
            conexion = getConexion();
            sentencia = conexion.prepareStatement(ENCABEZADO);
            sentencia.setShort(1, perInicial);
            sentencia.setShort(2, perFinal);
            sentencia.setString(3, ctaInicial);
            sentencia.setString(4, ctaFinal);
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                cuenta = convertirCuenta(resultado);
                listado.offer(cuenta);
            }
        } catch (SQLException sqle) {
            u.generaLogServer(LOG, Level.SEVERE, "Error en DAOMovimientoCuentas.encabezado: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return listado;
    }
    
    private Queue<MovimientoCuentaDetalle> getDetalle(short perInicial, short perFinal, String ctaInicial, String ctaFinal) throws ExcepcionGeneral {
        Queue<MovimientoCuentaDetalle> listado = new LinkedList<>();
        MovimientoCuentaDetalle movimiento;
        try {
            conexion = getConexion();
            sentencia = conexion.prepareStatement(DETALLE);
            sentencia.setShort(1, perInicial);
            sentencia.setShort(2, perFinal);
            sentencia.setString(3, ctaInicial);
            sentencia.setString(4, ctaFinal);
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                movimiento = convertirMovimiento(resultado);
                listado.offer(movimiento);
            }
        } catch (SQLException sqle) {
            u.generaLogServer(LOG, Level.SEVERE, "Error en DAOMovimientoCuentas.detalle: {0}", new Object[]{sqle.getMessage()});
            throw new ExcepcionGeneral(sqle.getMessage());
        } finally {
            cierraConexion(conexion, sentencia, resultado);
        }
        return listado;
    }
    
    private void removeLastComa(StringBuilder sb) {
        sb.delete(sb.length() - 1, sb.length());
    }
    
    private CuentasPuc convertirCuenta(ResultSet rs) throws SQLException {
        CuentasPuc cuenta = new CuentasPuc();
        cuenta.setIdcuenta(rs.getShort("_idcuenta"));
        cuenta.setCuenta(rs.getString("_cuenta"));
        cuenta.setNombre(rs.getString("_nombre"));
        cuenta.setSaldoAnterior(rs.getInt("_saldoanterior"));
        cuenta.setNaturaleza(rs.getString("_naturaleza"));
        return cuenta;
    }
    
    private MovimientoCuentaDetalle convertirMovimiento(ResultSet rs) throws SQLException {
        MovimientoCuentaDetalle movimiento = new MovimientoCuentaDetalle();
        movimiento.setIdcuenta(rs.getShort("_idcuenta"));
        movimiento.setFecha(rs.getDate("_fecha").toLocalDate());
        movimiento.setCcosto(rs.getString("_ccosto"));
        movimiento.setConsecutivo(rs.getString("_consecutivo"));
        movimiento.setComentario(rs.getString("_comentario"));
        movimiento.setTercero(rs.getString("_tercero"));
        movimiento.setDebito(rs.getInt("_debito"));
        movimiento.setCredito(rs.getInt("_credito"));
        return movimiento;
    }

}
