package lexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import control.Salida;
import errores.GestorErrores;
import errores.err.ErrorCadenaNoTerminada;
import errores.err.ErrorCadenaVariasLineas;
import errores.err.ErrorCharNoPer;
import errores.err.ErrorComentarioMalForm;
import errores.err.ErrorEnteroFueraDeRango;
import lexico.matrans.Accion;
import lexico.matrans.EntradaMatTrans;
import lexico.matrans.MatrizTransicion;
import lexico.tablapr.TablaPR;
import lexico.tablasim.TablaS;

public class AnalizadorLexico {
		
	private static BufferedReader ficheroFuente;
	private static Salida salidaLex;
	
	public static void iniciar(File fuente, File ficheroSalidaLex, File ficheroTS) {
		
		// Preparamos la lectura del fichero fuente
		try { ficheroFuente = new BufferedReader(new FileReader(fuente)); }
		catch( FileNotFoundException e ) { /*Ya se ha controlado esta situacion*/ }
		
		// Preparamos la salida del léxico
		salidaLex = new Salida(ficheroSalidaLex);
		
		// Iniciamos los sub-modulos
		Correspondencia.iniciar();
		TablaPR.iniciar();
		TablaS.iniciar(ficheroTS);
		MatrizTransicion.iniciar();
		
		// Empezamos: leemos el primer caracter del fichero
		leer();
	}
	
	// El caracter de cada lectura lo declaramos como int 
	// para que pueda almacenar el -1 del Reader, que es
	// mandado cuando se alcanza el final del fichero (eof)
	private static int chLeidoInt;
	
	// Cuando el caracter leído sea distinto de eof lo
	// castearemos y guardaremos en esta variable
	private static char chLeidoOK;
	
	// Llevamos una cuenta de las líneas para que el gestor
	// de errores dé mejores descripciones
	private static int lineaActual = 1;
	
	private static void leer() {
		
		try { chLeidoInt = ficheroFuente.read(); } 
		catch(IOException e) { e.printStackTrace(); }
		
		if( chLeidoInt != -1 ) {
			chLeidoOK = (char) chLeidoInt;
			
			if( chLeidoOK == '\n' )
				lineaActual++;
		}
		
	}
	
	// Flag que permite saber cuando parar de ejecutar
	private static boolean finAnalLexico = false;
	
	private static void terminarEjecucion() {
		finAnalLexico=true;
		
		try { ficheroFuente.close(); }
		catch(IOException e) { e.printStackTrace(); }
	}
	
	
	public static Token genToken(){
		
		Token res = null;
		
		// Variables auxiliares:
		
		Integer num=null;
		String lex="";	
		Integer pos = null;	   // Cuando busquemos en TS o TPR guardaremos la respuesta aquí
		
		
		EntradaMatTrans entrada = null;
		int estadoActual=0;
		Accion toDo = null;  			

		// Los estados no terminales son 0,1,..,6
		while( estadoActual <=6 ) {
			
			entrada = MatrizTransicion.getNextTrans(estadoActual,chLeidoInt);	
			  estadoActual = entrada.estado();
			  toDo = entrada.accion();
			
			switch( toDo ){
			
			case LEER: 
				leer();
				break;
				
			case CONCATENAR:
				lex+=chLeidoOK;
				leer();
				break;
				
			case DECLARAR_NUM:
				num = valor(chLeidoOK);
				leer();
				break;
				
			case INCREMENTAR_NUM:
				num = num*10 + valor(chLeidoOK);
				leer();
				break;
				
			case GENERAR_PR_ID:
				
				pos = TablaPR.get(lex);
				if( pos !=null ) {
					
					res = new Token(pos,"");
					
					if( lex.equals("function") )
						functionLeido = true;
					
					else if( lex.equals("if") || lex.equals("else") )
						ifElseLeido = true;
						
						
					if( esPalDeclaracion(lex) )
						var=DEC;
					
				}
				
				// Si no es una PR entonces es una variable
				else{
					System.out.println(lex);
					pos = TablaS.currentScopeGet(lex);
					
					if( var == DEC ) {
						if(pos!=null) { System.out.println("error var ya declarada"); terminarEjecucion(); }
						pos = TablaS.insert(lex);
					}
					
					else { // var == USO
						if( pos==null ) { // Buscamos en el resto de tablas
							pos = TablaS.get(lex);
							
							if(pos==null) { System.out.println("Error var no declarada"); terminarEjecucion(); }
						}
					}
					
					res = new Token(Correspondencia.de("ID"),pos);	
					
				}
				// Liberamos el lexema
				lex = "";
				break;
								
			case GENERAR_ENTERO:
				
				if(num<=Math.pow(2, 16)-1) {
					res = new Token(Correspondencia.de("ENTERO"),num);
				} else {
					GestorErrores.reportar(new
							ErrorEnteroFueraDeRango(num,lineaActual));
					terminarEjecucion();
				}
				
				// Liberamos num
				num = null;
				break;
				
			case GENERAR_CADENA: 
				leer();
				res = new Token(Correspondencia.de("CADENA"),"\""+lex+"\"");
				
				// Liberamos el lexema
				lex="";
				break;
				
			case GENERAR_MENOS:
				res = new Token(Correspondencia.de("MENOS"),"");
				break;
				
			case GENERAR_AUTO_DEC: 
				leer();
				res = new Token(Correspondencia.de("AUTO_DEC"),"");
				break;
				
			case GENERAR_IGUAL:
				leer();
				res = new Token(Correspondencia.de("IGUAL"),"");
				
				var = USO;
				
				break;
				
			case GENERAR_MAS:
				leer();
				res = new Token(Correspondencia.de("MAS"),"");
				break;
				
			case GENERAR_MAYOR:
				leer();
				res = new Token(Correspondencia.de("MAYOR"),"");
				break;
				
			case GENERAR_MENOR:
				leer();
				res = new Token(Correspondencia.de("MENOR"),"");
				break;
				
			case GENERAR_NEGACION:
				leer();
				res = new Token(Correspondencia.de("NEGACION"),"");
				break;
				
			case GENERAR_COMA:
				leer();		
				res = new Token(Correspondencia.de("COMA"),"");
				
				var = USO;
				
				break;
				
			case GENERAR_PUNTO_COMA:
				leer();
				res = new Token(Correspondencia.de("PUNTO_COMA"),"");
				
				var = USO;
				
				break;
				
			case GENERAR_PAR_AB:
				leer();
				res = new Token(Correspondencia.de("PAR_AB"),"");
				if( functionLeido ) {
					TablaS.abrirAmbito();
					functionLeido=false;
				}
				break;
				
			case GENERAR_PAR_CE:
				leer();
				res = new Token(Correspondencia.de("PAR_CE"),"");
				
				var = USO;
				
				break;
				
			case GENERAR_LLA_AB:
				leer();
				res = new Token(Correspondencia.de("LLA_AB"),"");
				break;
				
			case GENERAR_LLA_CE:
				leer();
				res = new Token(Correspondencia.de("LLA_CE"),"");
				
				if( ifElseLeido )
					ifElseLeido=false;
				else
					TablaS.cerrarAmbito();
				
				break;
				
				
			case ERR_CARACTER_NO_PERMITIDO:
				GestorErrores.reportar(new 
						ErrorCharNoPer(chLeidoOK,lineaActual));	
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
			} //EOSwitch
			
		} // EOWhile
		
		
		if( finAnalLexico ) {
			TablaS.cerrarAmbito(); // Para que se escriba la tabla global
			System.exit(0);	
		}
		
		
		// Antes de devolver el token lo escribimos en la salida
		if( res!=null ) salidaLex.escribir( res.toString() );
		
		return res;
	}
	
	private static int valor(char c) { return Integer.parseInt(c+""); }
	
	// Código de ámbitos
	private static final int USO = 0;
	private static final int DEC = 1;
	private static boolean functionLeido = false;
	private static boolean ifElseLeido = false;
	private static int var = USO;
	private static boolean esPalDeclaracion(String lex) {
		return lex.equals("int")||lex.equals("string")||lex.equals("boolean")||lex.equals("function");
	}

}
