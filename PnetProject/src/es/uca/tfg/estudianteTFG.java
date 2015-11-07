package es.uca.tfg;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


public class estudianteTFG {
	private String _sNombre;
	private String _sApellidos;
	private String _sTitulo;
	private String _sTutor1;
	private String _sTutor2;
	private boolean _bEstado;
	private LocalDateTime _ldtFecha;
	private double _dCalificacion;
	
	public estudianteTFG (String sNombre, String sApellidos, String sTitulo, boolean bEstado)
	{
		_sNombre = sNombre;
		_sApellidos = sApellidos;
		_sTitulo = sTitulo;
		_bEstado = bEstado;
	}
	
	public String get_sNombre() {
		return _sNombre;
	}
	public void set_sNombre(String _sNombre) {
		this._sNombre = _sNombre;
	}
	public String get_sApellidos() {
		return _sApellidos;
	}
	public void set_sApellidos(String _sApellidos) {
		this._sApellidos = _sApellidos;
	}
	public String get_sTitulo() {
		return _sTitulo;
	}
	public void set_sTitulo(String _sTitulo) {
		this._sTitulo = _sTitulo;
	}
	public String get_sTutor1() {
		return _sTutor1;
	}
	public void set_sTutor1(String _sTutor1) {
		this._sTutor1 = _sTutor1;
	}
	public String get_sTutor2() {
		return _sTutor2;
	}
	public void set_sTutor2(String _sTutor2) {
		this._sTutor2 = _sTutor2;
	}
	public boolean is_bEstado() {
		return _bEstado;
	}
	public void set_bEstado(boolean _bEstado) {
		this._bEstado = _bEstado;
	}
	public LocalDateTime get_ldtFecha() {
		return _ldtFecha;
	}
	public void set_ldtFecha(LocalDateTime _ldtFecha) {
		this._ldtFecha = _ldtFecha;
	}
	public double get_dCalificacion() {
		return _dCalificacion;
	}
	public void set_dCalificacion(double _dCalificacion) {
		this._dCalificacion = _dCalificacion;
	}
	
	public static estudianteTFG New (String sNombre, String sApellidos, String sTitulo, boolean bEstado) 
			throws Exception
	{
		Connection conexion = null;
		
		String sConsulta = String.format("INSERT INTO Alumno(Nombre, Apellidos, Titulo, Tutor1, Estado)" + 
				"VALUES('%s', '%s', '%s', '%s', %d)", sNombre, sApellidos, sTitulo, (bEstado) ? 1 : 0);
		
		try{
			conexion = AlumDatabase.Connection();
			conexion.createStatement().executeUpdate(sConsulta);
			return (new estudianteTFG(sNombre, sApellidos, sTitulo, bEstado));
		}
		catch(SQLException e) { throw e; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
}
