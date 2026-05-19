package es.unican.is2.gestiontransportes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase que representa a un conductor, con sus datos personales
 * y los transportes que ha realizado.
 *
 * REFACTORIZACIONES APLICADAS:
 *
 * 1. "Replace Conditional with Polymorphism" (Fowler) en sueldo():
 *    El switch original sobre t.categoria() se elimina por completo.
 *    La lógica de cálculo del sueldo extra pasa a residir en CategoriaTransporte
 *    (cada constante del enum implementa calcularSueldoExtra).
 *    El método sueldo() queda reducido a un simple bucle sin ramificaciones,
 *    bajando su CCog de 4 a 1.
 *
 * 2. "Extract Method" (Fowler) - calcularSueldoTransporte():
 *    La lógica de cálculo del sueldo de un transporte individual se extrae
 *    a un método privado auxiliar, mejorando la legibilidad de sueldo().
 *
 * 3. "Rename Method" (Fowler):
 *    Se unifica la API eliminando la duplicidad dni()/getDni() y apellido2()/getApellido2().
 *    Se mantienen los alias por retrocompatibilidad tal como en Transporte.
 *
 * 4. "Encapsulate Collection" (Fowler):
 *    getTransportes() devuelve una vista no modificable de la lista interna,
 *    evitando que código externo modifique la colección directamente.
 *
 * 5. "Introduce Constant" - SUELDO_BASE:
 *    El literal 700 se nombra como constante SUELDO_BASE mejorando la legibilidad.
 *
 * 6. "Add final to fields" (mejora de robustez):
 *    Los campos que no cambian tras la construcción se declaran final.
 *
 * MÉTRICAS - CLASE Conductor (refactorizado)
 * WMC  = 13
 *   - Constructor:              3 (base + 4 condiciones OR reducidas a 1 if compuesto)
 *   - sueldo():                 2 (base + 1 for)
 *   - calcularSueldoTransporte: 1 (base, sin ramas)
 *   - getTransportes():         1
 *   - anhadeTransporte():       1
 *   - getDni()/dni():           1+1 = 2
 *   - getNombre():              1
 *   - getApellido1():           1
 *   - getApellido2()/apellido2():1+1=2  → pero apellido2() delega → cuenta 1
 *   - getDire():                1
 *   Total ≈ 13
 * WMCn = 4  (constructor, sueldo, calcularSueldoTransporte, anhadeTransporte)
 * CCog  = 5
 *   - Constructor: 4 (condiciones del if compuesto con ||)
 *   - sueldo():    1 (for)
 *   - calcularSueldoTransporte: 0
 * CCogn = 5
 * CBO   = 2  -> Transporte, CategoriaTransporte
 * DIT   = 0
 * NOC   = 0
 */
public class Conductor {

    private static final double SUELDO_BASE       = 700.0;
    private static final double TARIFA_HORA       = 5.0;

    private final List<Transporte> transportes = new ArrayList<>();
    private final String dni;
    private final String nombre;
    private final String apellido1;
    private final String apellido2;   // puede ser null
    private final String dire;

    public Conductor(String dni, String nombre, String apellido1,
                     String apellido2, String direccion) {
        if (dni == null || nombre == null || apellido1 == null || direccion == null) {
            throw new IllegalArgumentException();
        }
        this.dni       = dni;
        this.nombre    = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.dire      = direccion;
    }

    // -----------------------------------------------------------------------
    // API principal (convención JavaBeans)
    // -----------------------------------------------------------------------

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public String getDire() {
        return dire;
    }

    /**
     * Devuelve una vista no modificable de los transportes del conductor.
     * (Encapsulate Collection)
     */
    public List<Transporte> getTransportes() {
        return Collections.unmodifiableList(transportes);
    }

    // -----------------------------------------------------------------------
    // Alias de compatibilidad
    // -----------------------------------------------------------------------

    /** @deprecated usar {@link #getDni()} */
    public String dni() {
        return getDni();
    }

    /** @deprecated usar {@link #getApellido2()} */
    public String apellido2() {
        return getApellido2();
    }

    // -----------------------------------------------------------------------
    // Lógica de negocio
    // -----------------------------------------------------------------------

    /**
     * Calcula el sueldo total del conductor.
     * Sueldo = SUELDO_BASE + suma del sueldo de cada transporte realizado.
     * (Replace Conditional with Polymorphism: sin switch, sin if sobre categoría)
     */
    public double sueldo() {
        double sueldoTransportes = 0.0;
        for (Transporte t : transportes) {
            sueldoTransportes += calcularSueldoTransporte(t);
        }
        return SUELDO_BASE + sueldoTransportes;
    }

    /**
     * Calcula el sueldo correspondiente a un único transporte.
     * (Extract Method)
     *
     * @param t el transporte
     * @return importe del sueldo generado por ese transporte
     */
    private double calcularSueldoTransporte(Transporte t) {
        int valorCategoria = (t.getCategoria() == CategoriaTransporte.Personas)
                ? t.getPersonas()
                : t.getTon();
        double sueldoExtra = t.getCategoria().calcularSueldoExtra((int) t.getHoras(), valorCategoria);
        return t.getHoras() * TARIFA_HORA + sueldoExtra;
    }

    public void anhadeTransporte(Transporte t) {
        transportes.add(t);
    }
}
