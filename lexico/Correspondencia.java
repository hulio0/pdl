package lexico;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

// Permite conocer rapidamente la correspondencia entre
// un codigo token numerico y su representacion "human friendly"
public class Correspondencia {
	
	private static final int NUM_TOKENS = 17;
	private static final BiMap<String,Integer> TABLA =
			HashBiMap.create(NUM_TOKENS);
	
	public static void iniciar() {
		TABLA.put("PR", 0);
		TABLA.put("ID", 1);
		TABLA.put("ENT", 2);
		TABLA.put("CAD", 3);
		TABLA.put("-", 4);
		TABLA.put("--", 5);
		TABLA.put("=", 6);
		TABLA.put("+", 7);
		TABLA.put(">", 8);
		TABLA.put("<", 9);
		TABLA.put("!", 10);
		TABLA.put(",", 11);
		TABLA.put(";", 12);
		TABLA.put("(", 13);
		TABLA.put(")", 14);
		TABLA.put("{", 15);
		TABLA.put("}", 16);
	}
	
	// Recibe representacion "friendly" y 
	// devuelve la correspondencia numerica
	public static Integer de(String codTokenFriendly) {
		return TABLA.get(codTokenFriendly);
	}
	
	// Analogo al metodo anterior
	public static String de(int codigoTokenNumerico) {
		return TABLA.inverse().get(codigoTokenNumerico);
	}
}
