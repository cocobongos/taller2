package esteditor.modelo;

import mereditor.modelo.base.Componente;

/***
 * 
 * @author cocobongos 
 *
 */
public class EstadoSubDiagrama extends Estado {

	private Diagrama diagrama;
	
	
	/**
	 * @param nombre
	 * @param id
	 * @param padre
	 * @param tipo
	 * @param diagrama
	 */
	public EstadoSubDiagrama(String nombre, String id, Componente padre,
			PropieadEstado tipo, Diagrama diagrama) {
		super(nombre, id, padre, tipo);
		this.diagrama = diagrama;
	}

	/**
	 * @param nombre
	 * @param id
	 * @param padre
	 * @param tipo
	 */
	public EstadoSubDiagrama(String nombre, String id, Componente padre,
			PropieadEstado tipo) {
		super(nombre, id, padre, tipo);
	}

	/**
	 * @param nombre
	 * @param id
	 * @param tipo
	 */
	public EstadoSubDiagrama(String nombre, String id, PropieadEstado tipo) {
		super(nombre, id, tipo);
	}

	/**
	 * @param nombre
	 */
	public EstadoSubDiagrama(String nombre) {
		super(nombre);
	}

	public EstadoSubDiagrama() {
		super();
	}
	
	
	public EstadoSubDiagrama(Diagrama d) {
		super();
		diagrama = d;
	}
	
	
	

	public Diagrama getDiagrama() {
		return diagrama;
	}

	public void setDiagrama(Diagrama diagrama) {
		this.diagrama = diagrama;
	}

}
