package controller;

import model.Database;
import model.Suministro;
import view.InterfazConsola;

public class Main {

	public static void main(String[] args) {

			Database data=new Database();
			Suministro sumins=new Suministro();
			InterfazConsola ic=new InterfazConsola();
			Controlador c= new Controlador(data,sumins,ic);
			c.seleccionPrincipal();
			
	}

}


