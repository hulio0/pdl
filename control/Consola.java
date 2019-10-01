package control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Consola {
		
	private static File consola;
	
	public static void iniciar(String dirActual) {
		
		consola = new File(dirActual+"\\CONSOLA.txt");
				
		if(consola.exists())
			consola.delete();
		
		try { consola.createNewFile(); }
		catch(IOException e) { e.printStackTrace(); }
	}
	
	public static void escribir(String texto) {
		
		// Pasarle true al constructor del FileWriter le indica que escriba al final
		// del fichero (así no se está cargando continuamente el contenido de éste)
		try( BufferedWriter bw = new BufferedWriter(new FileWriter(consola,true))  )
		{
			bw.write(texto); 
			bw.write("\n\n\n");
		}
		catch(IOException e) { e.printStackTrace(); }
	}

}
