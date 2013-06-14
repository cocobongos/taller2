package esteditor.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import esteditor.modelo.Transicion.TipoTransicion;
import mereditor.modelo.Atributo;
import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteAtributos;
import mereditor.modelo.base.ComponenteCardinal;
import mereditor.modelo.base.ComponenteNombre;
import mereditor.modelo.base.ComponenteTipado;
import mereditor.modelo.validacion.Observacion;
import mereditor.modelo.validacion.Validable;
import mereditor.modelo.validacion.ValidarCardinalidadCompleta;

/**
 * Created with IntelliJ IDEA.
 * User: peta
 * Date: 8/05/13
 * Time: 20:26
 * To change this template use File | Settings | File Templates.
 */
public class Transicion extends ComponenteNombre implements ComponenteAtributos, ComponenteTipado<TipoTransicion> {

	public enum TipoTransicion {
		ASOCIACION, COMPOSICION
	}

	protected TipoTransicion tipo = TipoTransicion.ASOCIACION;
	protected Set<Atributo> atributos = new HashSet<>();
	protected Set<EstadoTransicion> participantes = new HashSet<>();
	public Transicion() {
		super();
	}

	public Transicion(String nombre) {
		super(nombre);
	}

	public Transicion(String nombre, String id, TipoTransicion tipo) {
		super(nombre, id);
		this.tipo = tipo;
	}

	public void addAtributo(Atributo atributo) {
		this.atributos.add(atributo);
		atributo.setPadre(this);
	}

	public void removeAtributo(Atributo atributo) {
		this.atributos.remove(atributo);
	}
    
	public void addParticipante(EstadoTransicion participante) {
		this.participantes.add(participante);
		participante.getEstado().addTransicion(this);
	}

	public void removeParticipante(EstadoTransicion participante) {
		this.participantes.remove(participante);
		if(participante.getEstado() != null)
			participante.getEstado().removeTransicion(this);
	}

	/*
	 * Getter y setters
	 */
	@Override
	public TipoTransicion getTipo() {
		return tipo;
	}

	@Override
	public void setTipo(TipoTransicion tipo) {
		this.tipo = tipo;
	}

	public Set<EstadoTransicion> getParticipantes() {
		return Collections.unmodifiableSet(participantes);
	}

	public Set<Estado> getEstadosParticipantes() {
		Set<Estado> estados = new HashSet<>();

		for (EstadoTransicion estadoTransicion : this.participantes)
			estados.add(estadoTransicion.getEstado());

		return estados;
	}

	@Override
	public Set<Atributo> getAtributos() {
		return Collections.unmodifiableSet(atributos);
	}
	
	@Override
	public Collection<Componente> getComponentes() {
		return new ArrayList<Componente>(this.atributos);
	}
	
	@Override
	public void addValidaciones() {
		super.addValidaciones();
	}

	@Override
	public Observacion validar() {
		Observacion observacion = super.validar();

		for (Atributo atributo : this.atributos)
			observacion.addObservacion(atributo.validar());

		for (EstadoTransicion estadoTransicion : this.participantes)
			observacion.addObservacion(estadoTransicion.validar());

		return observacion;
	}

	@Override
	public boolean contiene(Componente componente) {
		boolean contiene = this.atributos.contains(componente);

		if (contiene)
			return contiene;

		// Verificar los hijos de los atributos
		for (Componente hijo : this.atributos) {
			contiene = hijo.contiene(componente);
			if (contiene)
				return contiene;
		}

		return super.contiene(componente);
	}

	/*
	 * Contiene la entidad que pertence a la relacion y su informacion asociada
	 * a la misma.
	 */
	public class EstadoTransicion implements Validable, ComponenteCardinal {
		protected Estado estado;
		protected String rol = "";
		protected String cardinalidadMinima = "1";
		protected String cardinalidadMaxima = "1";
		protected Transicion transicion;
		
		/**
		 * Lista de validaciones.
		 */
		protected List<mereditor.modelo.validacion.Validacion> validaciones = new ArrayList<>();

		public EstadoTransicion(Transicion transicion) {
			this.transicion = transicion;
			this.addValidaciones();
		}

		public EstadoTransicion(Transicion transicion, Estado estado, String rol,
				String cardinalidadMinima, String cardinalidadMaxima) {
			this(transicion);
			this.estado = estado;
			this.rol = rol;
			this.cardinalidadMinima = cardinalidadMinima;
			this.cardinalidadMaxima = cardinalidadMaxima;
		}

		public Estado getEstado() {
			return estado;
		}

		public void setEstado(Estado estado) {
			this.estado = estado;
		}

		public String getRol() {
			return rol;
		}

		public void setRol(String rol) {
			this.rol = rol;
		}

		@Override
		public String getCardinalidadMinima() {
			return cardinalidadMinima;
		}

		@Override
		public String getCardinalidadMaxima() {
			return cardinalidadMaxima;
		}

		@Override
		public void setCardinalidadMinima(String cardinalidadMinima) {
			this.cardinalidadMinima = cardinalidadMinima;
		}

		@Override
		public void setCardinalidadMaxima(String cardinalidadMaxima) {
			this.cardinalidadMaxima = cardinalidadMaxima;
		}

		@Override
		public String toString() {
			String label = "";
			if (!this.cardinalidadMinima.equals("1") || !this.cardinalidadMaxima.equals("1"))
				label = "(" + this.cardinalidadMinima + ", " + this.cardinalidadMaxima + ")";

			if (this.rol != null)
				label += " " + this.rol;

			return label;
		}

		public Transicion getTransicion(){
			return this.transicion;
		}
		
		@Override
		public void addValidaciones() {
			this.validaciones.add(new ValidarCardinalidadCompleta());
		}

		@Override
		public Observacion validar() {
			Observacion observacion = new Observacion(this);
			
			for (mereditor.modelo.validacion.Validacion validacion : this.validaciones)
				observacion.addObservaciones(validacion.validar(this));
			
			return observacion;
		}
	}

}
