package es.uca.tfg;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;

public class AlumDatabase {
	
	private static final String _CsPropertiesUrl = "./db.properties";
	
	public static Properties Properties(String sFile)
		throws IOException {
		
			InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(sFile);
				Properties result = new Properties();
				result.load(inputStream);
				return result;
			}
			finally { if (inputStream != null) inputStream.close(); }
	}
	
	public static Connection Connection() throws Exception
	{
		try
		{
			Properties properties = Properties(_CsPropertiesUrl);
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
				Class.forName(Properties(_CsPropertiesUrl
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
	
	public static String DateTime2Sql(LocalDateTime dt)
	{
		String sFechaSql = String.format("'%s'", dt.toString().substring(0, 10));
		
		return sFechaSql;
	}
		
	
}