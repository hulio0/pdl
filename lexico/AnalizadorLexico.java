package lexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import control.Salida;
import errores.ErrorCadenaNoTerminada;
import errores.ErrorCadenaVariasLineas;
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
			mat[0][UNDERSCORE] 	 = new EstadoAccion(23, Accion.ERR_CARACTER_NO_PERMITIDO);
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
			mat[0][RESTO_CARACT] = new EstadoAccion(23, Accion.ERR_CARACTER_NO_PERMITIDO);
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
			
			mat[2][DEL]   		 = irA8YGenerarEntero;
			mat[2][CR]    		 = irA8YGenerarEntero;
			mat[2][LETRA] 		 = irA8YGenerarEntero;
			mat[2][DIGITO]   	 = new EstadoAccion(2, Accion.INCREMENTAR_NUM);
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
			EstadoAccion irA3YConcatenar = new EstadoAccion(3, Accion.CONCATENAR);
			
			mat[3][DEL]   		 = irA3YConcatenar;
			mat[3][CR]    		 = new EstadoAccion(23,Accion.ERR_CADENA_EN_VARIAS_LINEAS);
			mat[3][LETRA] 		 = irA3YConcatenar;
			mat[3][DIGITO]   	 = irA3YConcatenar;
			mat[3][UNDERSCORE] 	 = irA3YConcatenar;
			mat[3][COMILLA] 	 = new EstadoAccion(9, Accion.GENERAR_CADENA);
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
			mat[3][EOF] 	 	 = new EstadoAccion(23, Accion.ERR_CADENA_NO_TERMINADA);
			mat[3][RESTO_CARACT] = irA3YConcatenar;
		}
		
		private static void iniciarEstado4() {
			EstadoAccion irA10YGenerarMenos = new EstadoAccion(10, Accion.GENERAR_MENOS);
			
			mat[4][DEL]   		 = irA10YGenerarMenos;
			mat[4][CR]    		 = irA10YGenerarMenos;
			mat[4][LETRA] 		 = irA10YGenerarMenos;
			mat[4][DIGITO]   	 = irA10YGenerarMenos;
			mat[4][UNDERSCORE] 	 = irA10YGenerarMenos;
			mat[4][COMILLA] 	 = irA10YGenerarMenos;
			mat[4][MENOS] 		 = new EstadoAccion(11, Accion.GENERAR_POS_DECREMENTO);
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
			EstadoAccion irA23YErrorComentario = new EstadoAccion(23, Accion.ERR_COMENTARIO_MAL_FORMADO);
			
			mat[5][DEL]   		 = irA23YErrorComentario;
			mat[5][CR]    		 = irA23YErrorComentario;
			mat[5][LETRA] 		 = irA23YErrorComentario;
			mat[5][DIGITO]   	 = irA23YErrorComentario;
			mat[5][UNDERSCORE] 	 = irA23YErrorComentario;
			mat[5][COMILLA] 	 = irA23YErrorComentario;
			mat[5][MENOS] 		 = irA23YErrorComentario;
			mat[5][BARRA] 		 = new EstadoAccion(6, Accion.LEER);;
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
			EstadoAccion irA6YLeer = new EstadoAccion(6, Accion.LEER);
			
			mat[6][DEL]   		 = irA6YLeer;
			mat[6][CR]    		 = new EstadoAccion(0, Accion.LEER);
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
			mat[6][EOF]		 = new EstadoAccion(23,Accion.TERMINAR_EJECUCION);
			mat[6][RESTO_CARACT] = irA6YLeer;
		}
		
		// Dado un caracter devuelve el correspondiente indice de la matriz al que hace
		// referencia. Ejemplo, dado '2' deberia de devolver DIGITO, que es el indice 3
		public static int indiceMatriz(int ch) {
			
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
			return mat[estadoActual][indiceMatriz(chLeido)];
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
		variable = USO;
		
		// Iniciamos los elementos del lexico necesarios		
		Correspondencia.iniciar();
		MatrizTransicion.iniciar();
		TablaS.iniciar();
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
	
	private static boolean finAnalLexico = false;
	private static void terminarEjecucion() {
		finAnalLexico=true;
		
		try { ficheroFuente.close(); }
		catch(IOException e) { e.printStackTrace(); }
	}
	
	private static int estadoActual = 0;
	
	private static void genToken(){
		estadoActual=0;
		
		// Variables auxiliares
		Integer num=null;
		String lex="";				
		Integer posicion = null;		// Cuando busquemos en TS o TPR guardaremos la respuesta aquí
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
					
					// Si es int,string o boolean
					if(esPalDeclaracion(lex))
						variable = DECLARACION;
					
					salidaLex.escribir(new 
							Token(Correspondencia.de("PR"),posicion).toString());
				}
				
				// Si no es una PR entonces es una variable
				else{
					
					// Buscamos en el scope actual si ya está la variable
					posicion = TablaS.currentScopeGet(lex);
					
					switch( variable ) {
					
					case DECLARACION:
						
						if( posicion!=null )
							System.out.println("Error var ya declarada en " +lineaActual +" " +lex);
						
						else
							posicion=TablaS.insert(lex);
						
						break;
						
					case USO:
					
						if( posicion!=null )
							System.out.println("todo ok, no hace falta hacer nada "+lineaActual+" "+lex);
						
						else {
							// Buscamos en todas las tablas yendo de más específico a la más general
							posicion=TablaS.get(lex);
							
							if( posicion == null )
								System.out.println("Error var no declarada "+lex);
							else
								System.out.println("todo ok, no hace falta hacer nada " +lex);
							
						}
						
						break;
					
					}
					
					// Buscamos a ver si ya estaba en la TS
					posicion = TablaS.get(lex);
					
					
					// Si no esta agregamos la variable a la tabla (TablaS se
					// encarga de crear la fila bien bien y tal)
					if(posicion == null) 
						posicion = TablaS.insert(lex);
					
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
				else {
					GestorErrores.reportar(new
							ErrorEnteroFueraDeRango(num,lineaActual));
					terminarEjecucion();
				}
				
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
				
				variable = USO;

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
				
				variable = USO;
				
				salidaLex.escribir(new
						Token(Correspondencia.de(","),"").toString());
				break;
				
			case GENERAR_PUNTO_COMA:
				leer();
				
				variable = USO;
				
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
				
				variable = USO;
				
				salidaLex.escribir(new
						Token(Correspondencia.de(")"),"").toString());
				break;
				
			case GENERAR_LLAVE_AB:
				leer();
				
				TablaS.abrirAmbito();
				
				salidaLex.escribir(new
						Token(Correspondencia.de("{"),"").toString());
				break;
				
			case GENERAR_LLAVE_CE:
				leer();
				
				TablaS.cerrarAmbito();
				
				salidaLex.escribir(new
						Token(Correspondencia.de("}"),"").toString());
				break;
				
				
			case ERR_CARACTER_NO_PERMITIDO:
				GestorErrores.reportar(new 
						ErrorCharNoPer(chActual,lineaActual));	
				terminarEjecucion();
				break;
				
			case ERR_COMENTARIO_MAL_FORMADO:
				GestorErrores.reportar(new
						ErrorComentarioMalForm(lineaActual));
				terminarEjecucion();
				break;
				
			
			case ERR_CADENA_NO_TERMINADA:
				GestorErrores.reportar(new
						ErrorCadenaNoTerminada(lex,lineaActual));
				terminarEjecucion();
				break;
				
			case ERR_CADENA_EN_VARIAS_LINEAS:
				GestorErrores.reportar(new
						ErrorCadenaVariasLineas(lex,lineaActual));
				terminarEjecucion();
				break;
				
			case TERMINAR_EJECUCION:
				terminarEjecucion();
				break;
				
			default:
				System.out.println("Error transitando");
				return;
			} //EOSwitch
			
		} // EOWhile
		
		// De momento en bucle hasta que se termine de leer el fichero
		if( finAnalLexico )
			System.exit(0);	
		genToken();
	}
	
	
	private static boolean esPalDeclaracion(String lex) {
		return lex.equals("int")||lex.equals("string")||lex.equals("boolean");
	}
	private static int variable;
	
	// Se pasa a DECLARACION cuando se lea int,string o boolean
	private static final int DECLARACION=0;
	
	// Se pasa a USO cuando se lea cualquier caracter que vaya después de una
	// declaración, es decir: paréntesis derecho, coma y punto y coma. 
	private static final int USO=1;

}
