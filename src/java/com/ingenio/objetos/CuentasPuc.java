package com.ingenio.objetos;

import java.util.ArrayList;
import java.util.List;

public class CuentasPuc {

    private short idcuenta;
    private String cuenta;
    private String nombre;
    private short idcuentapadre;
    private short idclasecuenta;
    private short idnivelcuenta;
    private boolean maneja_centrocosto;
    private int saldoAnterior;
    private int saldoActual;
    private String naturaleza;
    private List<MovimientoCuentaDetalle> movimientos = new ArrayList<>();

    public CuentasPuc() {
    }

    public CuentasPuc(short idcuenta, String cuenta, String nombre, short idcuentapadre, short idclasecuenta, short idnivelcuenta, boolean centrocosto) {
        this.idcuenta = idcuenta;
        this.cuenta = cuenta;
        this.nombre = nombre;
        this.idcuentapadre = idcuentapadre;
        this.idclasecuenta = idclasecuenta;
        this.idnivelcuenta = idnivelcuenta;
        this.maneja_centrocosto = centrocosto;
    }

    public CuentasPuc(String cuenta, String nombre, short idnivelcuenta, boolean centrocosto) {
        this.cuenta = cuenta;
        this.nombre = nombre;
        this.idnivelcuenta = idnivelcuenta;
        this.maneja_centrocosto = centrocosto;
    }

    public CuentasPuc(short idcuenta, String nombre, boolean maneja_centrocosto) {
        this.idcuenta = idcuenta;
        this.nombre = nombre;
        this.maneja_centrocosto = maneja_centrocosto;
    }

    public CuentasPuc(String cuenta, String nombre, boolean centrocosto) {
        this.cuenta = cuenta;
        this.nombre = nombre;
        this.maneja_centrocosto = centrocosto;
    }

    public short getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(short idcuenta) {
        this.idcuenta = idcuenta;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public short getIdcuentapadre() {
        return idcuentapadre;
    }

    public void setIdcuentapadre(short idcuentapadre) {
        this.idcuentapadre = idcuentapadre;
    }

    public short getIdclasecuenta() {
        return idclasecuenta;
    }

    public void setIdclasecuenta(short idclasecuenta) {
        this.idclasecuenta = idclasecuenta;
    }

    public short getIdnivelcuenta() {
        return idnivelcuenta;
    }

    public void setIdnivelcuenta(short idnivelcuenta) {
        this.idnivelcuenta = idnivelcuenta;
    }

    public boolean isManejaCentroCosto() {
        return maneja_centrocosto;
    }

    public void setManejaCentroCosto(boolean centrocosto) {
        this.maneja_centrocosto = centrocosto;
    }

    public int getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(int saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public int getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(int saldoActual) {
        this.saldoActual = saldoActual;
    }

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }
    
    public void addMovimiento(MovimientoCuentaDetalle movimiento) {
        movimientos.add(movimiento);
    }
    
    public void setMovimientos(List<MovimientoCuentaDetalle> movimientos) {
        this.movimientos = movimientos;
    }
    
    public List<MovimientoCuentaDetalle> getMovimientos() {
        return movimientos;
    }
    
    public String toJSON(){
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"id\":")
                .append(idcuenta)
                .append(",\"cuenta\":");
        if(cuenta != null){
            sb.append("\"")
                    .append(cuenta)
                    .append("\"");
        } else {
            sb.append(cuenta);
        }
        sb.append(",\"nombre\":");
        if(nombre != null){
            sb.append("\"")
                    .append(nombre)
                    .append("\"");
        } else {
            sb.append(nombre);
        }
        sb.append(",\"idcuentapadre\":")
                .append(idcuentapadre)
                .append(",\"idclasecuenta\":")
                .append(idclasecuenta)
                .append(",\"idnivelcuenta\":")
                .append(idnivelcuenta)
                .append(",\"maneja_centrocosto\":")
                .append(maneja_centrocosto)
                .append(",\"saldo_anterior\":")
                .append(getSaldoAnterior())
                .append(",\"saldo_actual\":")
                .append(getSaldoActual())
                .append(",\"naturaleza\":");
        if (getNaturaleza() != null) {
          sb.append("\"")
            .append(getNaturaleza())
            .append("\"");
        } else {
          sb.append(getNaturaleza());
        }
        sb.append(",\"movimientos\":[");
        for (MovimientoCuentaDetalle m : movimientos) {
            sb.append(m.toJSON())
                    .append(",");
        }
        if (movimientos.size() > 0) {
            sb.delete(sb.length()-1, sb.length());
        }
        sb.append("]}");
        return sb.toString();
    }
}
