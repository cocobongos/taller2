package esteditor.modelo;

import mereditor.modelo.Entidad;
import mereditor.modelo.Entidad.TipoEntidad;
import mereditor.modelo.base.Componente;

public class Estado extends Entidad {
	
	protected PropieadEstado propiedad;
	
	public enum PropieadEstado {
		INICIAL, FINAL
	}

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

	public void setPropiedad(PropieadEstado tipo) {
		this.propiedad = tipo;
	}
}
