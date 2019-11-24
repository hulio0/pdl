package lexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

import control.Control;
import control.Salida;
import errores.Error;
import errores.GestorErrores;
import errores.err.lex.ErrorCadenaNoTerminada;
import errores.err.lex.ErrorCadenaVariasLineas;
import errores.err.lex.ErrorCharNoPer;
import errores.err.lex.ErrorComentarioMalForm;
import errores.err.lex.ErrorEnteroFueraDeRango;
import lexico.matrans.Accion;
import lexico.matrans.EntradaMatTrans;
import lexico.matrans.MatrizTransicion;
import tablasim.TablaS;


public class AnalizadorLexico {
		
	private static BufferedReader ficheroFuente;
	private static Salida salidaLex;
	
	public static void iniciar(File fuente, File ficheroSalidaLex) {
		
		// Preparamos la lectura del fichero fuente
		try { ficheroFuente = new BufferedReader(new FileReader(fuente)); }
		catch( FileNotFoundException e ) { /*Ya se ha controlado esta situacion*/ }
		
		// Preparamos la salida del lexico
		salidaLex = new Salida(ficheroSalidaLex);
		
		// Iniciamos los sub-modulos
		Corresp.iniciar();
		MatrizTransicion.iniciar();
		
		// Leemos el primer caracter del fichero
		leer();
	}
	
	// El caracter de cada lectura lo declaramos como int 
	// para que pueda almacenar el -1 del Reader, que es
	// mandado cuando se alcanza el final del fichero (eof)
	private static int chLeidoInt;
	
	// Cuando el caracter leido sea distinto de eof lo castearemos
	// y guardaremos en esta variable para manejarlo mejor
	private static char chLeidoChar;
	
	// Llevamos una cuenta de las lineas para que el gestor
	// de errores de mejores descripciones
	private static int lineaActual = 1;
	
	private static void leer() {
		
		try { chLeidoInt = ficheroFuente.read(); } 
		catch(IOException e) { e.printStackTrace(); }
							
		if( chLeidoInt != -1 ) {
			chLeidoChar = (char) chLeidoInt;
			
			if( chLeidoChar == '\n' )
				lineaActual++;
		}
		
	}
	
	public static Token genToken(){
		Token res=null;
		
		// Variables auxiliares:
		
		BigInteger num=null;
		String lex="";	
		Integer pos = null;	   // Cuando busquemos en TS o TPR guardaremos la respuesta aqui
		
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
				lex+=chLeidoChar;
				leer();
				break;
				
			case DECLARAR_NUM:
				num = valor(chLeidoChar);
				leer();
				break;
				
			case INCREMENTAR_NUM:
				num = num.multiply( BigInteger.TEN );
				num = num.add( valor(chLeidoChar) );
				leer();
				break;
				
			case GENERAR_PR_ID:
				
				// Si es una PR, pos almacenará
				// el codigo de token correspondiente
				pos = Corresp.getPalRes(lex);
				if( pos !=null ) {
					res = new Token(pos);
				}
	
				// Si no es una PR entonces es un ID, pos
				// almacenará la posición en la TS de dicho id
				else{
					pos = TablaS.getLexico(lex);
					
					if( pos == null )
						pos = TablaS.insert(lex);
					
					res = new Token(Corresp.ID,pos);
				}
				// Liberamos el lexema
				lex = "";
				break;
								
			case GENERAR_ENTERO:				
				if(num.compareTo(MAX_ENTERO)<=0)
					res = new Token(Corresp.ENTERO,num);
				else		
					reportarError(new ErrorEnteroFueraDeRango(num));
				
				// Liberamos num
				num = null;
				break;
				
			case GENERAR_CADENA: 
				leer();
				res = new Token(Corresp.CADENA,"\""+lex+"\"");
				
				// Liberamos el lexema
				lex="";
				break;
				
			case GENERAR_MENOS:
				res = new Token(Corresp.MENOS);
				break;
				
			case GENERAR_AUTO_DEC: 
				leer();
				res = new Token(Corresp.AUTO_DEC);
				break;
				
			case GENERAR_IGUAL:
				leer();
				res = new Token(Corresp.IGUAL);				
				break;
				
			case GENERAR_MAS:
				leer();
				res = new Token(Corresp.MAS);
				break;
				
			case GENERAR_MAYOR:
				leer();
				res = new Token(Corresp.MAYOR);
				break;
				
			case GENERAR_MENOR:
				leer();
				res = new Token(Corresp.MENOR);
				break;
				
			case GENERAR_NEGACION:
				leer();
				res = new Token(Corresp.NEGACION);
				break;
				
			case GENERAR_COMA:
				leer();		
				res = new Token(Corresp.COMA);
				break;
				
			case GENERAR_PUNTO_COMA:
				leer();
				res = new Token(Corresp.PUNTO_COMA);
				break;
				
			case GENERAR_PAR_AB:
				leer();
				res = new Token(Corresp.PAR_AB);
				break;
				
			case GENERAR_PAR_CE:
				leer();
				res = new Token(Corresp.PAR_CE);
				break;
				
			case GENERAR_LLA_AB:
				leer();
				res = new Token(Corresp.LLA_AB);
				break;
				
			case GENERAR_LLA_CE:
				leer();
				res = new Token(Corresp.LLA_CE);
				break;
				
				
			case ERR_CARACTER_NO_PERMITIDO:
				reportarError(new ErrorCharNoPer(chLeidoChar));
				break;
				
			case ERR_COMENTARIO_MAL_FORMADO:
				reportarError(new ErrorComentarioMalForm());
				break;
				
			case ERR_CADENA_NO_TERMINADA:
				reportarError(new ErrorCadenaNoTerminada(lex));
				break;
				
			case ERR_CADENA_EN_VARIAS_LINEAS:
				reportarError(new ErrorCadenaVariasLineas(lex));
				break;
				
			case DEVOLVER_EOF:
				// Devolverle null al sintactico es nuestra 
				// manera de decir que hemos leido eof
				res = null;
				break;
			} //EOSwitch
			
		} // EOWhile
		
		// Antes de devolver el token lo escribimos
		// en la salida (si no es eof)
		if( res!=null ) salidaLex.escribir( res.toString() + "\n" );
		
		return res;
	}
	
	
	private static final BigInteger MAX_ENTERO = new BigInteger(32768+""); // 2^15
	private static BigInteger valor(char c) { return new BigInteger(c+""); }
	
	private static void reportarError(Error e) {
		GestorErrores.reportar( e );
		Control.terminarEjecucion();
	}
	
	public static int lineaActual() {
		return lineaActual;
	}
	
	public static void terminarEjecucion() {		
		try { ficheroFuente.close(); }
		catch(IOException e) { e.printStackTrace(); }
	}
	

}
