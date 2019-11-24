package lexico.matrans;

import java.util.regex.Pattern;

public class MatrizTransicion {
	public static final int N_ESTADOS_NO_TERMINALES = 7;
	public static final int N_SIMBOLOS = 21;
	
	// Estos son los simbolos que maneja la matriz
	// (representan las columnas de la misma)
	public static final int DEL 		 = 0;
	public static final int CR 			 = 1;
	public static final int LETRA 		 = 2;
	public static final int DIGITO 		 = 3;
	public static final int UNDERSCORE 	 = 4;
	public static final int COMILLA 	 = 5;
	public static final int MENOS	 	 = 6;
	public static final int BARRA 		 = 7;
	public static final int IGUAL 		 = 8;
	public static final int MAS 		 = 9;
	public static final int MAYOR 		 = 10;
	public static final int MENOR 		 = 11;
	public static final int DISTINTO 	 = 12;
	public static final int COMA 		 = 13;
	public static final int PUNTO_COMA 	 = 14;
	public static final int PAR_AB 		 = 15;
	public static final int PAR_CE 		 = 16;
	public static final int LLAV_AB		 = 17;
	public static final int LLAVE_CE 	 = 18;
	public static final int EOF 		 = 19;
	public static final int RESTO_CARACT = 20;
	
	
	public static final EntradaMatTrans[][] mat = 
			new EntradaMatTrans[N_ESTADOS_NO_TERMINALES][N_SIMBOLOS];
	
	public static void iniciar() {			
		iniciarEstado0();
		iniciarEstado1();
		iniciarEstado2();
		iniciarEstado3();
		iniciarEstado4();
		iniciarEstado5();
		iniciarEstado6();			
	}
	
	private static void iniciarEstado0() {			
		mat[0][DEL]   		 = new EntradaMatTrans(0, Accion.LEER);
		mat[0][CR]    		 = new EntradaMatTrans(0, Accion.LEER);
		mat[0][LETRA] 		 = new EntradaMatTrans(1, Accion.CONCATENAR);
		mat[0][DIGITO]   	 = new EntradaMatTrans(2, Accion.DECLARAR_NUM);
		mat[0][UNDERSCORE] 	 = new EntradaMatTrans(23, Accion.ERR_CARACTER_NO_PERMITIDO);
		mat[0][COMILLA] 	 = new EntradaMatTrans(3, Accion.LEER);
		mat[0][MENOS] 		 = new EntradaMatTrans(4, Accion.LEER);
		mat[0][BARRA] 		 = new EntradaMatTrans(5, Accion.LEER);
		mat[0][IGUAL] 		 = new EntradaMatTrans(12, Accion.GENERAR_IGUAL);
		mat[0][MAS] 		 = new EntradaMatTrans(13, Accion.GENERAR_MAS);
		mat[0][MAYOR] 		 = new EntradaMatTrans(14, Accion.GENERAR_MAYOR);
		mat[0][MENOR] 		 = new EntradaMatTrans(15, Accion.GENERAR_MENOR);
		mat[0][DISTINTO] 	 = new EntradaMatTrans(16, Accion.GENERAR_NEGACION);
		mat[0][COMA] 		 = new EntradaMatTrans(17, Accion.GENERAR_COMA);
		mat[0][PUNTO_COMA] 	 = new EntradaMatTrans(18, Accion.GENERAR_PUNTO_COMA);
		mat[0][PAR_AB] 		 = new EntradaMatTrans(19, Accion.GENERAR_PAR_AB);
		mat[0][PAR_CE] 		 = new EntradaMatTrans(20, Accion.GENERAR_PAR_CE);
		mat[0][LLAV_AB] 	 = new EntradaMatTrans(21, Accion.GENERAR_LLA_AB);
		mat[0][LLAVE_CE] 	 = new EntradaMatTrans(22, Accion.GENERAR_LLA_CE);
		mat[0][EOF] 	 	 = new EntradaMatTrans(23, Accion.DEVOLVER_EOF);
		mat[0][RESTO_CARACT] = new EntradaMatTrans(23, Accion.ERR_CARACTER_NO_PERMITIDO);
	}
	
	private static void iniciarEstado1() {
		EntradaMatTrans irA7YGenerarPRoID = new EntradaMatTrans(7, Accion.GENERAR_PR_ID);
		EntradaMatTrans irA1YConcatenar = new EntradaMatTrans(1, Accion.CONCATENAR);
		
		mat[1][DEL]   		 = irA7YGenerarPRoID;
		mat[1][CR]    		 = irA7YGenerarPRoID;
		mat[1][LETRA] 		 = irA1YConcatenar;
		mat[1][DIGITO]   	 = irA1YConcatenar;
		mat[1][UNDERSCORE] 	 = irA1YConcatenar;
		mat[1][COMILLA] 	 = irA7YGenerarPRoID;
		mat[1][MENOS] 		 = irA7YGenerarPRoID;
		mat[1][BARRA] 		 = irA7YGenerarPRoID;
		mat[1][IGUAL] 		 = irA7YGenerarPRoID;
		mat[1][MAS] 		 = irA7YGenerarPRoID;
		mat[1][MAYOR] 		 = irA7YGenerarPRoID;
		mat[1][MENOR] 		 = irA7YGenerarPRoID;
		mat[1][DISTINTO] 	 = irA7YGenerarPRoID;
		mat[1][COMA] 		 = irA7YGenerarPRoID;
		mat[1][PUNTO_COMA] 	 = irA7YGenerarPRoID;
		mat[1][PAR_AB] 		 = irA7YGenerarPRoID;
		mat[1][PAR_CE] 		 = irA7YGenerarPRoID;
		mat[1][LLAV_AB] 	 = irA7YGenerarPRoID;
		mat[1][LLAVE_CE] 	 = irA7YGenerarPRoID;
		mat[1][EOF		] 	 = irA7YGenerarPRoID;
		mat[1][RESTO_CARACT] = irA7YGenerarPRoID;
		
	}
	
	private static void iniciarEstado2() {
		EntradaMatTrans irA8YGenerarEntero = new EntradaMatTrans(8, Accion.GENERAR_ENTERO);
		
		mat[2][DEL]   		 = irA8YGenerarEntero;
		mat[2][CR]    		 = irA8YGenerarEntero;
		mat[2][LETRA] 		 = irA8YGenerarEntero;
		mat[2][DIGITO]   	 = new EntradaMatTrans(2, Accion.INCREMENTAR_NUM);
		mat[2][UNDERSCORE] 	 = irA8YGenerarEntero;
		mat[2][COMILLA] 	 = irA8YGenerarEntero;
		mat[2][MENOS] 		 = irA8YGenerarEntero;
		mat[2][BARRA] 		 = irA8YGenerarEntero;
		mat[2][IGUAL] 		 = irA8YGenerarEntero;
		mat[2][MAS] 		 = irA8YGenerarEntero;
		mat[2][MAYOR] 		 = irA8YGenerarEntero;
		mat[2][MENOR] 		 = irA8YGenerarEntero;
		mat[2][DISTINTO] 	 = irA8YGenerarEntero;
		mat[2][COMA] 		 = irA8YGenerarEntero;
		mat[2][PUNTO_COMA] 	 = irA8YGenerarEntero;
		mat[2][PAR_AB] 		 = irA8YGenerarEntero;
		mat[2][PAR_CE] 		 = irA8YGenerarEntero;
		mat[2][LLAV_AB] 	 = irA8YGenerarEntero;
		mat[2][LLAVE_CE] 	 = irA8YGenerarEntero;
		mat[2][EOF		] 	 = irA8YGenerarEntero;
		mat[2][RESTO_CARACT] = irA8YGenerarEntero;
	}
	
	private static void iniciarEstado3() {
		EntradaMatTrans irA3YConcatenar = new EntradaMatTrans(3, Accion.CONCATENAR);
		
		mat[3][DEL]   		 = irA3YConcatenar;
		mat[3][CR]    		 = new EntradaMatTrans(23,Accion.ERR_CADENA_EN_VARIAS_LINEAS);
		mat[3][LETRA] 		 = irA3YConcatenar;
		mat[3][DIGITO]   	 = irA3YConcatenar;
		mat[3][UNDERSCORE] 	 = irA3YConcatenar;
		mat[3][COMILLA] 	 = new EntradaMatTrans(9, Accion.GENERAR_CADENA);
		mat[3][MENOS] 		 = irA3YConcatenar;
		mat[3][BARRA] 		 = irA3YConcatenar;
		mat[3][IGUAL] 		 = irA3YConcatenar;
		mat[3][MAS] 		 = irA3YConcatenar;
		mat[3][MAYOR] 		 = irA3YConcatenar;
		mat[3][MENOR] 		 = irA3YConcatenar;
		mat[3][DISTINTO] 	 = irA3YConcatenar;
		mat[3][COMA] 		 = irA3YConcatenar;
		mat[3][PUNTO_COMA] 	 = irA3YConcatenar;
		mat[3][PAR_AB] 		 = irA3YConcatenar;
		mat[3][PAR_CE] 		 = irA3YConcatenar;
		mat[3][LLAV_AB] 	 = irA3YConcatenar;
		mat[3][LLAVE_CE] 	 = irA3YConcatenar;
		mat[3][EOF] 	 	 = new EntradaMatTrans(23, Accion.ERR_CADENA_NO_TERMINADA);
		mat[3][RESTO_CARACT] = irA3YConcatenar;
	}
	
	private static void iniciarEstado4() {
		EntradaMatTrans irA10YGenerarMenos = new EntradaMatTrans(10, Accion.GENERAR_MENOS);
		
		mat[4][DEL]   		 = irA10YGenerarMenos;
		mat[4][CR]    		 = irA10YGenerarMenos;
		mat[4][LETRA] 		 = irA10YGenerarMenos;
		mat[4][DIGITO]   	 = irA10YGenerarMenos;
		mat[4][UNDERSCORE] 	 = irA10YGenerarMenos;
		mat[4][COMILLA] 	 = irA10YGenerarMenos;
		mat[4][MENOS] 		 = new EntradaMatTrans(11, Accion.GENERAR_AUTO_DEC);
		mat[4][BARRA] 		 = irA10YGenerarMenos;
		mat[4][IGUAL] 		 = irA10YGenerarMenos;
		mat[4][MAS] 		 = irA10YGenerarMenos;
		mat[4][MAYOR] 		 = irA10YGenerarMenos;
		mat[4][MENOR] 		 = irA10YGenerarMenos;
		mat[4][DISTINTO] 	 = irA10YGenerarMenos;
		mat[4][COMA] 		 = irA10YGenerarMenos;
		mat[4][PUNTO_COMA] 	 = irA10YGenerarMenos;
		mat[4][PAR_AB] 		 = irA10YGenerarMenos;
		mat[4][PAR_CE] 		 = irA10YGenerarMenos;
		mat[4][LLAV_AB] 	 = irA10YGenerarMenos;
		mat[4][LLAVE_CE] 	 = irA10YGenerarMenos;
		mat[4][EOF]		 	 = irA10YGenerarMenos;
		mat[4][RESTO_CARACT] = irA10YGenerarMenos;
	}
	
	private static void iniciarEstado5() {
		EntradaMatTrans irA23YErrorComentario = new EntradaMatTrans(23, Accion.ERR_COMENTARIO_MAL_FORMADO);
		
		mat[5][DEL]   		 = irA23YErrorComentario;
		mat[5][CR]    		 = irA23YErrorComentario;
		mat[5][LETRA] 		 = irA23YErrorComentario;
		mat[5][DIGITO]   	 = irA23YErrorComentario;
		mat[5][UNDERSCORE] 	 = irA23YErrorComentario;
		mat[5][COMILLA] 	 = irA23YErrorComentario;
		mat[5][MENOS] 		 = irA23YErrorComentario;
		mat[5][BARRA] 		 = new EntradaMatTrans(6, Accion.LEER);;
		mat[5][IGUAL] 		 = irA23YErrorComentario;
		mat[5][MAS] 		 = irA23YErrorComentario;
		mat[5][MAYOR] 		 = irA23YErrorComentario;
		mat[5][MENOR] 		 = irA23YErrorComentario;
		mat[5][DISTINTO] 	 = irA23YErrorComentario;
		mat[5][COMA] 		 = irA23YErrorComentario;
		mat[5][PUNTO_COMA] 	 = irA23YErrorComentario;
		mat[5][PAR_AB] 		 = irA23YErrorComentario;
		mat[5][PAR_CE] 		 = irA23YErrorComentario;
		mat[5][LLAV_AB] 	 = irA23YErrorComentario;
		mat[5][LLAVE_CE] 	 = irA23YErrorComentario;
		mat[5][EOF] 	 	 = irA23YErrorComentario;
		mat[5][RESTO_CARACT] = irA23YErrorComentario;
	}
	
	private static void iniciarEstado6() {
		EntradaMatTrans irA6YLeer = new EntradaMatTrans(6, Accion.LEER);
		
		mat[6][DEL]   		 = irA6YLeer;
		mat[6][CR]    		 = new EntradaMatTrans(0, Accion.LEER);
		mat[6][LETRA] 		 = irA6YLeer;
		mat[6][DIGITO]   	 = irA6YLeer;
		mat[6][UNDERSCORE] 	 = irA6YLeer;
		mat[6][COMILLA] 	 = irA6YLeer;
		mat[6][MENOS] 		 = irA6YLeer;
		mat[6][BARRA] 		 = irA6YLeer;
		mat[6][IGUAL] 		 = irA6YLeer;
		mat[6][MAS] 		 = irA6YLeer;
		mat[6][MAYOR] 		 = irA6YLeer;
		mat[6][MENOR] 		 = irA6YLeer;
		mat[6][DISTINTO] 	 = irA6YLeer;
		mat[6][COMA] 		 = irA6YLeer;
		mat[6][PUNTO_COMA] 	 = irA6YLeer;
		mat[6][PAR_AB] 		 = irA6YLeer;
		mat[6][PAR_CE] 		 = irA6YLeer;
		mat[6][LLAV_AB] 	 = irA6YLeer;
		mat[6][LLAVE_CE] 	 = irA6YLeer;
		mat[6][EOF]			 = new EntradaMatTrans(23,Accion.DEVOLVER_EOF);
		mat[6][RESTO_CARACT] = irA6YLeer;
	}
	
	// Dado un caracter devuelve el correspondiente indice de la matriz al que hace
	// referencia. Ejemplo: dado '2' deberia de devolver DIGITO, que es el indice 3
	public static int indiceMatriz(int ch) {
			
		if( ch == -1 )
			return EOF;
		
		char c = (char) ch;
		
		if( Pattern.matches("[a-z|A-Z]",c+"") )
			return LETRA;
		
		else if( Pattern.matches("[0-9]",c+"") )
			return DIGITO;
		
		switch(c) {
		case ' ':
		case '\t':
			return DEL;
		case '\n':
		case '\r':
			return CR;
		case '_':
			return UNDERSCORE;
		case '\'':
			return COMILLA;
		case '-':
			return MENOS;
		case '/':
			return BARRA;
		case '=':
			return IGUAL;
		case '+':
			return MAS;
		case '>':
			return MAYOR;
		case '<':
			return MENOR;
		case '!':
			return DISTINTO;
		case ',':
			return COMA;
		case ';':
			return PUNTO_COMA;
		case '(':
			return PAR_AB;
		case ')':
			return PAR_CE;
		case '{':
			return LLAV_AB;
		case '}':
			return LLAVE_CE;
		default:
			return RESTO_CARACT;
		}
	}
	
	// Devuelve la siguiente transicion a ejecutar
	public static EntradaMatTrans getNextTrans(int estadoActual, int charActual) {
		return mat[estadoActual][ indiceMatriz(charActual) ];
	}
	
}
