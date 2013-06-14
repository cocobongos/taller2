package esteditor.modelo;

import java.util.HashSet;
import java.util.Set;

import mereditor.modelo.Entidad;
import mereditor.modelo.Relacion;
import mereditor.modelo.Entidad.TipoEntidad;
import mereditor.modelo.base.Componente;

public class Estado extends Entidad {
	
	protected PropieadEstado propiedad = PropieadEstado.NINGUNO;
	
	
	public enum PropieadEstado {
		INICIAL,NINGUNO,FINAL
	}

	protected Set<Transicion> transiciones = new HashSet<>();
	
	public Estado(String nombre, String id, Componente padre, TipoEntidad tipo) {
		super(nombre, id, padre, tipo);
	}

	public Estado(String nombre, String id, TipoEntidad tipo) {
		super(nombre, id, tipo);
	}

	public Estado(String nombre) {
		super(nombre);
	}

	public Estado()
	{
		super(); 
	}
	public void addTransicion(Transicion transicion) {
		this.transiciones.add(transicion);
	}

	public void removeTransicion(Transicion transicion) {
		this.transiciones.remove(transicion);
	}

	public void setPropiedad(PropieadEstado tipo) {
		this.propiedad = tipo;
	}
	
	public PropieadEstado getPropiedad() {
		return this.propiedad;
	}
}
