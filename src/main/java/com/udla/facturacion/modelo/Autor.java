package com.udla.facturacion.modelo;

import java.util.Collection;

import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;
 
@Entity @Getter @Setter
public class Autor extends Identificable {
 
    @Column(length=50) @Required
    String nombre;

    @OneToMany(mappedBy="autor")
    @ListProperties("number, description, price")
    Collection<Producto> productos;
  
}
