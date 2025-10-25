package com.udla.facturacion.modelo;

import java.util.*; 
import javax.persistence.*;

import org.openxava.annotations.CollectionView;
import org.openxava.annotations.View;

import lombok.*;

@Entity 
@Getter 
@Setter

@View( extendsView="super.DEFAULT", // La vista por defecto
    members="pedidos { pedidos } "
)
@View( name="SinClienteNiPedidos", // Una vista llamada SinClienteNiPedidos
    members=                       // que no incluye cliente ni pedidos
        "anyo, numero, date;" +   // Ideal para usarse desde Pedido
        "detalles;" +
        "observaciones"
)
public class Factura extends DocumentoComercial {
    
    @OneToMany(mappedBy="factura")
    @CollectionView("SinClienteNiFactura")
    Collection<Pedido> pedidos;
}