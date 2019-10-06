package errores;

import java.io.File;

import control.Salida;

public class GestorErrores {
	
	private static Salida salida;
	
	public static void iniciar(File ficheroSalidaErr) {
		salida = new Salida(ficheroSalidaErr);
	}
	
	public static void reportar(Error e) {
		salida.escribir( e.getDesc() );
	}

}
