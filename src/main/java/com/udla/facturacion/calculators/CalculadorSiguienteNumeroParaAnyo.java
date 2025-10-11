package com.udla.facturacion.calculators;

import javax.persistence.*;
import org.openxava.calculators.*;
import org.openxava.jpa.*;
import lombok.*;
 
public class CalculadorSiguienteNumeroParaAnyo
    implements ICalculator { 
 
    @Getter @Setter     
    int anyo; 
 
    public Object calculate() throws Exception { 
        Query query = XPersistence.getManager() 
            .createQuery("select max(f.numero) from Factura f where f.anyo = :anyo"); 
                                                             
        query.setParameter("anyo", anyo); 
        Integer lastNumber = (Integer) query.getSingleResult();
        return lastNumber == null ? 1 : lastNumber + 1; 
                                            
    }
 
}
