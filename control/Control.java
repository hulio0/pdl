package control;

import java.io.File;
import java.io.IOException;

import lexico.AnalizadorLexico;

public class Control {
	
	public static void iniciar(String dirActual, String nombreFicheroEntrada){
		
		// Cargamos el fichero de entrada
		File ficheroFuente = new File(dirActual+"//"+nombreFicheroEntrada);
		
		if( !ficheroFuente.exists() ) {
			System.out.println("No se ha encontrado el fichero fuente");
			return;
		}
		
		// Ahora creamos los ficheros de salida. Primero crearemos una carpeta para
		// meter toda la salida del programa all�, as� est� todo m�s organizado
				
		// La carpeta se llamar� SALIDA
		dirActual+="//SALIDA";
		
		// Bueno, antes de seguir habr� que cargarse cualquier otra ejecuci�n anterior. El problema es
		// que no podemos simplemente usar .delete en la carpeta pues esto s�lo  funciona cuando est�
		// vac�a as� que lo que haremos esta vez es simplemente borrar todos los FICHEROS de DENTRO
		// (si quisieramos borrar el contenido de sub carpetas nececitar�amos una funci�n recursiva)
		limpiar(dirActual);
		
		// La creamos.
		new File(dirActual).mkdir();
		
		// Ahora creamos los ficheros de salida dentro de ella. N�tese que dirActual "apunta"
		// al interior de la carpeta SALIDA, en otras palabras, ahora mismo estamos dentro de ella.
		// Luego, basta darles nombres a los ficheros, instanciar los File's y llamar al m�todo correspondiente
		File ficheroALexico = new File( dirActual + "//Salida Analizador L�xico.txt");
		File ficheroASintactico = new File( dirActual + "//Salida Analizador Sint�ctico.txt");
		File ficheroASemantico = new File( dirActual + "//Salida Analizador Sem�ntico.txt");
		File ficheroErrores = new File( dirActual + "//Salida Gestor de Errores.txt");
		
		try {
			ficheroALexico.createNewFile();
			ficheroASintactico.createNewFile();
			ficheroASemantico.createNewFile();
			ficheroErrores.createNewFile();
		} catch(IOException e) { System.out.println("No se han podido crear los ficheros de salida"); }
		
		
		
		// Ponemos en marcha los analizadores:
		AnalizadorLexico.iniciar(ficheroFuente, ficheroALexico);
	}

	private static void limpiar(String dir) {
		for( File f : new File(dir).listFiles() )
			f.delete();
	}
}
