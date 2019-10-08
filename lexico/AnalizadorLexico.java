package lexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import control.Salida;
import errores.ErrorCadenaNoTerminada;
import errores.ErrorCharNoPer;
import errores.ErrorComentarioMalForm;
import errores.ErrorEnteroFueraDeRango;
import errores.GestorErrores;
import lexico.auxiliares.Accion;
import lexico.auxiliares.EstadoAccion;
import lexico.auxiliares.FilaTS;
import lexico.auxiliares.Token;

public class AnalizadorLexico {
	
	private static class MatrizTransicion{
		
		public static final int N_ESTADOS_NO_TERMINALES = 7;
		public static final int N_SIMBOLOS = 21;
		
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
			mat[0][UNDERSCORE] 	 = new EstadoAccion(0, Accion.ERR_CARACTER_NO_PERMITIDO);
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
			mat[0][EOF] 	 	 = new EstadoAccion(23, Accion.TERMINAR_EJECUCION);
			mat[0][RESTO_CARACT] = new EstadoAccion(0, Accion.ERR_CARACTER_NO_PERMITIDO);
		}
		
		private static void iniciarEstado1() {
			EstadoAccion irA7YGenerarPRoID = new EstadoAccion(7, Accion.GENERAR_PR_ID);
			EstadoAccion irA1YConcatenar = new EstadoAccion(1, Accion.CONCATENAR);
			
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
			mat[2][EOF		] 	 = irA8YGenerarEntero;
			mat[2][RESTO_CARACT] = irA8YGenerarEntero;
		}
		
		private static void iniciarEstado3() {
			EstadoAccion irA0YErrCadMalForm = new EstadoAccion(0, Accion.ERR_CADENA_NO_TERMINADA);
			EstadoAccion irA9YGenerarCadena = new EstadoAccion(9, Accion.GENERAR_CADENA);
			EstadoAccion irA3YConcatenar = new EstadoAccion(3, Accion.CONCATENAR);
			
			mat[3][DEL]   		 = irA3YConcatenar;
			mat[3][CR]    		 = irA0YErrCadMalForm;
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
			mat[3][EOF] 	 	 = irA0YErrCadMalForm;
			mat[3][RESTO_CARACT] = irA3YConcatenar;
		}
		
		private static void iniciarEstado4() {
			EstadoAccion irA11YGenerarPosDecr = new EstadoAccion(11, Accion.GENERAR_POS_DECREMENTO);
			EstadoAccion irA10YGenerarMenos = new EstadoAccion(10, Accion.GENERAR_MENOS);
			
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
			mat[4][EOF]		 	 = irA10YGenerarMenos;
			mat[4][RESTO_CARACT] = irA10YGenerarMenos;
		}
		
		private static void iniciarEstado5() {
			EstadoAccion irA6YLeer = new EstadoAccion(6, Accion.LEER);
			EstadoAccion irA0YErrorComentario = new EstadoAccion(0, Accion.ERR_COMENTARIO_MAL_FORMADO);
			
			mat[5][DEL]   		 = irA0YErrorComentario;
			mat[5][CR]    		 = irA0YErrorComentario;
			mat[5][LETRA] 		 = irA0YErrorComentario;
			mat[5][DIGITO]   	 = irA0YErrorComentario;
			mat[5][UNDERSCORE] 	 = irA0YErrorComentario;
			mat[5][COMILLA] 	 = irA0YErrorComentario;
			mat[5][MENOS] 		 = irA0YErrorComentario;
			mat[5][BARRA] 		 = irA6YLeer;
			mat[5][IGUAL] 		 = irA0YErrorComentario;
			mat[5][MAS] 		 = irA0YErrorComentario;
			mat[5][MAYOR] 		 = irA0YErrorComentario;
			mat[5][MENOR] 		 = irA0YErrorComentario;
			mat[5][DISTINTO] 	 = irA0YErrorComentario;
			mat[5][COMA] 		 = irA0YErrorComentario;
			mat[5][PUNTO_COMA] 	 = irA0YErrorComentario;
			mat[5][PAR_AB] 		 = irA0YErrorComentario;
			mat[5][PAR_CE] 		 = irA0YErrorComentario;
			mat[5][LLAV_AB] 	 = irA0YErrorComentario;
			mat[5][LLAVE_CE] 	 = irA0YErrorComentario;
			mat[5][EOF] 	 	 = irA0YErrorComentario;
			mat[5][RESTO_CARACT] = irA0YErrorComentario;
		}
		
		private static void iniciarEstado6() {
			EstadoAccion irA0YLeer = new EstadoAccion(0, Accion.LEER);
			EstadoAccion irA6YLeer = new EstadoAccion(6, Accion.LEER);
			EstadoAccion irA23YTerminar = new EstadoAccion(23,Accion.TERMINAR_EJECUCION);
			
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
			mat[6][EOF]		 	 = irA23YTerminar;
			mat[6][RESTO_CARACT] = irA6YLeer;
		}
		
		// Dado un caracter devuelve el correspondiente indice de la matriz al que hace
		// referencia. Ejemplo, dado '2' deberia de devolver DIGITO, que es el indice 3
		public static int getPosicion(int ch) {
			
			if( ch == -1 )
				return EOF;
			
			char c = (char) ch;
			
			if( Pattern.matches("[a-z|A-Z]",c+"") )
				return LETRA;
			
			else if( Pattern.matches("[0-9]",c+"") )
				return DIGITO;
			
			switch(c) {
			
			// Incluimos '\r' como delimitador para que el compilador
			// se los salte. Esto para evitar problemas con aquello de
			// que ficheros editados en Windows se codifican de modo que
			// los saltos de linea se traducen en una secuencia de \r\n
			// en vez de solo \n
			case '\r':
			case ' ':
			case '\t':
				return DEL;
			case '\n':
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
		public static EstadoAccion getNextTrans() {
			return mat[estadoActual][getPosicion(chLeido)];
		}
		
	}
	
	private static BufferedReader ficheroFuente;
	private static Salida salidaLex;
	private static int lineaActual;
	
	public static void iniciar(File fuente, File ficheroSalidaLex) {
		
		try { ficheroFuente = new BufferedReader(new FileReader(fuente)); }
		catch( FileNotFoundException e ) { /*Ya se ha controlado esta situacion*/ }
		salidaLex = new Salida(ficheroSalidaLex);
		lineaActual=1;
		
		// Iniciamos los elementos del lexico necesarios		
		Correspondencia.iniciar();
		MatrizTransicion.iniciar();
		TablaPR.iniciar();
				
		// Empezamos: leemos el primer caracter del fichero
		leer();
		
		// Generamos todos los tokens
		genToken();
	}
	
	// Lo declaramos como int para que pueda almacenar el -1 del Reader, que
	// es mandado cuando se alcanza el final del fichero (eof)
	private static int chLeido;
	
	private static void leer() {
		
		try { chLeido = ficheroFuente.read(); } 
		catch(IOException e) { e.printStackTrace(); }
		
		if( (char) chLeido == '\n' )
			lineaActual++;
	}
	
	private static boolean finFichero = false;
	private static int estadoActual = 0;
	
	private static void genToken(){
		estadoActual=0;
		
		// Variables auxiliares
		Integer num=null;
		String lex="";				
		Integer posicion = null;		// Cuando busquemos en TS o TPR guardaremos la respuesta aqu�
		EstadoAccion entrada = null;	// Entrada de la matriz de transiciones que indica el sig. mov
		Accion toDo = null;  			// accion semantica a realizar en cada transicion
		char chActual = '?'; 			// Haremos cast a chLeido para manejar el caracter

		// Los estados no terminales son 0,1,2,3,4,5,6
		while( estadoActual <=6 ) {
			
			chActual = (char) chLeido;
			
			entrada = MatrizTransicion.getNextTrans();	
			estadoActual = entrada.estado();
			toDo = entrada.accion();
			
			switch( toDo ){
			
			case LEER: 
				leer();
				break;
				
			case CONCATENAR:
				lex+=chActual;
				leer();
				break;
				
			case DECLARAR_NUM:
				num = Integer.parseInt(chActual+"");
				leer();
				break;
				
			case INCREMENTAR_NUM:
				num = num*10 + Integer.parseInt(chActual+"");
				leer();
				break;
				
			case GENERAR_PR_ID:
				
				posicion = TablaPR.get(lex);
				if( posicion !=null ) {
					salidaLex.escribir(new 
							Token(Correspondencia.de("PR"),posicion).toString());
				}
				
				// Si no es una PR entonces es una variable
				else{
					
					// Buscamos a ver si ya está en la TS
					posicion = TablaS.get(lex);
					
					// Si no esta añadimos la variable a la tabla
					if(posicion == null) 
						posicion = TablaS.insert(new FilaTS(lex));
					
					// En cualquier caso se genera el token de variable
					salidaLex.escribir(new 
							Token(Correspondencia.de("ID"),posicion).toString());	
				}
				// Liberamos el lexema
				lex = "";
				break;
								
			case GENERAR_ENTERO:
				if(num<=Math.pow(2, 16)-1)
					salidaLex.escribir(new 
							Token(Correspondencia.de("ENT"),num).toString());
				else
					GestorErrores.reportar(new
							ErrorEnteroFueraDeRango(num,lineaActual));
				
				// Liberamos num
				num = null;
				break;
				
			case GENERAR_CADENA: 
				leer();
				salidaLex.escribir(new
						Token(Correspondencia.de("CAD"),"\""+lex+"\"").toString());
				
				// Liberamos el lexema
				lex="";
				break;
				
			case GENERAR_MENOS:
				salidaLex.escribir(new
						Token(Correspondencia.de("-"),"").toString());
				break;
				
			case GENERAR_POS_DECREMENTO: 
				leer();
				salidaLex.escribir(new
						Token(Correspondencia.de("--"),"").toString());
				break;
				
			case GENERAR_IGUAL:
				leer();
				salidaLex.escribir(new
						Token(Correspondencia.de("="),"").toString());
				break;
				
			case GENERAR_MAS:
				leer();
				salidaLex.escribir(new
						Token(Correspondencia.de("+"),"").toString());
				break;
				
			case GENERAR_MAYOR:
				leer();
				salidaLex.escribir(new
						Token(Correspondencia.de(">"),"").toString());
				break;
				
			case GENERAR_MENOR:
				leer();
				salidaLex.escribir(new
						Token(Correspondencia.de("<"),"").toString());
				break;
				
			case GENERAR_DISTINTO:
				leer();
				salidaLex.escribir(new
						Token(Correspondencia.de("!"),"").toString());
				break;
				
			case GENERAR_COMA:
				leer();
				salidaLex.escribir(new
						Token(Correspondencia.de(","),"").toString());
				break;
				
			case GENERAR_PUNTO_COMA:
				leer();
				salidaLex.escribir(new
						Token(Correspondencia.de(";"),"").toString());
				break;
				
			case GENERAR_PARENTESIS_AB:
				leer();
				salidaLex.escribir(new
						Token(Correspondencia.de("("),"").toString());
				break;
				
			case GENERAR_PARENTESIS_CE:
				leer();
				salidaLex.escribir(new
						Token(Correspondencia.de(")"),"").toString());
				break;
				
			case GENERAR_LLAVE_AB:
				leer();
				salidaLex.escribir(new
						Token(Correspondencia.de("{"),"").toString());
				break;
				
			case GENERAR_LLAVE_CE:
				leer();
				salidaLex.escribir(new
						Token(Correspondencia.de("}"),"").toString());
				break;
				
			// En los errores tambien leemos (basicamente pq si no lo hacemos entramos
			// en un bucle infinito. La otra opción es parar el Alex pero queremos que
			// continue para que muestre todos los errores lexicos del fichero)
			case ERR_CARACTER_NO_PERMITIDO:
				GestorErrores.reportar(new 
						ErrorCharNoPer(chActual,lineaActual));	
				leer();
				break;
				
			case ERR_COMENTARIO_MAL_FORMADO:
				GestorErrores.reportar(new
						ErrorComentarioMalForm(lineaActual));
				leer();
				break;
				
			
			case ERR_CADENA_NO_TERMINADA:
				GestorErrores.reportar(new
						ErrorCadenaNoTerminada(lex,lineaActual));
				break;
				
			case TERMINAR_EJECUCION:
				finFichero = true;
				try { ficheroFuente.close(); }
				catch(IOException e) { e.printStackTrace(); }
				break;
				
			default:
				System.out.println("Error transitando");
				return;
			} //EOSwitch
			
		} // EOWhile
		
		// De momento en bucle hasta que se termine de leer el fichero
		if( finFichero )
			System.exit(0);	
		genToken();
	}

}
