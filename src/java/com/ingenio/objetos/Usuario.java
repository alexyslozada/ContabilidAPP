package com.ingenio.objetos;

public class Usuario {
    private short idusuario;
    private String identificacion;
    private String nombre;
    private String correo;
    private String clave;
    private Perfil perfil;
    private boolean activo;

    public Usuario() {
    }

    public Usuario(short idusuario, String identificacion, String nombre, String correo, String clave, Perfil perfil, boolean activo) {
        this.idusuario = idusuario;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.perfil = perfil;
        this.activo = activo;
    }

    public short getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(short idusuario) {
        this.idusuario = idusuario;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
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
        sb.append("\"idusuario\":");
        sb.append(idusuario);
        sb.append(",");
        sb.append("\"identificacion\":\"");
        sb.append(identificacion);
        sb.append("\",");
        sb.append("\"nombre\":\"");
        sb.append(nombre);
        sb.append("\",");
        sb.append("\"correo\":\"");
        sb.append(correo);
        sb.append("\",");
        sb.append("\"perfil\":");
        sb.append(perfil.toJSON());
        sb.append(",");
        sb.append("\"activo\":");
        sb.append(activo);
        sb.append("}");
        return sb.toString();
    }
}
