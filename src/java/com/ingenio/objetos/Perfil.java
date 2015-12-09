package com.ingenio.objetos;

public class Perfil {
    private short idperfil;
    private String nombre;
    private boolean activo;

    public Perfil() {
    }

    public Perfil(short idperfil, String nombre, boolean activo) {
        this.idperfil = idperfil;
        this.nombre = nombre;
        this.activo = activo;
    }

    public short getIdperfil() {
        return idperfil;
    }

    public void setIdperfil(short idperfil) {
        this.idperfil = idperfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public String toJSON(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"idperfil\":");
        sb.append(idperfil);
        sb.append(",");
        sb.append("\"perfil\":\"");
        sb.append(nombre);
        sb.append("\",");
        sb.append("\"activo\":");
        sb.append(activo);
        sb.append("}");
        return sb.toString();
    }
}
