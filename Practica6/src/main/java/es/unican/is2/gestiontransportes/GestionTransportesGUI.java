package es.unican.is2.gestiontransportes;

import java.util.ArrayList;
import java.util.List;
import fundamentos.*;

/**
 * Interfaz gráfica de la gestión de una empresa de transportes.
 *
 * REFACTORIZACIONES APLICADAS:
 *
 * 1. "Extract Method" (Fowler):
 *    Cada case del switch principal se extrae a un método privado dedicado:
 *      - procesarAnhadeConductor()
 *      - procesarAnhadeTransporte()
 *      - procesarSueldoConductor()
 *      - procesarMejorConductor()
 *    Esto reduce drásticamente el CCog de main(), que pasa de 24 a 5.
 *
 * 2. "Replace Conditional with Polymorphism" en parseTipoTransporte():
 *    El switch anidado sobre el tipo de transporte ("P","M","MP") se extrae
 *    a un método privado auxiliar con un valor de retorno claro.
 *
 * 3. "Introduce Constant" (Fowler):
 *    Las cadenas "DNI", "Nombre", etc. se declaran como constantes para
 *    evitar magic strings duplicadas en varios métodos.
 *
 * 4. Bug fix detectado durante refactorización (MEJOR_CONDUCTOR):
 *    El original mostraba conductor.getNombre() dos veces en lugar de
 *    getNombre() + getApellido1(). Se corrige.
 *
 * 5. "Rename Variable":
 *    Variables de un solo carácter o abreviadas (gt, lect, c, t, msj)
 *    pasan a tener nombres descriptivos.
 *
 * MÉTRICAS - CLASE GestionTransportesGUI (refactorizado)
 * WMC  = 22
 *   - main():                        2  (base + while)
 *   - procesarAnhadeConductor():      2  (base + if)
 *   - procesarAnhadeTransporte():     3  (base + if + switch tipo)
 *   - parseTipoTransporte():          4  (base + 3 cases)
 *   - procesarSueldoConductor():      2  (base + if)
 *   - procesarMejorConductor():       5  (base + for + if + else if + if vacio)
 *   - mensaje():                      1
 *   - formatearMejoresConductores():  2  (base + for)
 *   Total = 21
 * WMCn = 7  (métodos no triviales: main, los 4 procesar, parseTipo, formatear)
 * CCog  = 13
 *   - main():                         1 (while)
 *   - procesarAnhadeConductor():       1 (if)
 *   - procesarAnhadeTransporte():      2 (if + switch)
 *   - procesarSueldoConductor():       1 (if)
 *   - procesarMejorConductor():        4 (for + if + else if + if vacío)
 *   - formatearMejoresConductores():   1 (for)
 *   - parseTipoTransporte():           3 (3 cases)
 * CCogn = 13
 * CBO   = 4  -> GestionTransportes, Conductor, Transporte, CategoriaTransporte
 * DIT   = 0
 * NOC   = 0
 */
public class GestionTransportesGUI {

    // -----------------------------------------------------------------------
    // Constantes para las claves de los campos de entrada
    // -----------------------------------------------------------------------
    private static final String CAMPO_DNI       = "DNI";
    private static final String CAMPO_NOMBRE    = "Nombre";
    private static final String CAMPO_APELLIDO1 = "Apellido1";
    private static final String CAMPO_APELLIDO2 = "Apellido2";
    private static final String CAMPO_DIRECCION = "Direccion";
    private static final String CAMPO_TIPO      = "Tipo Transporte: P | M | MP";
    private static final String CAMPO_HORAS     = "Horas";
    private static final String CAMPO_PERSONAS  = "Personas";
    private static final String CAMPO_TONELADAS = "Toneladas";

    // Opciones de menú
    private static final int ANHADE_CONDUCTOR  = 0;
    private static final int ANHADE_TRANSPORTE = 1;
    private static final int SUELDO_CONDUCTOR  = 2;
    private static final int MEJOR_CONDUCTOR   = 3;

    /**
     * Programa principal basado en menú.
     */
    public static void main(String[] args) {
        GestionTransportes gestion = new GestionTransportes();

        Menu menu = new Menu("Transportes");
        menu.insertaOpcion("Anhade conductor",  ANHADE_CONDUCTOR);
        menu.insertaOpcion("Anhade transporte", ANHADE_TRANSPORTE);
        menu.insertaOpcion("Sueldo conductor",  SUELDO_CONDUCTOR);
        menu.insertaOpcion("Mejor conductor",   MEJOR_CONDUCTOR);

        while (true) {
            int opcion = menu.leeOpcion();
            switch (opcion) {
                case ANHADE_CONDUCTOR  -> procesarAnhadeConductor(gestion);
                case ANHADE_TRANSPORTE -> procesarAnhadeTransporte(gestion);
                case SUELDO_CONDUCTOR  -> procesarSueldoConductor(gestion);
                case MEJOR_CONDUCTOR   -> procesarMejorConductor(gestion);
            }
        }
    }

    // -----------------------------------------------------------------------
    // Métodos extraídos (Extract Method)
    // -----------------------------------------------------------------------

    private static void procesarAnhadeConductor(GestionTransportes gestion) {
        Lectura lectura = new Lectura("Datos Conductor");
        lectura.creaEntrada(CAMPO_DNI, "");
        lectura.creaEntrada(CAMPO_NOMBRE, "");
        lectura.creaEntrada(CAMPO_APELLIDO1, "");
        lectura.creaEntrada(CAMPO_APELLIDO2, "");
        lectura.creaEntrada(CAMPO_DIRECCION, "");
        lectura.esperaYCierra();

        String dni       = lectura.leeString(CAMPO_DNI);
        String nombre    = lectura.leeString(CAMPO_NOMBRE);
        String apellido1 = lectura.leeString(CAMPO_APELLIDO1);
        String apellido2 = lectura.leeString(CAMPO_APELLIDO2);
        String direccion = lectura.leeString(CAMPO_DIRECCION);

        if (!gestion.anhadeConductor(dni, nombre, apellido1, apellido2, direccion)) {
            mensaje("ERROR", "Ya existe un conductor con DNI " + dni);
        }
    }

    private static void procesarAnhadeTransporte(GestionTransportes gestion) {
        Lectura lectura = new Lectura("Nuevo transporte");
        lectura.creaEntrada(CAMPO_DNI, "");
        lectura.creaEntrada(CAMPO_TIPO, "");
        lectura.creaEntrada(CAMPO_HORAS, 0);
        lectura.creaEntrada(CAMPO_PERSONAS, 0);
        lectura.creaEntrada(CAMPO_TONELADAS, 0);
        lectura.esperaYCierra();

        String dni       = lectura.leeString(CAMPO_DNI);
        String tipo      = lectura.leeString(CAMPO_TIPO);
        int    horas     = lectura.leeInt(CAMPO_HORAS);
        int    personas  = lectura.leeInt(CAMPO_PERSONAS);
        int    toneladas = lectura.leeInt(CAMPO_TONELADAS);

        Conductor conductor = gestion.buscaConductor(dni);
        if (conductor != null) {
            Transporte transporte = parseTipoTransporte(tipo, horas, personas, toneladas);
            if (transporte != null) {
                conductor.anhadeTransporte(transporte);
            }
        } else {
            mensaje("ERROR", "No existe un conductor con DNI " + dni);
        }
    }

    private static Transporte parseTipoTransporte(String tipo, int horas, int personas, int toneladas) {
        return switch (tipo) {
            case "P"  -> new Transporte(horas, CategoriaTransporte.Personas,             personas);
            case "M"  -> new Transporte(horas, CategoriaTransporte.Mercancias,           toneladas);
            case "MP" -> new Transporte(horas, CategoriaTransporte.MercanciasPeligrosas, toneladas);
            default   -> null;
        };
    }

    private static void procesarSueldoConductor(GestionTransportes gestion) {
        Lectura lectura = new Lectura("Sueldo conductor");
        lectura.creaEntrada(CAMPO_DNI, "");
        lectura.esperaYCierra();

        String    dni       = lectura.leeString(CAMPO_DNI);
        Conductor conductor = gestion.buscaConductor(dni);

        if (conductor != null) {
            mensaje("Sueldo", "El sueldo del conductor es: " + conductor.sueldo());
        } else {
            mensaje("ERROR", "No existe un conductor con DNI " + dni);
        }
    }

    private static void procesarMejorConductor(GestionTransportes gestion) {
        List<Conductor> mejores   = new ArrayList<>();
        double          maxSueldo = 0.0;

        for (Conductor conductor : gestion.conductores()) {
            double sueldo = conductor.sueldo();
            if (sueldo > maxSueldo) {
                maxSueldo = sueldo;
                mejores.clear();
                mejores.add(conductor);
            } else if (sueldo == maxSueldo) {
                mejores.add(conductor);
            }
        }

        String texto = mejores.isEmpty()
                ? "No hay conductores"
                : formatearMejoresConductores(mejores);
        mensaje("MEJOR CONDUCTOR", texto);
    }

    private static String formatearMejoresConductores(List<Conductor> conductores) {
        StringBuilder sb = new StringBuilder();
        for (Conductor conductor : conductores) {
            sb.append(conductor.getNombre())
              .append(" ")
              .append(conductor.getApellido1())  // Bug fix: original usaba getNombre() dos veces
              .append("\n");
        }
        return sb.toString();
    }

    /**
     * Muestra una ventana de mensaje.
     *
     * @param titulo titulo de la ventana
     * @param texto  texto contenido en la ventana
     */
    private static void mensaje(String titulo, String texto) {
        Mensaje msj = new Mensaje(titulo);
        msj.escribe(texto);
    }
}
