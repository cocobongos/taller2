package mereditor.interfaz.swt.figuras;

import java.util.List;

import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import esteditor.modelo.Estado;

public class EstadoFigure extends Figura<Estado> {

	private Ellipse ellipse;
	
	
	public EstadoFigure(Estado e) {
		super(e);
		this.setRepresentacion(EstilosFiguras.get(Entidad.class, this.componente.getTipo()));
	}

	@Override
	protected void init() {
		
		this.lblName = new Label();
		this.lblName.setFont(estadoFont);
		
		this.ellipse = new Ellipse();
		this.ellipse.setOpaque(false);
		this.ellipse.setLocation(this.getLocation());
		this.ellipse.setAntialias(SWT.ON);
		this.ellipse.setBackgroundColor(stateColor);
		this.ellipse.setLayoutManager(new BorderLayout());
		this.ellipse.setSize(this.lblName.getTextBounds().getSize().getExpanded(20, 5));
		this.ellipse.add(this.lblName, BorderLayout.CENTER);
		this.ellipse.setSize(new Dimension(100, 100));

		this.setBounds(ellipse.getBounds());
		this.add(ellipse);
		this.actualizar();
	}

	/**
	 * Conecta un atributo a esta figura
	 * 
	 * @param figura
	 */
	public Connection conectarAtributo(Figura<Atributo> figura) {
		Connection conexion = Figura.conectarChopboxEllipse(this, figura);

		this.getParent().add(conexion);
		this.conexiones.put(figura.componente.getId(), conexion);

		return conexion;
	}

	/**
	 * Conecta esta entidad con el conector que une otra entidad con una
	 * relación en común.
	 * 
	 * @param id
	 *            identificador de la otra entidad.
	 * @param conexionEntidad
	 *            conector entre la otra entidad y la relacion.
	 * @return conexion entre esta entidad y el circulo en el midpoint de
	 *         conexionEntidad.
	 */
	public Connection conectarEntidad(String id, Connection conexionEntidad) {
		Ellipse circuloConexion = this.circuloIdentificador();
		conexionEntidad.add(circuloConexion, new MidpointLocator(conexionEntidad, 0));

		Connection conexion = Figura.conectarChopbox(this, circuloConexion);
		this.getParent().add(conexion);
		this.conexiones.put(id, conexion);

		return conexion;
	}

	@Override
	public void actualizar() {
		this.lblName.setText(this.componente.getNombre());
	}

	/**
	 * Conecta los atributos y entidades que pertenecen a un mismo
	 * identificador.
	 * 
	 * @param conexiones
	 */
	public void conectarIdentificador(List<Connection> conexiones) {
		if (conexiones.size() > 1 && this.getParent() != null) {
			Connection conexionOrigen = conexiones.get(0);
			Ellipse circuloOrigen = this.circuloIdentificador();
			conexionOrigen.add(circuloOrigen, new MidpointLocator(conexionOrigen, 0));

			for (Connection conexion : conexiones) {
				if (conexion != conexionOrigen) {
					Ellipse circulo = this.circuloIdentificador();
					conexion.add(circulo, new MidpointLocator(conexion, 0));

					this.getParent().add(Figura.conectarChopbox(circuloOrigen, circulo));

					conexionOrigen = conexion;
					circuloOrigen = circulo;
				}
			}
		}
	}

	/**
	 * Devuelve una figura circulo que se utiliza para unir los identificadores.
	 * 
	 * @return
	 */
	private Ellipse circuloIdentificador() {
		Color negro = new Color(null, 0, 0, 0);
		Ellipse circulo = new Ellipse();
		circulo.setAntialias(SWT.ON);
		circulo.setSize(10, 10);
		circulo.setBackgroundColor(negro);
		return circulo;
	}
}
