package lexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import control.Salida;
import errores.GestorErrores;
import errores.err.ErrorCadenaNoTerminada;
import errores.err.ErrorCadenaVariasLineas;
import errores.err.ErrorCharNoPer;
import errores.err.ErrorComentarioMalForm;
import errores.err.ErrorEnteroFueraDeRango;
import lexico.auxiliares.Accion;
import lexico.auxiliares.EntradaMatTrans;
import lexico.tpr.TablaPR;
import lexico.ts.MatrizTransicion;
import lexico.ts.TablaS;

public class AnalizadorLexico {
		
	private static BufferedReader ficheroFuente;
	private static Salida salidaLex;
	private static int lineaActual;
	
	public static void iniciar(File fuente, File ficheroSalidaLex) {
		
		// Iniciamos los sub-modulos
		Correspondencia.iniciar();
		TablaPR.iniciar();
		MatrizTransicion.iniciar();
		TablaS.iniciar();

		try { ficheroFuente = new BufferedReader(new FileReader(fuente)); }
		catch( FileNotFoundException e ) { /*Ya se ha controlado esta situacion*/ }
		salidaLex = new Salida(ficheroSalidaLex);
		lineaActual=1;

				
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
		Integer pos = null;		// Cuando busquemos en TS o TPR guardaremos la respuesta aquí
		EntradaMatTrans entrada = null;	// Entrada de la matriz de transiciones que indica el sig. mov
		Accion toDo = null;  			// accion semantica a realizar en cada transicion
		char chActual = '?'; 			// Haremos cast a chLeido para manejar el caracter

		// Los estados no terminales son 0,1,2,3,4,5,6
		while( estadoActual <=6 ) {
			
			chActual = (char) chLeido;

			
			entrada = MatrizTransicion.getNextTrans(estadoActual,chLeido);	

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
				
				pos = TablaPR.get(lex);
				if( pos !=null ) {
					
					salidaLex.escribir(new 
							Token(pos,"").toString());
				}
				
				// Si no es una PR entonces es una variable
				else{
									
					// Buscamos a ver si ya estaba en la TS
					pos = TablaS.get(lex);
					
					// Si no esta agregamos la variable a la tabla (TablaS se
					// encarga de crear la fila bien bien y tal)
					if(pos == null) 
						pos = TablaS.insert(lex);
					
					// En cualquier caso se genera el token de variable
					salidaLex.escribir(new 
							Token(Correspondencia.de("ID"),pos).toString());	
				}
				// Liberamos el lexema
				lex = "";
				break;
								
			case GENERAR_ENTERO:
				if(num<=Math.pow(2, 16)-1)
					salidaLex.escribir(new 
							Token(Correspondencia.de("ENTERO"),num).toString());
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
						Token(Correspondencia.de("CADENA"),"\""+lex+"\"").toString());
				
				// Liberamos el lexema
				lex="";
				break;
				
			case GENERAR_MENOS:
				salidaLex.escribir(new
						Token(Correspondencia.de("MENOS"),"").toString());
				break;
				
			case GENERAR_AUTO_DEC: 
				leer();
				salidaLex.escribir(new
						Token(Correspondencia.de("AUTO_DEC"),"").toString());
				break;
				
			case GENERAR_IGUAL:
				leer();
				
				salidaLex.escribir(new
						Token(Correspondencia.de("IGUAL"),"").toString());
				break;
				
			case GENERAR_MAS:
				leer();
				salidaLex.escribir(new
						Token(Correspondencia.de("MAS"),"").toString());
				break;
				
			case GENERAR_MAYOR:
				leer();
				salidaLex.escribir(new
						Token(Correspondencia.de("MAYOR"),"").toString());
				break;
				
			case GENERAR_MENOR:
				leer();
				salidaLex.escribir(new
						Token(Correspondencia.de("MENOR"),"").toString());
				break;
				
			case GENERAR_NEGACION:
				leer();
				salidaLex.escribir(new
						Token(Correspondencia.de("DISTINTO"),"").toString());
				break;
				
			case GENERAR_COMA:
				leer();
								
				salidaLex.escribir(new
						Token(Correspondencia.de("COMA"),"").toString());
				break;
				
			case GENERAR_PUNTO_COMA:
				leer();
								
				salidaLex.escribir(new
						Token(Correspondencia.de("PUNTO_COMA"),"").toString());
				break;
				
			case GENERAR_PAR_AB:
				leer();
				salidaLex.escribir(new
						Token(Correspondencia.de("PAR_AB"),"").toString());
				break;
				
			case GENERAR_PAR_CE:
				leer();
								
				salidaLex.escribir(new
						Token(Correspondencia.de("PAR_CE"),"").toString());
				break;
				
			case GENERAR_LLA_AB:
				leer();
				
				TablaS.abrirAmbito();
				
				salidaLex.escribir(new
						Token(Correspondencia.de("LLA_AB"),"").toString());
				break;
				
			case GENERAR_LLA_CE:
				leer();
				
				TablaS.cerrarAmbito();
				
				salidaLex.escribir(new
						Token(Correspondencia.de("LLA_CE"),"").toString());
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
	
	
	/* Ambitos
	private static boolean esPalDeclaracion(String lex) {
		return lex.equals("int")||lex.equals("string")||lex.equals("boolean");
	}
	private static int variable;
	
	// Se pasa a DECLARACION cuando se lea int,string o boolean
	private static final int DECLARACION=0;
	
	// Se pasa a USO cuando se lea cualquier caracter que vaya después de una
	// declaración, es decir: paréntesis derecho, coma y punto y coma. 
	private static final int USO=1;
	*/

}
