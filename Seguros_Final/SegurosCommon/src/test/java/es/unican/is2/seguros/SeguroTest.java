package es.unican.is2.seguros;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class SeguroTest {

    private Seguro seguro;

    @BeforeEach
    public void setUp() {
        seguro = new Seguro();
    }

    @Test
    public void testPrecio() {
        // S1: Fecha futura
        seguro.setFechaInicio(LocalDate.now().plusDays(1));
        seguro.setCobertura(Cobertura.TODO_RIESGO);
        seguro.setPotencia(100);
        assertEquals(0.0, seguro.precio());

        // S2: Límite inferior potencia (<90) y descuento 1er año (TERCEROS = 400)
        seguro.setFechaInicio(LocalDate.now());
        seguro.setCobertura(Cobertura.TERCEROS);
        seguro.setPotencia(89);
        assertEquals(320.0, seguro.precio());

        // S3: Límite exacto potencia (90) y descuento 1er año (TERCEROS_LUNAS = 600)
        seguro.setFechaInicio(LocalDate.now().minusMonths(6)); 
        seguro.setCobertura(Cobertura.TERCEROS_LUNAS);
        seguro.setPotencia(90);
        assertEquals(504.0, seguro.precio());

        // S4: Límite exacto potencia (110) y sin descuento (TODO_RIESGO = 1000)
        seguro.setFechaInicio(LocalDate.now().minusYears(1)); 
        seguro.setCobertura(Cobertura.TODO_RIESGO);
        seguro.setPotencia(110);
        assertEquals(1050.0, seguro.precio());

        // S5: Límite superior potencia (>110) y sin descuento (TERCEROS = 400)
        seguro.setFechaInicio(LocalDate.now().minusYears(2)); 
        seguro.setCobertura(Cobertura.TERCEROS);
        seguro.setPotencia(111);
        assertEquals(480.0, seguro.precio());
    }
}