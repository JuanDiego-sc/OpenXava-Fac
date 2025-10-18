package com.udla.facturacion.modelo;

import java.time.*;
import java.util.Collection;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.calculators.*;

import com.udla.facturacion.calculators.CalculadorSiguienteNumeroParaAnyo;

import lombok.*;

@View(members= 
 "anio, number, date;" + 
 "cliente;" + 
 "detalles;" +
 "comments"
)

@Entity @Getter @Setter
public class Factura {

    @Id
    @GeneratedValue(generator="system-uuid")
    @Hidden
    @GenericGenerator(name="system-uuid", strategy="uuid")
    @Column(length=32)
    String oid;
 
    @Column(length=4)
    @DefaultValueCalculator(CurrentYearCalculator.class) 
    int anio;
 
    @Column(length=6)
    int number;
 
    @Required
    @DefaultValueCalculator(CurrentLocalDateCalculator.class) 
    LocalDate date;
 
    @TextArea
    String comments;

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