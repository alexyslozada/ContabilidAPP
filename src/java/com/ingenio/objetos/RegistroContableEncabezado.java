package com.ingenio.objetos;

import com.ingenio.utilidades.Utilidades;
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
    
    public void setFechaMovimiento(java.sql.Date fecha){
        if(fecha != null){
            fechaMovimiento = Calendar.getInstance();
            fechaMovimiento.setTime(fecha);
        }
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
        if(fechaCreacion != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(fechaCreacion.getTime());
        }
        return null;
    }

    public void setFechaCreacion(Calendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public void setFechaCreacion(java.sql.Date fecha){
        if(fecha != null){
            fechaCreacion = Calendar.getInstance();
            fechaCreacion.setTime(fecha);
        }
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
        if(fechaUpdate != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(fechaUpdate.getTime());
        }
        return null;
    }

    public void setFechaUpdate(Calendar fechaUpdate) {
        this.fechaUpdate = fechaUpdate;
    }
    
    public void setFechaUpdate(java.sql.Date fecha){
        if (fecha != null){
            fechaUpdate = Calendar.getInstance();
            fechaUpdate.setTime(fecha);
        }
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
        Utilidades.get().appendJSON(sb, getFechaMovimientoFormat());
        sb.append(",\"comentario\":");
        Utilidades.get().appendJSON(sb, comentario);
        sb.append(",\"periodo\":")
                .append(periodo.toJSON())
                .append(",\"usuario\":");
        if(usuario != null){
            sb.append(usuario.toJSON());
        } else {
            sb.append(usuario);
        }
        sb.append(",\"fechaCreacion\":");
        Utilidades.get().appendJSON(sb, getFechaCreacionFormat());
        sb.append(",\"usuarioUpdate\":");
        if(usuarioUpdate != null){
            sb.append(usuarioUpdate.toJSON());
        } else {
            sb.append(usuarioUpdate);
        }
        sb.append(",\"fechaUpdate\":");
        Utilidades.get().appendJSON(sb, getFechaUpdateFormat());
        sb.append(",\"anulado\":")
                .append(anulado)
                .append(",\"registros\":[");
        if(registros != null){
            for(RegistroContableDetalle rcd:registros){
                sb.append(rcd.toJSON());
            }
        }
        sb.append("]").append("}");
        return sb.toString();
    }
}
