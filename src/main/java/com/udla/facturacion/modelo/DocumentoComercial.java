package com.udla.facturacion.modelo;

import java.time.*;
import java.util.Collection;

import javax.persistence.*;
import org.openxava.annotations.*;
import org.openxava.calculators.*;

import com.udla.facturacion.calculators.CalculadorSiguienteNumeroParaAnyo;

import lombok.*;

@View(members=
    "anyo, numero, date," + 
    "datos {" + // Una pestaña 'datos' para los datos principales del documento
        "cliente;" +
        "detalles;" +
        "observaciones" +
    "}"
)

@Entity @Getter @Setter
abstract public class DocumentoComercial extends Identificable {
 
    @Column(length=4)
    @DefaultValueCalculator(CurrentYearCalculator.class) 
    int anyo;
 
    @Required
    @DefaultValueCalculator(CurrentLocalDateCalculator.class) 
    LocalDate date;
 
    @TextArea
    String observaciones;

    @Column(length=6)
    @DefaultValueCalculator(value=CalculadorSiguienteNumeroParaAnyo.class,
    properties=@PropertyValue(name="anyo") 
)   
    int numero;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @ReferenceView("Simple")
    Cliente cliente;

    @ElementCollection
    @ListProperties("producto.number, producto.description, cantidad, producto.price")
    Collection<Detalle> detalles;
 
}