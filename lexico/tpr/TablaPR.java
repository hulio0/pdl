package lexico.tpr;

import java.util.Map;
import java.util.HashMap;

import lexico.Correspondencia;

// Estructura de datos que permite hacer la correspondencia
// entre cada palabra reservada y el codigo numerico que la identifica
public class TablaPR {

	private static final Map<String,Integer> TABLA = 
			new HashMap<String,Integer>();
	
	public static void iniciar() {
		TABLA.put("boolean",
				Correspondencia.de("BOOLEAN"));
		
		TABLA.put("else",
				Correspondencia.de("ELSE"));
		
		TABLA.put("function",
				Correspondencia.de("FUNCTION"));
		
		TABLA.put("if",
				Correspondencia.de("IF"));
		
		TABLA.put("input",
				Correspondencia.de("INPUT"));
		
		TABLA.put("int",
				Correspondencia.de("INT"));
		
		TABLA.put("print",
				Correspondencia.de("PRINT"));
		
		TABLA.put("return",
				Correspondencia.de("RETURN"));
		
		TABLA.put("string",
				Correspondencia.de("STRING"));
		
		TABLA.put("var",
				Correspondencia.de("VAR"));
	}
	
	// Devuelve null si lex no es ninguna pal. reservada
	public static Integer get(String lex) {
		return TABLA.get(lex);
	}
	
}
