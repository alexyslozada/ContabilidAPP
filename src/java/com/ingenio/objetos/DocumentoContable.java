package com.ingenio.objetos;

public class DocumentoContable {

    private short id_documento;
    private String abreviatura;
    private String documento;
    private int consecutivo;

    public DocumentoContable() {
    }

    public DocumentoContable(String abreviatura, String documento) {
        this.abreviatura = abreviatura;
        this.documento = documento;
    }

    public DocumentoContable(short id_documento, String abreviatura, String documento) {
        this.id_documento = id_documento;
        this.abreviatura = abreviatura;
        this.documento = documento;
    }

    public short getId_documento() {
        return id_documento;
    }

    public void setId_documento(short id_documento) {
        this.id_documento = id_documento;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(int consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String toJSON(){
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"id\":")
                .append(id_documento)
                .append(",\"abreviatura\":\"")
                .append(abreviatura)
                .append("\",\"documento\":\"")
                .append(documento)
                .append("\",\"consecutivo\":")
                .append(consecutivo)                
                .append("}");
        return sb.toString();
    }
}
