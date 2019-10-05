package lexico;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

// Estructura de datos que permite hacer una correspondencia entre cada palabra clave
// y la posición que ocupa en la estructura
public class TablaPR {

	private static final int NUM_PR = 10;
	private static final BiMap<String,Integer> TABLA = HashBiMap.create(NUM_PR);
	
	public static void iniciar() {
		TABLA.put("boolean", 1);
		TABLA.put("else", 2);
		TABLA.put("function", 3);
		TABLA.put("if", 4);
		TABLA.put("input", 5);
		TABLA.put("int", 6);
		TABLA.put("print", 7);
		TABLA.put("return", 8);
		TABLA.put("string", 9);
		TABLA.put("var", 10);
	}
	
	public static boolean buscarTPR(String lex) {
		return TABLA.containsKey(lex);
	}
	
	public static Integer get(String pr) {
		return TABLA.get(pr);
	}
	
	public static String get(Integer codPR) {
		return TABLA.inverse().get(codPR);
	}
	
	
}
