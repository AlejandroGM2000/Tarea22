package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Suministro {
	
	/*
	 * Método encargado de generar una tabla. Recibe como parámetro la
	 * conexión, el nombre de la tabla a generar, la query que define la tabla y un
	 * objeto tipo stament.
	 */
	
	public boolean creaTabla(Connection conn,String Name_Table, String query,Statement st) {
		try {
			st.executeUpdate(
			"CREATE TABLE IF NOT EXISTS "+Name_Table+ "("+query+");");
			return(true);
		}
		catch (SQLException ex) {
			return(false);
		}
	}
	/*
	 * Método encargado de realizar la inserción de datos en la tabla.
	 * Recibe como parámetro el nombre de tabla sobre la cual realizar 
	 * la inserción, los campos de dicha tabla y sus correspondientes valores. Además, es necesario
	 * pasar como parámetro un objeto statement.
	 */
	
	public boolean insertarTabla(String name_tabla, String campos,String valores,Statement st) {
		try {
				String query="INSERT INTO "+name_tabla+"("+campos+")"+"Values"+valores+";";
				st.executeUpdate(query);
				return(true);
		}
		catch (SQLException ex) {
			return(false);
		}

	}
	
	/*
	 * Método encargado de realizar el borrado de las tablas
	 * Recibe como parámetro el nombre de la tabla a borrar.
	 */
	
	public boolean borrarTabla(String name_table,Statement st){
		try {
			st.executeUpdate("DROP TABLE IF EXISTS "+name_table);
			return(true);
		}
		catch (SQLException ex) {
			return(false);
		}
	}
	
	/*
	 * Método encargado de preparar la query para la inserción de los datos. (piezas)
	*/
	
	
	public String prepareStringPiezas(String name) {
		String campos="";
		campos=campos+"('"+name+"')";
		return(campos);
	}
	/*
	 * Método encargado de preparar la query para la inserción de los datos. (Proveedor)
	 */
	
	public String prepareStringProveedor(String id,String name) {
		String campos="";
		campos=campos+"('"+id+"'"+","+"'"+name+"')";
		return(campos);
	}
	
	/*
	 * Método encargado de preparar la query para la inserción de los datos. (Suministros)
	 */
	
	public String prepareStringSuministros(int cod_pieza,String id_pro,int precio) {
		String campos="";
		campos=campos+"('"+cod_pieza+"'"+","+"'"+id_pro+"'"+","+"'"+precio+"')";
		return(campos);
	}
	


	
}
