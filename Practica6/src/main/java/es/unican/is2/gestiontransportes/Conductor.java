package es.unican.is2.gestiontransportes;
import java.util.ArrayList;

/**
 * Clase que representa a un conductor, con sus datos personales
 * y los transportes que ha realizado. 
 */

/**
 * MÉTRICAS INICIALES - CLASE CONDUCTOR
 * WMC = 18
 * - Métodos simples: 1 + 6 getters = 7
 * - Contructor: 5 (1 base + 4 condiciones)
 * - Método sueldo(): 6 (1 base + 1 por for + 1 por switch + 1 por if)
 * CCog = 9
 * - Constructor: 4 (1 base + 4 condiciones)
 * - Método sueldo(): 7 (1 for + 2 witch anidado + 3 por if anidado + 1 else)
 * CBO = 1
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
