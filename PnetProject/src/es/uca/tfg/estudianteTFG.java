package es.uca.tfg;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


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
	
	public estudianteTFG (String sDNI, String sNombre, String sApellidos, String sTitulo, String sTutor1, boolean bEstado)
	{
		_sDNI = sDNI;
		_sNombre = sNombre;
		_sApellidos = sApellidos;
		_sTitulo = sTitulo;
		_bEstado = bEstado;
		_sTutor1 = sTutor1;
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

	public boolean is_bEstado() {
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
	
	public static void setTutor2(String sDNI, String sTutor2) throws Exception {
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
	
	public static void setFecha(String sDNI, LocalDateTime ldtFecha) throws Exception {
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
		
	public static void main(String[] args) throws Exception {
		//estudianteTFG est = New("45985221E", "prueba", "PruebaPrueba", "TFG de prueba", "Kevin", false);
		setTutor2("45985221E", "Pruebas");
		LocalDateTime ldt = LocalDateTime.now().plusDays(2l);
		setFecha("45985221E", ldt);
		System.out.println("OK");
	}
}
