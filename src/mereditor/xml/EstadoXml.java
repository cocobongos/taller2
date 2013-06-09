package mereditor.xml;

import java.util.HashSet;

import mereditor.control.EstadoControl;
import mereditor.modelo.Atributo;
import esteditor.modelo.Estado;

import org.w3c.dom.Element;

public class EstadoXml extends EstadoControl implements Xmlizable {

	public EstadoXml() {}

	public EstadoXml(EstadoControl componente) {
		this.id = componente.getId();
		this.nombre = componente.getNombre();
		this.tipo = componente.getTipo();

		this.atributos = new HashSet<Atributo>(componente.getAtributos());
		this.identificadores = componente.getIdentificadores();
		
		this.figures = componente.getFiguras();
	}

	@Override
	public Element toXml(ParserXML parser_) throws Exception {
		ModeloBaseParserXml parser=(ModeloBaseParserXml) parser_;
		
		Element elemento = parser.crearElemento(Constants.ENTIDAD_TAG);
		parser.agregarId(elemento, this.id.toString());
		parser.agregarTipo(elemento, this.tipo.toString());
		parser.agregarNombre(elemento, nombre);

		if (this.atributos.size() > 0) {
			Element atributosElement = parser.agregarElementoAtributos(elemento);
			for (Atributo atributo : this.atributos)
				atributosElement.appendChild(parser.convertirXmlizable(atributo).toXml(parser));
		}

		if (this.identificadores.size() > 0) {
			Element identificadoresXml = parser.agregarIdentificadores(elemento);

			for (Identificador identificador : this.identificadores) {
				Element identificadorXml = parser.agregarIdentificador(identificadoresXml);
				
				for(Atributo attr : identificador.getAtributos())
					parser.agregarReferenciaAtributo(identificadorXml, attr.getId());
				
				
				//for(Estado entidad : identificador.getEstadoes())
					//parser.agregarReferenciaEstado(identificadorXml, entidad.getId());
			}
		}

		return elemento;
	}

	@Override
	//FIXME falta el resto de los atributos de un estado su propiedad, etc
	public void fromXml(Element elemento, ParserXML parser_) throws Exception {
		ModeloBaseParserXml parser=(ModeloBaseParserXml) parser_;
		
		this.id = parser.obtenerId(elemento);
		this.nombre = parser.obtenerNombre(elemento);
		//this.tipo = PropieadEstado.valueOf(parser.obtenerPropiedadEstado(elemento));

		parser.registrar(this);

		for (Atributo atributo : parser.obtenerAtributos(elemento)) {
			atributo.setPadre(this);
			this.atributos.add(atributo);
		}

		// Obtener identificadores externos
		this.identificadores.addAll(parser.obtenerIdentificadores(elemento, this));
	}
}
