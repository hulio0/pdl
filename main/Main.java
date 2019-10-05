package main;

import control.Control;

public class Main {

	public static void main(String[] args) {
		
		String dirActual = System.getProperty("user.dir");
		
		String nombreFicheroEntrada = "Entrada.txt";
		if(args.length>0)
			nombreFicheroEntrada = args[0];
		
		
		// Ponemos en marcha el programa
		Control.iniciar(dirActual, nombreFicheroEntrada);
	}
	
}
