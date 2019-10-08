package main;

import java.io.File;

import control.Control;

public class Main {

	public static void main(String[] args) {
		
		File dirActual = new File(System.getProperty("user.dir"));
		
		String nombreFicheroFuente = "PIdG83.txt";
		if(args.length>0)
			nombreFicheroFuente = args[0];
		
		File ficheroFuente = new File(dirActual,nombreFicheroFuente);
		
		if( !ficheroFuente.exists() ) {
			System.out.println("No se ha encontrado el fichero fuente");
			return;
		}
		
		// Ponemos en marcha el programa
		Control.iniciar(dirActual, ficheroFuente);
	}
	
}
