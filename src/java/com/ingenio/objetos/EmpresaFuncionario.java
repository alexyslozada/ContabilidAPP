package com.ingenio.objetos;

public class EmpresaFuncionario {
    private short id_funcionario;
    private TipoIdentificacion tipoIdentificacion;
    private String identificacion;
    private String digito_verificacion;
    private String nombre;
    private TipoFuncionario tipoFuncionario;
    private boolean vigente;

    public EmpresaFuncionario() {
    }

    public EmpresaFuncionario(short id_funcionario, 
                              short tipo_identificacion,
                              String identificacion,
                              String digito_verificacion,
                              String nombre,
                              short tipo_funcionario,
                              boolean vigente) {
        
        tipoIdentificacion = new TipoIdentificacion();
        tipoFuncionario    = new TipoFuncionario();
        
        this.id_funcionario = id_funcionario;
        tipoIdentificacion.setIdtipodocumento(tipo_identificacion);
        this.identificacion = identificacion;
        this.digito_verificacion = digito_verificacion;
        this.nombre = nombre;
        tipoFuncionario.setId_tipo_funcionario(tipo_funcionario);
        this.vigente = vigente;
    }

    public short getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(short id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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

    public TipoFuncionario getTipoFuncionario() {
        return tipoFuncionario;
    }

    public void setTipoFuncionario(TipoFuncionario tipoFuncionario) {
        this.tipoFuncionario = tipoFuncionario;
    }

    public boolean isVigente() {
        return vigente;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }
        
}