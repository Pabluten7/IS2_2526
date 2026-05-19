package es.unican.is2.gestiontransportes;

/**
 * MÉTRICAS - CLASE TransporteMercanciasPeligrosas (refactorizado)
 * WMC  = 2
 * - TransporteMercanciasPeligrosas(): 1  (base)
 * - calcularSueldoExtra():            1  (base)
 * Total = 2
 * WMCn = 1  (2 / 2 métodos)
 * CCog  = 0
 * - TransporteMercanciasPeligrosas(): 0
 * - calcularSueldoExtra():            0
 * CCogn = 0
 * CBO   = 1  -> TransporteMercancias (Padre)
 * DIT   = 2
 * NOC   = 0
 */

public class TransporteMercanciasPeligrosas extends TransporteMercancias {

    private static final double PLUS_PELIGROSIDAD = 50.0;

    public TransporteMercanciasPeligrosas(double horas, int ton) {
        super(horas, ton);
    }

    @Override
    public double calcularSueldoExtra() {
        // Aprovecha el cálculo del padre (Mercancias) y le suma el plus
        return super.calcularSueldoExtra() + PLUS_PELIGROSIDAD; 
    }
}