package es.unican.is2.gestiontransportes;

/**
 * MÉTRICAS - CLASE Transporte (refactorizado)
 * WMC  = 4
 * - Transporte():          2  (base + if)
 * - getHoras():            1  (base)
 * - calcularSueldoExtra(): 1  (base, abstracto)
 * Total = 4
 * WMCn = 1.33  (4 / 3 métodos)
 * CCog  = 1
 * - Transporte():          1  (if)
 * - getHoras():            0
 * - calcularSueldoExtra(): 0
 * CCogn = 0.33  (1 / 3 métodos)
 * CBO   = 0
 * DIT   = 0
 * NOC   = 3  -> TransportePersonas, TransporteMercancias, TransporteMercanciasPeligrosas
 */

/**
 * Clase ABSTRACTA que representa un transporte genérico.
 * * REFACTORIZACIÓN APLICADA: "Replace Type Code with Subclasses"
 * Se elimina la enumeración CategoriaTransporte. La lógica específica 
 * (toneladas, personas, sueldo extra) pasa a las clases hijas.
 */
public abstract class Transporte {

    private static final double MIN_VALOR = 0;
    private final double horas;

    public Transporte(double horas) {
        if (horas <= MIN_VALOR) {
            throw new IllegalArgumentException("Las horas deben ser mayores que 0");
        }
        this.horas = horas;
    }

    public double getHoras() {
        return horas;
    }

    /**
     * Método polimórfico. Cada hijo calculará su propio sueldo extra.
     */
    public abstract double calcularSueldoExtra();
}