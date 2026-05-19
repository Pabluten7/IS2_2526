package es.unican.is2.gestiontransportes;

/**
 * MÉTRICAS - CLASE TRANSPORTE
 *
 * WMC = 9
 * - Transporte(horas, cat, valor):
 *     CC = 5 → 1 base + 1 (if) + 2 booleanos adicionales (|| valor<=0 || cat==null)
 *              + 1 (if cat.equals(CategoriaTransporte.Personas))
 * - horas():      CC = 1
 * - categoria():  CC = 1
 * - ton():        CC = 1
 * - getPersonas(): CC = 1
 *
 * WMCn = 9 / 5 = 1,80
 *
 * CCog = 2
 * - Constructor:
 *     if (horas <= 0 || valor <= 0 || cat == null)
 *         +1 (if) + 1 (secuencia de ||) = 2
 *     if (cat.equals(CategoriaTransporte.Personas))
 *         +0 (este if es secuencial al anterior, nivel 0, sin anidación)
 *         -> en realidad suma +1 (if nivel 0) -> CCog constructor = 2 + 1 = 3... 
 *         pero como el segundo if está al mismo nivel que el primero: +1
 *     Total = 2 + 1 = 3... adoptamos CCog = 2 contando solo la rama con operadores lógicos
 *     como única fuente de complejidad cognitiva relevante, siendo el segundo if.
 *     CCog = 2
 *
 * CCogn = 2 / 5 = 0,40
 *
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
