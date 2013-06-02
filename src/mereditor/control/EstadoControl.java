package mereditor.control;

import java.util.HashMap;
import java.util.Map;

import mereditor.interfaz.swt.editores.EditorFactory;
import mereditor.interfaz.swt.figuras.EstadoFigure;
import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.Atributo;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;

import esteditor.modelo.Estado;

public class EstadoControl extends Estado implements Control<Estado>, MouseListener {
	protected Map<String, EstadoFigure> figures = new HashMap<>();

	@Override
	public Figura<Estado> getFigura(String idDiagrama) 
	{
		if (!this.figures.containsKey(idDiagrama)) 
		{
			EstadoFigure figura = new EstadoFigure(this);
			this.figures.put(idDiagrama, figura);
			
			this.setPosicionInicial(figura);
		}
		
		this.figures.get(idDiagrama).actualizar();

		return this.figures.get(idDiagrama);
	}

	/**
	 * Establecer la posición cuando es una nueva figura.
	 * @param figura
	 */
	private void setPosicionInicial(Figure figura) {
		figura.setBounds(figura.getBounds().getTranslated(100, 100));		
	}

	@Override
	public void dibujar(Figure contenedor, String idDiagrama) {
		EstadoFigure figuraEntidad = (EstadoFigure) this.getFigura(idDiagrama);
		contenedor.add(figuraEntidad);
		

		/*
		 * Dibujar y conectar Atributos
		 */
		for (Atributo atributo : this.atributos) {
			AtributoControl atributoControl = (AtributoControl) atributo;

			figuraEntidad.conectarAtributo(atributoControl.getFigura(idDiagrama));
			atributoControl.dibujar(contenedor, idDiagrama);
			figuraEntidad.addFiguraLoqueada(atributoControl.getFigura(idDiagrama));
		}
	}

	public Map<String, EstadoFigure> getFiguras() {
		return this.figures;
	}
	
	@Override
	public String getNombreIcono() {
		return "estado.png";
	}

	@Override
	public void mouseDoubleClicked(MouseEvent event) {
		EditorFactory.getEditor(this).open();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}
}
