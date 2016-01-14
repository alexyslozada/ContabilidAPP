package com.ingenio.objetos;

public class CuentasPuc {

    private short idcuenta;
    private String cuenta;
    private String nombre;
    private short idcuentapadre;
    private short idclasecuenta;
    private short idnivelcuenta;
    private boolean maneja_centrocosto;

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

    
}
