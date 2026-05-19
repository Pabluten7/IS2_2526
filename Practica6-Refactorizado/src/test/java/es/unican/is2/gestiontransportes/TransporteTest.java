package es.unican.is2.gestiontransportes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests de regresión para la clase Transporte (refactorizada).
 *
 * Cambios respecto al test original:
 * - Se usan los nuevos getters con convención JavaBeans:
 * horas()     -> getHoras()
 * categoria() -> getCategoria()
 * ton()       -> getTon()
 * - Los alias de compatibilidad siguen funcionando, pero los tests
 * usan la API nueva para reflejar la refactorización.
 * - Los casos de prueba (valores y resultados esperados) NO se modifican.
 */
public class TransporteTest {

    @Test
    public void testConstructor() {

        // Casos válidos - Mercancías
        TransporteMercancias sutMercancias = new TransporteMercancias(1, 1);
        assertEquals(1,                          sutMercancias.getHoras());
        assertEquals(1,                          sutMercancias.getTon());

        TransporteMercanciasPeligrosas sutPeligrosas = new TransporteMercanciasPeligrosas(10, 1000);
        assertEquals(10,                                 sutPeligrosas.getHoras());
        assertEquals(1000,                               sutPeligrosas.getTon());

        // Casos válidos - Personas
        TransportePersonas sutPersonas = new TransportePersonas(10, 10);
        assertEquals(10,                          sutPersonas.getHoras());
        assertEquals(10,                          sutPersonas.getPersonas());

        // Casos no válidos
        assertThrows(IllegalArgumentException.class,
                () -> new TransporteMercancias(0, 1));
        assertThrows(IllegalArgumentException.class,
                () -> new TransporteMercancias(10, 0));
    }
}