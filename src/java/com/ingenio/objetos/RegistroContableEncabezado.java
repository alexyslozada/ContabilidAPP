package com.ingenio.objetos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class RegistroContableEncabezado {
    private int id_reg_con_enc;
    private DocumentoContable documento;
    private Calendar fechaMovimiento;
    private String comentario;
    private Periodo periodo;
    private Usuario usuario;
    private Calendar fechaCreacion;
    private Usuario usuarioUpdate;
    private Calendar fechaUpdate;
    private boolean anulado;
    private List<RegistroContableDetalle> registros;

    public RegistroContableEncabezado() {
    }

    public int getId_reg_con_enc() {
        return id_reg_con_enc;
    }

    public void setId_reg_con_enc(int id_reg_con_enc) {
        this.id_reg_con_enc = id_reg_con_enc;
    }

    public DocumentoContable getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoContable documento) {
        this.documento = documento;
    }

    public Calendar getFechaMovimiento() {
        return fechaMovimiento;
    }

    public String getFechaMovimientoFormat(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fechaMovimiento.getTime());
    }
    
    public void setFechaMovimiento(Calendar fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Calendar getFechaCreacion() {
        return fechaCreacion;
    }
    
    public String getFechaCreacionFormat(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fechaCreacion.getTime());
    }

    public void setFechaCreacion(Calendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getUsuarioUpdate() {
        return usuarioUpdate;
    }

    public void setUsuarioUpdate(Usuario usuarioUpdate) {
        this.usuarioUpdate = usuarioUpdate;
    }

    public Calendar getFechaUpdate() {
        return fechaUpdate;
    }
    
    public String getFechaUpdateFormat(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fechaUpdate.getTime());
    }

    public void setFechaUpdate(Calendar fechaUpdate) {
        this.fechaUpdate = fechaUpdate;
    }

    public boolean isAnulado() {
        return anulado;
    }

    public void setAnulado(boolean anulado) {
        this.anulado = anulado;
    }

    public List<RegistroContableDetalle> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroContableDetalle> registros) {
        this.registros = registros;
    }
    
    public void addRegistro(RegistroContableDetalle registro){
        this.registros.add(registro);
    }
    
    public String toJSON(){
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"id\":")
                .append(id_reg_con_enc)
                .append(",\"documento\":")
                .append(documento.toJSON())
                .append(",\"fechaMovimiento\":");
        if(fechaMovimiento != null){
            sb.append("\"")
                    .append(getFechaMovimientoFormat())
                    .append("\"");
        } else {
            sb.append(fechaMovimiento);
        }
        sb.append(",\"comentario\":");
        if(comentario != null){
            sb.append("\"")
                    .append(comentario)
                    .append("\"");
        } else {
            sb.append(comentario);
        }
        sb.append(",\"periodo\":")
                .append(periodo.toJSON())
                .append(",\"usuario\":");
        if(usuario != null){
            sb.append("\"")
                    .append(usuario)
                    .append("\"");
        } else {
            sb.append(usuario);
        }
        sb.append(",\"fechaCreacion\":");
        if(fechaCreacion != null){
            sb.append("\"")
                    .append(getFechaCreacionFormat())
                    .append("\"");
        } else {
            sb.append(fechaCreacion);
        }
        sb.append(",\"usuarioUpdate\":");
        if(usuarioUpdate != null){
            sb.append("\"")
                    .append(usuarioUpdate)
                    .append("\"");
        } else {
            sb.append(usuarioUpdate);
        }
        sb.append(",\"fechaUpdate\":");
        if(fechaUpdate != null){
            sb.append("\"")
                    .append(getFechaUpdate())
                    .append("\"");
        } else {
            sb.append(fechaUpdate);
        }
        sb.append(",\"anulado\":")
                .append(anulado)
                .append(",\"registros\":[");
        for(RegistroContableDetalle rcd:registros){
            sb.append(rcd.toJSON());
        }
        sb.append("]")
                .append("}");
        return sb.toString();
    }
}
