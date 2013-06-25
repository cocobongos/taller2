package mereditor.modelo.validacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import esteditor.modelo.Estado;
import esteditor.modelo.Estado.PropieadEstado;

import mereditor.modelo.Diagrama;
//import mereditor.modelo.Proyecto;

/*Cocobongos
 * Clase para la validacion de diagramas de estados
 * 
 * 
 */

public class ValidarDiagramaEstados implements Validacion{
	
	@Override
	public List<Observacion> validar(Validable componente) {
		List<Observacion> observaciones = new ArrayList<>();

	//	Proyecto proyecto = (Proyecto) componente;
		Diagrama diagrama = (Diagrama) componente;
		Set<Estado> estados = diagrama.getEstados(false);

		//TODO hacer una validacion correcta ,
		//validar que existan transiciones entre los estados
		//inicial y final
		
		boolean inicio=false;
		boolean fin=false;

		for (Estado estado : estados)
		{
			
			if(estado.getPropiedad().equals((PropieadEstado.INICIAL)))
			{
				inicio=true;
			}else if(estado.getPropiedad().equals((PropieadEstado.FINAL)))
			{
				fin=true;
			}
		}			

		if (inicio==false || fin==false) {
			String msj = "El diagrama no posee estado final o inicial.";
			msj = String.format(msj);
			observaciones.add(new Observacion(msj));
		}


		return observaciones;
	}


}
