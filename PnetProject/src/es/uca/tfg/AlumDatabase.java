package es.uca.tfg;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlumDatabase {
	
	/**
	 * Función que introduce un estudiante nuevo en la Base de Datos dados unos parámetros
	 * @param sDNI
	 * @param sNombre
	 * @param sApellidos
	 * @param sTitulo
	 * @param sTutor1
	 * @param bEstado
	 * @return
	 * @throws Exception
	 */
	public static estudianteTFG New (String sDNI, String sNombre, String sApellidos, String sTitulo, String sTutor1, boolean bEstado) 
			throws Exception
	{
		Connection conexion = null;
		
		//Instrucción SQL
		String sConsulta = String.format("INSERT INTO Alumno(DNI, Nombre, Apellidos, Titulo, Tutor1, Estado)" + 
				"VALUES('%s', '%s', '%s', '%s', '%s', %d)", sDNI, sNombre, sApellidos, sTitulo, sTutor1, (bEstado) ? 1 : 0);
		System.out.println(sConsulta);
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/pnetproject", "pnet", "pnet");
			conexion.createStatement().executeUpdate(sConsulta);
			//Se devuelve el estudiante creado
			return (new estudianteTFG(sDNI, sNombre, sApellidos, sTitulo, sTutor1, bEstado));
		}
		catch(SQLException e) { throw e; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
	
	/**
	 * Devuelve una lista con todos los estudiantes de la Base de Datos
	 * @return
	 * @throws Exception
	 */
	public static List<estudianteTFG> getAllEstudiantes() throws Exception {
		Connection conexion = null;
		ResultSet rsResultado = null;
		//Lista donde se almacenan los estudiantes recibidos
		List<estudianteTFG> estudiantes = new ArrayList<estudianteTFG>();
		
		//Instrucción SQL
		String sConsulta = "SELECT * FROM Alumno";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/pnetproject", "pnet", "pnet");
			rsResultado = conexion.createStatement().executeQuery(sConsulta);
			
			//Se introducen uno a uno los estudiantes obtenidos.
			while(rsResultado.next()) {
				estudianteTFG alumno = new estudianteTFG(rsResultado.getString("DNI"), 
						rsResultado.getString("nombre"),
						rsResultado.getString("apellidos"), 
						rsResultado.getString("titulo"), 
						rsResultado.getString("tutor1"),
						rsResultado.getBoolean("estado"));
				
				if(rsResultado.getObject("tutor2") != null) {
					alumno.setTutor2(rsResultado.getString("DNI"), rsResultado.getString("tutor2"));
					
				}
				
				if(rsResultado.getDate("fecha") != null) {
					alumno.setFecha(rsResultado.getString("DNI"), rsResultado.getDate("fecha"));
				}
				
				if(rsResultado.getDouble("calificacion") != 0) {
					alumno.setCalificacion(rsResultado.getString("DNI"), rsResultado.getDouble("calificacion"));
				}
				
				estudiantes.add(alumno);
			}
			
			//Se devuelve la lista formada, si no hay estudiantes estará vacía
			return estudiantes;
		}
		catch(SQLException e) { throw e; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
	
	/**
	 * Función que obtiene un estudiante concreto según sus apellidos
	 * @param sApellidos
	 * @return
	 * @throws Exception
	 */
	public static String getEstudiante(String sApellidos) throws Exception {
		Connection conexion = null;
		ResultSet rsResultado = null;
		String resultado = "";
		
		//Instrucción SQL
		String sConsulta = String.format("SELECT * FROM Alumno WHERE Apellidos='%s'", sApellidos);
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/pnetproject", "pnet", "pnet");
			rsResultado = conexion.createStatement().executeQuery(sConsulta);
			
			//Se formatea la salida a texto plano
			while(rsResultado.next()) {
				resultado += String.format("DNI: %s <br /> \nNombre: %s \n<br />"
										+ "Apellidos: %s\n<br />Titulo de TFG: %s \n<br />"
										+ "Tutor 1: %s\n<br />Tutor 2: %s \n<br />"
										+ "Presentado: %s\n<br />Fecha: %s \n<br />"
										+ "Calificacion: %s \n\n <br />" , rsResultado.getString("DNI"), rsResultado.getString("nombre"), 
										rsResultado.getString("apellidos"), rsResultado.getString("titulo"),
										rsResultado.getString("tutor1"), rsResultado.getString("tutor2"),
										rsResultado.getBoolean("estado"), rsResultado.getString("fecha"),
										rsResultado.getString("calificacion"));
			}
			
			resultado = resultado.replace("null", "No tiene");
			resultado = resultado.replace("true", "Sí");
			resultado = resultado.replace("false", "No");
					
			//Se devuelve el texto plano formateado
			return resultado;
		}
		catch(SQLException e) { throw e; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
	
	/**
	 * Función que actualiza un estudiante dado unos apellidos y un objeto estudiante
	 * @param sApellidos
	 * @param eEstudiante
	 * @return
	 * @throws Exception
	 */
	public static boolean updateEstudiante(String sApellidos, estudianteTFG eEstudiante) throws Exception{
		Connection conexion = null;
		
		//Instrucción SQL, se sustituyen las , de double para introducir bien los valores
		String sCal = String.format("calificacion=%f", eEstudiante.get_dCalificacion());
		sCal = sCal.replace(',', '.');
		
		String sConsulta = String.format("UPDATE Alumno SET estado=%d, fecha='%s', %s,"
				+ " titulo='%s' WHERE apellidos='%s'", eEstudiante.get_bEstado() ? 1 : 0, new java.sql.Date(eEstudiante.get_ldtFecha().getTime()),
				sCal, eEstudiante.get_sTitulo(), sApellidos);
		System.out.println(sConsulta);
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/pnetproject", "pnet", "pnet");
			if(conexion.createStatement().executeUpdate(sConsulta) != 0) {
				//Si se ha actualizado se devuelve TRUE, si no FALSE
				return true;
			}else return false;

		}
		catch(SQLException e) { throw e; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
	
	/**
	 * Función que elimina un estudiante dados sus apellidos
	 * @param sApellidos
	 * @return
	 * @throws Exception
	 */
	public static boolean deleteEstudiante(String sApellidos) throws Exception{
		Connection conexion = null;
		
		
		//Instrucción SQL
		String sConsulta = String.format("DELETE FROM Alumno WHERE apellidos='%s'", sApellidos);
		System.out.println(sConsulta);
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/pnetproject", "pnet", "pnet");
			if(conexion.createStatement().executeUpdate(sConsulta) != 0) {
				//Si se ha eliminado se devuelve TRUE, si no se devuelve FALSE
				return true;
			}else return false;

		}
		catch(SQLException e) { throw e; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
	
	/**
	 * Función que añade un estudiante a la Base de Datos
	 * @param eEstudiante
	 * @return
	 * @throws Exception
	 */
	public static boolean addEstudiante(estudianteTFG eEstudiante) throws Exception {
		String sDNI = eEstudiante.get_sDNI();
		String sNombre = eEstudiante.get_sNombre();
		String sApellidos = eEstudiante.get_sApellidos();
		String sTitulo = eEstudiante.get_sTitulo();
		String sTutor1 = eEstudiante.get_sTutor1();
		boolean bEstado = eEstudiante.get_bEstado();
		
		estudianteTFG aux = New(sDNI, sNombre, sApellidos, sTitulo, sTutor1, bEstado);
		
		if(eEstudiante.get_sTutor2() != "" && eEstudiante.get_sTutor2() != null) {
			aux.setTutor2(eEstudiante.get_sDNI(), eEstudiante.get_sTutor2());
		}
		
		if(eEstudiante.get_ldtFecha() != null) {
			aux.setFecha(eEstudiante.get_sDNI(), eEstudiante.get_ldtFecha());
		}		
		if(eEstudiante.get_dCalificacion() != 0) {
			aux.setCalificacion(eEstudiante.get_sDNI(), eEstudiante.get_dCalificacion());
		}
		
		return true;
		
	}	
	
	/**
	 * Función que adapta una fecha de Java al estilo de SQL
	 * @param dt
	 * @return
	 */
	public static String DateTime2Sql(Date dt)
	{
		//Si se recibe una fecha válida se devuelve convertida, si no, se devuelve nulo
		if(dt != null) {
			String sFechaSql = String.format("'%s'", dt.toString().substring(0, 10));
		
			return sFechaSql;
		} else {
			return null;
		}
	}

		
	
}