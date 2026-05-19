package es.unican.is2.gestiontransportes;
import java.util.ArrayList;

/**
 * Clase que representa a un conductor, con sus datos personales
 * y los transportes que ha realizado. 
 */

/**
 * MÉTRICAS - CLASE CONDUCTOR
 * 
 * WMC = 18
 * - Constructor(dni, nombre, apellido1, apellido2, direccion):
 *     CC = 5 -> 1 base + 4 booleanos en if (dni==null || nombre==null || apellido1==null || direccion==null)
 * - dni():          CC = 1
 * - getDni():       CC = 1
 * - getNombre():    CC = 1
 * - getApellido1(): CC = 1
 * - apellido2():    CC = 1
 * - getDire():      CC = 1
 * - anhadeTransporte(): CC = 1
 * - sueldo():
 *     CC = 6 -> 1 base + 1 (for) + 3 (case Mercancias, case MercanciasPeligrosas, case Personas)
 *              + 1 (if t.getPersonas() < 10)
 *
 * WMCn = 18 / 8 = 2,25
 *
 * CCog = 9
 * - Constructor:
 *     if (dni==null || nombre==null || apellido1==null || direccion==null)
 *         +1 (if) + 1 (||) + 1 (||) + 1 (||) = 4
 * - sueldo():
 *     for -> +1 (nivel 0)
 *     switch dentro del for → +1 +1 (nidación nivel 1) = +2
 *     if (t.getPersonas() < 10) dentro del switch → +1 +2 (nidación nivel 2) = +3
 *     else -> +1 (siempre plano)
 *     Total sueldo = 1 + 2 + 3 + 1 = 7
 *     CCog total = 4 + 7 = 9
 *
 * CCogn = 9 / 8 = 1,13
 *
 * CBO = 1 -> Transporte
 * DIT = 0
 * NOC = 0
 */

public class Conductor {

	private ArrayList<Transporte> transportes = new ArrayList<Transporte>();
	private String dni;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String dire;

	public Conductor(String dni, String nombre, String apellido1,
			String apellido2, String direccion) {
		if (dni == null || nombre == null || apellido1 == null || direccion == null) {
			throw new IllegalArgumentException();
		}
		this.dni = dni;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.dire = direccion;
	}

	public String dni() {
		return dni;
	}

	public String getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public String apellido2() {
		return apellido2;
	}

	public String getDire() {
		return dire;
	}

	public double sueldo() {
		double sueldoTransportes = 0;
		for (Transporte t : transportes) {
			double sueldoExtraTransporte = 0.0;
			switch (t.categoria()) {
				case Mercancias:
					sueldoExtraTransporte = t.ton() * 2;
					break;
				case MercanciasPeligrosas:
					sueldoExtraTransporte = t.ton() * 2 + 50;
					break;
				case Personas:
					if (t.getPersonas() < 10)
						sueldoExtraTransporte = t.horas() * 0.5;
					else
							sueldoExtraTransporte = t.horas();
					break;
			}
			sueldoTransportes += t.horas() * 5 + sueldoExtraTransporte;
		}
		return 700 + sueldoTransportes;
	}

	public void anhadeTransporte(Transporte t) {
		transportes.add(t);
	}

}
