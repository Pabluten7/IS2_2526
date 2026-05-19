package es.unican.is2.gestiontransportes;

/**
 * MÉTRICAS - CLASE TransportePersonas (refactorizado)
 * WMC  = 5
 * - TransportePersonas():  2  (base + if)
 * - getPersonas():         1  (base)
 * - calcularSueldoExtra(): 2  (base + operador ternario)
 * Total = 5
 * WMCn = 1.67  (5 / 3 métodos)
 * CCog  = 2
 * - TransportePersonas():  1  (if)
 * - getPersonas():         0
 * - calcularSueldoExtra(): 1  (operador ternario)
 * CCogn = 0.67  (2 / 3 métodos)
 * CBO   = 1  -> Transporte (Padre)
 * DIT   = 1
 * NOC   = 0
 */

public class TransportePersonas extends Transporte {

    private static final int UMBRAL_PERSONAS = 10;
    private static final double TARIFA_POCOS = 0.5;
    private static final double TARIFA_MUCHOS = 1.0;
    
    private final int personas;

    public TransportePersonas(double horas, int personas) {
        super(horas);
        if (personas <= 0) {
            throw new IllegalArgumentException("El número de personas debe ser mayor que 0");
        }
        this.personas = personas;
    }

    public int getPersonas() {
        return personas;
    }

    @Override
    public double calcularSueldoExtra() {
        double tarifa = (personas < UMBRAL_PERSONAS) ? TARIFA_POCOS : TARIFA_MUCHOS;
        return getHoras() * tarifa;
    }
}