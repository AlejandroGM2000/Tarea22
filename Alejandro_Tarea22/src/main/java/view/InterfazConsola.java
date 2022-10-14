package view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InterfazConsola {
	
	public  final String NamePiezas="Piezas";
	public  final String NameProv="Proveedores";
	public  final String NameSum="Suministros";
	
	public int menu() {
		int n=0;
		System.out.println("\nBienvenido!!");
		System.out.println("1-Inicializacion Base de Datos");
		System.out.println("2-Insertar paquetes de datos");
		System.out.println("3-Borrar tabla");
		System.out.println("4-Acceder a las tablas");
		System.out.println("5-Salir del programa");
		System.out.println("Seleccione una accion");
		n=lecturaNumeroEntero();
		return(n);

	}
	public int lecturaNumeroEntero() {
		int n=0;
		Scanner sc8 = new Scanner(System.in);
		n = sc8.nextInt();
		return(n);
	}
		
	
	public int menuGestionTablas() {
		int i=0;
		System.out.println("\n1-Añadir registro");
		System.out.println("2-Consultar por ID o nombre");
		System.out.println("3-Listar registro");
		System.out.println("4-Modificar registro");
		System.out.println("5-Borrar registro");
		System.out.println("6-Salir");
		System.out.println("Seleccione una opcion");
		i=lecturaNumeroEntero();
		return(i);
	}
	

	
	public String menuTabla() {
		String tab="";
		System.out.println("Introduzca el nombre de la tabla");	
		tab=lecturaCadena();
		return(tab);
	}
	
	public String lecturaCadena() {
		String tab="";
		Scanner sc7 = new Scanner(System.in);
		tab = sc7.nextLine();
		return(tab);
	}
		

	
	
	public void aviso() {
		System.out.println("\nBase/Tabla de datos no creada");
	}
	public void avisoMenu() {
		System.out.println("\nDebe introducir valores enteros en el rango indicado");
	}
	public void avisoTabla() {
		System.out.println("\nEsta tabla no esta disponible");
	}
	public void creacionDatabase() {
		System.out.println("\n-Abierta base de datos - Ok");
	}
	public void creacionTabla() {
		System.out.println("\n-Creada tabla - Ok");
	}
	public void borrarTablaOk() {
		System.out.println("\n-Borrar tabla contacto - Ok");
	}
	public void borrarTablaNoOk() {
		System.out.println("\nDebe mantener la estructura relacional");
	}
	public void insercionOk() {
		System.out.println("\n-Añadir registros a la tabla - Ok");
	}
	public void insercionnoOK() {
		System.out.println("\nSe ha producido un error en la insercion");
	}

	public void error_lecturaEntero() {
		System.out.println("\nDebe introducir un numero entero");
	}
	public void error_sqlDefecto() {
		System.out.println("\nSe ha producido un fallo SQL");
	}
	public int SeleccionePiezasIdName() {
		System.out.println("\nDesea realizar la busqueda por ID o nombre?");
		System.out.println("Introduzca");
		System.out.println("0-Busqueda por nombre");
		System.out.println("1-Busqueda por codigo\n");
		return(lecturaInt());
	}
	
	public int SeleccioneProvIdName() {
		System.out.println("\nDesea realizar la busqueda por ID o nombre?");
		System.out.println("Introduzca");
		System.out.println("0-Busqueda por nombre");
		System.out.println("1-Busqueda por codigo\n");
		return(lecturaInt());
	}
	
	public int SeleccioneSumIdName() {
		System.out.println("\nDesea realizar la busqueda por ID de pieza o ID proveedor?");
		System.out.println("Introduzca");
		System.out.println("0-Busqueda por ID pieza");
		System.out.println("1-Busqueda por ID proveedor\n");
		return(lecturaInt());
	}

	public String lecturaString() {
		String lectura="";
		System.out.println("Introduzca un nombre");
		lectura=lecturaCadena();
		return(lectura);
	}
	
	public int lecturaInt() {
		int myint=0;
		Scanner sc=new Scanner(System.in);
		System.out.println("Introduzca un numero entero");
		myint=sc.nextInt();
		return(myint);
	}
	public void resultadoConsulta() {
		System.out.println("Los resultados de la consulta son:");
		
	}
	
	public void mostrarConsultaPiezas (ResultSet rs) {
		try {
			while (rs.next()) {
				System.out.println(rs.getString(1) + " " + rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			error_sqlDefecto();
		}
	}
	
	public void mostrarConsultaProveedores (ResultSet rs) {
		try {
			while (rs.next()) {
				System.out.println(rs.getString(1) + " " + rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			error_sqlDefecto();
		}
	}
	
	public void mostrarConsultaSuministros (ResultSet rs) {
		try {
			while (rs.next()) {
				System.out.println(rs.getString(1) + " " + rs.getString(2)+" "+rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			error_sqlDefecto();
		}
	}
	public void consultaVacia() {
		System.out.println("\nLa consulta esta vacia");
	}
	public void valorDefecto() {
		System.out.println("\nSe establece el valor 0");
	}
	public void avisoDelete() {
		System.out.println("\nSi desea borrar este proveedor/pieza, elimine antes el correspondiente Suministro");
	}
	public void borraRegistro() {
		System.out.println("\nRegistro borrado");
	}
	public int modificaProveedor() {
		System.out.println("\n Desea realizar la modificacion por nombre o ID?");
		System.out.println("Introduzca");
		System.out.println("0-Busqueda por nombre");
		System.out.println("1-Busqueda por codigo\n");
		return(lecturaInt());
		
	}
	
	public void modificaSuministro() {
		System.out.println("\n Modificacion del precio");
			
	}
	
	public void prosigaModificacion() {
		System.out.println("\nRealize la modificacion que desee");
	}
	public void precio() {
		System.out.println("\nIntroduzca el nuevo precio");
		
	}
	public void modificacion() {
		System.out.println("\nModificacion realizada con exito");
	}
	public void nuevoNombrePieza() {
		// TODO Auto-generated method stub
		System.out.println("\nIntroduzca el nuevo nombre de la pieza");
		
	}
	public void nuevoCodigoProv() {
		System.out.println("\nIntroduzca el nuevo codigo de proveedor");
	}
	public void nuevoNombreProv() {
		System.out.println("\nIntroduzca el nuevo nombre de proveedor");
		
	}
}
