package esteditor.modelo;

import mereditor.modelo.base.Componente;

/**
 * 
 * @author cocobongos
 *
 */
public class EstadoFormulario extends Estado {

	public EstadoFormulario(String nombre, String id, Componente padre,
			TipoEntidad tipo) {
		super(nombre, id, padre, tipo);
	}

	public EstadoFormulario(String nombre, String id, TipoEntidad tipo) {
		super(nombre, id, tipo);
	}

	public EstadoFormulario(String nombre) {
		super(nombre);
	}

	/***
	 * 
	 */
	public EstadoFormulario() {
		super();
	}

}
