package com.udla.facturacion.modelo;

import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;


@View(name="Simple", 
    members="number, name" 
)
@Entity
@Getter @Setter
public class Cliente {

    @Id
    @Column(length = 6)
    int number;

    @Required
    @Column(length = 50)
    String name;

    @Embedded
    Direccion address;
}
