package control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// Clase que recibe como parametro un fichero y permite escribir
// sobre el mismo cualquier string siempre que necesitemos. Usamos
// esta clase para que cada modulo pueda sacar su respectiva salida
public class Salida {
	
	private File fichero;
	
	public Salida(File fichero) {
		this.fichero = fichero;
		
	}
	
	public void escribir(String texto) {
			
		// Pasarle true al constructor del FileWriter le indica que escriba al final
		// del fichero (asi no se esta cargando continuamente el contenido de este)
		try( FileWriter fw = new FileWriter(this.fichero,true) )
		{
			fw.write( texto + "\n" ); 
		}
		catch(IOException e) { e.printStackTrace(); }
	}

}

