package lexico;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

// Permite conocer rápidamente la correspondencia entre
// un lexema y su código token 
public class Correspondencia {
	
	private static final int NUM_TOKENS = 26;
	private static final BiMap<String,Integer> TABLA = HashBiMap.create(NUM_TOKENS);
	
	public static void iniciar() {
		TABLA.put("var", 1);
		TABLA.put("int", 2);
		TABLA.put("boolean", 3);
		TABLA.put("string", 4);
		TABLA.put("ENTERO", 5);
		TABLA.put("CADENA", 6);
		TABLA.put("ID", 7);
		TABLA.put("=", 8);
		TABLA.put("+", 9);
		TABLA.put("-", 10);
		TABLA.put(">", 11);
		TABLA.put("<", 12);
		TABLA.put("!=", 13);
		TABLA.put("--", 14);
		TABLA.put(",", 15);
		TABLA.put(";", 16);
		TABLA.put("if", 17);
		TABLA.put("else", 18);
		TABLA.put("function", 19);
		TABLA.put("(", 20);
		TABLA.put(")", 21);
		TABLA.put("{", 22);
		TABLA.put("}", 23);
		TABLA.put("return", 24);
		TABLA.put("input", 25);
		TABLA.put("print", 26);
	}

	public static Integer lexToID(String lex) {
		return TABLA.get(lex);
	}
	
	public static String IDToLex(int id) {
		return TABLA.inverse().get(id);
	}
}
