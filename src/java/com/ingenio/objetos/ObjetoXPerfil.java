package com.ingenio.objetos;

public class ObjetoXPerfil {
    private short idPerfil;
    private String objeto;
    private boolean insertar;
    private boolean modificar;
    private boolean borrar;
    private boolean consultar;

    public short getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(short idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public boolean isInsertar() {
        return insertar;
    }

    public void setInsertar(boolean insertar) {
        this.insertar = insertar;
    }

    public boolean isModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }

    public boolean isBorrar() {
        return borrar;
    }

    public void setBorrar(boolean borrar) {
        this.borrar = borrar;
    }

    public boolean isConsultar() {
        return consultar;
    }

    public void setConsultar(boolean consultar) {
        this.consultar = consultar;
    }
    
    public String toJSON(){
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"idperfil\": ")
                .append(idPerfil)
                .append(", ")
                .append("\"objeto\": \"")
                .append(objeto)
                .append("\", ")
                .append("\"insertar\": ")
                .append(insertar)
                .append(", ")
                .append("\"modificar\": ")
                .append(modificar)
                .append(", ")
                .append("\"borrar\": ")
                .append(borrar)
                .append(", ")
                .append("\"consultar\": ")
                .append(consultar)
                .append("}");
        return sb.toString();
    }
}
