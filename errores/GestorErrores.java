package errores;

import java.io.File;

import control.Salida;
import errores.err.Error;

// Es el que recibe los errores de los diferentes modulos.
// Basicamente se encarga de escribir las descripciones de
// los errores en el fichero de errores
public class GestorErrores {
	
	private static Salida salidaGestErr;
	
	public static void iniciar(File ficheroSalidaErr) {
		salidaGestErr = new Salida(ficheroSalidaErr);
	}
	
	public static void reportar(Error e) {
		salidaGestErr.escribir( e.getDesc() + "\n" );
	}

}
