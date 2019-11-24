package semantico;

import java.io.File;

import control.Salida;

public class AnalizadorSemantico {
	
	private static Salida salidaSem;
	
	public static void iniciar(File ficheroSalidaLex) {
		salidaSem = new Salida(ficheroSalidaLex);
	}
	
	public static void terminarEjecucion() {
		
	}

}
