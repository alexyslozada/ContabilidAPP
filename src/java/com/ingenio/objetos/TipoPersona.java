package com.ingenio.objetos;

public class TipoPersona {
    private short id_tipo_persona;
    private String nombre;

    public TipoPersona() {
    }

    public TipoPersona(short id_tipo_persona, String nombre) {
        this.id_tipo_persona = id_tipo_persona;
        this.nombre = nombre;
    }

    public short getId_tipo_persona() {
        return id_tipo_persona;
    }

    public void setId_tipo_persona(short id_tipo_persona) {
        this.id_tipo_persona = id_tipo_persona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
