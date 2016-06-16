package com.ingenio.objetos;

import java.util.ArrayList;
import java.util.List;

public class Departamento {
    private String codigo;
    private String nombre;
    private List<Ciudad> ciudades = new ArrayList<>();

    public Departamento() {
    }

    public Departamento(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
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

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }
    
    public void addCiudad(Ciudad ciudad){
        this.ciudades.add(ciudad);
    }
}
