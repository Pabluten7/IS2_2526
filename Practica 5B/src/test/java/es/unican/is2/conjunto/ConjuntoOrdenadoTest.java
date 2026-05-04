package es.unican.is2.conjunto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConjuntoOrdenadoTest {
    
    private ConjuntoOrdenado<Integer> conjunto;

    @BeforeEach
    public void setUp() {
        conjunto = new ConjuntoOrdenado<Integer>();
    }

    @Test
    public void testAdd() {
        // Caso: Añadir elemento nulo (debe lanzar NullPointerException)
        assertThrows(NullPointerException.class, () -> conjunto.add(null));

        // Caso: Añadir en conjunto vacío
        assertTrue(conjunto.add(10));
        assertEquals(1, conjunto.size());
        assertEquals(10, conjunto.get(0));

        // Caso: Añadir elemento menor (se coloca al principio)
        assertTrue(conjunto.add(5));
        assertEquals(2, conjunto.size());
        assertEquals(5, conjunto.get(0));

        // Caso: Añadir elemento mayor (se coloca al final)
        assertTrue(conjunto.add(20));
        assertEquals(3, conjunto.size());
        assertEquals(20, conjunto.get(2));

        // Caso: Añadir elemento intermedio
        assertTrue(conjunto.add(15));
        assertEquals(4, conjunto.size());
        assertEquals(15, conjunto.get(2));

        // Caso: Añadir duplicado (debe retornar false y no alterar tamaño)
        assertFalse(conjunto.add(10));
        assertEquals(4, conjunto.size());
    }

    @Test
    public void testGet() {
        conjunto.add(10);
        conjunto.add(20);

        assertEquals(10, conjunto.get(0));
        assertEquals(20, conjunto.get(1));

        assertThrows(IndexOutOfBoundsException.class, () -> conjunto.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> conjunto.get(2));
    }

    @Test
    public void testRemove() {
        conjunto.add(10);
        conjunto.add(20);
        conjunto.add(30);

        assertEquals(20, conjunto.remove(1));
        assertEquals(2, conjunto.size());
        assertEquals(30, conjunto.get(1));

        assertThrows(IndexOutOfBoundsException.class, () -> conjunto.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> conjunto.remove(5));
    }

    @Test
    public void testClearAndSize() {
        assertEquals(0, conjunto.size());
        conjunto.add(10);
        assertEquals(1, conjunto.size());
        conjunto.clear();
        assertEquals(0, conjunto.size());
    }
}