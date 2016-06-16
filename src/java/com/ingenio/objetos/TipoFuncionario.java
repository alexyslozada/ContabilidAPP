package com.ingenio.objetos;

public class TipoFuncionario {

    private short id_tipo_funcionario;
    private String nombre;

    public TipoFuncionario() {
    }

    public TipoFuncionario(short id_tipo_funcionario, String nombre) {
        this.id_tipo_funcionario = id_tipo_funcionario;
        this.nombre = nombre;
    }

    public short getId_tipo_funcionario() {
        return id_tipo_funcionario;
    }

    public void setId_tipo_funcionario(short id_tipo_funcionario) {
        this.id_tipo_funcionario = id_tipo_funcionario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
