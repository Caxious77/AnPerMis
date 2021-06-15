package basededatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import funcionalidad.Main;
public class Conexion{
	
	private static final String url = "jdbc:mysql://localhost:3306/tfg";
	private static final String user = "java";
	private static final String password = "VZBqxgdptOCKspOs";//
	
	public Connection conectar() {
		Connection conexion = null;
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception ex) {
		    System.out.println(Main.FechayHora()+"Error, no se ha podido cargar MySQL JDBC Driver");
		}

				try {
					conexion = DriverManager.getConnection(url, user, password);
					System.out.println(Main.FechayHora()+"conexion establecida con la base de datos");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	return conexion;	
	}
	
}


	
	
	

