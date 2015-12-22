package com.ingenio.utilidades;

public final class Constantes {
    /*
     Tipos de mensaje
    */
    public static final byte MSG_ERROR       = 1;
    public static final byte MSG_CORRECTO    = 2;
    public static final byte MSG_ADVERTENCIA = 3;
    public static final byte MSG_NO_AUTENTICADO = 4;
    
    /*
     Mensajes de texto
    */
    
    public static final String MSG_NO_AUTENTICADO_TEXT = "Usted no se encuentra autenticado.";
    public static final String MSG_SIN_PERMISO_TEXT = "Su perfil no tiene permiso para realizar esta acción";
    public static final String MSG_ERROR_GENERAL_TEXT = "Ocurrió un error: ";
    public static final String MSG_ELIMINADO_TEXT = "Se ha eliminado correctamente el registro.";
    public static final String MSG_ELIMINADO_NO_TEXT = "No se ha eliminado ningún registro.";
    public static final String MSG_ACTUALIZADO_TEXT = "Se ha actualizado correctamente el registro";
    public static final String MSG_ACTUALIZADO_NO_TEXT = "No se ha actualizado ningún registro";
}
