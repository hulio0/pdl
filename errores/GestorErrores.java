package errores;

import java.io.File;
import control.Modulo;
import control.Salida;

// Es el que recibe los errores de los diferentes modulos.
// Basicamente se encarga de escribir las descripciones de
// los errores en el fichero de errores. Tambien permite
// consultar si se ha producido un error o no.
public class GestorErrores implements Modulo {
	
	private static Salida salidaGestErr;
	
	public static void iniciar(File ficheroSalidaErr) {
		salidaGestErr = new Salida(ficheroSalidaErr);
	}
	
	private static boolean huboError = false;
	public static void reportar(Error e) {
		salidaGestErr.escribir( e + "\n" );
		huboError = true;
	}

	public static boolean huboError() {
		return huboError;
	}
	
	public static void terminarEjecucion() {}
}
