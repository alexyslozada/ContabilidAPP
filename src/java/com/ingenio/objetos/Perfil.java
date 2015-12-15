package com.ingenio.objetos;

import java.util.ArrayList;
import java.util.List;

public class Perfil {
    private short idperfil;
    private String nombre;
    private boolean activo;
    private List<ObjetoXPerfil> objetos = new ArrayList<>();

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

    public List<ObjetoXPerfil> getObjetos() {
        return objetos;
    }

    public void setObjetos(List<ObjetoXPerfil> objetos) {
        this.objetos = objetos;
    }
    
    public String toJSON(){
        StringBuilder sb = new StringBuilder();
        sb.append("{")
          .append("\"idperfil\":")
          .append(idperfil)
          .append(",")
          .append("\"perfil\":\"")
          .append(nombre)
          .append("\",")
          .append("\"activo\":")
          .append(activo)
          .append(", ")
          .append("\"objetos\": ")
          .append("[");
        for (ObjetoXPerfil objeto : objetos) {
            sb.append(objeto.toJSON());
            sb.append(",");
        }
        if(objetos.size() > 0){
            sb.delete(sb.length()-1, sb.length());
        }
        sb.append("]")
          .append("}");
        return sb.toString();
    }
}
