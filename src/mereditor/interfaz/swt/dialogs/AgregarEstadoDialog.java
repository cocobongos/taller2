package mereditor.interfaz.swt.dialogs;

import java.util.Set;

import mereditor.control.EntidadControl;
import mereditor.interfaz.swt.editores.Editor;
import mereditor.interfaz.swt.editores.EditorFactory;
import mereditor.modelo.Entidad;

import org.eclipse.swt.widgets.Shell;

import esteditor.modelo.Estado;

public class AgregarEstadoDialog extends AgregarComponenteDialog<Estado> {

	/**
	 * @wbp.parser.constructor
	 */
	protected AgregarEstadoDialog(Shell shell) {
		super(shell);
	}

	public AgregarEstadoDialog() {
		super();
	}

	@Override
	protected Set<Estado> loadComponentes() {
		// Obtener las estados de los ancestros
		Set<Estado> estados = this.principal.getProyecto().getEstadosDisponibles();
		Set<Estado> estadosDiagrama = this.principal.getProyecto().getEstadosDiagrama();

		// Quitar las que ya tiene
		estados.removeAll(estadosDiagrama);

		return estados;
	}
	
	@Override
	protected Editor<?> getEditor() {
		return EditorFactory.getEditor(new EntidadControl());
	}

	@Override
	protected String getNombreComponente() {
		return "Estado";
	}


}
