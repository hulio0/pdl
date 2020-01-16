package tablasim;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import control.Modulo;
import control.Salida;
import sintsem.AnalizadorSintSem;
import sintsem.tipo.Tipo;
import tablasim.tabla.FilaTS;
import tablasim.tabla.Tabla;


public class TablaS implements Modulo {

	private static Salida salidaTS;
	private static Tabla global;
	private static Tabla local;

	private static Tabla actual;

	// Cada tabla tendra un identificador numerico
	private static int nextTable;

	// Iremos asignando a cada FILA que se cree un
	// identificador, ese identificador es el que
	// llevara cada token ID en su atributo (será
	// su posición en la tabla de símbolos correspondiente)
	private static int nextRow;

	public static void iniciar(File ficheroTS) {
		salidaTS = new Salida(ficheroTS);

		// La tabla global siempre tiene el id 0
		// (pues es la primera que se crea)
		global = new Tabla(0);

		// Inicialmente, lógicamente no hay tabla local
		local = null;

		// La siguiente tabla que se cree tendrá id 1 y,
		// por decisión de diseño, la primera fila que se
		// cree tendra tambien id 1
		nextTable = nextRow = 1;

		// Inicialmente, la tabla actual será la global
		actual = global;
	}

	public static void abrirAmbito(Tipo tipoRetornoEsperado) {
		local = new Tabla(nextTable++,tipoRetornoEsperado);
		actual = local;
	}

	public static void cerrarAmbito() {
		salidaTS.escribir(local.toString());
		local = null;
		actual = global;

		// OBS: La tabla de símbolos global
		// se escribe en el terminarEjecucion()
		// de este modulo
	}
	
	public static boolean estoyEnFuncion() {
		return actual == local;
	}

	// Inserta en una nueva fila el lexema del token ID que quiere añadir
	// el léxico. Este método devuelve el id que se le asigna a la nueva fila
	public static int insertar(String lex) {
		int idNuevaFila = nextRow++;
		FilaTS f = new FilaTS();
		  f.setID(idNuevaFila);
		  f.setLex(lex);

		actual.insert(f);
		return idNuevaFila;
	}
	
	
	// Usado por el léxico, solo busca en la TS actual
	// salvo que estemos en un ambito local, no lo hayamos
	// encontrado en la tabla local y NO estemos declarando
	public static Integer getPosTSLexico(String lex) {
		FilaTS res = null;
		res = actual.getByLex(lex);	
		
		if(res==null && estoyEnFuncion() && !AnalizadorSintSem.estoyEnDeclaracion())
			res = global.getByLex(lex);
		
		return ( res != null ? res.getID() : null );
	}
	
	// Auxiliar para no repetir código de get. Busca
	// por posTS en todas las tablas que puede
	private static FilaTS getByPS(Integer posTS) {
		FilaTS res = actual.getByPosTS(posTS);
		if(res==null && estoyEnFuncion())
			res = global.getByPosTS(posTS);
		return res;
	}

	// Usado por el sintáctico (siempre encontrará la fila correspondiente)
	public static String getLexema(Integer posTS) {
		return getByPS(posTS).getLex();
	}
	
	// Usado por el semántico (siempre encontrará la fila correspondiente).
	public static Tipo getTipo(Integer posTS) {		
		FilaTS res = getByPS(posTS);
		
		// Aunque puede ocurrir que el tipo de la fila esté a null, es decir, caso
		// en el que el usuario utiliza un ID que no ha sido declarado aún. Cuando esto
		// ocurre se interpreta que es entero y global.
		if( res.getTipo() == null ) {
			
			if( estoyEnFuncion() ) {
				local.remove(posTS);
				global.insert(res); 
			}
			
			global.agregarTipoDesp(posTS, Tipo.entero());			
			return Tipo.entero();
		}
		
		return res.getTipo();	
 	}

	public static void agregarTipoDesp(Integer posTS,Tipo t) {	
		actual.agregarTipoDesp(posTS,t);
	}
	
	public static void agregarTipoFunc(Integer posTS,Tipo.Funcion tipoFun) {
		FilaTS fila = global.getByPosTS(posTS);
		fila.setTipo(tipoFun);
	}
	
	
	// Es imposible llamar a este método si no estamos en
	// un ámbito local (el sintáctico lo evitará)
	public static Tipo tipoRetornoEsperado() {
		return local.getTipoRetornoEsperado();
	}
	
	public static void terminarEjecucion() {
		// Si esto ocurre es que ha habido algun error
		// lexico, sintactico o semantico
		if(local!=null)
			salidaTS.escribir(local.toString());
		

		salidaTS.escribir(global.toString());
	}

}
