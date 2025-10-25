package com.udla.facturacion.modelo;

import java.math.BigDecimal;
import java.time.*;
import java.util.Collection;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.calculators.*;
import org.openxava.jpa.XPersistence;

import com.udla.facturacion.calculators.CalculadorPorcentajeIVA;
import lombok.*;

@View(members = "anyo, numero, date," +
                "datos {" + // Una pestaña 'datos' para los datos principales del documento
                "cliente;" +
                "detalles;" +
                "observaciones" +
                "}")

@Entity
@Getter
@Setter
abstract public class DocumentoComercial extends Identificable {

        @Column(length = 4)
        @DefaultValueCalculator(CurrentYearCalculator.class)
        int anyo;

        @Required
        @DefaultValueCalculator(CurrentLocalDateCalculator.class)
        LocalDate date;

        @TextArea
        String observaciones;

        @Column(length = 6)
        @ReadOnly
        int numero;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @ReferenceView("Simple")
        Cliente cliente;

        @ElementCollection
        @ListProperties("producto.number, producto.description, cantidad, precioPorUnidad, " +
                        "importe+[" +
                        "documentoComercial.porcentajeIVA," +
                        "documentoComercial.iva," +
                        "documentoComercial.importeTotal" +
                        "]")
        private Collection<Detalle> detalles;

        @DefaultValueCalculator(CalculadorPorcentajeIVA.class)
        BigDecimal porcentajeIVA;

        @ReadOnly
        @Money
        @Calculation("sum(detalles.importe) * porcentajeIVA / 100")
        BigDecimal iva;

        @ReadOnly
        @Money
        @Calculation("sum(detalles.importe) + iva")
        BigDecimal importeTotal;

        @PrePersist // Ejecutado justo antes de grabar el objeto por primera vez
        private void calcularNumero() {
                Query query = XPersistence.getManager().createQuery(
                                "select max(f.numero) from " +
                                                getClass().getSimpleName() + // De esta forma es válido para Factura y 
                                                                             // edido
                                                " f where f.anyo = :anyo");
                query.setParameter("anyo", anyo);
                Integer ultimoNumero = (Integer) query.getSingleResult();
                this.numero = ultimoNumero == null ? 1 : ultimoNumero + 1;
        }

        @org.hibernate.annotations.Formula("IMPORTETOTAL * 0.10") // El cálculo usando SQL
        @Setter(AccessLevel.NONE) // El setter no se genera, sólo necesitamos el getter
        @Money
        BigDecimal beneficioEstimado;

}