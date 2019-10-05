package lexico;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// TO-DO: Cambiar esta clase a Salida y meterla en el paquete Control. Meter instancias
// de esta clase como atributos en los analizadores y utilizarla para sacar sus resultados
public class SalidaLexico {
	
	private static File fichSalida;
	
	public static void iniciar(File fichSal) {
		fichSalida = fichSal;
		
	}
	
	public static void escribir(String texto) {
			
		// Pasarle true al constructor del FileWriter le indica que escriba al final
		// del fichero (así no se está cargando continuamente el contenido de éste)
		try( BufferedWriter bw = new BufferedWriter(new FileWriter(fichSalida,true))  )
		{
				bw.write(texto); 
				bw.newLine();
		}
		catch(IOException e) { e.printStackTrace(); }
	}

}
