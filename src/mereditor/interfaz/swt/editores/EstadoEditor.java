package mereditor.interfaz.swt.editores;

import java.util.List;

import mereditor.control.EstadoControl;
import esteditor.modelo.Estado;
import esteditor.modelo.Estado.PropieadEstado;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class EstadoEditor extends Editor<Estado> {
	protected Text txtNombre;
	protected Combo cboTipo;

	/**
	 * Constructor para el editor visual
	 * 
	 * @wbp.parser.constructor
	 */
	protected EstadoEditor(Shell shell) {
		super(shell);
		this.componente = new EstadoControl();
	}

	public EstadoEditor() {
		this(new EstadoControl());
	}

	public EstadoEditor(Estado Estado) {
		super(Estado);
		this.titulo = "Editor - " + componente.getNombre();
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(400, 600);
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);

		/**
		 * Campos generales.
		 */
		Composite header = new Composite(dialogArea, SWT.NONE);
		header.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		header.setLayout(new GridLayout(2, false));

		this.txtNombre = createLabelText(header, NOMBRE);
		this.txtNombre.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		this.cboTipo = createLabelCombo(header, PROPIEDAD);
		this.cboTipo.setItems(Editor.PropiedadEstados);

		
		Composite botonesIdentificadores = new Composite(dialogArea, SWT.NONE);
		botonesIdentificadores.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, false, false, 1, 1));
		botonesIdentificadores.setLayout(new RowLayout(SWT.VERTICAL));
		
		Button btnNuevoAtributo = new Button(botonesIdentificadores, SWT.PUSH);
		btnNuevoAtributo.setText(Editor.INTERFAZ);

		Button btnEliminarAtributo = new Button(botonesIdentificadores, SWT.PUSH);
		btnEliminarAtributo.setText(Editor.SUBDIAGRAMA);

		/*
		 * ExpandBar expandBar = new ExpandBar(botones, SWT.NONE);
		 * expandBar.setLayoutData(new RowData(98, SWT.DEFAULT));
		 * expandBar.setSpacing(6);
		 */

		return dialogArea;
	}

	@Override
	protected void cargarDatos() {
		this.txtNombre.setText(this.componente.getNombre());
		this.cboTipo.setText(this.componente.getTipo().name());
	}

	@Override
	protected void aplicarCambios() {
		componente.setNombre(txtNombre.getText());
		componente.setPropiedad(PropieadEstado.valueOf(this.cboTipo.getText()));

	}

	@Override
	protected boolean validar(List<String> errors) {
		if (this.txtNombre.getText().length() == 0)
			errors.add("Debe completar el nombre.");

		return errors.isEmpty();
	}
}
