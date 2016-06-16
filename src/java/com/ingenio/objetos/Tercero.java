package com.ingenio.objetos;

import com.ingenio.utilidades.Utilidades;

public class Tercero {

    private short id_tercero;
    private short tipo_identificacion;
    private String numero_identificacion;
    private String digito_verificacion;
    private short tipo_persona;
    private String razon_social;
    private String primer_apellido;
    private String segundo_apellido;
    private String primer_nombre;
    private String segundo_nombre;
    private String direccion;
    private String telefono;
    private String depto_residencia;
    private short ciudad_residencia;
    private String correo;

    public Tercero() {
    }

    public Tercero(short id_tercero, short tipo_identificacion, String numero_identificacion, String digito_verificacion, short tipo_persona, String razon_social, String primer_apellido, String segundo_apellido, String primer_nombre, String segundo_nombre, String direccion, String telefono, String depto_residencia, short ciudad_residencia, String correo) {
        this.id_tercero = id_tercero;
        this.tipo_identificacion = tipo_identificacion;
        this.numero_identificacion = numero_identificacion;
        this.digito_verificacion = digito_verificacion;
        this.tipo_persona = tipo_persona;
        this.razon_social = razon_social;
        this.primer_apellido = primer_apellido;
        this.segundo_apellido = segundo_apellido;
        this.primer_nombre = primer_nombre;
        this.segundo_nombre = segundo_nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.depto_residencia = depto_residencia;
        this.ciudad_residencia = ciudad_residencia;
        this.correo = correo;
    }

    public Tercero(short tipo_identificacion, String numero_identificacion, String digito_verificacion, short tipo_persona, String razon_social, String primer_apellido, String segundo_apellido, String primer_nombre, String segundo_nombre, String direccion, String telefono, String depto_residencia, short ciudad_residencia, String correo) {
        this.tipo_identificacion = tipo_identificacion;
        this.numero_identificacion = numero_identificacion;
        this.digito_verificacion = digito_verificacion;
        this.tipo_persona = tipo_persona;
        this.razon_social = razon_social;
        this.primer_apellido = primer_apellido;
        this.segundo_apellido = segundo_apellido;
        this.primer_nombre = primer_nombre;
        this.segundo_nombre = segundo_nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.depto_residencia = depto_residencia;
        this.ciudad_residencia = ciudad_residencia;
        this.correo = correo;
    }

    
    public short getId_tercero() {
        return id_tercero;
    }

    public void setId_tercero(short id_tercero) {
        this.id_tercero = id_tercero;
    }

    public short getTipo_identificacion() {
        return tipo_identificacion;
    }

    public void setTipo_identificacion(short tipo_identificacion) {
        this.tipo_identificacion = tipo_identificacion;
    }

    public String getNumero_identificacion() {
        return numero_identificacion;
    }

    public void setNumero_identificacion(String numero_identificacion) {
        this.numero_identificacion = numero_identificacion;
    }

    public String getDigito_verificacion() {
        return digito_verificacion;
    }

    public void setDigito_verificacion(String digito_verificacion) {
        this.digito_verificacion = digito_verificacion;
    }

    public short getTipo_persona() {
        return tipo_persona;
    }

    public void setTipo_persona(short tipo_persona) {
        this.tipo_persona = tipo_persona;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getPrimer_apellido() {
        return primer_apellido;
    }

    public void setPrimer_apellido(String primer_apellido) {
        this.primer_apellido = primer_apellido;
    }

    public String getSegundo_apellido() {
        return segundo_apellido;
    }

    public void setSegundo_apellido(String segundo_apellido) {
        this.segundo_apellido = segundo_apellido;
    }

    public String getPrimer_nombre() {
        return primer_nombre;
    }

    public void setPrimer_nombre(String primer_nombre) {
        this.primer_nombre = primer_nombre;
    }

    public String getSegundo_nombre() {
        return segundo_nombre;
    }

    public void setSegundo_nombre(String segundo_nombre) {
        this.segundo_nombre = segundo_nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDepto_residencia() {
        return depto_residencia;
    }

    public void setDepto_residencia(String depto_residencia) {
        this.depto_residencia = depto_residencia;
    }

    public short getCiudad_residencia() {
        return ciudad_residencia;
    }

    public void setCiudad_residencia(short ciudad_residencia) {
        this.ciudad_residencia = ciudad_residencia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String toJSON(){
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"id\":")
                .append(id_tercero)
                .append(",\"tipo_identificacion\":")
                .append(tipo_identificacion)
                .append(",\"numero_identificacion\":");
        Utilidades.get().appendJSON(sb, numero_identificacion);
        sb.append(",\"digito_verificacion\":");
        Utilidades.get().appendJSON(sb, digito_verificacion);
        sb.append(",\"tipo_persona\":")
                .append(tipo_persona)
                .append(", \"razon_social\":");
        Utilidades.get().appendJSON(sb, razon_social);
        sb.append(",\"primer_apellido\":");
        Utilidades.get().appendJSON(sb, primer_apellido);
        sb.append(",\"segundo_apellido\":");
        Utilidades.get().appendJSON(sb, segundo_apellido);
        sb.append(",\"primer_nombre\":");
        Utilidades.get().appendJSON(sb, primer_nombre);
        sb.append(",\"segundo_nombre\":");
        Utilidades.get().appendJSON(sb, segundo_nombre);
        sb.append(",\"direccion\":");
        Utilidades.get().appendJSON(sb, direccion);
        sb.append(",\"telefono\":");
        Utilidades.get().appendJSON(sb, telefono);
        sb.append(",\"depto_residencia\":")
                .append(depto_residencia)
                .append(",\"ciudad_residencia\":")
                .append(ciudad_residencia)
                .append(",\"correo\":");
        Utilidades.get().appendJSON(sb, correo);
        sb.append("}");
        return sb.toString();
    }
}
