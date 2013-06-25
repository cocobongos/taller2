package mereditor.interfaz.swt.editores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import mereditor.interfaz.swt.Principal;
import esteditor.modelo.Estado;
import esteditor.modelo.Transicion;
import esteditor.modelo.Transicion.EstadoTransicion;

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public class EstadoTransicionTabla extends Tabla<EstadoTransicion> {
	private Transicion transicion;
	private List<String> options;
	private List<Estado> optionsEstado;

	public EstadoTransicionTabla(Composite parent, Transicion transicion)
	{
		super(parent);
		this.transicion = transicion;
	}

	@Override
	protected void initColumnas() {
		this.columnas.add(Editor.ESTADO);
		this.columnas.add(Editor.ESTADOTIPOTRANSICION);
	}

	@Override
	protected void initEditorsCeldas(Table table) {
		this.loadEntidades();

		this.editoresCeldas.add(new ComboBoxCellEditor(table, options
				.toArray(new String[options.size()]), SWT.READ_ONLY));
		this.editoresCeldas.add(new TextCellEditor(table));
	}
	
	private void loadEntidades() {
		Set<Estado> estados = Principal.getInstance().getProyecto()
				.getEstadosDiagrama();
		
		this.optionsEstado = new ArrayList<>(estados);
		Collections.sort(this.optionsEstado);		
		this.options = new ArrayList<>();		

		for (Estado estado : this.optionsEstado) {
			options.add(estado.getNombre());
		}		
	}

	@Override
	protected String getTextoColumna(EstadoTransicion element, int columnIndex) {
		String property = this.columnas.get(columnIndex);
		switch (property) {
		case Editor.ESTADO:
			return element.getEstado() != null ? element.getEstado()
					.getNombre() : "";
		default:
			return (String) this.getValorCelda(element, property);
		}
	}

	@Override
	protected Object getValorCelda(EstadoTransicion element, String property) {
		switch (property) {
		case Editor.ESTADO:
			String nombre = element.getEstado() != null ? element.getEstado()
					.getNombre() : null;
			return nombre != null ? options.indexOf(nombre) : 0;
		case Editor.ESTADOTIPOTRANSICION:
			return element.getEstadoTipo();
		default:
			throw new RuntimeException("Propiedad invalida '" + property
					+ "' al obtener su valor.");
		}
	}

	@Override
	protected void setValorCelda(EstadoTransicion element, String property,
			Object value) {
		switch (property) {
		case Editor.ESTADO:
			element.setEstado(this.optionsEstado.get((int) value));
			break;
		case Editor.ESTADOTIPOTRANSICION:
			element.setestadoTipo(value.toString());
			break;
		default:
			throw new RuntimeException("Propiedad invalida '" + property
					+ "' al establecer su valor.");
		}
	}

	@Override
	protected EstadoTransicion nuevoElemento() {
		return transicion.new EstadoTransicion(transicion);
	}

	@Override
	protected void abrirEditor(EstadoTransicion elemento) {
		// No es necesario.
	}

	@Override
	protected EstadoTransicion agregarElemento() {
		return null;
	}
}
