package com.ingenio.excepciones;

public class ExcepcionGeneral extends RuntimeException{
    public ExcepcionGeneral(){
        this("Excepción no especificada");
    };
    
    public ExcepcionGeneral(String mensaje){
        super(mensaje);
    }
}
