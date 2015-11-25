package es.uca.tfg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.DateTimeAtCompleted;


public class estudianteTFG {
	private String _sDNI;
	private String _sNombre;
	private String _sApellidos;
	private String _sTitulo;
	private String _sTutor1;
	private String _sTutor2;
	private boolean _bEstado;
	private LocalDate _ldtFecha;
	private double _dCalificacion;
	
	public estudianteTFG() {}
	
	public estudianteTFG (String sDNI, String sNombre, String sApellidos, String sTitulo, String sTutor1, boolean bEstado)
	{
		_sDNI = sDNI;
		_sNombre = sNombre;
		_sApellidos = sApellidos;
		_sTitulo = sTitulo;
		_bEstado = bEstado;
		_sTutor1 = sTutor1;
	}
	
	public String get_sDNI() {
		return _sDNI;
	}
	
	public String get_sNombre() {
		return _sNombre;
	}
	
	public String get_sApellidos() {
		return _sApellidos;
	}
	
	public String get_sTitulo() {
		return _sTitulo;
	}

	public String get_sTutor1() {
		return _sTutor1;
	}
	
	public String get_sTutor2() {
		return _sTutor2;
	}

	public boolean get_bEstado() {
		return _bEstado;
	}

	public LocalDate get_ldtFecha() {
		return _ldtFecha;
	}

	public double get_dCalificacion() {
		return _dCalificacion;
	}
	
	
	public static estudianteTFG New (String sDNI, String sNombre, String sApellidos, String sTitulo, String sTutor1, boolean bEstado) 
			throws Exception
	{
		Connection conexion = null;
		
		String sConsulta = String.format("INSERT INTO Alumno(DNI, Nombre, Apellidos, Titulo, Tutor1, Estado)" + 
				"VALUES('%s', '%s', '%s', '%s', '%s', %d)", sDNI, sNombre, sApellidos, sTitulo, sTutor1, (bEstado) ? 1 : 0);
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/pnetproject", "pnet", "pnet");
			conexion.createStatement().executeUpdate(sConsulta);
			return (new estudianteTFG(sDNI, sNombre, sApellidos, sTitulo, sTutor1, bEstado));
		}
		catch(SQLException e) { throw e; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
	
	public void setTutor2(String sDNI, String sTutor2) throws Exception {
		Connection conexion = null;
		
		String sConsulta = String.format("UPDATE Alumno SET tutor2='%s' WHERE DNI='%s'", sTutor2, sDNI);
		System.out.println(sConsulta);
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/pnetproject", "pnet", "pnet");
			conexion.createStatement().executeUpdate(sConsulta);
			_sTutor2 = sTutor2;
		}
		catch(SQLException e) { throw e; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
	
	public void setFecha(String sDNI, LocalDate ldtFecha) throws Exception {
		Connection conexion = null;
		
		String sConsulta = String.format("UPDATE Alumno SET Fecha=%s WHERE DNI='%s'", AlumDatabase.DateTime2Sql(ldtFecha), sDNI);
		System.out.println(sConsulta);
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/pnetproject", "pnet", "pnet");
			conexion.createStatement().executeUpdate(sConsulta);
			_ldtFecha = ldtFecha;
		}
		catch(SQLException e) { throw e; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
	
	public void setCalificacion(String sDNI, double dCalificacion) throws Exception {
		Connection conexion = null;
				
		String sConsulta = String.format("UPDATE Alumno SET calificacion=%f WHERE DNI='%s'", dCalificacion, sDNI);
		sConsulta = sConsulta.replace(',', '.');
		System.out.println(sConsulta);
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/pnetproject", "pnet", "pnet");
			conexion.createStatement().executeUpdate(sConsulta);
			_dCalificacion = dCalificacion;
		}
		catch(SQLException e) { throw e; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
	
	public static List<estudianteTFG> getAllEstudiantes() throws Exception {
		Connection conexion = null;
		ResultSet rsResultado = null;
		List<estudianteTFG> estudiantes = new ArrayList<estudianteTFG>();
		
		String sConsulta = "SELECT * FROM Alumno";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/pnetproject", "pnet", "pnet");
			rsResultado = conexion.createStatement().executeQuery(sConsulta);
			
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
					alumno.setFecha(rsResultado.getString("DNI"), rsResultado.getDate("fecha").toLocalDate());
				}
				
				if(rsResultado.getDouble("calificacion") != 0) {
					alumno.setCalificacion(rsResultado.getString("DNI"), rsResultado.getDouble("calificacion"));
				}
				
				estudiantes.add(alumno);
			}
			
			return estudiantes;
		}
		catch(SQLException e) { throw e; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
	
	public static String getEstudiante(String sApellidos) throws Exception {
		Connection conexion = null;
		ResultSet rsResultado = null;
		String resultado = "";
		
		String sConsulta = String.format("SELECT * FROM Alumno WHERE Apellidos='%s'", sApellidos);
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/pnetproject", "pnet", "pnet");
			rsResultado = conexion.createStatement().executeQuery(sConsulta);
			
			while(rsResultado.next()) {
				resultado += String.format("DNI: %s \nNombre: %s \n"
										+ "Apellidos: %s\nTitulo de TFG: %s \n"
										+ "Tutor 1: %s\nTutor 2: %s \n"
										+ "Presentado: %s\nFecha: %s \n"
										+ "Calificacion: %s \n\n" , rsResultado.getString("DNI"), rsResultado.getString("nombre"), 
										rsResultado.getString("apellidos"), rsResultado.getString("titulo"),
										rsResultado.getString("tutor1"), rsResultado.getString("tutor2"),
										rsResultado.getBoolean("estado"), rsResultado.getString("fecha"),
										rsResultado.getString("calificacion"));
			}
			
			resultado = resultado.replace("null", "No tiene");
			resultado = resultado.replace("true", "Sí");
			resultado = resultado.replace("false", "No");
					
			
			return resultado;
		}
		catch(SQLException e) { throw e; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
	
	public static boolean updateEstudiante(String sApellidos, estudianteTFG eEstudiante) throws Exception{
		Connection conexion = null;
		
		String sCal = String.format("calificacion=%f", eEstudiante.get_dCalificacion());
		sCal = sCal.replace(',', '.');
		
		String sConsulta = String.format("UPDATE Alumno SET estado=%d, fecha=%s, %s,"
				+ " titulo='%s' WHERE apellidos='%s'", eEstudiante.get_bEstado() ? 1 : 0, AlumDatabase.DateTime2Sql(eEstudiante.get_ldtFecha()),
				sCal, eEstudiante.get_sTitulo(), sApellidos);
		System.out.println(sConsulta);
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/pnetproject", "pnet", "pnet");
			if(conexion.createStatement().executeUpdate(sConsulta) != 0) {
				return true;
			}else return false;

		}
		catch(SQLException e) { throw e; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
	
	public static boolean deleteEstudiante(String sApellidos) throws Exception{
		Connection conexion = null;
		
		String sConsulta = String.format("DELETE FROM Alumno WHERE apellidos='%s'", sApellidos);
		System.out.println(sConsulta);
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/pnetproject", "pnet", "pnet");
			if(conexion.createStatement().executeUpdate(sConsulta) != 0) {
				return true;
			}else return false;

		}
		catch(SQLException e) { throw e; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
	
	public boolean addEstudiante(estudianteTFG eEstudiante) throws Exception {
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
	
}
