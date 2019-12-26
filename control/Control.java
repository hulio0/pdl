package control;

import java.io.File;
import java.io.IOException;

import errores.GestorErrores;
import lexico.AnalizadorLexico;
import semantico.AnalizadorSemantico;
import sintactico.AnalizadorSintactico;
import tablasim.TablaS;

// Clase que crea los ficheros de salida y pone en marcha todos los modulos del programa
public class Control {
	
	public static void iniciar(File dirActual, File ficheroFuente){
		
		// Creamos los ficheros de salida: primero crearemos una carpeta
		// para meterlos dentro. La carpeta se llamará igual que el fichero fuente		
		dirActual = new File( dirActual , extraerNombre(ficheroFuente) );	
		
		
		// Si la carpeta ya existe (por ejemplo, por una ejecucion anterior)
		// habra que limpiar/eliminar todo su contenido. 
		if( dirActual.exists() && dirActual.isDirectory() )
			limpiar(dirActual);
		
		// Creamos la carpeta
		else
			dirActual.mkdir();
		
		// Ahora si, creamos los ficheros de salida
		File ficheroALexico 	= new File( dirActual , "Salida_Analizador_Lexico.txt");
		File ficheroTS 			= new File( dirActual , "Salida_Tabla_Simbolos.txt");
		File ficheroASintactico = new File( dirActual , "Salida_Analizador_Sintactico.txt");
		File ficheroErrores 	= new File( dirActual , "Salida_Gestor_Errores.txt");
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
		AnalizadorSintactico.iniciar(ficheroASintactico);
		//AnalizadorSemantico.iniciar(ficheroASemantico);	//TODO quitar paquete semantico e incluirlo en sintactico
	}
	
	// Elimina todos los ficheros del directorio que le pasemos.
	// No elimina sub-carpetas con info. dentro (no es nuestro caso)
	// pues .delete solo funciona con ellas cuando estan vacias
	private static void limpiar(File carpeta) {
		for( File f : carpeta.listFiles() )
			f.delete();
	}
	
	// Llama a terminar ejecución de cada módulo y acaba el programa
	public static void terminarEjecucion() {
		GestorErrores.terminarEjecucion();
		TablaS.terminarEjecucion();
		AnalizadorLexico.terminarEjecucion();
		AnalizadorSintactico.terminarEjecucion();
		AnalizadorSemantico.terminarEjecucion();
		
		if( GestorErrores.huboError() ) {
			System.out.println("Se han encontrado errores en el fichero fuente");
			System.exit(1);
		}
		
		else {
			System.out.println("Todo OK");
			System.exit(0);
		}
		
	}
	
	private static String extraerNombre(File fichero) {
		String nombreFich = fichero.getName();
		return nombreFich.substring(0, nombreFich.lastIndexOf("."));
	}
}
