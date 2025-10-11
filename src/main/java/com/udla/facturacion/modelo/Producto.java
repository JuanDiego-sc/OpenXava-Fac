package com.udla.facturacion.modelo;

import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;
 
@Entity @Getter @Setter
public class Producto {
 
    @Id @Column(length=9)
    int number;
 
    @Column(length=50) @Required
    String description;
 
}
