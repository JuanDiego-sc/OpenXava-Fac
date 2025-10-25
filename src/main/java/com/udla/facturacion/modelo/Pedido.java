package com.udla.facturacion.modelo;

import java.time.DayOfWeek;

import javax.persistence.*;

import org.openxava.annotations.Depends;
import org.openxava.annotations.ReferenceView;
import org.openxava.annotations.View;

import lombok.*;

@Entity
@Getter
@Setter

@View(extendsView = "super.DEFAULT", members = "diasEntregaEstimados," + // AÑADE ESTA LÍNEA
        "factura { factura }")

@View(name = "SinClienteNiFactura", // Una vista llamada SinClienteNiFactura
        members = // que no incluye cliente ni factura
        "anyo, numero, date;" + // Ideal para ser usada desde Factura
                "detalles;" +
                "comments")
public class Pedido extends DocumentoComercial {

    @ManyToOne
    @ReferenceView("SinClienteNiPedidos")
    Factura factura;

    @Depends("fecha")
    public int getDiasEntregaEstimados() {
        if (getDate().getDayOfYear() < 15) {
            return 20 - getDate().getDayOfYear();
        }
        if (getDate().getDayOfWeek() == DayOfWeek.SUNDAY)
            return 2;
        if (getDate().getDayOfWeek() == DayOfWeek.SATURDAY)
            return 3;
        return 1;
    }

    @Column(columnDefinition = "INTEGER DEFAULT 1")
    int diasEntrega;

    @PrePersist
    @PreUpdate
    private void recalcularDiasEntrega() {
        setDiasEntrega(getDiasEntregaEstimados());
    }

}
