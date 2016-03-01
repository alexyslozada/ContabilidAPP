package com.ingenio.negocio;

import com.ingenio.dao.DAOCierreContable;
import com.ingenio.dao.DAOPeriodo;
import com.ingenio.excepciones.ExcepcionGeneral;
import com.ingenio.objetos.Periodo;
import com.ingenio.utilidades.Utilidades;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CierreContable {

    private static final Logger LOG = Logger.getLogger(CierreContable.class.getName());
    
    public boolean cierreContable(Periodo periodo, short idUsuario) throws ExcepcionGeneral {
        boolean respuesta = false;
        short idPeriodo = periodo.getId_periodo_contable();
        DAOCierreContable dao = new DAOCierreContable();
        DAOPeriodo daoPeriodo = new DAOPeriodo();
        
        try {
            
            if (!daoPeriodo.periodoCierreIsValid(periodo.getAnio(), periodo.getMes())) {
                throw new ExcepcionGeneral("El periodo a cerrar no es válido. Debe ser el periodo siguiente al último periodo cerrado.");
            }
            if (dao.periodoCerrado(idPeriodo)) {
                throw new ExcepcionGeneral("El periodo ya tiene información de cierre. Por favor revise.");
            }
            if (!dao.movimientosBalanceados(idPeriodo)) {
                throw new ExcepcionGeneral("El periodo no se encuentra Balanceado.");
            }
            
            short encabezado = dao.insertarCierreEncabezado(idUsuario, idPeriodo);
            dao.insertaCuentasDetalle(encabezado, idPeriodo);
            dao.insertaCuentasNivel(encabezado, (short)5);
            dao.insertaCuentasNivel(encabezado, (short)4);
            dao.insertaCuentasNivel(encabezado, (short)3);
            dao.insertaCuentasNivel(encabezado, (short)2);
            
            if (!dao.cierreBalanceado(encabezado)) {
                throw new ExcepcionGeneral("El cierre no ha quedado balanceado");
            }
            
            dao.poblarSaldoAnterior(encabezado, idPeriodo);
            dao.calcularSaldoNuevo(encabezado);
            daoPeriodo.cerrarPeriodo(idPeriodo);
            respuesta = true;
        } catch (ExcepcionGeneral eg){
            Utilidades.get().generaLogServer(LOG, Level.SEVERE, "Error en DAOCierreContable.periodoCerrado: {0}", new Object[]{eg.getMessage()});
            throw new ExcepcionGeneral(eg.getMessage());
        }
        return respuesta;
    }
    
    
}
