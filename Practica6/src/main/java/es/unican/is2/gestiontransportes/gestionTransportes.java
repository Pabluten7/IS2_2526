package es.unican.is2.gestiontransportes;
import java.util.ArrayList;
import java.util.List;

/**
 * MÉTRICAS - CLASE GESTIONTRANSPORTES
 * WMC = 6
 * - conductores(): 1
 * - buscaConductor(): 3 (1 base + 1 por for + 1 por if)
 * - anhadeConductor(): 2 (1 base + 1 por if)
 * CCog = 3
 * - buscaConductor(): 2 (1 por for + 1 por if anidado en for)
 * - anhadeConductor(): 1 (1 por if)
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
