package control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Salida {
	
	private File fichero;
	
	public Salida(File fichero) {
		this.fichero = fichero;
		
	}
	
	public void escribir(String texto) {
			
		// Pasarle true al constructor del FileWriter le indica que escriba al final
		// del fichero (así no se está cargando continuamente el contenido de éste)
		try( BufferedWriter bw = new BufferedWriter(new FileWriter(this.fichero,true))  )
		{
				bw.write(texto); 
				bw.newLine();
		}
		catch(IOException e) { e.printStackTrace(); }
	}

}

