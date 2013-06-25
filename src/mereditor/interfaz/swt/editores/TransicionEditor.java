package mereditor.interfaz.swt.editores;

import java.util.List;

import mereditor.control.TransicionControl;
import esteditor.modelo.Transicion;
import esteditor.modelo.Transicion.EstadoTransicion;
import esteditor.modelo.Transicion.TipoTransicion;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

public class TransicionEditor extends Editor<Transicion> {
	protected Text txtNombre;
	protected Text txtDescripcion;
	protected Combo cboTipo;
	protected AtributosTabla tblAtributos;
	protected EstadoTransicionTabla tblEstados;

	/**
	 * Utilizado para la creaci��n de una nueva relacion.
	 */
	public TransicionEditor() {
		this(new TransicionControl());
	}

	public TransicionEditor(Transicion transicion) {
		super(transicion);
		this.titulo = "Editor - " + componente.getNombre();
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(400, 500);
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

		this.txtNombre = createLabelText(header, Editor.NOMBRE);
		this.txtNombre.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		this.txtDescripcion = createLabelText(header, Editor.DESCRIPCION);
		this.txtDescripcion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		this.cboTipo = createLabelCombo(header, Editor.TIPO);
		this.cboTipo.setItems(Editor.TiposTransiciones);

		/**
		 * Atributos.
		 
		Group grupoAtributos = new Group(dialogArea, SWT.NONE);
		grupoAtributos.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grupoAtributos.setText("Atributos");
		grupoAtributos.setLayout(new GridLayout(1, true));
		
		Composite botonesAttrs = new Composite(grupoAtributos, SWT.NONE);
		botonesAttrs.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, false, false, 1, 1));
		botonesAttrs.setLayout(new RowLayout(SWT.HORIZONTAL));

		Button btnNuevoAtributo = new Button(botonesAttrs, SWT.PUSH);
		btnNuevoAtributo.setText("Nuevo");
		
		Button btnEliminarAtributo = new Button(botonesAttrs, SWT.PUSH);
		btnEliminarAtributo.setText(Editor.ELIMINAR);

		// TableViewer
		this.tblAtributos = new AtributosTabla(grupoAtributos);

		// Agregar un nuevo atributo cuando se hace click sobre el boton
		btnNuevoAtributo.addSelectionListener(this.tblAtributos.nuevo);
		// Eliminar atributo
		btnEliminarAtributo.addSelectionListener(this.tblAtributos.eliminar);
*/
		/**
		 * Entidades.
		 */
		Group grupoEntidades = new Group(dialogArea, SWT.NONE);
		grupoEntidades.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grupoEntidades.setText("Estados");
		grupoEntidades.setLayout(new GridLayout(1, true));
		
		Composite botonesEntidades = new Composite(grupoEntidades, SWT.NONE);
		botonesEntidades.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, false, false, 1, 1));
		botonesEntidades.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Button btnNuevaEntidadRelacion = new Button(botonesEntidades, SWT.PUSH);
		btnNuevaEntidadRelacion.setText(Editor.NUEVO);
		
		Button btnEliminarEntidadRelacion = new Button(botonesEntidades, SWT.PUSH);
		btnEliminarEntidadRelacion.setText(Editor.ELIMINAR);

		this.tblEstados = new EstadoTransicionTabla(grupoEntidades, this.componente);
		
		// Agregar un nuevo atributo cuando se hace click sobre el boton
		btnNuevaEntidadRelacion.addSelectionListener(this.tblEstados.nuevo);
		// Eliminar atributo
		btnEliminarEntidadRelacion.addSelectionListener(this.tblEstados.eliminar);

		return dialogArea;
	}

	@Override
	protected void cargarDatos() {
		this.txtNombre.setText(this.componente.getNombre());
		this.cboTipo.setText(this.componente.getTipo().name());
		this.txtDescripcion.setText(this.componente.getDescripcion());
		//tblAtributos.setElementos(this.componente.getAtributos());
		tblEstados.setElementos(this.componente.getParticipantes());
	}

	@Override
	protected void aplicarCambios() {
		componente.setNombre(txtNombre.getText());
		componente.setTipo(TipoTransicion.valueOf(this.cboTipo.getText()));
		componente.setDescripcion(txtDescripcion.getText());
		//for (Atributo atributo : this.tblAtributos.getElementos())
		//	componente.addAtributo(atributo);
		
	//	for (Atributo atributo : this.tblAtributos.getElementosEliminados())
	//		componente.removeAtributo(atributo);
		
		for (EstadoTransicion entidadRelacion : this.tblEstados.getElementos())
			componente.addParticipante(entidadRelacion);
		
		for (EstadoTransicion entidadRelacion : this.tblEstados.getElementosEliminados())
			componente.removeParticipante(entidadRelacion);
	}

	@Override
	protected boolean validar(List<String> errors) {
		if (txtNombre.getText().trim().length() == 0)
			errors.add("Debe completar el nombre.");

		return errors.isEmpty();
	}
}
