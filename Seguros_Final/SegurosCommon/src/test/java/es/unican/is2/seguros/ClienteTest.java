package es.unican.is2.seguros;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class ClienteTest {

    private Cliente cliente;
    private Seguro seguro1;
    private Seguro seguro2;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        
        seguro1 = new Seguro();
        seguro1.setFechaInicio(LocalDate.now().minusYears(2));
        seguro1.setCobertura(Cobertura.TERCEROS); // Base 400
        seguro1.setPotencia(80); // Queda en 400
        
        seguro2 = new Seguro();
        seguro2.setFechaInicio(LocalDate.now().minusYears(2));
        seguro2.setCobertura(Cobertura.TODO_RIESGO); // Base 1000
        seguro2.setPotencia(80); // Queda en 1000
    }

    @Test
    public void testTotalSeguros() {
        // C1: Lista vacía
        cliente.setMinusvalia(false);
        assertEquals(0.0, cliente.totalSeguros());

        // C2: 1 seguro sin minusvalía
        cliente.getSeguros().add(seguro1);
        cliente.setMinusvalia(false);
        assertEquals(400.0, cliente.totalSeguros());

        // C3: 1 seguro con minusvalía (descuento del 25%)
        cliente.setMinusvalia(true);
        assertEquals(300.0, cliente.totalSeguros()); 

        // C4: 2 seguros con minusvalía
        cliente.getSeguros().add(seguro2); 
        cliente.setMinusvalia(true);
        assertEquals(1050.0, cliente.totalSeguros()); 
    }
package es.unican.is2.seguros;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class ClienteTest {

    private Cliente cliente;
    private Seguro seguro1;
    private Seguro seguro2;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        
        seguro1 = new Seguro();
        seguro1.setFechaInicio(LocalDate.now().minusYears(2));
        seguro1.setCobertura(Cobertura.TERCEROS); // Base 400
        seguro1.setPotencia(80); // Queda en 400
        
        seguro2 = new Seguro();
        seguro2.setFechaInicio(LocalDate.now().minusYears(2));
        seguro2.setCobertura(Cobertura.TODO_RIESGO); // Base 1000
        seguro2.setPotencia(80); // Queda en 1000
    }

    @Test
    public void testTotalSeguros() {
        // C1: Lista vacía
        cliente.setMinusvalia(false);
        assertEquals(0.0, cliente.totalSeguros());

        // C2: 1 seguro sin minusvalía
        cliente.getSeguros().add(seguro1);
        cliente.setMinusvalia(false);
        assertEquals(400.0, cliente.totalSeguros());

        // C3: 1 seguro con minusvalía (descuento del 25%)
        cliente.setMinusvalia(true);
        assertEquals(300.0, cliente.totalSeguros()); 

        // C4: 2 seguros con minusvalía
        cliente.getSeguros().add(seguro2); 
        cliente.setMinusvalia(true);
        assertEquals(1050.0, cliente.totalSeguros()); 
    }
}