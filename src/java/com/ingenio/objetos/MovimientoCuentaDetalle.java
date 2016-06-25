package com.ingenio.objetos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MovimientoCuentaDetalle {
    private short idcuenta;
    private LocalDate fecha;
    private String ccosto;
    private String consecutivo;
    private String comentario;
    private String tercero;
    private int debito;
    private int credito;

    public MovimientoCuentaDetalle() {
    }

    public MovimientoCuentaDetalle(short idcuenta, LocalDate fecha, String consecutivo, String comentario, String tercero, int debito, int credito) {
        this.idcuenta = idcuenta;
        this.fecha = fecha;
        this.consecutivo = consecutivo;
        this.comentario = comentario;
        this.tercero = tercero;
        this.debito = debito;
        this.credito = credito;
    }

    public short getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(short idcuenta) {
        this.idcuenta = idcuenta;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    
    public String getFechaFormat() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        return fecha.format(dtf);
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCcosto() {
        return ccosto;
    }

    public void setCcosto(String ccosto) {
        this.ccosto = ccosto;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getTercero() {
        return tercero;
    }

    public void setTercero(String tercero) {
        this.tercero = tercero;
    }

    public int getDebito() {
        return debito;
    }

    public void setDebito(int debito) {
        this.debito = debito;
    }

    public int getCredito() {
        return credito;
    }

    public void setCredito(int credito) {
        this.credito = credito;
    }
    
    public String toJSON(){
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"fecha\":\"")
                .append(getFechaFormat())
                .append("\",\"ccosto\":\"")
                .append(getCcosto())
                .append("\",\"consecutivo\":\"")
                .append(getConsecutivo())
                .append("\",\"comentario\":\"")
                .append(getComentario())
                .append("\",\"tercero\":\"")
                .append(getTercero())
                .append("\",\"debito\":")
                .append(getDebito())
                .append(",\"credito\":")
                .append(getCredito())
                .append("}");
        return sb.toString();
    }
}
