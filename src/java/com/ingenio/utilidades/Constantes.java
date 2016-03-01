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
    public static final String MSG_CONSULTA_REALIZADA_TEXT = "Consulta realizada";
    public static final String MSG_ERROR_GENERAL_TEXT = "Ocurrió un error: ";
    public static final String MSG_CREADO_TEXT = "Registro creado con el ID: ";
    public static final String MSG_ELIMINADO_TEXT = "Se ha eliminado correctamente el registro.";
    public static final String MSG_ELIMINADO_NO_TEXT = "No se ha eliminado ningún registro.";
    public static final String MSG_ACTUALIZADO_TEXT = "Se ha actualizado correctamente el registro";
    public static final String MSG_ACTUALIZADO_NO_TEXT = "No se ha actualizado ningún registro";
    public static final String MSG_CONSULTA_NO_VALIDA_TEXT = "El tipo de consulta no es válido.";
    public static final String MSG_REGISTROS_NO_ENCONTRADOS_TEXT = "Registro no encontrado.";
    public static final String MSG_REGISTRO_ANULADO_TEXT = "Registro anulado correctamente.";
    public static final String MSG_REGISTRO_NO_ANULADO_TEXT = "El registro no fue anulado.";
    public static final String MSG_CIERRE_CORRECTO = "El cierre se ha realizado correctamente, por favor abra el nuevo periodo.";
    
    /* Acciones en texto */
    public static final String INSERTAR  = "insertar";
    public static final String MODIFICAR = "modificar";
    public static final String BORRAR    = "borrar";
    public static final String CONSULTAR = "consultar";
    
    /* Nombres de los atributos que ayudan en las sesiones */
    public static final String REGISTROCONTABLEENCABEZADO = "RegistroContableEncabezado";
    public static final String REGISTROCONTABLEDETALLE = "RegistroContableDetalle";
}
