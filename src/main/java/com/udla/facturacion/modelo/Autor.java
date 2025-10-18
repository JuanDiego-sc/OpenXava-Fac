package com.udla.facturacion.modelo;

import java.util.Collection;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import lombok.*;
 
@Entity @Getter @Setter
public class Autor {
 
    @Id @GeneratedValue(generator="system-uuid") @Hidden
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    String oid;
 
    @Column(length=50) @Required
    String nombre;

    @OneToMany(mappedBy="autor")
    @ListProperties("number, description, price")
    Collection<Producto> productos;
  
}
