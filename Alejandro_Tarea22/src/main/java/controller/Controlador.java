package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.Database;
import model.Suministro;
import view.InterfazConsola;

public class Controlador {
	private Database db;
	private Suministro sum;
	private InterfazConsola ic;
	
	//Definicion nombre de las tablas
	
	public static final String NamePiezas = "Piezas";
	public static final String NameProv = "Proveedores";
	public static final String NameSum = "Suministros";

	//Definicion de los parametros de las tablas
	
	private static final String query_piezas = "Codigo INT not null AUTO_INCREMENT,Nombre NVARCHAR(100) not null,PRIMARY KEY(Codigo)";
	private static final String query_suministra = "CodigoPieza INT not null,IdProveedor char(4) not null,Precio  INT not null,PRIMARY KEY(CodigoPieza,IdProveedor),FOREIGN KEY (CodigoPieza) REFERENCES Piezas (Codigo) ON DELETE CASCADE ON UPDATE CASCADE,FOREIGN KEY (IDProveedor) REFERENCES Proveedores (ID) ON DELETE CASCADE ON UPDATE CASCADE";
	private static final String query_proveedores = "ID char(4) not null,Nombre NVARCHAR(100) not null,PRIMARY KEY(ID)";
	
	//Definicion de los campos de las tablas
	private static final String campo_piezas = "Nombre";
	private static final String campo_prov = "ID,Nombre";
	private static final String campo_sum = "CodigoPieza,IdProveedor,Precio";

	
	//Definicion de los datos a insertar
	private static final String piezas_defecto = "('Condensador'),('Inductor'),('Microcontrolador'),('Bateria'),('Pantalla'),('Sensor Temperatura'),('Sensor Posicion'),('Servomotor'),('Bobina'),('Resistor')";
	private static final String prov_defecto = "('1000','Microsoft'),('1001','Texas Instrument'),('1002','Microchip'),('1003','Tesla'),('1004','HP'), ('1005','DigiKey'),('1006','BOSCH'),('1007','Siemens'),('1008','GE'),('1009','Amazon')";
	private static final String sum_defecto = "(1,'1000',1),(2,'1002',2),(3,'1001',7),(7,'1009',20),(6,'1007',70),(5,'1006',14),(4,'1005',21),(9,'1004',17),(10,'1003',8),(8,'1008',1)";

	//Constructor del controlador
	
	public Controlador(Database db, Suministro sum, InterfazConsola ic) {
		this.db = db;
		this.sum = sum;
		this.ic = ic;
	}

	/*
	 * Método encargado de realizar la inserción de nuevos registros
	 * Recibe como parámetro el nombre de la tabla sobre el cual se realizará la inserción
	 * Se interacciona con el usuario para introducir los datos correspondientes.
	 */
	
	public void insert(String name_tab, Statement st) {
		String lectura_piezas = "";
		String lp_prepare = "";
		boolean piezas = false;

		String lecturaID_proveedores = "";
		String lecturaName_proveedores = "";
		String lpr_prepare = "";
		boolean proveedores = false;

		int lecturaPz_suministro = 0;
		String lecturaID_suministro = "";
		int lecturaPrecio_suministro = 0;
		String ls_prepare = "";
		boolean suministro = true;

		if (NamePiezas.equals(name_tab)) {
			lectura_piezas = ic.lecturaString();
			lp_prepare = sum.prepareStringPiezas(lectura_piezas);
			piezas = sum.insertarTabla(name_tab, campo_piezas, lp_prepare, st);
			messageinsert(piezas);
		}

		if (NameProv.equals(name_tab)) {
			lecturaID_proveedores = ic.lecturaString();
			lecturaName_proveedores = ic.lecturaString();

			lpr_prepare = sum.prepareStringProveedor(lecturaID_proveedores, lecturaName_proveedores);
			proveedores = sum.insertarTabla(name_tab, campo_prov, lpr_prepare, st);
			messageinsert(proveedores);
		}

		if (NameSum.equals(name_tab)) {
			lecturaPz_suministro = controllecturaInt();
			lecturaID_suministro = ic.lecturaString();
			lecturaPrecio_suministro = controllecturaInt();
			ls_prepare = sum.prepareStringSuministros(lecturaPz_suministro, lecturaID_suministro,
					lecturaPrecio_suministro);
			suministro = sum.insertarTabla(name_tab, campo_sum, ls_prepare, st);
			messageinsert(suministro);
		}

	}
/*
 * Método encargado de comprobar que una consulta a la base de datos no se halla vacio
 * Devuelver true si esta llena. 
 */
	public boolean compruebaResultadoConsulta(ResultSet rs) {
		try {
			if (rs.isBeforeFirst()) {
				return (true);
			} else {
				ic.consultaVacia();
				return (false);
			}
		} catch (SQLException e) {
			ic.error_sqlDefecto();
			return (false);
		}

	}

	/*
	 * Método encargado de controlar la interacción con el usuario
	 * Si introduce un valor diferente de 0 o 1, se establece un valor por 
	 * defecto a 0.
	 */
	public int controlSeleccion(int control) {
		if ((control != 0) && (control != 1)) {
			ic.avisoMenu();
			ic.valorDefecto();
			control = 0;
		}
		return (control);

	}
/*
 * Método que permite realizar una consulta en la tabla de Piezas por nombre.
 */
	public boolean consultarPiezasNombre(Statement st, String name) {
		ResultSet rs;
		try {
			String query = "select * from " + NamePiezas + " where Nombre=" + "'" + name + "'";
			rs = st.executeQuery(query);
			if (compruebaResultadoConsulta(rs)) {
				ic.resultadoConsulta();
				ic.mostrarConsultaPiezas(rs);
				return (true);
			}
		} catch (SQLException e) {
			ic.error_sqlDefecto();
			return (false);
		}
		return (false);
	}
/*
 * Método encargado de realizar una consulta en la tabla Piezas por código.
 */
	public boolean consultarPiezasCodigo(Statement st, int id) {
		ResultSet rs;
		try {
			String query = "select * from " + NamePiezas + " where Codigo=" + id;
			rs = st.executeQuery(query);
			if (compruebaResultadoConsulta(rs)) {
				ic.resultadoConsulta();
				ic.mostrarConsultaPiezas(rs);
				return (true);
			}
		} catch (SQLException e) {
			ic.error_sqlDefecto();
			return (false);
		}
		return (false);
	}
/*
 * Método encargado de realizar una consulta a la tabla proveedores por nombre
 */
	public boolean consultarProvNombre(Statement st, String name) {
		ResultSet rs;
		try {
			String query = "select * from " + NameProv + " where Nombre=" + "'" + name + "'";
			rs = st.executeQuery(query);
			if (compruebaResultadoConsulta(rs)) {
				ic.resultadoConsulta();
				ic.mostrarConsultaProveedores(rs);
				return (true);
			}
		} catch (SQLException e) {
			ic.error_sqlDefecto();
			return (false);
		}
		return (false);
	}
	
	/*
	 * Método encargado de realizar una consulta a la tabla proveedores por ID
	 */

	public boolean consultarProvID(Statement st, String id) {
		ResultSet rs;
		try {
			String query = "select * from " + NameProv + " where ID=" + id;
			rs = st.executeQuery(query);
			if (compruebaResultadoConsulta(rs)) {
				ic.resultadoConsulta();
				ic.mostrarConsultaProveedores(rs);
				return (true);
			}
		} catch (SQLException e) {
			ic.error_sqlDefecto();
			return (false);
		}
		return (false);
	}
	/*
	 * Método encargado de realizar una consulta a la tabla Suministros por Codigo de pieza
	 */
	public boolean consultaSumCodigo(Statement st, int id) {

		ResultSet rs;
		try {

			String query = "select * from " + NameSum + " where CodigoPieza=" + "'" + id + "'";
			rs = st.executeQuery(query);
			if (compruebaResultadoConsulta(rs)) {
				ic.resultadoConsulta();
				ic.mostrarConsultaSuministros(rs);
				return (true);

			}
		} catch (SQLException e) {
			ic.error_sqlDefecto();
			return (false);
		}
		return (false);
	}
	/*
	 * Método encargado de realizar una consulta a la tabla Suministros por id de proveedor
	 */
	public boolean consultaSumID(Statement st, String name_id) {
		ResultSet rs;
		try {

			String query = "select * from " + NameSum + " where IdProveedor=" + "'" + name_id + "'";
			rs = st.executeQuery(query);
			if (compruebaResultadoConsulta(rs)) {
				ic.resultadoConsulta();
				ic.mostrarConsultaSuministros(rs);
				return (true);

			}
		} catch (SQLException e) {
			ic.error_sqlDefecto();
			return (false);
		}
		return (false);
	}
	
	/*
	 * Método encargado de mostrar los registros. Recibe como parámetro el 
	 * nombre de la tabla sobre el cual se realiza la consulta. 
	 */

	public void listarResultados(String NameTab, Statement st) {
		ResultSet rs;
		if (NamePiezas.equals(NameTab)) {
			String query = "select * from " + NamePiezas;
			try {
				rs = st.executeQuery(query);
				ic.resultadoConsulta();
				ic.mostrarConsultaPiezas(rs);
			} catch (SQLException e) {
				ic.error_sqlDefecto();
			}
		} else if (NameProv.equals(NameTab)) {
			String query = "select * from " + NameProv;
			try {
				rs = st.executeQuery(query);
				ic.resultadoConsulta();
				ic.mostrarConsultaProveedores(rs);
			} catch (SQLException e) {
				ic.error_sqlDefecto();
			}

		} else {
			String query = "select * from " + NameSum;
			try {
				rs = st.executeQuery(query);
				ic.resultadoConsulta();
				ic.mostrarConsultaSuministros(rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				ic.error_sqlDefecto();
			}
		}

	}

	/*
	 * * Método encargado de modificar el nombre de pieza a partir de un nombre de pieza anterior.
	 */
	public void modificaRegistroPiezasName(Statement st, String name) {
		String new_name = "";
		String query = "";
		ic.nuevoNombrePieza();
		int actualizado = 0;
		new_name = ic.lecturaString();
		query = "UPDATE " + NamePiezas + " SET Nombre= " + "'" + new_name + "'" + " WHERE Nombre= " + "'" + name + "'";
		try {
			actualizado = st.executeUpdate(query);
			ic.modificacion();
		} catch (SQLException e) {
			ic.error_sqlDefecto();
		}
	}
	/*
	 * * Método encargado de modificar el código de pieza a partir de un código de pieza anterior.
	 */
	public void modificaRegistroPiezasCodigo(Statement st, int id) {
		String new_name = "";
		String query = "";
		ic.nuevoNombrePieza();
		int actualizado = 0;
		new_name = ic.lecturaString();
		query = "UPDATE " + NamePiezas + " SET Nombre= " + "'" + new_name + "'" + " WHERE Codigo= " + "'" + id + "'";
		try {
			actualizado = st.executeUpdate(query);
			ic.modificacion();
		} catch (SQLException e) {
			ic.error_sqlDefecto();
		}
	}
	/*
	 * * Método encargado de modificar el nombre de proveedor a partir de un nombre de proveedor anterior. 
	 */
	public void modificaRegistroProveedoresName(Statement st, String name) {
		String query = "";
		ic.nuevoNombreProv();
		String new_name = ic.lecturaString();
		query = "UPDATE " + NameProv + " SET Nombre= " + "'" + new_name + "'" + " WHERE Nombre= " + "'" + name + "'";
		try {
			int actualizado = st.executeUpdate(query);
			ic.modificacion();
		} catch (SQLException e) {
			ic.error_sqlDefecto();
		}

	}
/*
 * * Método encargado de modificar el id de proveedor a partir de un id anterior. 
 */
	public void modificaRegistroProveedoresId(Statement st, String name_id) {
		String query = "";
		ic.nuevoCodigoProv();
		String new_name = ic.lecturaString();
		query = "UPDATE " + NameProv + " SET ID= " + "'" + new_name + "'" + " WHERE ID= " + "'" + name_id + "'";
		try {
			int actualizado = st.executeUpdate(query);
			ic.modificacion();
		} catch (SQLException e) {
			ic.error_sqlDefecto();
		}

	}
	/*
	 * Método encargado de modificar el precio de un suministro a partir del código de pieza. 
	 */
	public void modificaRegistroSumCodigo(Statement st, int id) {
		ic.precio();
		int price_new = ic.lecturaInt();
		String query = "UPDATE " + NameSum + " SET Precio = " + price_new + " WHERE CodigoPieza = " + id;
		try {
			int actualizado = st.executeUpdate(query);
			ic.modificacion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ic.error_sqlDefecto();
		}

	}
/*
 * Método encargado de modificar el precio de un suministro a partir del Id de proveedor.
 */
	public void modificaRegistroSumID(Statement st, String id) {
		ic.precio();
		int price_new = ic.lecturaInt();
		String query = "UPDATE " + NameSum + " SET Precio = " + price_new + " WHERE IdProveedor = " + id;
		try {
			int actualizado = st.executeUpdate(query);
			ic.modificacion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ic.error_sqlDefecto();
		}

	}
	/*
	 * Método encargado de eliminar un registro de Piezas por nombre. 
	 */
	public void eliminaPiezasName(Statement st, String name) {
		String query = "DELETE FROM " + NamePiezas + " WHERE Nombre = " + "'" + name + "'";
		try {
			int borrado = st.executeUpdate(query);
			ic.borraRegistro();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ic.error_sqlDefecto();
		}
	}
/*
 * Método encargado de eliminar un registro de Piezas por id.
 */
	public void eliminaPiezasCodigo(Statement st, int id) {
		String query = "DELETE FROM " + NamePiezas + " WHERE Codigo = " + id;
		try {
			int borrado = st.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ic.error_sqlDefecto();
		}
		ic.borraRegistro();
	}
	/*
	 * Método encargado de eliminar un registro de proveedores por nombre
	 */
	public void eliminaProvName(Statement st, String name) {
		String query = "DELETE FROM " + NameProv + " WHERE Nombre = " + "'" + name + "'";
		try {
			int borrado = st.executeUpdate(query);
		} catch (SQLException e) {
			ic.error_sqlDefecto();
		}
		ic.borraRegistro();
	}
	/*
	 * Método encargado de eliminar un registro de proveedores por ID.
	 */
	
	public void eliminaProvID(Statement st, String name) {
		String query = "DELETE FROM " + NameProv + " WHERE ID = " + "'" + name + "'";
		try {
			int borrado = st.executeUpdate(query);
		} catch (SQLException e) {
			ic.error_sqlDefecto();
		}
		ic.borraRegistro();
	}
	/*
	 * Método para eliminar un registro de suministros por Codigo de pieza.
	 */
	public void eliminaSumCodigo(Statement st,int id) {
		String query = "DELETE FROM " + NameSum + " WHERE CodigoPieza = " + id;
		try {
			int borrado = st.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ic.error_lecturaEntero();
		}
		
		ic.borraRegistro();
	}
	//Método para eliminar un registro de suministros por Id de proveedor
	
	public void eliminaSumID(Statement st,String name) {
		String query = "DELETE FROM " + NameSum + " WHERE IdProveedor = " + "'" + name + "'";
		try {
			int borrado = st.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ic.error_lecturaEntero();
		}
		
		ic.borraRegistro();
	}
/*
 * Código principal que controla el tratamiento especifico de cada una de las tablas (introducir registros, listar registros
 * borrar registros...)
 */
	public void tratamientoTablas(Statement st) {
		boolean fin = false;
		String name_tab = "";
		name_tab = controlSeleccionTabla();

		while (fin != true) {
			String param_borrar = "";
			int n = 0;
			n = controlGestionTablas();
			int control = 10;
			String name;
			int id = 0;
			boolean lleno = false;

			switch (n) {
			case 1:
				insert(name_tab, st);
				break;
			case 2:
				try {
					if (name_tab.equals(NamePiezas)) {
						control = ic.SeleccionePiezasIdName();
						control = controlSeleccion(control);
						if (control == 0) {
							name = ic.lecturaString();
							lleno = consultarPiezasNombre(st, name);
						} else {
							id = ic.lecturaInt();
							lleno = consultarPiezasCodigo(st, id);
						}
					}
					if (name_tab.equals(NameProv)) {
						control = ic.SeleccioneProvIdName();
						control = controlSeleccion(control);
						if (control == 0) {
							name = ic.lecturaString();
							lleno = consultarProvNombre(st, name);
						} else {
							String name_id = ic.lecturaString();
							lleno = consultarProvID(st, name_id);
						}
					}
					if (name_tab.equals(NameSum)) {
						control = ic.SeleccioneSumIdName();
						control = controlSeleccion(control);
						if (control == 0) {
							id = ic.lecturaInt();
							lleno = consultaSumCodigo(st, id);
						} else {
							String name_id = ic.lecturaString();
							lleno = consultaSumID(st, name_id);
						}
					}
				} catch (InputMismatchException ex) {
					ic.error_lecturaEntero();
				}

				break;
			case 3:
				listarResultados(name_tab, st);
				break;
			case 4:
				try {
					if (name_tab.equals(NamePiezas)) {
						control = ic.SeleccionePiezasIdName();
						control = controlSeleccion(control);
						if (control == 0) {
							name = ic.lecturaString();
							lleno = consultarPiezasNombre(st, name);
							if (lleno == true) {
								modificaRegistroPiezasName(st, name);
							}
						} else {
							id = ic.lecturaInt();
							lleno = consultarPiezasCodigo(st, id);
							if (lleno == true) {
								modificaRegistroPiezasCodigo(st, id);
							}
						}
					}
					if (name_tab.equals(NameProv)) {
						control = ic.SeleccioneProvIdName();
						control = controlSeleccion(control);
						if (control == 0) {
							name = ic.lecturaString();
							lleno = consultarProvNombre(st, name);
							if (lleno == true) {
								modificaRegistroProveedoresName(st, name);
							}
						} else {
							String name_id = ic.lecturaString();
							lleno = consultarProvID(st, name_id);
							if (lleno == true) {
								modificaRegistroProveedoresId(st, name_id);
							}
						}
					}
					if (name_tab.equals(NameSum)) {
						control = ic.SeleccioneSumIdName();
						control = controlSeleccion(control);
						if (control == 0) {
							id = ic.lecturaInt();
							lleno = consultaSumCodigo(st, id);
							if (lleno == true) {
								modificaRegistroSumCodigo(st, id);
							}
						} else {
							String name_id = ic.lecturaString();
							lleno = consultaSumID(st, name_id);
							if (lleno == true) {
								modificaRegistroSumID(st, name_id);
							}
						}
					}
				} catch (InputMismatchException ex) {
					ic.error_lecturaEntero();
				}

				break;
			case 5:
				try {
					if (name_tab.equals(NamePiezas)) {
						control = ic.SeleccionePiezasIdName();
						control = controlSeleccion(control);
						if (control == 0) {
							name = ic.lecturaString();
							lleno = consultarPiezasNombre(st, name);
							if (lleno == true) {
								eliminaPiezasName(st, name);
							}
						} else {
							id = ic.lecturaInt();
							lleno = consultarPiezasCodigo(st, id);
							if (lleno == true) {
								eliminaPiezasCodigo(st, id);
							}
						}
					}
					if (name_tab.equals(NameProv)) {
						control = ic.SeleccioneProvIdName();
						control = controlSeleccion(control);
						if (control == 0) {
							name = ic.lecturaString();
							lleno = consultarProvNombre(st, name);
							if (lleno == true) {
								eliminaProvName(st, name);
							}
						} else {
							String name_id = ic.lecturaString();
							lleno = consultarProvID(st, name_id);
							if (lleno == true) {
								eliminaProvID(st, name_id);
							}
						}
					}
					if (name_tab.equals(NameSum)) {
						control = ic.SeleccioneSumIdName();
						control = controlSeleccion(control);
						if (control == 0) {
							id = ic.lecturaInt();
							lleno = consultaSumCodigo(st, id);
							if (lleno == true) {
								eliminaSumCodigo(st, id);
							}
						} else {
							String name_id = ic.lecturaString();
							lleno = consultaSumID(st, name_id);
							if (lleno == true) {
								eliminaSumID(st, name_id);
							}
						}
					}
				} catch (InputMismatchException ex) {
					ic.error_lecturaEntero();
				}
				break;
			case 6:
				fin = true;
				break;
			}

		}

	}
/*
 * Método encargado de mostrar el menu general(ic.menu) y realizar un control sobre
 * la interacción por teclado del usuario. 
 */
	public int controlMenuGeneral() {
		boolean esEntero = false;
		int n = 0;
		while (esEntero != true) {
			try {
				n = ic.menu();
				esEntero = true;
				if ((n > 5) || (n < 0)) {
					ic.avisoMenu();
				}
			} catch (InputMismatchException ex) {
				ic.error_lecturaEntero();
				esEntero = false;
			}
		}
		return (n);
	}

	public void borradoTabla(boolean b) {
		if (b == true) {
			ic.borrarTablaOk();
		} else {
			ic.borrarTablaNoOk();
		}
	}

	public void checking(boolean check) {
		if (check == true) {
			ic.creacionTabla();
		} else {
			ic.aviso();
		}
	}

	public void messageinsert(boolean b) {
		if (b == true) {
			ic.insercionOk();
		} else {
			ic.insercionnoOK();
		}
	}
/*
 * Método encargado de asegurar que el usuario introduzca el nombre de una tabla existente
 * Mientras no lo introduzca se le pide.
 */
	public String controlSeleccionTabla() {
		boolean name_correct = false;
		String tab = "";
		while (name_correct != true) {
			tab = ic.menuTabla();
			if ((NamePiezas.equals(tab)) || (NameProv.equals(tab)) || (NameSum.equals(tab))) {
				name_correct = true;
			}
		}
		return (tab);
	}
/*
 * Se muestra el menu detallado de gestion de tablas y se realiza un control sobre la interacción del usuario.
 */
	public int controlGestionTablas() {
		boolean esEntero = false;
		int n = 0;
		while (esEntero != true) {
			try {
				n = ic.menuGestionTablas();
				esEntero = true;
				if ((n > 6) || (n < 0)) {
					ic.avisoMenu();
				}
			} catch (InputMismatchException ex) {
				ic.error_lecturaEntero();
				esEntero = false;
			}
		}
		return (n);
	}

	public int controllecturaInt() {
		int myint = 0;
		boolean isInt = false;
		while (isInt != true) {
			try {
				myint = ic.lecturaInt();
				isInt = true;
			} catch (InputMismatchException ex) {
				ic.error_lecturaEntero();
			}
		}
		return (myint);
	}
	/*
	 * Código principal que regula el funcionamiento del programa y donde interactuan todos los métodos anteriores.
	 */

	public void seleccionPrincipal() {
		boolean fin = false;
		Statement st = null;

		while (fin != true) {

			int selec = 0;
			String tab = "";

			selec = controlMenuGeneral();

			switch (selec) {

			case 1:
				try {
					Connection c = db.crearConexion("root", "Tsystems22", "ex22");
					if (c != null) {
						ic.creacionDatabase();
					}
					st = c.createStatement();
					boolean check1 = sum.creaTabla(c, NamePiezas, query_piezas, st);
					checking(check1);
					boolean check2 = sum.creaTabla(c, NameProv, query_proveedores, st);
					checking(check2);
					boolean check3 = sum.creaTabla(c, NameSum, query_suministra, st);
					checking(check3);
				} catch (SQLException ex) {
					System.out.println(ex);
				}

				break;

			case 2:
				sum.insertarTabla(NamePiezas, campo_piezas, piezas_defecto, st);
				sum.insertarTabla(NameProv, campo_prov, prov_defecto, st);
				sum.insertarTabla(NameSum, campo_sum, sum_defecto, st);
				ic.insercionOk();
				break;

			case 3:
				if (st != null) {
					tab = ic.menuTabla();
					if (tab.equals(NamePiezas) || tab.equals(NameProv) || tab.equals(NameSum)) {
						boolean borrado = sum.borrarTabla(tab, st);
						borradoTabla(borrado);
					} else {
						ic.avisoTabla();
					}
				} else {
					ic.aviso();
				}
				break;

			case 4:
				if (st != null) {
					tratamientoTablas(st);
				} else {
					ic.aviso();
				}
				break;

			case 5:
				fin = true;
				break;

			}
		}

	}

}
