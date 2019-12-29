package main;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import control.Control;
import lexico.Token;

public class Main {

	public static void main(String[] args) {
		
		if( args.length > 2 ) {
			System.err.println("ERROR: Sólo se permiten 2 argumentos como máximo: nombre del\n"
							  +"fichero (obligatorio) y (opcional) la opción friendly (-f o --friendly)");
			System.exit(1);
		}
		
		if( args.length == 2 && !(args[1].equals("--friendly") || args[1].equals("-f") )){
			System.err.println("ERROR: Opción friendly mal introducida. La manera correcta es\n"
					  		  +"-f o bien --friendly");
			System.exit(1);
		}
		
		if( args.length == 1 && ( args[0].equals("-h") || args[0].equals("--help") ) ){
			System.out.println("USO: java -jar Procesador.jar <FILE> [-f|--friendly]\n"
							  +"DESCRIPCIÓN: Analiza el fichero especificado. Los tokens\n"
							  +"se muestran con códigos numéricos salvo que se especifique\n"
							  +"la opción friendly");
			System.exit(0);
		}
		
		
		File dirActual = obtenerDirectorioEjecutable();
		File ficheroFuente = null;
		if(args.length>0) {
		
			ficheroFuente = new File(dirActual, args[0]);
			if( !ficheroFuente.exists() ) {
				System.err.println("ERROR: No se ha encontrado el fichero fuente");
				System.exit(1);
			}

			if(args.length == 2)
				Token.setMode(Token.Modo.FRIENDLY);
		}

		// El usuario va por la via de dejar el fichero que le
		// interesa en la misma carpeta donde está el ejecutable. Cogemos
		// el primer fichero con extensión .js que encontremos
		else {

			List<File> listaFicherosJS =
					Arrays.stream( dirActual.listFiles() )
										.filter( fich -> fich.getName().endsWith(".js") )
										.sorted()
										.collect( Collectors.toList() );

			if( listaFicherosJS.size()==0 ) {
				System.err.println("ERROR: No se ha encontrado ningún fichero .js\n"
								  +"en el directorio actual");
				System.exit(1);
			}
			
			ficheroFuente = listaFicherosJS.get(0);

			// Esta opción mostrará los tokens en formato friendly por defecto
			Token.setMode(Token.Modo.FRIENDLY);
		}

		// Ponemos en marcha el programa
		Control.iniciar(dirActual, ficheroFuente);
	}

	private static File obtenerDirectorioEjecutable() {
		return new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
	}

}
