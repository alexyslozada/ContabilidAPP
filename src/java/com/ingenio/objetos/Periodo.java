package com.ingenio.objetos;

import java.util.Calendar;

public class Periodo {
    private short id_periodo_contable;
    private short anio;
    private short mes;
    private boolean abierto;
    private Calendar fecha_cierre;

    public Periodo() {
    }

    public Periodo(short anio, short mes) {
        this.anio = anio;
        this.mes = mes;
    }

    public Periodo(short anio, short mes, boolean abierto) {
        this.anio = anio;
        this.mes = mes;
        this.abierto = abierto;
    }

    public short getId_periodo_contable() {
        return id_periodo_contable;
    }

    public void setId_periodo_contable(short id_periodo_contable) {
        this.id_periodo_contable = id_periodo_contable;
    }

    public short getAnio() {
        return anio;
    }

    public void setAnio(short anio) {
        this.anio = anio;
    }

    public short getMes() {
        return mes;
    }

    public void setMes(short mes) {
        this.mes = mes;
    }

    public boolean isAbierto() {
        return abierto;
    }

    public void setAbierto(boolean abierto) {
        this.abierto = abierto;
    }

    public Calendar getFecha_cierre() {
        return fecha_cierre;
    }

    public void setFecha_cierre(Calendar fecha_cierre) {
        this.fecha_cierre = fecha_cierre;
    }
    
    
}
