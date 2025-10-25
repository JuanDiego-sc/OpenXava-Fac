package com.udla.facturacion.calculators;

import org.openxava.calculators.*; // Para usar 'ICalculator'

import com.udla.facturacion.util.PreferenciasFacturacion;
 
public class CalculadorPorcentajeIVA implements ICalculator {
 
    public Object calculate() throws Exception {
        return PreferenciasFacturacion.getPorcentajeIVADefecto();
    }
}
