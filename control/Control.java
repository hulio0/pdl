package control;

import java.io.File;
import java.io.IOException;

import errores.GestorErrores;
import lexico.AnalizadorLexico;
import sintsem.AnalizadorSintSem;
import tablasim.TablaS;

// Clase que crea los ficheros de salida y pone en marcha todos los modulos del programa
public class Control {

	public static void iniciar(File dirActual, File ficheroFuente){

		// Primero crearemos una carpeta para meter los ficheros
		// de salida. La carpeta se llamará igual que el fichero fuente
		dirActual = new File( dirActual , extraerNombre(ficheroFuente) );
		
		

		// Si la carpeta ya existe (por ejemplo, por una ejecucion anterior)
		// habra que limpiar/eliminar todo su contenido.
		if( dirActual.isDirectory() )
			limpiar(dirActual);
		
		// Si no existe la creamos
		else
			dirActual.mkdir();

		// Ahora si, creamos los ficheros de salida
		File ficheroALexico 	= new File( dirActual , "Tokens.txt");
		File ficheroTS 			= new File( dirActual , "Tabla_Simbolos.txt");
		File ficheroASintactico = new File( dirActual , "Parse.txt");
		File ficheroErrores 	= new File( dirActual , "Errores.txt");
		try {
			ficheroALexico.createNewFile();
			ficheroTS.createNewFile();
			ficheroASintactico.createNewFile();
			ficheroErrores.createNewFile();
		} catch(IOException e) { e.printStackTrace(); }

		// Ponemos en marcha los modulos
		GestorErrores.iniciar(ficheroErrores);
		TablaS.iniciar(ficheroTS);
		AnalizadorLexico.iniciar(ficheroFuente, ficheroALexico);
		AnalizadorSintSem.iniciar(ficheroASintactico);
	}

	// Llama a terminar ejecución de cada módulo y acaba el programa
	public static void terminarEjecucion() {
		GestorErrores.terminarEjecucion();
		TablaS.terminarEjecucion();
		AnalizadorLexico.terminarEjecucion();
		AnalizadorSintSem.terminarEjecucion();

		if( GestorErrores.huboError() ) {
			System.out.println("Se han encontrado errores en el fichero fuente");
			System.exit(2);
		}

		else {
			System.out.println("Programa aceptado");
			System.exit(0);
		}

	}

	// Le quita la extensión al nombre del fichero
	// (Por ejemplo, Prueba.js -> Prueba)
	private static String extraerNombre(File fichero) {
		String nombreFich = fichero.getName();
		return nombreFich.substring(0, nombreFich.indexOf("."));
	}

	// Elimina todos los ficheros del directorio que le pasemos.
	// No elimina sub-carpetas con info. dentro (no es nuestro caso)
	// pues .delete solo funciona con ellas cuando estan vacias
	private static void limpiar(File carpeta) {
		for( File f : carpeta.listFiles() ) {
			f.delete();
		}
	}
}
