package es.unican.is2.gestiontransportes;

/**

 * REFACTORIZACIÓN APLICADA:
 *   Uso Fowler para remplazar el condicional por un polimorfismo
 *   El switch sobre la categoría del transporte que existía en Conductor.sueldo()
 *   elimino trasladando la lógica de cálculo de sueldo extra a cada valor
 *   del enum. Cada categoría sabe cómo calcular su propio sueldo extra,
 *   eliminando el acoplamiento en Conductor y reduciendo su CCog.
 *
 * MÉTRICAS - ENUM CategoriaTransporte (refactorizado)
 * WMC  = 3  (un método abstracto implementado en las 3 constantes del enum)
 * WMCn = 3
 * CCog = 1  (un if en la implementación de Personas)
 * CCogn = 1
 * CBO  = 0
 * DIT  = 0
 * NOC  = 0
 */
public enum CategoriaTransporte {

    Mercancias {
        @Override
        public double calcularSueldoExtra(int horas, int valor) {
            return valor * 2;
        }
    },

    MercanciasPeligrosas {
        @Override
        public double calcularSueldoExtra(int horas, int valor) {
            return valor * 2 + 50;
        }
    },

    Personas {
        private static final int    UMBRAL_PERSONAS = 10;
        private static final double TARIFA_POCOS    = 0.5;
        private static final double TARIFA_MUCHOS   = 1.0;

        @Override
        public double calcularSueldoExtra(int horas, int valor) {
            double tarifa = (valor < UMBRAL_PERSONAS) ? TARIFA_POCOS : TARIFA_MUCHOS;
            return horas * tarifa;
        }
    };

    /**
     * Calcula el sueldo extra de un transporte según su categoría.
     *
     * @param horas horas que duró el transporte
     * @param valor toneladas (Mercancias/MercanciasPeligrosas) o personas (Personas)
     * @return importe del sueldo extra
     */
    public abstract double calcularSueldoExtra(int horas, int valor);
}
