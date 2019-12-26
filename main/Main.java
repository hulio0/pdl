package main;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import control.Control;
import lexico.Token;

public class Main {

	public static void main(String[] args) {
		File dirActual = obtenerDirectorioEjecutable();

		File ficheroFuente = null;

		if(args.length>0) {
			ficheroFuente = new File(dirActual, args[0]);

			if( !ficheroFuente.exists() ) {
				System.out.println("No se ha encontrado el fichero fuente");
				return;
			}

			if(args.length>1 && (args[1].equals("--friendly") || args[1].equals("-f"))) {
				Token.setMode(Token.Modo.FRIENDLY);
			}
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
				System.out.println("No se ha encontrado ningún fichero .js en el directorio actual");
				return;
			} else {
				ficheroFuente = listaFicherosJS.get(0);
			}

			// Esta opción mostrará los tokens en formato friendly por defecto
			Token.setMode(Token.Modo.FRIENDLY);
		}

		//QUITAR ESTO (ya se permite que el usuario seleccione friendly)
		//Token.setMode(Token.Modo.FRIENDLY);

		// Ponemos en marcha el programa
		Control.iniciar(dirActual, ficheroFuente);
	}

	private static File obtenerDirectorioEjecutable() {
		return new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
	}

}
