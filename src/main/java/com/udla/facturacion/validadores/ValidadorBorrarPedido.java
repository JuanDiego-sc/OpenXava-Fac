package com.udla.facturacion.validadores;

import org.openxava.util.*;
import org.openxava.validators.*;

import com.udla.facturacion.modelo.Pedido;
 
public class ValidadorBorrarPedido
    implements IRemoveValidator { // Ha de implementar 'IRemoveValidator'
 
    private Pedido pedido;
 
    public void setEntity(Object entity) // La entidad a borrar se inyectará...
        throws Exception // ...con este método antes de la validación
    {
        this.pedido = (Pedido) entity;
    }
 
    public void validate(Messages errors) // La lógica de validación
        throws Exception
    {
        if (pedido.getFactura() != null) {
            // Añadiendo mensajes a 'errors' la validación fallará y el
            // borrado se abortará
            errors.add("no_puede_borrar_pedido_con_factura");
        }
    }
}