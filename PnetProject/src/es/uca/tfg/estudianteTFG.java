package es.uca.tfg;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class estudianteTFG {
	private String _sDNI;
	private String _sNombre;
	private String _sApellidos;
	private String _sTitulo;
	private String _sTutor1;
	private String _sTutor2;
	private boolean _bEstado;
	private LocalDateTime _ldtFecha;
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

	public LocalDateTime get_ldtFecha() {
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
			conexion = AlumDatabase.Connection();
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
		
		try{
			conexion = AlumDatabase.Connection();
			conexion.createStatement().executeUpdate(sConsulta);
		}
		catch(SQLException e) { throw e; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
	
	public void setFecha(String sDNI, LocalDateTime ldtFecha) throws Exception {
		Connection conexion = null;
		
		String sConsulta = String.format("UPDATE Alumno SET Fecha=%s WHERE DNI='%s'", AlumDatabase.DateTime2Sql(ldtFecha), sDNI);
		System.out.println(sConsulta);
		try{
			conexion = AlumDatabase.Connection();
			conexion.createStatement().executeUpdate(sConsulta);
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
			conexion = AlumDatabase.Connection();
			conexion.createStatement().executeUpdate(sConsulta);
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
			conexion = AlumDatabase.Connection();
			rsResultado = conexion.createStatement().executeQuery(sConsulta);
			
			while(rsResultado.next()) {
				estudiantes.add(new estudianteTFG(rsResultado.getString("DNI"), 
						rsResultado.getString("nombre"),
						rsResultado.getString("apellidos"), 
						rsResultado.getString("titulo"), 
						rsResultado.getString("tutor1"),
						rsResultado.getBoolean("estado")));
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
			conexion = AlumDatabase.Connection();
			rsResultado = conexion.createStatement().executeQuery(sConsulta);
			
			while(rsResultado.next()) {
				resultado += String.format("DNI: %s \nNombre: %s \n"
										+ "Apellidos: %s\n Titulo de TFG: %s \n"
										+ "Tutor 1: %s\n Tutor 2: %s \n"
										+ "Presentado: %s\n Fecha: %s \n"
										+ "Calificacion: %s \n\n" , rsResultado.getString("DNI"), rsResultado.getString("nombre"), 
										rsResultado.getString("apellidos"), rsResultado.getString("titulo"),
										rsResultado.getString("tutor1"), rsResultado.getString("tutor2"),
										rsResultado.getBoolean("estado"), rsResultado.getString("fecha"),
										rsResultado.getString("calificacion"));
			}
					
			
			return resultado;
		}
		catch(SQLException e) { throw e; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
	
	public static boolean updateEstudiante(String sApellidos, estudianteTFG eEstudiante) throws Exception{
		Connection conexion = null;
		
		String sConsulta = String.format("UPDATE Alumno SET apellidos='%s' WHERE DNI='%s'", sApellidos, eEstudiante.get_sDNI());
		
		try{
			conexion = AlumDatabase.Connection();
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
		
		try{
			conexion = AlumDatabase.Connection();
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
	
		
	/*public static void main(String[] args) throws Exception {
		//estudianteTFG est = New("45985421E", "prueba", "PruebaPrueba", "TFG de prueba", "Kevin", false);
		/*setTutor2("45985221E", "Pruebas");
		LocalDateTime ldt = LocalDateTime.now().plusDays(2l);
		setFecha("45985221E", ldt);*/
		/*setCalificacion("45985221E", 10d);
		setCalificacion("45985421E", 10d);*/
		
		
		
		/*List<estudianteTFG> estudiantes = getAllEstudiantes();
		
		System.out.println(estudiantes.get(0).get_sDNI());
		System.out.println(estudiantes.get(1).get_sDNI());
		System.out.println(getEstudiante("PruebaPrueba"));
		System.out.println("OK");
	}*/
}
