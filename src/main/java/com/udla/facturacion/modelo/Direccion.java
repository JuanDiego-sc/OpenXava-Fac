package com.udla.facturacion.modelo;

import javax.persistence.*;
import lombok.*;
 
@Embeddable 
@Getter @Setter
public class Direccion {
 
    @Column(length = 30) 
    String publicWay;
 
    @Column(length = 5)
    int postalCode;
 
    @Column(length = 20)
    String location;
 
    @Column(length = 30)
    String city;
 
}
