package com.ingenio.objetos;

public class Paginacion {
    private short pagina = 1;
    private short limite = 10;
    private short columna_orden = 1;
    private String tipo_orden = "asc";

    public Paginacion() {
    }

    public Paginacion(short pagina, short limite, short columna_orden, String tipo_orden){
        setPagina(pagina);
        setLimite(limite);
        setColumna_orden(columna_orden);
        setTipo_orden(tipo_orden);
    }
    
    public short getPagina() {
        return pagina;
    }

    public final void setPagina(short pagina) {
        if(pagina > 1){
            this.pagina = pagina;
        }
    }

    public short getLimite() {
        return limite;
    }

    public final void setLimite(short limite) {
        if(limite > 0){
            this.limite = limite;
        }
    }

    public short getColumna_orden() {
        return columna_orden;
    }

    public final void setColumna_orden(short columna_orden) {
        if(columna_orden > 0){
            this.columna_orden = columna_orden;
        }
    }

    public String getTipo_orden() {
        return tipo_orden;
    }

    public final void setTipo_orden(String tipo_orden) {
        if(tipo_orden != null){
            if(tipo_orden.equals("asc") || tipo_orden.equals("desc")){
                this.tipo_orden = tipo_orden;
            }
        }
    }
    
    
}
