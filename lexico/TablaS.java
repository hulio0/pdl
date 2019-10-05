package lexico;


import lexico.auxiliares.FilaTS;
import java.util.ArrayList;

public class TablaS {
	
	private static ArrayList<FilaTS> tabla;
	
	public static Integer get(String lexema) {
		boolean found = false;
		int i;
		for(i=0;i<tabla.size() && !found;i++)
			found = tabla.get(i).lex().equals(lexema);
		
		return ( found ? i : null );
	}
	
	public static Integer insert(FilaTS fila) {
		int posLibre = tabla.size();
		tabla.add(posLibre,fila);
		return posLibre;
	}
}
