package es.unican.is2.gestiontransportes;

/**
 * MÉTRICAS - CLASE TransporteMercancias (refactorizado)
 * WMC  = 4
 * - TransporteMercancias(): 2  (base + if)
 * - getTon():               1  (base)
 * - calcularSueldoExtra():  1  (base)
 * Total = 4
 * WMCn = 1.33  (4 / 3 métodos)
 * CCog  = 1
 * - TransporteMercancias(): 1  (if)
 * - getTon():               0
 * - calcularSueldoExtra():  0
 * CCogn = 0.33  (1 / 3 métodos)
 * CBO   = 1  -> Transporte (Padre)
 * DIT   = 1
 * NOC   = 1  -> TransporteMercanciasPeligrosas
 */

public class TransporteMercancias extends Transporte {

    private final int ton;

    public TransporteMercancias(double horas, int ton) {
        super(horas);
        if (ton <= 0) {
            throw new IllegalArgumentException("Las toneladas deben ser mayor que 0");
        }
        this.ton = ton;
    }

    public int getTon() {
        return ton;
    }

    @Override
    public double calcularSueldoExtra() {
        return ton * 2.0;
    }
}