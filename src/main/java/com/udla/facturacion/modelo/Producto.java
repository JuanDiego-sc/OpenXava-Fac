package com.udla.facturacion.modelo;

import java.math.BigDecimal;

import javax.persistence.*;

import org.openxava.annotations.*;

import com.udla.facturacion.anotaciones.ISBN;

import lombok.*;

@Entity
@Getter
@Setter
public class Producto {

    @Id
    @Column(length = 9)
    int number;

    @Column(length = 50)
    @Required
    String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @DescriptionsList
    Categoria category;

    @Money
    BigDecimal price;

    @Files
    @Column(length = 32)
    String photos;

    @TextArea
    String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList
    Autor autor;

    @ISBN(buscar=false) // En este caso no se hace una búsqueda en la web para validar el ISBN
    private String isbn; 

}
