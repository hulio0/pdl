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
		// meter toda la salida del programa allí, así está todo más organizado
		
		// La carpeta se llamará SALIDA
		dirActual+="//SALIDA";
		
		// La creamos.
		new File(dirActual).mkdir();
		
		// Ahora creamos los ficheros de salida dentro de ella. Nótese que dirActual "apunta"
		// al interior de la carpeta SALIDA, en otras palabras, ahora mismo estamos dentro de ella.
		// Luego, basta darles nombres a los ficheros, instanciar los File's y llamar al método correspondiente
		File ficheroALexico = new File( dirActual + "//Salida Analizador Léxico.txt");
		File ficheroASintactico = new File( dirActual + "//Salida Analizador Sintáctico.txt");
		File ficheroASemantico = new File( dirActual + "//Salida Analizador Léxico.txt");
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

}
