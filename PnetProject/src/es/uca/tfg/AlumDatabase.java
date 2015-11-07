package es.uca.tfg;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import es.uca.tfg.Config;

public class AlumDatabase {
	
	private static final String _CsPropertiesUrl = "./db.properties";
	
	public static Connection Connection() throws Exception
	{
		try
		{
			Properties properties = Config.Properties(_CsPropertiesUrl);
			return DriverManager.getConnection(
					properties.getProperty("jdbc.url"),
					properties.getProperty("jdbc.username"),
					properties.getProperty("jdbc.password")
					);
			
		}
		catch(Exception e) {throw e;}
	}
	
	public static void LoadDriver() 
 throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, IOException {
				Class.forName(Config.Properties(_CsPropertiesUrl
		            ).getProperty("jdbc.driverClassName")).newInstance();
		}
	
	public static void main(String[] args) throws Exception
	{
		Connection conexion = null;

		try{
			conexion = Connection();
		}
		catch(SQLException ee) { throw ee; }
		finally {
			if(conexion != null) conexion.close();
		}
	}
		
	
}