package lexico;

import java.util.HashMap;
import java.util.Map;

// Permite conocer la correspondencia entre el codigo
// de token y el lexema asocicado al token
public class Corresp {
	
	public static final int BOOLEAN		= 1;
	public static final int ELSE		= 2;
	public static final int FUNCTION	= 3;
	public static final int IF			= 4;
	public static final int INPUT		= 5;
	public static final int INT			= 6;
	public static final int PRINT		= 7;
	public static final int RETURN		= 8;
	public static final int STRING		= 9;
	public static final int VAR			= 10;
	public static final int AUTO_DEC	= 11;
	public static final int ENTERO		= 12;
	public static final int CADENA		= 13;
	public static final int ID			= 14;
	public static final int IGUAL		= 15;
	public static final int COMA		= 16;
	public static final int PUNTO_COMA 	= 17;
	public static final int PAR_AB		= 18;
	public static final int PAR_CE		= 19;
	public static final int LLA_AB		= 20;
	public static final int LLA_CE		= 21;
	public static final int MAS			= 22;
	public static final int MENOS		= 23;
	public static final int NEGACION	= 24;
	public static final int MENOR		= 25;
	public static final int MAYOR		= 26;
		
	private static final Map<String,Integer> LEX_TO_COD = new
			HashMap<String,Integer>();
	
	
	public static void iniciar() {
		LEX_TO_COD.put("boolean", BOOLEAN);		
		LEX_TO_COD.put("else", ELSE);		
		LEX_TO_COD.put("function", FUNCTION);
		LEX_TO_COD.put("if", IF);
		LEX_TO_COD.put("input", INPUT);
		LEX_TO_COD.put("int", INT);
		LEX_TO_COD.put("print", PRINT);
		LEX_TO_COD.put("return", RETURN);
		LEX_TO_COD.put("string", STRING);
		LEX_TO_COD.put("var", VAR);
		LEX_TO_COD.put("--", AUTO_DEC);
		LEX_TO_COD.put("=", IGUAL);
		LEX_TO_COD.put(",", COMA);
		LEX_TO_COD.put(";", PUNTO_COMA);
		LEX_TO_COD.put("(", PAR_AB);
		LEX_TO_COD.put(")", PAR_CE);
		LEX_TO_COD.put("{", LLA_AB);
		LEX_TO_COD.put("}", LLA_CE);
		LEX_TO_COD.put("+", MAS);
		LEX_TO_COD.put("-", MENOS);
		LEX_TO_COD.put("!", NEGACION);
		LEX_TO_COD.put("<", MENOR);
		LEX_TO_COD.put(">", MAYOR);
	}
	
	
	public static Integer de( String lex ) {
		return LEX_TO_COD.get(lex);
	}
	
	public static String friendlyDe( int cod ) {
		switch(cod) {
		case BOOLEAN:
			return "BOOL";
		case ELSE:
			return "ELSE";
		case FUNCTION:
			return "FUNC";
		case IF:
			return "IF";
		case INPUT:
			return "INPUT";
		case INT:
			return "INT";
		case PRINT:
			return "PRINT";
		case RETURN:
			return "RET";
		case STRING:
			return "STR";
		case VAR:
			return "VAR";
		case AUTO_DEC:
			return "AUT_DEC";
		case ENTERO:
			return "ENT";
		case CADENA:
			return "CAD";
		case ID:
			return "ID";
		case IGUAL:
			return "IGUAL";
		case COMA:
			return "COMA";
		case PUNTO_COMA:
			return "PUNT_COM";
		case PAR_AB:
			return "PAR_AB";
		case PAR_CE:
			return "PAR_CE";
		case LLA_AB:
			return "LLA_AB";
		case LLA_CE:
			return "LLA_CE";
		case MAS:
			return "MAS";
		case MENOS:
			return "MENOS";
		case NEGACION:
			return "NEGAC";
		case MENOR:
			return "MENOR";
		case MAYOR:
			return "MAYOR";	
		default:
			return "ERROR, CORRESPONDENCIA NO ENCONTRADA";
		}
	}
	
	// Si es una palabra reservada devuelve su código, en caso
	// contrario devuelve null
	public static Integer getPalRes(String lex) {
		Integer cod = Corresp.de( lex );
		
		if( cod == null )
			return null;
		
		switch( cod ) {
		
		case BOOLEAN:
			return BOOLEAN;
		case ELSE:
			return ELSE;
		case FUNCTION:
			return FUNCTION;
		case IF:
			return IF;
		case INPUT:
			return INPUT;
		case INT:
			return INT;
		case PRINT:
			return PRINT;
		case RETURN:
			return RETURN;
		case STRING: 
			return STRING;
		case VAR:
			return VAR;
		default:
			return null;
		}
	}	
}
