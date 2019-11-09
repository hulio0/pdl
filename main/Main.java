package main;

import java.io.File;

import control.Control;
import lexico.Token;

public class Main {

	public static void main(String[] args) {
		
		File dirActual = new File(System.getProperty("user.dir"));
		
		String nombreFicheroFuente = "Prueba.txt";
		if(args.length>0) {
			nombreFicheroFuente = args[0];
			
			if(args.length>1 && args[1].equals("friendly"))
				Token.setMode(Token.Modo.FRIENDLY);
		}
		
		File ficheroFuente = new File(dirActual,nombreFicheroFuente);
		
		if( !ficheroFuente.exists() ) {
			System.out.println("No se ha encontrado el fichero fuente");
			return;
		}
		
		//QUITAR ESTO
		Token.setMode(Token.Modo.FRIENDLY);
		
		// Ponemos en marcha el programa
		Control.iniciar(dirActual, ficheroFuente);
	}
	
}
