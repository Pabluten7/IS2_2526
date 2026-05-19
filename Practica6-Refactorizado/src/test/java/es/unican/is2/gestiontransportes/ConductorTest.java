package es.unican.is2.gestiontransportes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests de regresión para la clase Conductor (refactorizada).
 *
 * Cambios respecto al test original:
 * - Se usan los nuevos getters con convención JavaBeans:
 * dni()      -> getDni()
 * apellido2()-> getApellido2()
 * - Se añade testCalcularSueldoExtra() para cubrir la nueva lógica
 * delegada en CategoriaTransporte (nuevos métodos = nueva cobertura).
 * - Los casos de prueba (valores y resultados esperados) NO se modifican.
 */
public class ConductorTest {

    private static Conductor sut;

    @Test
    public void testConstructor() {
        // Casos válidos
        sut = new Conductor("123123123X", "Pepe", "Martinez", "Fernandez", "Avda. de los Castros s/n");
        assertEquals("123123123X",              sut.getDni());
        assertEquals("123123123X",              sut.dni());          // alias de compatibilidad
        assertEquals("Pepe",                    sut.getNombre());
        assertEquals("Martinez",               sut.getApellido1());
        assertEquals("Fernandez",              sut.getApellido2());
        assertEquals("Avda. de los Castros s/n", sut.getDire());

        sut = new Conductor("123123123X", "Pepe", "Martinez", null, "Avda. de los Castros s/n");
        assertEquals("123123123X",              sut.getDni());
        assertEquals("Pepe",                    sut.getNombre());
        assertEquals("Martinez",               sut.getApellido1());
        assertNull(sut.getApellido2());
        assertEquals("Avda. de los Castros s/n", sut.getDire());

        // Casos no válidos
        assertThrows(IllegalArgumentException.class,
                () -> new Conductor(null, "Pepe", "Martinez", "Fernandez", "Avda. de los Castros s/n"));
        assertThrows(IllegalArgumentException.class,
                () -> new Conductor("123123123X", null, "Martinez", "Fernandez", "Avda. de los Castros s/n"));
        assertThrows(IllegalArgumentException.class,
                () -> new Conductor("123123123X", "Pepe", null, "Fernandez", "Avda. de los Castros s/n"));
        assertThrows(IllegalArgumentException.class,
                () -> new Conductor("123123123X", "Pepe", "Martinez", "Fernandez", null));
    }

    @Test
    public void testSueldoYAnhadeTransporte() {
        sut = new Conductor("123123123X", "Pepe", "Martinez", "Fernandez", "Avda. de los Castros s/n");

        // Sueldo base sin transportes
        assertTrue(sut.sueldo() == 700);

        // Personas < 10 (tarifa 0.5/hora)
        Transporte transPersonas1hora1per = new TransportePersonas(1, 1);
        sut.anhadeTransporte(transPersonas1hora1per);
        assertEquals(705.5, sut.sueldo());

        Transporte transPersonas10horas9per = new TransportePersonas(10, 9);
        sut.anhadeTransporte(transPersonas10horas9per);
        assertEquals(760.5, sut.sueldo());

        // Personas >= 10 (tarifa 1.0/hora)
        Transporte transPersonas1hora10per = new TransportePersonas(1, 10);
        sut.anhadeTransporte(transPersonas1hora10per);
        assertEquals(766.5, sut.sueldo());

        Transporte transPersonas10horas20per = new TransportePersonas(10, 20);
        sut.anhadeTransporte(transPersonas10horas20per);
        assertEquals(826.5, sut.sueldo());

        // Mercancías (2€/ton)
        Transporte transMercancias1hora1ton = new TransporteMercancias(1, 1);
        sut.anhadeTransporte(transMercancias1hora1ton);
        assertEquals(833.5, sut.sueldo());

        // Mercancías Peligrosas (2€/ton + 50€)
        Transporte transMercancias10horas100ton = new TransporteMercanciasPeligrosas(10, 100);
        sut.anhadeTransporte(transMercancias10horas100ton);
        assertEquals(1133.5, sut.sueldo());
    }

    /**
     * Test añadido para cubrir el nuevo método calcularSueldoExtra()
     * introducido en CategoriaTransporte por la refactorización
     * "Replace Conditional with Polymorphism".
     */
    @Test
    public void testCalcularSueldoExtra() {
        // Mercancias: valor * 2
        assertEquals(20.0, new TransporteMercancias(5, 10).calcularSueldoExtra());

        // MercanciasPeligrosas: valor * 2 + 50
        assertEquals(70.0, new TransporteMercanciasPeligrosas(5, 10).calcularSueldoExtra());

        // Personas < 10: horas * 0.5
        assertEquals(2.5, new TransportePersonas(5, 9).calcularSueldoExtra());

        // Personas >= 10: horas * 1.0
        assertEquals(5.0, new TransportePersonas(5, 10).calcularSueldoExtra());
    }
}