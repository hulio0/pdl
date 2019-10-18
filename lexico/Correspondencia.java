package lexico;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

// Permite conocer rapidamente la correspondencia entre
// un codigo token numerico y su representacion "human friendly"
public class Correspondencia {
	
	private static final int NUM_TOKENS = 26;
	private static final BiMap<String,Integer> TABLA =
			HashBiMap.create(NUM_TOKENS);
	
	public static void iniciar() {
		TABLA.put("BOOLEAN", 1);		
		TABLA.put("ELSE", 2);		
		TABLA.put("FUNCTION", 3);
		TABLA.put("IF", 4);
		TABLA.put("INPUT", 5);
		TABLA.put("INT", 6);
		TABLA.put("PRINT", 7);
		TABLA.put("RETURN", 8);
		TABLA.put("STRING", 9);
		TABLA.put("VAR", 10);
		TABLA.put("AUTO_DEC", 11);
		TABLA.put("ENTERO", 12);
		TABLA.put("CADENA", 13);
		TABLA.put("ID", 14);
		TABLA.put("IGUAL", 15);
		TABLA.put("COMA", 16);
		TABLA.put("PUNTO_COMA", 17);
		TABLA.put("PAR_AB", 18);
		TABLA.put("PAR_CE", 19);
		TABLA.put("LLA_AB", 20);
		TABLA.put("LLA_CE", 21);
		TABLA.put("MAS", 22);
		TABLA.put("MENOS", 23);
		TABLA.put("NEGACION", 24);
		TABLA.put("MENOR", 25);
		TABLA.put("MAYOR", 26);
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
