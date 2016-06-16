package com.ingenio.objetos;

public class Empresa {
    private TipoIdentificacion tipo_identificacion;
    private String numero_identificacion;
    private String digito_verificacion;
    private String nombre;
    private String direccion;
    private String telefono;
    private Departamento departamento;
    private String direccion_web;
    private String correo;
    private String actividad;
    private boolean autorretenedor;
    private TipoPersona tipo_persona;
    private TipoRegimen tipo_regimen;
    private String logo;

    public Empresa() {
    }

    public Empresa(TipoIdentificacion tipo_identificacion, String numero_identificacion, String digito_verificacion, String nombre, String direccion, String telefono, Departamento departamento, String direccion_web, String correo, String actividad, boolean autorretenedor, TipoPersona tipo_persona, TipoRegimen tipo_regimen, String logo) {
        this.tipo_identificacion = tipo_identificacion;
        this.numero_identificacion = numero_identificacion;
        this.digito_verificacion = digito_verificacion;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.departamento = departamento;
        this.direccion_web = direccion_web;
        this.correo = correo;
        this.actividad = actividad;
        this.autorretenedor = autorretenedor;
        this.tipo_persona = tipo_persona;
        this.tipo_regimen = tipo_regimen;
        this.logo = logo;
    }

    public TipoIdentificacion getTipo_identificacion() {
        return tipo_identificacion;
    }

    public void setTipo_identificacion(TipoIdentificacion tipo_identificacion) {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public String getDireccion_web() {
        return direccion_web;
    }

    public void setDireccion_web(String direccion_web) {
        this.direccion_web = direccion_web;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public boolean isAutorretenedor() {
        return autorretenedor;
    }

    public void setAutorretenedor(boolean autorretenedor) {
        this.autorretenedor = autorretenedor;
    }

    public TipoPersona getTipo_persona() {
        return tipo_persona;
    }

    public void setTipo_persona(TipoPersona tipo_persona) {
        this.tipo_persona = tipo_persona;
    }

    public TipoRegimen getTipo_regimen() {
        return tipo_regimen;
    }

    public void setTipo_regimen(TipoRegimen tipo_regimen) {
        this.tipo_regimen = tipo_regimen;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    
}