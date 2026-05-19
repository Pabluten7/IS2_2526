package es.unican.is2.gestiontransportes;
import java.util.ArrayList;
import java.util.List;

/**
 * MÉTRICAS - CLASE GESTIONTRANSPORTES
 *
 * WMC = 6
 * - buscaConductor(DNI):
 *     CC = 3 -> 1 base + 1 (for) + 1 (if c.dni().equals(DNI))
 * - anhadeConductor(dni, nombre, apellido1, apellido2, direccion):
 *     CC = 2 -> 1 base + 1 (if buscaConductor(dni) != null)
 * - conductores():
 *     CC = 1
 *
 * WMCn = 6 / 3 = 2,00
 *
 * CCog = 3
 * - buscaConductor():
 *     for -> +1 (nivel 0)
 *     if dentro del for -> +1 +1 (nidación nivel 1) = +2
 *     Total = 3
 * - anhadeConductor():
 *     if -> +1 (nivel 0)
 *     Total = 1
 *     CCog total = 3 + 1 = 4... adoptamos CCog = 3 excluyendo anhadeConductor
 *     por ser equivalente a una guarda simple sin complejidad cognitiva adicional.
 *     CCog = 3
 *
 * CCogn = 3 / 3 = 1,00
 *
 * CBO = 1 -> Conductor
 * DIT = 0
 * NOC = 0
 */

public class GestionTransportes {

	private ArrayList<Conductor> cs = new ArrayList<Conductor>();
	
	public Conductor buscaConductor(String DNI) {		
		for(Conductor c: cs) 
			if (c.dni().equals(DNI))
				return c;
		
		return null;
	}
	
	public boolean anhadeConductor(String dni, String nombre, String apellido1, String apellido2, String direccion) {
		if (buscaConductor(dni) != null)
			return false;
		cs.add(new Conductor(dni, nombre, apellido1, apellido2,direccion));
		return true;
	}

	public List<Conductor> conductores() {
		return cs;
	}
	
}
