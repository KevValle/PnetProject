package es.uca.tfg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public class estudianteTFG {
	//Parámetros que conforman un estudiante
	private String _sDNI;
	private String _sNombre;
	private String _sApellidos;
	private String _sTitulo;
	private String _sTutor1;
	private String _sTutor2;
	private boolean _bEstado;
	private Date _ldtFecha;
	private double _dCalificacion;
	
	/**
	 * Constructor nulo necesario para cierta función de la Base de Datos
	 */
	public estudianteTFG() {}
	
	/**
	 * Constructor con los parámetros mínimos para crear un estudiante
	 * @param sDNI
	 * @param sNombre
	 * @param sApellidos
	 * @param sTitulo
	 * @param sTutor1
	 * @param bEstado
	 */
	public estudianteTFG (String sDNI, String sNombre, String sApellidos, String sTitulo, String sTutor1, boolean bEstado)
	{
		_sDNI = sDNI;
		_sNombre = sNombre;
		_sApellidos = sApellidos;
		_sTitulo = sTitulo;
		_bEstado = bEstado;
		_sTutor1 = sTutor1;
	}
	
	
	//Conjunto de funciones GET y SET necesarias
	
	
	/**
	 * @return
	 */
	public String get_sDNI() {
		return _sDNI;
	}
	
	/**
	 * @return
	 */
	public String get_sNombre() {
		return _sNombre;
	}
	
	/**
	 * @return
	 */
	public String get_sApellidos() {
		return _sApellidos;
	}
	
	/**
	 * @return
	 */
	public String get_sTitulo() {
		return _sTitulo;
	}

	/**
	 * @return
	 */
	public String get_sTutor1() {
		return _sTutor1;
	}
	
	/**
	 * @return
	 */
	public String get_sTutor2() {
		return _sTutor2;
	}

	/**
	 * @return
	 */
	public boolean get_bEstado() {
		return _bEstado;
	}

	/**
	 * @return
	 */
	public Date get_ldtFecha() {
		return _ldtFecha;
	}

	/**
	 * @return
	 */
	public double get_dCalificacion() {
		return _dCalificacion;
	}
	
	/**
	 * Función que añade un segundo tutor al estudiante y lo actualiza en la Base de Datos
	 * @param sDNI
	 * @param sTutor2
	 * @throws Exception
	 */
	public void setTutor2(String sDNI, String sTutor2) throws Exception {
		Connection conexion = null;
		
		//Instrucción SQL
		String sConsulta = String.format("UPDATE Alumno SET tutor2='%s' WHERE DNI='%s'", sTutor2, sDNI);
		System.out.println(sConsulta);
		
		try{
			//Conexión con la Base de Datos
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/pnetproject", "pnet", "pnet");
			conexion.createStatement().executeUpdate(sConsulta);
			//Asignación de tutor al objeto
			_sTutor2 = sTutor2;
		}
		catch(SQLException e) { throw e; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
	
	/**
	 * Función que añade una fecha de presentación a un estudiante y lo actualiza en la Base de Datos
	 * @param sDNI
	 * @param ldtFecha
	 * @throws Exception
	 */
	public void setFecha(String sDNI, Date ldtFecha) throws Exception {
		Connection conexion = null;
		
		//Instrucción SQL
		String sConsulta = String.format("UPDATE Alumno SET Fecha='%s' WHERE DNI='%s'", new java.sql.Date(ldtFecha.getTime()), sDNI);
		System.out.println(sConsulta);
		try{
			//Conexión con la Base de Datos
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/pnetproject", "pnet", "pnet");
			conexion.createStatement().executeUpdate(sConsulta);
			//Asignación de fecha al objeto
			_ldtFecha = ldtFecha;
		}
		catch(SQLException e) { throw e; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
	
	/**
	 * Función que añade una fecha de presentación a un estudiante y lo actualiza en la Base de Datos
	 * @param sDNI
	 * @param dCalificacion
	 * @throws Exception
	 */
	public void setCalificacion(String sDNI, double dCalificacion) throws Exception {
		Connection conexion = null;
				
		//Instrucción SQL
		String sConsulta = String.format("UPDATE Alumno SET calificacion=%f WHERE DNI='%s'", dCalificacion, sDNI);
		sConsulta = sConsulta.replace(',', '.');
		System.out.println(sConsulta);
		try{
			//Coneción a la Base de Datos
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/pnetproject", "pnet", "pnet");
			conexion.createStatement().executeUpdate(sConsulta);
			//Asignación de calificación al objeto
			_dCalificacion = dCalificacion;
		}
		catch(SQLException e) { throw e; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
	
}
