package lexico.ts;


import java.util.ArrayList;

public class TablaS {
	
	private static ArrayList<FilaTS> tabla =
			new ArrayList<FilaTS>();
	
	public static Integer get(String lexema) {
		int res = -1;
		for(int i=0;i<tabla.size() && res==-1;i++)
			if(tabla.get(i).lex().equals(lexema)) res=i;
		
		return ( res!=-1 ? res : null );
	}
	
	public static Integer insert(FilaTS fila) {
		int posLibre = tabla.size();
		tabla.add(posLibre,fila);
		return posLibre;
	}
}
