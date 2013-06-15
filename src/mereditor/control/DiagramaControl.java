package mereditor.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import mereditor.interfaz.swt.figuras.EstadoFigure;
import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.Atributo;
import mereditor.modelo.Diagrama;
import mereditor.modelo.Proyecto;
//import mereditor.modelo.Entidad.Identificador;
import esteditor.modelo.Estado.Identificador;
import esteditor.modelo.Transicion;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Figure;

import esteditor.modelo.Estado;

public class DiagramaControl extends Diagrama implements Control<Diagrama> {
	
	public DiagramaControl(Proyecto proyecto) {
		super(proyecto);
	}

	@Override
	public Figura<Diagrama> getFigura(String idDiagrama) {
		return null;
	}

	@Override
	public void dibujar(Figure contenedor, String idDiagrama) {
		idDiagrama = idDiagrama != null ? idDiagrama : this.id;

		Set<Estado> entidades = this.getEstados(false);
		this.dibujar(contenedor, idDiagrama, entidades);
		this.dibujar(contenedor, idDiagrama, this.getTransiciones(false));
		this.dibujar(contenedor, idDiagrama, this.getJerarquias(false));

		List<Identificador> identificadores = new ArrayList<>();
		for (Estado entidad : entidades)
			identificadores.addAll(entidad.getIdentificadores());

		this.dibujarIdentificadores(identificadores);
	}

	/**
	 * Se encarga de la lógica de dibujar las conexiones entre los diferentes
	 * elementos de los <code>Identificador</code>es de las <code>Entidad</code>
	 * es que pertenecen a este <code>Diagrama</code>.
	 * 
	 * @param identificadores
	 *            Lista de todos los <code>Identificador</code>es de las
	 *            <code>Entidad</code>es del <code>Diagrama</code>.
	 */
	private void dibujarIdentificadores(List<Identificador> identificadores) {
		for (Identificador identificador : identificadores) {
			List<Connection> conexiones = new ArrayList<>();

			EstadoControl entidadCtrl = (EstadoControl) identificador.getEstado();
			EstadoFigure figEntidad = (EstadoFigure) entidadCtrl.getFigura(this.id);

			// Internos y mixtos
			if (identificador.isInterno()) {
				// Agregar los conectores a los atributos
				for (Atributo atributo : identificador.getAtributos())
					conexiones.add(figEntidad.getConexion(atributo.getId()));
			}
			// Externos
			if (identificador.isExterno()) {
				// Recorrer las entidades del identificador
				for (Estado entidadIdf : identificador.getEstados()) {
					// Encontrar la relacion que comparten
					TransicionControl relacion = (TransicionControl) entidadCtrl.transicion(entidadIdf);
					if (relacion != null && this.contiene(relacion)) {
						Figura<Transicion> figRelacion = relacion.getFigura(this.getId());
						// Obtener el conector de la relacion con la entidad del
						// identificador
						Connection conexion = figRelacion.getConexion(entidadIdf.getId());
						// Unir el conector con la entidad que tiene el
						// identificador.
						conexiones.add(figEntidad.conectarEntidad(entidadIdf.getId(), conexion));
					}
				}
			}
			// Mixtos
			if (identificador.isMixto()) {
				// Agregar los conectores a los atributos (se repite código para
				// mayor claridad)
				for (Atributo atributo : identificador.getAtributos())
					conexiones.add(figEntidad.getConexion(atributo.getId()));
				// Agregar los conectores de las entidades con la relacion
				for (Estado entidadIdf : identificador.getEstados()) {
					TransicionControl relacion = (TransicionControl) entidadCtrl.transicion(entidadIdf);
					if (relacion != null && this.contiene(relacion)) {
						Figura<Transicion> figRelacion = relacion.getFigura(this.getId());
						conexiones.add(figRelacion.getConexion(entidadIdf.getId()));
					}
				}
			}

			figEntidad.conectarIdentificador(conexiones);
		}
	}

	private void dibujar(Figure contenedor, String id, Collection<?> componentes) {
		for (Object componente : componentes)
			((Control<?>) componente).dibujar(contenedor, id);
	}

	public void dibujar(Figure contenedor) {
		this.dibujar(contenedor, this.id);
	}

	@Override
	public String getNombreIcono() {
		return "diagrama.png";
	}
}

