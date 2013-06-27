package mereditor.interfaz.swt.editores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import mereditor.control.TransicionControl;
import mereditor.interfaz.swt.Principal;
import esteditor.modelo.Estado;
import esteditor.modelo.Transicion;
import esteditor.modelo.Transicion.EstadoTransicion;
import esteditor.modelo.Transicion.TipoEstadoTransicion;
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
	protected Combo estadoOrigen;
	protected Combo estadoDestino;
	protected EstadoTransicion estadoTransicionOrigen;
	protected EstadoTransicion estadoTransicionDestino;
	protected AtributosTabla tblAtributos;
	
	protected EstadoTransicionTabla tblEstados;
	
	protected ArrayList<String> options;
	protected ArrayList<Estado> optionsEstado;

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
		

		this.cargarEstados();
		
		this.estadoOrigen =createLabelCombo(header,"Origen");
		this.estadoOrigen.setItems(options
				.toArray(new String[options.size()]));
		
		this.estadoDestino =createLabelCombo(header,"Destino");
		this.estadoDestino.setItems(options
				.toArray(new String[options.size()]));
		this.estadoTransicionDestino= this.componente.new EstadoTransicion(this.componente);
		this.estadoTransicionOrigen= this.componente.new EstadoTransicion(this.componente);

		return dialogArea;
	}

	@Override
	protected void cargarDatos() {
		this.txtNombre.setText(this.componente.getNombre());
		this.cboTipo.setText(this.componente.getTipo().name());
		this.txtDescripcion.setText(this.componente.getDescripcion());
		
		Set<EstadoTransicion> participantes;
		participantes=this.componente.getParticipantes();
		
		for(EstadoTransicion estado : participantes) 
	    {
			if (estado.getEstadoTipo()==TipoEstadoTransicion.DESTINO)
				this.estadoDestino.setText(estado.getEstado().getNombre());
			if (estado.getEstadoTipo()==TipoEstadoTransicion.ORIGEN)
				this.estadoOrigen.setText(estado.getEstado().getNombre());
				
	    }

	}
	
	public void cargarEstados()
	{
		Set<Estado> estados = Principal.getInstance().getProyecto()
				.getEstadosDiagrama();
		
		this.optionsEstado = new ArrayList<>(estados);
		Collections.sort(this.optionsEstado);		
		this.options = new ArrayList<String>();		

		for (Estado estado : this.optionsEstado) {
			this.options.add(estado.getNombre());
		}	
	
	}

	@Override
	protected void aplicarCambios() {
		componente.setNombre(txtNombre.getText());
		componente.setTipo(TipoTransicion.valueOf(this.cboTipo.getText()));
		componente.setDescripcion(txtDescripcion.getText());
	
		int value=	this.options.indexOf(estadoOrigen.getText());
		
		this.estadoTransicionOrigen.setEstado(this.optionsEstado.get((int)value));
		this.estadoTransicionOrigen.setEstadoTipo(TipoEstadoTransicion.ORIGEN);
		componente.addParticipante(this.estadoTransicionOrigen);
		
		value=this.options.indexOf(estadoDestino.getText());
		this.estadoTransicionDestino.setEstado(this.optionsEstado.get((int)value));
		this.estadoTransicionDestino.setEstadoTipo(TipoEstadoTransicion.DESTINO);
		componente.addParticipante(this.estadoTransicionDestino);
		
	}

	@Override
	protected boolean validar(List<String> errors) {
		if (txtNombre.getText().trim().length() == 0)
			errors.add("Debe completar el nombre.");
		
		if (estadoOrigen.getText().trim().length() == 0)
			errors.add("Debe seleccionar un estado origen.");
		
		if (estadoDestino.getText().trim().length() == 0)
			errors.add("Debe seleccionar un estado destino.");
		
		if (estadoOrigen.getText() == estadoDestino.getText())
			errors.add("El estado origen y destino no pueden ser iguales.");
		
		
		return errors.isEmpty();
	}
}
