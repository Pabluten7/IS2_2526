package es.unican.is2.gestiontransportes;

/**
 * MÉTRICAS - CLASE TRANSPORTE
 * WMC = 9
 * - Métodos simples: 4 getters = 4
 * - Constructor: 5 (1 base + 2 condiciones del if + 2 booleanos adicionales por ||)
 * CCog = 2
 * - Constructor: 2 (1 por if + 1 por secuencia de ||)
 * CBO = 1 -> CategoriaTransporte
 * DIT = 0
 * NOC = 0
 */

/* Clase que representa un transporte realizado por un conductor */
public class Transporte {
	
	private double horas;
	private int ton;
	private int personas;
	private CategoriaTransporte cat;
	
	/**
	 * Constructor de la clase Transporte
	 * @param horas Horas que ha durado el transporte
	 * @param cat Categoria del transporte
	 * @param valor En caso de ser un transporte de tipo Personas, 
	 * representa el numero de personas, en caso de ser de tipo Mercancias 
	 * representa las toneladas
	 */ 
	public Transporte(double horas, CategoriaTransporte cat, int valor) throws IllegalArgumentException {
		if (horas <= 0 || valor <= 0 || cat == null) {
			throw new IllegalArgumentException();
		}
		this.horas = horas;
		this.cat = cat;
		if (cat.equals(CategoriaTransporte.Personas)) {
			this.personas = valor;
		} else  {
			this.ton = valor;
		}
	}
	
	public double horas() {
		return horas;
	}

	public CategoriaTransporte categoria() {
		return cat;
	}

	public int ton() {
		return ton;
	}

	public int getPersonas() {
		return personas;
	}
	
}
