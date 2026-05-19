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
 * 1. "Replace Type Code with Subclasses" (Fowler):
 * Se ha eliminado el enumerado CategoriaTransporte. Ahora la lógica 
 * reside en la jerarquía polimórfica de Transporte.
 *
 * 2. "Replace Conditional with Polymorphism" (Fowler) en sueldo():
 * El switch original sobre t.categoria() se elimina por completo.
 * El método sueldo() queda reducido a un simple bucle sin ramificaciones.
 *
 * 3. "Extract Method" (Fowler) - calcularSueldoTransporte():
 * La lógica de cálculo del sueldo de un transporte individual se extrae
 * a un método privado auxiliar.
 *
 * 4. "Rename Method" (Fowler):
 * Se unifica la API eliminando la duplicidad dni()/getDni() y apellido2()/getApellido2().
 *
 * 5. "Encapsulate Collection" (Fowler):
 * getTransportes() devuelve una vista no modificable de la lista interna.
 *
 * 6. "Introduce Constant" y "Add final to fields":
 * Se utilizan constantes para los literales y final para los atributos inmutables.
 *
 * MÉTRICAS - CLASE Conductor (refactorizado)
 * WMC  = 17
 * - Constructor:              5 (1 base + 1 por if + 3 por operadores ||)
 * - sueldo():                 2 (1 base + 1 por for)
 * - calcularSueldoTransporte: 1 (base, sin ramas)
 * - Métodos simples (getters, alias, add): 9 (1 cada uno)
 * WMCn = 1.41 (17 / 12 métodos)
 * CCog  = 3
 * - Constructor: 2 (1 por if + 1 por la secuencia de operadores ||)
 * - sueldo():    1 (1 por for)
 * - calcularSueldoTransporte: 0
 * CCogn = 3
 * CBO   = 1  -> Transporte (Colecciones de Java no suman y CategoriaTransporte se eliminó)
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
     * @param t el transporte (ahora es polimórfico)
     * @return importe del sueldo generado por ese transporte
     */
    private double calcularSueldoTransporte(Transporte t) {
        // Polimorfismo puro. El CBO y la complejidad bajan drásticamente 
        // al no requerir comprobaciones de tipos ni conversiones de variables.
        return (t.getHoras() * TARIFA_HORA) + t.calcularSueldoExtra();
    }

    public void anhadeTransporte(Transporte t) {
        transportes.add(t);
    }
}