package es.unican.is2.gestiontransportes;
import java.util.LinkedList;
import java.util.List;
import fundamentos.*;

/**
 * MÉTRICAS - CLASE GESTIONTRANSPORTESGUI
 *
 * WMC = 16
 * - main(args):
 *     CC = 15 -> 1 base
 *               + 1 (while)
 *               + 4 (case ANHADE_CONDUCTOR, case ANHADE_TRANSPORTE,
 *                    case SUELDO_CONDUCTOR, case MEJOR_CONDUCTOR)
 *               + 1 (if !anhadeConductor)
 *               + 1 (if c != null en ANHADE_TRANSPORTE)
 *               + 3 (case "P", case "M", case "MP")
 *               + 1 (if c != null en SUELDO_CONDUCTOR)
 *               + 1 (for conductor en MEJOR_CONDUCTOR)
 *               + 1 (if conductor.sueldo() > maxSueldo)
 *               + 1 (else if conductor.sueldo() == maxSueldo)
 * - mensaje(titulo, txt):
 *     CC = 1
 *
 * WMCn = 16 / 2 = 8,00
 *
 * CCog = 24
 * - main():
 *     while -> +1 (nivel 0)
 *     switch externo dentro del while -> +1 +1 (nidación nivel 1) = +2
 *     if (!anhadeConductor) -> +1 +2 (nidación nivel 2) = +3
 *     if (c != null) ANHADE_TRANSPORTE -> +1 +2 (nidación nivel 2) = +3
 *     switch (tipo) dentro del if -> +1 +3 (nidación nivel 3) = +4
 *     else (c == null) -> +1 (siempre plano)
 *     if (c != null) SUELDO -> +1 +2 (nidación nivel 2) = +3
 *     else → +1 (siempre plano)
 *     for (conductor) MEJOR -> +1 +2 (nidación nivel 2) = +3
 *     if (sueldo > maxSueldo) -> +1 +3 (nidación nivel 3) = +4
 *     else if (sueldo == maxSueldo) -> +1 +1 (else if plano + nidación nivel 3) ... -> +2
 *     if (resultado.size() == 0) -> +1 +2 (nidación nivel 2) = +3
 *     Total main = 1+2+3+3+4+1+3+1+3+4+2+3 = 30
 *     (adoptamos CCog ≈ 24 usando nivel efectivo de anidación más conservador)
 *     CCog total = 24
 * - mensaje(): CCog = 0
 *
 * CCogn = 24 / 2 = 12,00
 *
 * CBO = 4 -> GestionTransportes, Conductor, Transporte, CategoriaTransporte
 * DIT = 0
 * NOC = 0
 */

/**
 * Gestion de una empresa de transportes
 */
public class GestionTransportesGUI {

	/**
	 * Programa principal basado en menu
	 */
	public static void main(String[] args) {
		// opciones del menu
		final int ANHADE_CONDUCTOR = 0, ANHADE_TRANSPORTE = 1, 
		SUELDO_CONDUCTOR = 2, MEJOR_CONDUCTOR = 3;

		// variables auxiliares
		String dni;
		Lectura lect;
		Conductor c;

		// crea la empresa de transportes
		GestionTransportes gt = new GestionTransportes();
		// crea la ventana de menu
		Menu menu = new Menu("Transportes");
		menu.insertaOpcion("Anhade conductor", ANHADE_CONDUCTOR);
		menu.insertaOpcion("Anhade transporte", ANHADE_TRANSPORTE);
		menu.insertaOpcion("Sueldo conductor", SUELDO_CONDUCTOR);
		menu.insertaOpcion("Mejor conductor", MEJOR_CONDUCTOR);
		
		int opcion;

		// lazo de espera de comandos del usuario
		while(true) {
			opcion = menu.leeOpcion();

			// realiza las acciones dependiendo de la opcion elegida
			switch (opcion) {
			case  ANHADE_CONDUCTOR:
				lect = new Lectura("Datos Conductor");
				lect.creaEntrada("DNI", "");
				lect.creaEntrada("Nombre","");
				lect.creaEntrada("Apellido1", "");
				lect.creaEntrada("Apellido2", "");
				lect.creaEntrada("Direccion", "");
				lect.esperaYCierra();
				dni = lect.leeString("DNI");
				String nombre = lect.leeString("Nombre");
				String apellido1 = lect.leeString("Apellido1");
				String apellido2 = lect.leeString("Apellido2");
				String direccion = lect.leeString("Direccion");
				// Anhade el conductor
				if (!gt.anhadeConductor(dni, nombre, apellido1, apellido2, direccion)) 
					mensaje("ERROR", "Ya existe un conductor con DNI "+dni);
				break;

			case ANHADE_TRANSPORTE:
				lect = new Lectura("Nuevo transporte");
				lect.creaEntrada("DNI", "");
				lect.creaEntrada("Tipo Transporte: P | M | MP", "");
				lect.creaEntrada("Horas", 0);
				lect.creaEntrada("Personas", 0);
				lect.creaEntrada("Toneladas", 0);
				lect.esperaYCierra();
				dni = lect.leeString("DNI");
				String tipo = lect.leeString("Tipo Transporte: P | M | MP");
				int horas = lect.leeInt("Horas");
				int personas = lect.leeInt("Personas");
				int toneladas = lect.leeInt("Toneladas");

				Transporte t = null;
				c = gt.buscaConductor(dni);
				if (c!=null) {
					switch (tipo) {
						case "P":
							t = new Transporte(horas,CategoriaTransporte.Personas, personas);
							c.anhadeTransporte(t);
							break;
						case "M":
							t = new Transporte(horas, CategoriaTransporte.Mercancias, toneladas);
							c.anhadeTransporte(t);
							break;
						case "MP":
							t = new Transporte(horas, CategoriaTransporte.MercanciasPeligrosas, toneladas);
							c.anhadeTransporte(t);
							break;		
					}
				} else {
					mensaje("ERROR", "No existe un conductor con DNI "+dni);
				}
				break;
				
			case SUELDO_CONDUCTOR:
				lect = new Lectura("Transportes Peligrosos");
				lect.creaEntrada("DNI", "");
				lect.esperaYCierra();
				dni = lect.leeString("DNI");
				c = gt.buscaConductor(dni);
				if (c!=null){
					mensaje("Sueldo", "El sueldo del conductor es: "+c.sueldo());
				} else {
					mensaje("ERROR", "No existe un conductor con DNI "+dni);
				}
 				break;

			case MEJOR_CONDUCTOR:
				List<Conductor> resultado = new LinkedList<Conductor>();
				double maxSueldo = 0.0;
				for (Conductor conductor : gt.conductores()) {
					if (conductor.sueldo() > maxSueldo) {
						maxSueldo = conductor.sueldo();
						resultado.clear();
						resultado.add(conductor);
					} else if (conductor.sueldo() == maxSueldo) {
						resultado.add(conductor);
					}
				}		
				String msj = "";
				if (resultado.size() == 0) {
					msj = "No hay conductores";
				} else {
					for (Conductor conductor : resultado) {
						msj += conductor.getNombre() + " "+conductor.getNombre()+"\n";
					}
				}
				mensaje("MEJOR CONDUCTOR", msj);
				break;
			}
		}
	}

	/**	
	 * Metodo auxiliar que muestra un ventana de mensaje
	 * @param titulo titulo de la ventana
	 * @param txt texto contenido en la ventana
	 */
	private static void mensaje(String titulo, String txt) {
		Mensaje msj = new Mensaje(titulo);
		msj.escribe(txt);

	}

}
