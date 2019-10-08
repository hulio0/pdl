package lexico;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

// Estructura de datos que permite hacer la correspondencia
// entre cada palabra reservada y el codigo numerico que la identifica
public class TablaPR {

	private static final int NUM_PR = 10;
	private static final BiMap<String,Integer> TABLA =
			HashBiMap.create(NUM_PR);
	
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
	
	public static boolean buscarTPR(String palTexto) {
		return TABLA.containsKey(palTexto);
	}
	
	public static Integer get(String palTexto) {
		return TABLA.get(palTexto);
	}
	
	public static String get(Integer palCodigo) {
		return TABLA.inverse().get(palCodigo);
	}
	
	
}
