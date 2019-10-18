package sintactico;

import java.io.File;

import control.Salida;
import lexico.AnalizadorLexico;

public class AnalizadorSintactico {
	
	private static Salida salidaSint;
	
	public static void iniciar(File ficheroSalidaSint) {
		salidaSint = new Salida(ficheroSalidaSint);

		
		while( AnalizadorLexico.genToken()!=null );
	}

}
