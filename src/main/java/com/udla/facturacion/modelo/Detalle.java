package com.udla.facturacion.modelo;

import java.math.BigDecimal;

import javax.persistence.*;

import org.openxava.annotations.DefaultValueCalculator;
import org.openxava.annotations.Depends;
import org.openxava.annotations.Money;
import org.openxava.annotations.PropertyValue;

import com.udla.facturacion.calculators.CalculadorPrecioPorUnidad;

import lombok.*;
 
@Embeddable @Getter @Setter
public class Detalle {
 
    int cantidad;
 
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    Producto producto;

    @Money
    @Depends("precioPorUnidad, cantidad") // precioPorUnidad en vez de producto.numero
    public BigDecimal getImporte() {
    if (precioPorUnidad == null) return BigDecimal.ZERO; // precioPorUnidad en vez de producto y producto.getPrecio()
    return new BigDecimal(cantidad).multiply(precioPorUnidad); // precioPorUnidad en vez de producto.getPrecio()
}

    @DefaultValueCalculator(
    value=CalculadorPrecioPorUnidad.class,
    properties=@PropertyValue(
        name="numeroProducto", 
        from="producto.numero") 
    )

    @Money
    BigDecimal precioPorUnidad;

    
 
}
