package com.udla.facturacion.modelo;

import java.time.DayOfWeek;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;

import org.openxava.annotations.Depends;
import org.openxava.annotations.EntityValidator;
import org.openxava.annotations.PropertyValue;
import org.openxava.annotations.ReferenceView;
import org.openxava.annotations.View;
import org.openxava.util.XavaResources;

import lombok.*;

@Entity
@Getter
@Setter

@View(extendsView = "super.DEFAULT", members = "diasEntregaEstimados, entregado, " + // Añade entregado
        "factura { factura }")

@View(name = "SinClienteNiFactura", // Una vista llamada SinClienteNiFactura
        members = // que no incluye cliente ni factura
        "anyo, numero, date;" + // Ideal para ser usada desde Factura
                "detalles;" +
                "observaciones;")

// @EntityValidator(
// value=com.udla.facturacion.validadores.ValidadorEntregadoParaEstarEnFactura.class,
// // Clase con la lógica de validación
// properties= {
// @PropertyValue(name="anyo"), // El contenido de estas propiedades
// @PropertyValue(name="numero"), // se mueve desde la entidad 'Pedido'
// @PropertyValue(name="factura"), // al validador antes de
// @PropertyValue(name="entregado") // ejecutar la validación
// })

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

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    boolean entregado;

    // @PrePersist
    // @PreUpdate // Antes de crear o modificar
    // private void validar() throws Exception {
    // if (factura != null && !isEntregado()) { // La lógica de validación
    // // La excepción de validación del entorno Bean Validation
    // throw new javax.validation.ValidationException(
    // XavaResources.getString( // Para leer un mensaje i18n
    // "pedido_debe_estar_entregado",
    // getAnyo(),
    // getNumero()));
    // }
    // }

    @AssertTrue( // Antes de grabar confirma que el método devuelve true, si no lanza una 
                 // xcepción
            message = "pedido_debe_estar_entregado" // Mensaje de error en caso retorne false
    )
    private boolean isEntregadoParaEstarEnFactura() { // ...
        return factura == null || isEntregado(); // La lógica de validación
    }

    @PreRemove
    private void validarPreBorrar() {
        if (factura != null) { // La lógica de validación
            throw new javax.validation.ValidationException( // Lanza una excepción runtime
                    XavaResources.getString( // Para obtener un mensaje de texto
                            "no_puede_borrar_pedido_con_factura"));
        }
    }

}
