package mereditor.interfaz.swt.dialogs;

import java.util.Set;

import mereditor.control.TransicionControl;
import mereditor.interfaz.swt.editores.Editor;
import mereditor.interfaz.swt.editores.EditorFactory;

import org.eclipse.swt.widgets.Shell;

import esteditor.modelo.Transicion;

public class AgregarTransicionDialog extends AgregarComponenteDialog<Transicion> {

	/**
	 * @wbp.parser.constructor
	 */
	protected AgregarTransicionDialog(Shell shell) {
		super(shell);
	}

	public AgregarTransicionDialog() {
		super();
	}

	@Override
	protected Set<Transicion> loadComponentes() {
		// Obtener las entidades de los ancestros
		Set<Transicion> relaciones = this.principal.getProyecto().getTransicionesDisponibles();
		Set<Transicion> entidadesDiagrama = this.principal.getProyecto().getTransicionesDiagrama();
		// Quitar las que ya tiene
		relaciones.removeAll(entidadesDiagrama);

		return relaciones;
	}

	@Override
	protected Editor<?> getEditor() {
		return EditorFactory.getEditor(new TransicionControl());
	}

	@Override
	protected String getNombreComponente() {
		return "Transicion";
	}
}
