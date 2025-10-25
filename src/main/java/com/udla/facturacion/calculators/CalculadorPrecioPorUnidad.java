package com.udla.facturacion.calculators;

import lombok.*;
 
import static org.openxava.jpa.XPersistence.*; 

import org.openxava.calculators.ICalculator;

import com.udla.facturacion.modelo.Producto;
 
public class CalculadorPrecioPorUnidad implements ICalculator {
 
    @Getter @Setter
    int numeroProducto;
 
    @Override
    public Object calculate() throws Exception {
        Producto producto = getManager() // getManager() de XPersistence
            .find(Producto.class, numeroProducto); // Busca el producto
        return producto.getPrice();    // Retorna su precio
    }
 
}
