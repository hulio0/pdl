package control;

import java.io.File;
import java.io.IOException;

import errores.GestorErrores;
import lexico.AnalizadorLexico;
import sintactico.AnalizadorSintactico;

// Clase que crea los ficheros de salida y pone en marcha todos los modulos del programa
public class Control {
	
	public static void iniciar(File dirActual, File ficheroFuente){
		
		// Creamos los ficheros de salida: primero crearemos una carpeta
		// para meterlos dentro. La carpeta se llamara SALIDA
		dirActual = new File(dirActual,"SALIDA");
		
		// Si la carpeta ya existe (por ejemplo, por una ejecucion anterior)
		// habra que limpiar/eliminar todo su contenido. 
		if( dirActual.exists() )
			limpiar(dirActual);
		
		// Creamos la carpeta
		else
			dirActual.mkdir();
		
		// Ahora si, creamos los ficheros de salida
		File ficheroALexico = new File( dirActual , "Salida Analizador Lexico.txt");
		File ficheroTS = new File( dirActual , "Salida Tabla de Simbolos.txt");
		File ficheroASintactico = new File( dirActual , "Salida Analizador Sintactico.txt");
		File ficheroASemantico = new File( dirActual , "Salida Analizador Semantico.txt");
		File ficheroErrores = new File( dirActual , "Salida Gestor de Errores.txt");
		try {
			ficheroALexico.createNewFile();
			ficheroASintactico.createNewFile();
			ficheroASemantico.createNewFile();
			ficheroErrores.createNewFile();
		} catch(IOException e) { e.printStackTrace(); }
		
		// Ponemos en marcha los modulos
		GestorErrores.iniciar(ficheroErrores);
		AnalizadorLexico.iniciar(ficheroFuente, ficheroALexico, ficheroTS);
		AnalizadorSintactico.iniciar(ficheroASintactico);
	}
	
	// Elimina todos los ficheros del directorio que le pasemos.
	// No elimina sub-carpetas pues .delete solo funciona con ellas
	// cuando estan vacias
	private static void limpiar(File carpeta) {
		for( File f : carpeta.listFiles() )
			f.delete();
	}
}
