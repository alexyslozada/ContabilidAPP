package com.ingenio.objetos;

public class TipoRegimen {
    private short id_regimen;
    private String nombre;

    public TipoRegimen() {
    }

    public TipoRegimen(short id_regimen, String nombre) {
        this.id_regimen = id_regimen;
        this.nombre = nombre;
    }

    public short getId_regimen() {
        return id_regimen;
    }

    public void setId_regimen(short id_regimen) {
        this.id_regimen = id_regimen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
