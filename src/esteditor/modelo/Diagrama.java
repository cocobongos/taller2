package esteditor.modelo;

import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;

public class Diagrama extends ComponenteNombre {

	public Diagrama() {
		
	}

	public Diagrama(String nombre) {
		super(nombre);
	}

	public Diagrama(String nombre, String id) {
		super(nombre, id);
	}

	public Diagrama(String nombre, String id, Componente padre) {
		super(nombre, id, padre);
	}

}
