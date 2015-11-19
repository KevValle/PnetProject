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
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;


@Path("/tfg")
public class serviciosRest {

	private estudianteTFG bd = new estudianteTFG();
	
	@GET
	@Path("/all")
	@Produces ({"application/json"})
	public List<estudianteTFG> getAllEstudiantes () throws Exception
	{
		return estudianteTFG.getAllEstudiantes();
	}
	
	@GET
	@Path("/estudiante/{apellidos}")
	@Consumes (MediaType.TEXT_PLAIN)
	@Produces (MediaType.TEXT_PLAIN)
	public String Estudiante (@PathParam("apellidos")String apellidos) throws Exception
	{
		return estudianteTFG.getEstudiante(apellidos);
	}
	
	@PUT
	@Path("/update/{apellidos}")
	@Consumes({"application/json"})
	@Produces (MediaType.TEXT_PLAIN)
	public String Update (@PathParam("apellidos") String apellidos, estudianteTFG estudiante) throws Exception
	{
		if(estudianteTFG.updateEstudiante(apellidos,estudiante))
			return "Actualizado con exito.";
		else
			return "No se pudo actualizar";
	}
	
	@DELETE
	@Path("/delete/{apellidos}")
	@Produces (MediaType.TEXT_PLAIN)
	public String Delete (@PathParam("apellidos")String apellidos) throws Exception
	{
		if(estudianteTFG.deleteEstudiante(apellidos))
			return "Eliminado con exito.";
		else
			return "No se pudo eliminar";
	}
	
	@POST
	@Path("/addEstudiante/")
	@Consumes({"application/json"})
	public String AddEstudiante(estudianteTFG estudiante) throws Exception
	{
		if(bd.addEstudiante(estudiante))
			return "Estudiante agregado.";
		else
			return "No se pudo agregar";
	}	
}
