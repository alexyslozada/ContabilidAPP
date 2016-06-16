package com.ingenio.objetos;

public class TipoIdentificacion {
    private short idtipodocumento;
    private String sigla;
    private String documento;
    private String codigo_dian;
    
    public TipoIdentificacion(){}

    public TipoIdentificacion(short idtipodocumento, String sigla, String documento, String codigo_dian) {
        this.idtipodocumento = idtipodocumento;
        this.sigla = sigla;
        this.documento = documento;
        this.codigo_dian = codigo_dian;
    }

    public short getIdtipodocumento() {
        return idtipodocumento;
    }

    public void setIdtipodocumento(short idtipodocumento) {
        if(idtipodocumento > 0){
            this.idtipodocumento = idtipodocumento;
        }
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla.substring(0, 3);
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCodigo_dian() {
        return codigo_dian;
    }

    public void setCodigo_dian(String codigo_dian) {
        this.codigo_dian = codigo_dian.substring(0, 2);
    }
}
