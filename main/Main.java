package main;

import java.util.Arrays;

import control.Consola;
import control.Control;

public class Main {

	public static void main(String[] args) {
		
		// Obtenemos el directorio donde se está ejecutando el código
		String dirActual = System.getProperty("user.dir");
		
		// Iniciamos la "consola"
		Consola.iniciar(dirActual);
		
		String nombreFicheroEntrada = "Entrada.txt";
		if(args.length>0)
			nombreFicheroEntrada = args[0];
		
		// Iniciamos el "Cerebro"
		try {
			Control.iniciar(dirActual,nombreFicheroEntrada);
		}catch(Exception e) { 
			Consola.escribir( mensaje(e) );
			e.printStackTrace();			
		}
		
		Consola.escribir("Perro");
		Consola.escribir("Gato");
		Consola.escribir("Chano");
	}
	
	private static String mensaje(Exception e) {
		return e.getMessage() + "\n" + Arrays.toString( e.getStackTrace() );
	}
	
}
