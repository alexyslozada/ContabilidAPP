package com.ingenio.objetos;

public class Ciudad {
    private short idciudad;
    private String codigo;
    private String nombre;

    public Ciudad() {
    }

    public Ciudad(short idciudad, String codigo, String nombre) {
        this.idciudad = idciudad;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public short getIdciudad() {
        return idciudad;
    }

    public void setIdciudad(short idciudad) {
        this.idciudad = idciudad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
