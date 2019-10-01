package control;

import java.io.File;
import java.io.IOException;

import excepciones.FicheroEntradaNoValido;

public class Control {
	
	private static String dirActual;
	
	private static File entrada;
	private static File salidaAnalizadorLexico;
	
	public static void iniciar(String directorio, String nombreFicheroEntrada)
		throws FicheroEntradaNoValido, IOException {
		
		// Guardamos el directorio donde nos encontramos
		dirActual = directorio;
		
		// Cargamos el fichero de entrada
		entrada = new File(dirActual+"//"+nombreFicheroEntrada);
		
		if( !entrada.exists() )
			throw new FicheroEntradaNoValido("No se ha encontrado el fichero de entrada");
		
		// Ahora creamos los ficheros de salida
		crearFicheros(dirActual);
		
		Consola.escribir("Inicio completado satisfactoriamente");
	}
	
	private static void crearFicheros(String directorio) throws IOException {
		
		// La carpeta donde guardaremos los ficheros de 
		// salida se llamará SALIDA
		directorio += "//SALIDA";
		
		// Creamos la carpeta
		new File(directorio).mkdir();
		
		// Instanciamos los File's de los ficheros de salida
		salidaAnalizadorLexico = new File( directorio + "\\SALIDA ANALIZADOR LÉXICO.txt" );

		// Creamos los ficheros de salida dentro de la carpeta
		salidaAnalizadorLexico.createNewFile();
		
	}

}
