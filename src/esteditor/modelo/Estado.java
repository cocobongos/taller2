package esteditor.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;
import mereditor.modelo.Relacion;
import mereditor.modelo.Entidad.Identificador;
import mereditor.modelo.Entidad.TipoEntidad;
import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteAtributos;
import mereditor.modelo.base.ComponenteNombre;
import mereditor.modelo.validacion.Observacion;

public class Estado  extends ComponenteNombre implements ComponenteAtributos {
	
	protected PropieadEstado propiedad = PropieadEstado.NINGUNO;
	protected Set<Atributo> atributos = new HashSet<>();
	protected Set<Identificador> identificadores = new HashSet<>();
	
	public enum PropieadEstado {
		INICIAL,NINGUNO,FINAL
	}

	protected Set<Transicion> transiciones = new HashSet<>();
	
	public Estado() {
		super();
	}

	public Estado(String nombre) {
		super(nombre);
	}
	
	public Estado(String nombre, String id, Componente padre, PropieadEstado tipo) {
		super(nombre, id, padre);
		this.propiedad = tipo;
	}

	public Estado(String nombre, String id, PropieadEstado tipo) {
		super(nombre, id);
		this.propiedad = tipo;
	}

	public void addIdentificador(Identificador identificador) {
		identificadores.add(identificador);
	}

	public void removeIdentificador(Identificador identificador) {
		identificadores.remove(identificador);
	}
	public void addAtributo(Atributo atributo) {
		this.atributos.add(atributo);
		atributo.setPadre(this);
	}
	
	public void removeAtributo(Atributo atributo) {
		this.atributos.remove(atributo);
		// Lista donde se guardan los identificadores a editar
		// se debe hacer de esta forma dado que no se puede modificar
		// una collection que se esta recorriendo con un iterador.
		List<Identificador> identificadores = new ArrayList<>();

		// Recorrer los identificadores para buscar el atributo y sacarlo.
		for (Identificador identificador : this.identificadores)
			if (identificador.contiene(atributo))
				identificadores.add(identificador);

		for (Identificador identificador : this.identificadores)
			identificador.removeAtributo(atributo);
	}
	public void addTransicion(Transicion transicion) {
		this.transiciones.add(transicion);
	}

	public void removeTransicion(Transicion transicion) {
		this.transiciones.remove(transicion);
	}

	@Override
	public Collection<Atributo> getAtributos() {
		return Collections.unmodifiableCollection(this.atributos);
	}

	public Set<Identificador> getIdentificadores() {
		return Collections.unmodifiableSet(this.identificadores);
	}

	public Set<Transicion> getTransiciones() {
		return Collections.unmodifiableSet(this.transiciones);
	}

	public Transicion transicion(Estado entidad) {
		Set<Transicion> interseccion = new HashSet<>(this.transiciones);
		interseccion.retainAll(entidad.transiciones);

		if (interseccion.size() > 0)
			return interseccion.iterator().next();

		return null;
	}
	
	@Override
	public Collection<Componente> getComponentes() {
		return new ArrayList<Componente>(this.atributos);
	}

	public void setPropiedad(PropieadEstado tipo) {
		this.propiedad = tipo;
	}

	
	public PropieadEstado getPropiedad() {
		return this.propiedad;
	}
	public Observacion validar() {
		Observacion observacion = super.validar();

		for (Atributo atributo : this.atributos)
			observacion.addObservacion(atributo.validar());

		return observacion;
	}

	@Override
	public boolean contiene(Componente componente) {
		boolean contiene = this.atributos.contains(componente);
		if (contiene)
			return contiene;
		for (Componente hijo : this.atributos) {
			contiene = hijo.contiene(componente);
			if (contiene)
				return contiene;
		}
		return false;
	}

	
	/**
	 * Representa un identificador de una identidad. Contiene referencias a los
	 * Atributos y Entidades que componen el Identificador.
	 */
	public class Identificador {
		private Estado entidad;
		private Set<Atributo> atributos = new HashSet<>();
		private Set<Estado> entidades = new HashSet<>();
		
		//FIXME estado deberia heredar de entidad? si, hasta que punto? no, es el mismo codigo?
		private Set<Estado> estados = new HashSet<>();

		public Identificador(Estado entidad) {
			if (entidad == null)
				throw new IllegalArgumentException("Entidad no puede ser null.");

			this.entidad = entidad;
		}

		public void addAtributo(Atributo atributo) {
			if (!this.entidad.contiene(atributo))
				throw new IllegalArgumentException(
						"Un atributo debe pertenecer a la entidad para ser identificador.");

			this.atributos.add(atributo);
		}

		public void removeAtributo(Atributo atributo) {
			this.atributos.remove(atributo);
			this.verificarVacio();
		}

		public void addEstado(Estado entidad) {
			if (this.entidad.equals(entidad))
				throw new RuntimeException(
						"Una Entidad no puede ser su propio identificador.");

			this.entidades.add(entidad);
		}

		public void removeEstado(Estado entidad) {
			this.entidades.remove(entidad);
			this.verificarVacio();
		}

		/**
		 * Verifica si el identificador no tiene ningun atributo o entidad y lo
		 * elimina en ese caso.
		 */
		private void verificarVacio() {
			if (this.atributos.isEmpty() && this.entidades.isEmpty())
				this.entidad.identificadores.remove(this);
		}

		public Set<Atributo> getAtributos() {
			return Collections.unmodifiableSet(atributos);
		}

		public Set<Estado> getEstados() {
			return Collections.unmodifiableSet(entidades);
		}

		/*
		 * Devuelve la entidad de la cual es identificador.
		 */
		public Estado getEstado() {
			return this.entidad;
		}

		public boolean isInterno() {
			return this.entidades.isEmpty();
		}

		public boolean isExterno() {
			return this.atributos.isEmpty();
		}

		public boolean isMixto() {
			return !this.atributos.isEmpty() && !this.entidades.isEmpty();
		}

		/**
		 * Indica si el identificador contiene al componente especificado.
		 * 
		 * @param componente
		 * @return
		 */
		public boolean contiene(Componente componente) {
			return this.atributos.contains(componente)
					|| this.entidades.contains(componente);
		}


	}
}
