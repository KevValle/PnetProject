package es.uca.tfg;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/tfg")
public class serviciosRest {
	
	/**
	 * Funci�n que devuelve mediante json todos los estudiantes del sistema
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/all")
	@Produces ({"application/json"})
	public List<estudianteTFG> getAllEstudiantes () throws Exception
	{
		//Devuelve una lista de estudianteTFG obtenidos de la base de datos
		return AlumDatabase.getAllEstudiantes();
	}
	
	/**
	 * Funci�n que devuelve un estudiante concreto
	 * @param apellidos
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/estudiante/{apellidos}")
	@Consumes (MediaType.TEXT_PLAIN)
	@Produces (MediaType.TEXT_PLAIN)
	public String Estudiante (@PathParam("apellidos")String apellidos) throws Exception
	{
		//Devuelve un estudiante de la Base de Datos con esos apellidos
		return AlumDatabase.getEstudiante(apellidos);
	}
	
	/**
	 * Funci�n que actualiza un estudiante en la Base de Datos y devuelve una cadena
	 * @param apellidos
	 * @param estudiante
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("/update/{apellidos}")
	@Consumes({"application/json"})
	@Produces (MediaType.TEXT_PLAIN)
	public String Update (@PathParam("apellidos") String apellidos, estudianteTFG estudiante) throws Exception
	{
		//Se actualiza el estudiante con esos apellidos seg�n los datos del nuevo recibido
		if(AlumDatabase.updateEstudiante(apellidos,estudiante))
			//El estudiante ha sido actualizado, la funci�n ha devuelto TRUE
			return "Actualizado con exito.";
		else
			//El estudiante no se ha podido actualizar, la funci�n ha devuelto FALSE
			return "No se pudo actualizar";
	}
	
	/**
	 * Funci�n que elimina un estudiante seg�n sus apellidos
	 * @param apellidos
	 * @return
	 * @throws Exception
	 */
	@DELETE
	@Path("/delete/{apellidos}")
	@Produces (MediaType.TEXT_PLAIN)
	public String Delete (@PathParam("apellidos")String apellidos) throws Exception
	{
		//Se busca el estudiante y si existe se elimina
		if(AlumDatabase.deleteEstudiante(apellidos))
			//La funci�n ha devuelto TRUE, por lo que el estudiante ha sido encontrado y eliminado
			return "Eliminado con exito.";
		else
			//La funci�n ha devuelto FALSE, por lo que ha habido alg�n error y no se ha eliminado
			return "No se pudo eliminar";
	}
	
	/**
	 * Funci�n que introduce un estudiante en el sistema
	 * @param estudiante
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/addEstudiante/")
	@Consumes({"application/json"})
	public String AddEstudiante(estudianteTFG estudiante) throws Exception
	{
		//Se a�ade a la Base de Datos el estudiante recibido
		if(AlumDatabase.addEstudiante(estudiante))
			//La funci�n ha devuelto TRUE, por lo que se ha a�adido bien el estudiante
			return "Estudiante agregado.";
		else
			//La funci�n ha devuelto FALSE, por lo que ha habido alg�n error y no se ha a�adido
			return "No se pudo agregar";
	}	
}
