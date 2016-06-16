package com.ingenio.objetos;

public class RegistroContableDetalle {
    private int id_reg_con_det;
    private int id_reg_con_enc;
    private CuentasPuc cuentaPuc;
    private int debito;
    private int credito;
    private Tercero tercero;
    private CentroCosto centroCosto;

    public RegistroContableDetalle() {
    }

    public int getId_reg_con_det() {
        return id_reg_con_det;
    }

    public void setId_reg_con_det(int id_reg_con_det) {
        this.id_reg_con_det = id_reg_con_det;
    }

    public int getId_reg_con_enc() {
        return id_reg_con_enc;
    }

    public void setId_reg_con_enc(int id_reg_con_enc) {
        this.id_reg_con_enc = id_reg_con_enc;
    }

    public CuentasPuc getCuentaPuc() {
        return cuentaPuc;
    }

    public void setCuentaPuc(CuentasPuc cuentaPuc) {
        this.cuentaPuc = cuentaPuc;
    }

    public int getDebito() {
        return debito;
    }

    public void setDebito(int debito) {
        this.debito = debito;
    }

    public int getCredito() {
        return credito;
    }

    public void setCredito(int credito) {
        this.credito = credito;
    }

    public Tercero getTercero() {
        return tercero;
    }

    public void setTercero(Tercero tercero) {
        this.tercero = tercero;
    }

    public CentroCosto getCentroCosto() {
        return centroCosto;
    }

    public void setCentroCosto(CentroCosto centroCosto) {
        this.centroCosto = centroCosto;
    }
    
    public String toJSON(){
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"id\":")
                .append(id_reg_con_det)
                .append(",\"id_enc\":")
                .append(id_reg_con_enc)
                .append(",\"cuentaPuc\":")
                .append(cuentaPuc.toJSON())
                .append(",\"debito\":")
                .append(debito)
                .append(",\"credito\":")
                .append(credito)
                .append(",\"tercero\":")
                .append(tercero.toJSON())
                .append(",\"centroCosto\":")
                .append(centroCosto.toJSON())
                .append("}");
        return sb.toString();
    }
}
