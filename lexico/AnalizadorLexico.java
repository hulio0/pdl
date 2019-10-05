package lexico;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import lexico.auxiliares.Accion;
import lexico.auxiliares.EstadoAccion;

public class AnalizadorLexico {
	
	private static class MatrizTransicion{
		
		public static final int N_ESTADOS_NO_TERMINALES = 7;
		public static final int N_SIMBOLOS = 20;
		
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
		public static final int RESTO_CARACT = 19;
		
		public static final EstadoAccion[][] mat = 
				new EstadoAccion[N_ESTADOS_NO_TERMINALES][N_SIMBOLOS];
		
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
			mat[0][DEL]   		 = new EstadoAccion(0, Accion.LEER);
			mat[0][CR]    		 = new EstadoAccion(0, Accion.LEER);
			mat[0][LETRA] 		 = new EstadoAccion(1, Accion.CONCATENAR);
			mat[0][DIGITO]   	 = new EstadoAccion(2, Accion.DECLARAR_NUM);
			mat[0][UNDERSCORE] 	 = new EstadoAccion(0, Accion.ERR_CARACTER_NO_RECONOCIDO);
			mat[0][COMILLA] 	 = new EstadoAccion(3, Accion.LEER);
			mat[0][MENOS] 		 = new EstadoAccion(4, Accion.LEER);
			mat[0][BARRA] 		 = new EstadoAccion(5, Accion.LEER);
			mat[0][IGUAL] 		 = new EstadoAccion(12, Accion.GENERAR_IGUAL);
			mat[0][MAS] 		 = new EstadoAccion(13, Accion.GENERAR_MAS);
			mat[0][MAYOR] 		 = new EstadoAccion(14, Accion.GENERAR_MAYOR);
			mat[0][MENOR] 		 = new EstadoAccion(15, Accion.GENERAR_MENOR);
			mat[0][DISTINTO] 	 = new EstadoAccion(16, Accion.GENERAR_DISTINTO);
			mat[0][COMA] 		 = new EstadoAccion(17, Accion.GENERAR_COMA);
			mat[0][PUNTO_COMA] 	 = new EstadoAccion(18, Accion.GENERAR_PUNTO_COMA);
			mat[0][PAR_AB] 		 = new EstadoAccion(19, Accion.GENERAR_PARENTESIS_AB);
			mat[0][PAR_CE] 		 = new EstadoAccion(20, Accion.GENERAR_PARENTESIS_CE);
			mat[0][LLAV_AB] 	 = new EstadoAccion(21, Accion.GENERAR_LLAVE_AB);
			mat[0][LLAVE_CE] 	 = new EstadoAccion(22, Accion.GENERAR_LLAVE_CE);
			mat[0][RESTO_CARACT] = new EstadoAccion(0, Accion.ERR_CARACTER_NO_RECONOCIDO);
		}
		
		private static void iniciarEstado1() {
			EstadoAccion irA7YGenerarEntero = new EstadoAccion(7, Accion.GENERAR_ENTERO);
			EstadoAccion irA1YConcatenar = new EstadoAccion(1, Accion.CONCATENAR);
			
			mat[1][DEL]   		 = irA7YGenerarEntero;
			mat[1][CR]    		 = irA7YGenerarEntero;
			mat[1][LETRA] 		 = irA1YConcatenar;
			mat[1][DIGITO]   	 = irA1YConcatenar;
			mat[1][UNDERSCORE] 	 = irA1YConcatenar;
			mat[1][COMILLA] 	 = irA7YGenerarEntero;
			mat[1][MENOS] 		 = irA7YGenerarEntero;
			mat[1][BARRA] 		 = irA7YGenerarEntero;
			mat[1][IGUAL] 		 = irA7YGenerarEntero;
			mat[1][MAS] 		 = irA7YGenerarEntero;
			mat[1][MAYOR] 		 = irA7YGenerarEntero;
			mat[1][MENOR] 		 = irA7YGenerarEntero;
			mat[1][DISTINTO] 	 = irA7YGenerarEntero;
			mat[1][COMA] 		 = irA7YGenerarEntero;
			mat[1][PUNTO_COMA] 	 = irA7YGenerarEntero;
			mat[1][PAR_AB] 		 = irA7YGenerarEntero;
			mat[1][PAR_CE] 		 = irA7YGenerarEntero;
			mat[1][LLAV_AB] 	 = irA7YGenerarEntero;
			mat[1][LLAVE_CE] 	 = irA7YGenerarEntero;
			mat[1][RESTO_CARACT] = irA7YGenerarEntero;
			
		}
		
		private static void iniciarEstado2() {
			EstadoAccion irA8YGenerarEntero = new EstadoAccion(8, Accion.GENERAR_ENTERO);
			EstadoAccion irA2EIncrementarNum = new EstadoAccion(2, Accion.INCREMENTAR_NUM);
			
			mat[2][DEL]   		 = irA8YGenerarEntero;
			mat[2][CR]    		 = irA8YGenerarEntero;
			mat[2][LETRA] 		 = irA8YGenerarEntero;
			mat[2][DIGITO]   	 = irA2EIncrementarNum;
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
			mat[2][RESTO_CARACT] = irA8YGenerarEntero;
		}
		
		private static void iniciarEstado3() {
			EstadoAccion irA9YGenerarCadena = new EstadoAccion(9, Accion.GENERAR_CADENA);
			EstadoAccion irA3YConcatenar = new EstadoAccion(3, Accion.CONCATENAR);
			
			mat[3][DEL]   		 = irA3YConcatenar;
			mat[3][CR]    		 = irA3YConcatenar;
			mat[3][LETRA] 		 = irA3YConcatenar;
			mat[3][DIGITO]   	 = irA3YConcatenar;
			mat[3][UNDERSCORE] 	 = irA3YConcatenar;
			mat[3][COMILLA] 	 = irA9YGenerarCadena;
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
			mat[3][RESTO_CARACT] = irA3YConcatenar;
		}
		
		private static void iniciarEstado4() {
			EstadoAccion irA11YGenerarPosDecr = new EstadoAccion(11, Accion.GENERAR_POS_DECREMENTO);
			EstadoAccion irA10YGenerarMenos = new EstadoAccion(3, Accion.GENERAR_MENOS);
			
			mat[4][DEL]   		 = irA10YGenerarMenos;
			mat[4][CR]    		 = irA10YGenerarMenos;
			mat[4][LETRA] 		 = irA10YGenerarMenos;
			mat[4][DIGITO]   	 = irA10YGenerarMenos;
			mat[4][UNDERSCORE] 	 = irA10YGenerarMenos;
			mat[4][COMILLA] 	 = irA10YGenerarMenos;
			mat[4][MENOS] 		 = irA11YGenerarPosDecr;
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
			mat[4][RESTO_CARACT] = irA10YGenerarMenos;
		}
		
		private static void iniciarEstado5() {
			EstadoAccion irA6YLeer = new EstadoAccion(6, Accion.LEER);
			EstadoAccion irA5YErrorComentario = new EstadoAccion(5, Accion.ERR_INICIO_COMENTARIO_IMCOMPLETO);
			
			mat[5][DEL]   		 = irA5YErrorComentario;
			mat[5][CR]    		 = irA5YErrorComentario;
			mat[5][LETRA] 		 = irA5YErrorComentario;
			mat[5][DIGITO]   	 = irA5YErrorComentario;
			mat[5][UNDERSCORE] 	 = irA5YErrorComentario;
			mat[5][COMILLA] 	 = irA5YErrorComentario;
			mat[5][MENOS] 		 = irA5YErrorComentario;
			mat[5][BARRA] 		 = irA6YLeer;
			mat[5][IGUAL] 		 = irA5YErrorComentario;
			mat[5][MAS] 		 = irA5YErrorComentario;
			mat[5][MAYOR] 		 = irA5YErrorComentario;
			mat[5][MENOR] 		 = irA5YErrorComentario;
			mat[5][DISTINTO] 	 = irA5YErrorComentario;
			mat[5][COMA] 		 = irA5YErrorComentario;
			mat[5][PUNTO_COMA] 	 = irA5YErrorComentario;
			mat[5][PAR_AB] 		 = irA5YErrorComentario;
			mat[5][PAR_CE] 		 = irA5YErrorComentario;
			mat[5][LLAV_AB] 	 = irA5YErrorComentario;
			mat[5][LLAVE_CE] 	 = irA5YErrorComentario;
			mat[5][RESTO_CARACT] = irA5YErrorComentario;
		}
		
		private static void iniciarEstado6() {
			EstadoAccion irA0YLeer = new EstadoAccion(0, Accion.LEER);
			EstadoAccion irA6YLeer = new EstadoAccion(6, Accion.LEER);
			
			mat[6][DEL]   		 = irA6YLeer;
			mat[6][CR]    		 = irA0YLeer;
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
			mat[6][RESTO_CARACT] = irA6YLeer;
		}
	
		public static void transitar() {
			
		}
	}
	
	private static RandomAccessFile fichFuente;
	private static long posicionActual;	
	private static char charActual;
	
	
	public static void iniciar(File ficheroFuente, File ficheroSalidaLex) {
		
		try { fichFuente = new RandomAccessFile(ficheroFuente, "r"); }
		catch( FileNotFoundException e ) { /*Esta situaci�n ya ha sido tratada en Control*/ }
		
		SalidaLexico.iniciar(ficheroSalidaLex);
		MatrizTransicion.iniciar();
		TablaPR.iniciar();
		CorrespLexCod.iniciar();
		
	}

}
