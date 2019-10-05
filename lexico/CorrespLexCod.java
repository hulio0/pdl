package lexico;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

// Permite conocer rápidamente la correspondencia entre
// un lexema y su código token 
public class CorrespLexCod {
	
	private static final int NUM_TOKENS = 17;
	private static final BiMap<String,Integer> TABLA = HashBiMap.create(NUM_TOKENS);
	
	public static void iniciar() {
		TABLA.put("PR", 1);
		TABLA.put("ID", 2);
		TABLA.put("ENT", 3);
		TABLA.put("CAD", 4);
		TABLA.put("-", 5);
		TABLA.put("--", 6);
		TABLA.put("=", 7);
		TABLA.put("+", 8);
		TABLA.put(">", 9);
		TABLA.put("<", 10);
		TABLA.put("!", 11);
		TABLA.put(",", 12);
		TABLA.put(";", 13);
		TABLA.put("(", 14);
		TABLA.put(")", 15);
		TABLA.put("{", 16);
		TABLA.put("}", 17);
	}

	public static Integer get(String lex) {
		return TABLA.get(lex);
	}
	
	public static String get(int id) {
		return TABLA.inverse().get(id);
	}
}
