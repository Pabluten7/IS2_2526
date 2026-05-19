package es.unican.is2.gestiontransportes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests de regresión para la clase Transporte (refactorizada).
 *
 * Cambios respecto al test original:
 * - Se usan los nuevos getters con convención JavaBeans:
 *     horas()     -> getHoras()
 *     categoria() -> getCategoria()
 *     ton()       -> getTon()
 * - Los alias de compatibilidad siguen funcionando, pero los tests
 *   usan la API nueva para reflejar la refactorización.
 * - Los casos de prueba (valores y resultados esperados) NO se modifican.
 */
public class TransporteTest {

    @Test
    public void testConstructor() {

        // Casos válidos - Mercancías
        Transporte sut = new Transporte(1, CategoriaTransporte.Mercancias, 1);
        assertEquals(1,                          sut.getHoras());
        assertEquals(CategoriaTransporte.Mercancias, sut.getCategoria());
        assertEquals(1,                          sut.getTon());
        assertEquals(0,                          sut.getPersonas());

        sut = new Transporte(10, CategoriaTransporte.MercanciasPeligrosas, 1000);
        assertEquals(10,                                 sut.getHoras());
        assertEquals(CategoriaTransporte.MercanciasPeligrosas, sut.getCategoria());
        assertEquals(1000,                               sut.getTon());
        assertEquals(0,                                  sut.getPersonas());

        // Casos válidos - Personas
        sut = new Transporte(10, CategoriaTransporte.Personas, 10);
        assertEquals(10,                          sut.getHoras());
        assertEquals(CategoriaTransporte.Personas, sut.getCategoria());
        assertEquals(10,                          sut.getPersonas());
        assertEquals(0,                           sut.getTon());

        // Casos no válidos
        assertThrows(IllegalArgumentException.class,
                () -> new Transporte(0, CategoriaTransporte.Mercancias, 1));
        assertThrows(IllegalArgumentException.class,
                () -> new Transporte(10, CategoriaTransporte.Mercancias, 0));
        assertThrows(IllegalArgumentException.class,
                () -> new Transporte(10, null, 10));
    }
}
