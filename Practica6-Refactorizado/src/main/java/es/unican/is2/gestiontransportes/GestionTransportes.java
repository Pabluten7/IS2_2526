package es.unican.is2.gestiontransportes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase que gestiona la colección de conductores de la empresa.
 *
 * REFACTORIZACIONES APLICADAS:
 *
 * 1. "Encapsulate Collection" (Fowler):
 *    conductores() devuelve ahora una vista no modificable (unmodifiableList),
 *    evitando que el cliente pueda alterar la lista interna directamente.
 *
 * 2. "Rename Variable" - nombre del campo:
 *    El campo "cs" pasa a llamarse "conductores", nombre auto-explicativo
 *    que elimina la necesidad de comentarios adicionales.
 *
 * 3. "Introduce Explaining Variable" en buscaConductor():
 *    Se hace explícito que la comparación es por DNI mediante el nombre del parámetro.
 *
 * MÉTRICAS - CLASE GestionTransportes (refactorizado)
 * WMC  = 6  (igual que el original)
 *   - conductores():      1
 *   - buscaConductor():   3 (base + for + if)
 *   - anhadeConductor():  2 (base + if)
 * WMCn = 6
 * CCog  = 3  (igual que el original: for, if anidado, if)
 * CCogn = 3
 * CBO   = 1  -> Conductor
 * DIT   = 0
 * NOC   = 0
 */
public class GestionTransportes {

    private final List<Conductor> conductores = new ArrayList<>();

    /**
     * Busca un conductor por su DNI.
     *
     * @param dni DNI del conductor a buscar
     * @return el conductor encontrado, o null si no existe
     */
    public Conductor buscaConductor(String dni) {
        for (Conductor c : conductores) {
            if (c.getDni().equals(dni)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Añade un nuevo conductor si no existe ya uno con el mismo DNI.
     *
     * @return true si se añadió, false si ya existía un conductor con ese DNI
     */
    public boolean anhadeConductor(String dni, String nombre, String apellido1,
                                   String apellido2, String direccion) {
        if (buscaConductor(dni) != null) {
            return false;
        }
        conductores.add(new Conductor(dni, nombre, apellido1, apellido2, direccion));
        return true;
    }

    /**
     * Devuelve una vista no modificable de la lista de conductores.
     */
    public List<Conductor> conductores() {
        return Collections.unmodifiableList(conductores);
    }
}
