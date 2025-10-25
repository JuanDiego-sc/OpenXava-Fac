package com.udla.facturacion.modelo;

import javax.persistence.*;

import org.openxava.annotations.ReferenceView;
import org.openxava.annotations.View;

import lombok.*;
 
@Entity @Getter @Setter

@View(extendsView="super.DEFAULT", // Extiende de la vista de DocumentoComercial
    members="factura { factura } " // Añadimos factura dentro de una pestaña
)

@View( name="SinClienteNiFactura", // Una vista llamada SinClienteNiFactura
    members=                       // que no incluye cliente ni factura
        "anyo, numero, date;" +   // Ideal para ser usada desde Factura
        "detalles;" +
        "comments"
)
public class Pedido extends DocumentoComercial {
 
    @ManyToOne
    @ReferenceView("SinClienteNiPedidos")
    Factura factura;
}