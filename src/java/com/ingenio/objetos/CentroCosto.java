package com.ingenio.objetos;

import com.ingenio.utilidades.Utilidades;

public class CentroCosto {

    private short id_centro_costo;
    private String codigo;
    private String nombre;

    public CentroCosto() {
    }

    public CentroCosto(short id_centro_costo, String codigo, String nombre) {
        this.id_centro_costo = id_centro_costo;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public CentroCosto(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
    
    public short getId_centro_costo() {
        return id_centro_costo;
    }

    public void setId_centro_costo(short id_centro_costo) {
        this.id_centro_costo = id_centro_costo;
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

    public String toJSON(){
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"id\":")
                .append(id_centro_costo)
                .append(",\"codigo\":");
        Utilidades.get().appendJSON(sb, codigo);;
        sb.append(",\"nombre\":");
        Utilidades.get().appendJSON(sb, nombre);
        sb.append("}");
        return sb.toString();
    }
}
