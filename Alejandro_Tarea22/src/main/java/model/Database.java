package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
	/*
	 * Método encargado de generar la conexión hacia la base de datos
	 * Es necesario pasarle el nombre usuario(root), password y el 
	 * nombre de la base de datos
	 */
	
	public Connection crearConexion(String login,String password,String bd) {
		Connection conn = null;
		String url="jdbc:mysql://localhost:3306/" + bd;
		try {
			String sURL = url;
			conn = DriverManager.getConnection(sURL,login,password);
			if (conn != null) {
				return(conn);
			}
			else {
				return(conn);
			}
		}
		catch (SQLException ex) {
			return(conn);
		}
	}
}
