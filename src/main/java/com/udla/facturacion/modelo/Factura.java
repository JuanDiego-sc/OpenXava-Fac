package com.udla.facturacion.modelo;

import java.time.*;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.calculators.*;

import com.udla.facturacion.calculators.CalculadorSiguienteNumeroParaAnyo;

import lombok.*;

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
 
}