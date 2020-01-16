package sintsem;

import java.io.File;

import control.Control;
import control.Modulo;
import control.Salida;
import errores.Error;
import errores.GestorErrores;
import errores.err.sem.ErrorCodigoInalcanzable;
import errores.err.sem.ErrorFaltaSentenciaReturn;
import errores.err.sem.ErrorIfNoValido;
import errores.err.sem.ErrorInputNoValido;
import errores.err.sem.ErrorNoEsUnaFuncion;
import errores.err.sem.ErrorPosDecrementoNoValido;
import errores.err.sem.ErrorPrintNoValido;
import errores.err.sem.ErrorReturnFueraSitio;
import errores.err.sem.ErrorTipoAsignacionIncompatible;
import errores.err.sem.ErrorTipoReturnIncompatibleFuncion;
import errores.err.sem.ErrorTiposArgumentosNoValidos;
import errores.err.sem.ErrorTiposExpresionMayorMenor;
import errores.err.sem.ErrorTiposExpresionNegacion;
import errores.err.sem.ErrorTiposExpresionSumaResta;
import errores.err.sint.ErrorArgumentoNoValido;
import errores.err.sint.ErrorAsignacionOLlamadaMalConstruida;
import errores.err.sint.ErrorCuerpoFuncionIncorrecto;
import errores.err.sint.ErrorCuerpoIfElseIncorrecto;
import errores.err.sint.ErrorCuerpoProgramaIncorrecto;
import errores.err.sint.ErrorExpresionMalFormada;
import errores.err.sint.ErrorIfElseMalConstruido;
import errores.err.sint.ErrorIfMalTerminado;
import errores.err.sint.ErrorParametroNoValido;
import errores.err.sint.ErrorReturnIncorrecto;
import errores.err.sint.ErrorSentenciaMalConstruida;
import errores.err.sint.ErrorTipoNoValido;
import errores.err.sint.ErrorTokenNoEsperado;
import lexico.AnalizadorLexico;
import lexico.Corresp;
import lexico.Token;
import sintsem.tipo.Tipo;
import sintsem.tipo.Tupla;
import tablasim.TablaS;

public class AnalizadorSintSem implements Modulo {

	// El semántico no tiene salida
	private static Salida salidaSint;

	private static int codTokActual;
	private static Object atribTokActual;
	
	private static final int EOF = Token.EOF;

	public static void iniciar(File ficheroSalidaSint) {
		salidaSint = new Salida(ficheroSalidaSint);
		salidaSint.escribir("D ");

		pedirToken();

		P();
	}

	private static void pedirToken() {
		Token t = AnalizadorLexico.genToken();
		codTokActual = t.id();
		atribTokActual = t.atrib();
	}
	
	// Devuelve el lexema correspondiente al código de token que le pasemos.
	// Por ejemplo, con PUNTO_COMA devolveria ";" , con ELSE devolvería "else",
	// con ENTERO devolveria el numero que leyó el lexico, etc. Este método es
	// útil para los mensajes de error.
	private static String lexema(int codToken) {

		switch( codToken ) {

		case Corresp.ENTERO:
		case Corresp.CADENA:
			return atribTokActual.toString();

		// Buscamos el lex. en la TS, siempre 
		// encontraremos la entrada correspondiente
		case Corresp.ID:
			if( codTokActual == Corresp.ID )
				return TablaS.getLexema( (Integer) atribTokActual );
			
			// Caso en el que esperábamos un
			// identificador y no nos lo han mandado
			else
				return "ID";

		case EOF:
			return "Fin de fichero";
			
		// En el resto de casos, Corresp nos da
		// el lexema que estamos buscando
		default:
			return Corresp.de(codToken);
		}
	}
	
	// Si el token actual no hace match con el esperado se lanza un error.
	// Si coinciden, se pide otro token al léxico y se devuelve el atributo
	// del token actual. Esto se usa principalmente para conseguir las posTS
	// de los tokens ID
	private static Object comprobarToken(int tokenEsperado) {
		
		if( codTokActual != tokenEsperado ) {
			reportarError(new ErrorTokenNoEsperado(lexema(codTokActual),
	  			   	   							   lexema(tokenEsperado)) );
		}
		
		Object res = atribTokActual;
		pedirToken();
		return res;
	}


	// Programa
	private static void P() {

		switch( codTokActual ) {

		// Regla 1 [ P->DP ]
		case Corresp.VAR:		// First(DP)
			escribir(1);
			D();
			P();
			break;

		// Regla 2 [ P->FP ]
		case Corresp.FUNCTION:	// First(FP)
			escribir(2);
			F();
			P();
			break;

		// Regla 3 [ P->SP ]
		case Corresp.ID:		// First(SP)
		case Corresp.PRINT:
		case Corresp.INPUT:
		case Corresp.IF:
		case Corresp.RETURN:
			escribir(3);
			S();
			P();
			break;

		// Regla 4 [ P -> eof ]
		case EOF:
			escribir(4);
			Control.terminarEjecucion();  // Regla Semántica
			break;

		default:
			reportarError(new ErrorCuerpoProgramaIncorrecto( lexema(codTokActual) ));

		}
	}

	// Declaracion
	private static void D() {

		// Regla 5 [ D -> var T id ; ], única regla
		escribir(5);
		
		zonaDeclaracion = true;	// Regla semántica
		
		comprobarToken( Corresp.VAR );
		Tipo tipoT = T();
		Integer posTS = (Integer) comprobarToken(Corresp.ID);
			
		TablaS.agregarTipoDesp(posTS, tipoT); // Regla semántica
		zonaDeclaracion = false;
		
		comprobarToken(Corresp.PUNTO_COMA);	
	}

	// Tipo
	private static Tipo T() {
		switch( codTokActual ) {

		// Regla 6 [ T -> int ]
		case Corresp.INT:
			escribir(6);
			comprobarToken(Corresp.INT);
			return Tipo.entero();

		// Regla 7 [ T -> string ]
		case Corresp.STRING:
			escribir(7);
			comprobarToken(Corresp.STRING);
			return Tipo.cadena();

		// Regla 8 [ T -> boolean ]
		case Corresp.BOOLEAN:
			escribir(8);
			comprobarToken(Corresp.BOOLEAN);
			return Tipo.logico();

		default:
			reportarError(new ErrorTipoNoValido( lexema(codTokActual) ));
			return null;
		}
	}

	// Función
	private static void F() {

		// Regla 9 [ F -> function T2 id ( Pr ) { C } ], única regla
		escribir(9);
		
		zonaDeclaracion = true;		// Regla semántica
		
		comprobarToken(Corresp.FUNCTION);
		Tipo tipoRetorno = T2();
		Integer posTS = (Integer) comprobarToken(Corresp.ID);
		
		int linea = guardarLinea();
		TablaS.abrirAmbito(tipoRetorno);	//Regla semántica
		
		comprobarToken(Corresp.PAR_AB);
		Tupla parametros = Pr();		
		comprobarToken(Corresp.PAR_CE);
		
		zonaDeclaracion = false;	// Regla semántica
		TablaS.agregarTipoFunc(posTS, Tipo.funcion(parametros, tipoRetorno));
		
		comprobarToken(Corresp.LLA_AB);
		boolean tieneReturnCuerpo = C(false);
		
		TablaS.cerrarAmbito();		// Regla semántica
		
		comprobarToken(Corresp.LLA_CE);

		if( !tieneReturnCuerpo && !tipoRetorno.esVacio() )  // Regla semántica
			reportarError( new ErrorFaltaSentenciaReturn(linea) );
		
	}


	// Tipo + "void"
	private static Tipo T2() {

		switch( codTokActual ) {

		// Regla 10 [ T2 -> lambda ]
		case Corresp.ID:		// Follow(T2)
			escribir(10);
			return Tipo.vacio();

		// Regla 11 [ T2 -> T ]
		case Corresp.INT:		// First(T)
		case Corresp.STRING:
		case Corresp.BOOLEAN:
			escribir(11);
			return T();

		default:
			reportarError(new ErrorTipoNoValido( lexema(codTokActual) ));
			return null;
		}

	}


	// Parametro
	private static Tupla Pr() {

		switch( codTokActual ) {

		// Regla 12 [ Pr -> lambda ]
		case Corresp.PAR_CE:	// Follow(Pr)
			escribir(12);
			return Tupla.vacia();

		// Regla 13 [ Pr -> T id Rp ]
		case Corresp.INT:		// First(T id Rp)
		case Corresp.STRING:
		case Corresp.BOOLEAN:
			escribir(13);
					
			Tipo tipoPrimerParam = T();
			Integer posTS = (Integer) comprobarToken(Corresp.ID);
			
			TablaS.agregarTipoDesp(posTS, tipoPrimerParam);	// Regla semántica
			
			Tupla tiposRestoParam = Rp();
						
			if( tiposRestoParam.estaVacia() )
				return new Tupla(tipoPrimerParam);
			else
				return new Tupla(tipoPrimerParam,tiposRestoParam);

		default:
			reportarError(new ErrorParametroNoValido(lexema(codTokActual) ));
			return null;
		}
	}

	// Resto Parametros
	private static Tupla Rp() {

		switch( codTokActual ) {

		// Regla 14 [ Rp -> lambda ]
		case Corresp.PAR_CE:	// Follow(Rp)
			escribir(14);
			return Tupla.vacia();

		// Regla 15 [ Rp -> , T id Rp ]
		case Corresp.COMA:		// First(, T id Rp)
			escribir(15);
						
			comprobarToken(Corresp.COMA);
			Tipo tipoParam = T();
			Integer posTS = (Integer) comprobarToken(Corresp.ID);
			
			TablaS.agregarTipoDesp(posTS, tipoParam);	// Regla semántica
			
			Tupla tipoRestoParam = Rp();
			
			if( tipoRestoParam.estaVacia() )
				return new Tupla(tipoParam);
			else
				return new Tupla(tipoParam, tipoRestoParam);

		default:
			reportarError(new ErrorParametroNoValido(lexema(codTokActual) ));
			return null;
		}
	}


	// Cuerpo-funcion (devuelve true si el cuerpo tiene return).
	// Recibe como parametro si hemos encontrado o no un return en
	// ese punto del cuerpo
	private static Boolean C(boolean returnEncontrado) {
		switch( codTokActual ) {

		// Regla 16 [ C -> lambda ]
		case Corresp.LLA_CE:	// Follow(C)
			escribir(16);
			
			// Si ya hemos terminado el cuerpo. Respondemos
			// si hemos encontrado un return hasta este punto.
			// Esta información fluirá hacia arriba, hasta llegar
			// a la C que se encuentra en la regla de función
			return returnEncontrado;

		// Regla 17 [ C -> DC ]
		case Corresp.VAR:		// First(DC)
			escribir(17);
			
			if(returnEncontrado)	// Regla semántica
				reportarError(new ErrorCodigoInalcanzable());
			
			D();
			
			// Seguimos explorando el cuerpo de la función,
			// indicando que de momento no hemos encontrado
			// un return (ya que lo que acabamos de encontrar
			// es una declaración)
			return C(false);	

		// Regla 18 [ C -> SC ]
		case Corresp.ID:		// First(SC)
		case Corresp.PRINT:
		case Corresp.INPUT:
		case Corresp.IF:
		case Corresp.RETURN:
			escribir(18);
			
			if(returnEncontrado)	// Regla semántica
				reportarError(new ErrorCodigoInalcanzable());
			
			boolean esReturnS = S();
			
			// Seguimos explorando el cuerpo de la función,
			// indicando lo que ha sido la sentencia que acabamos
			// de encontrar (que podría ser un return o no)
			return C(esReturnS);

		default:
			reportarError(new ErrorCuerpoFuncionIncorrecto( lexema(codTokActual) ));
			return null;
		}
		
	}

	// Expresion (>,<)
	private static Tipo E() {

		// Regla 19 [ E -> E2 Eaux ], unica regla
		escribir(19);
		
		Tipo tipoE2 = E2();
		Tipo tipoEaux = Eaux();

		// No hay expresión >,<
		if( tipoEaux.esVacio() )
			return tipoE2;
		
		// Si hay expresión >,< E2 tendrá
		// que ser de tipo entero
		else if( tipoE2.esEntero() )
			return Tipo.logico();
		
		else {
			reportarError(new ErrorTiposExpresionMayorMenor(tipoE2));
			return null;
		}
	}

	// Aux para quitar rec izq de E
	private static Tipo Eaux() {
		
		Tipo tipoE2;

		switch( codTokActual ) {

		// Regla 20 [ Eaux -> lambda ]
		case Corresp.PAR_CE:		// Follow(Eaux)
		case Corresp.PUNTO_COMA:
		case Corresp.COMA:
			escribir(20);
			return Tipo.vacio();

		// Regla 21 [ Eaux -> > E2 ]
		case Corresp.MAYOR:			// Fist(> E2 )
			escribir(21);
			
			comprobarToken(Corresp.MAYOR);
			tipoE2 = E2();
			
			if( !tipoE2.esEntero() )
				reportarError(new ErrorTiposExpresionMayorMenor(tipoE2));
			
			return Tipo.logico(); // Con que sea != de Vacío nos vale
				
			
		// Regla 22 [ Eaux -> < E2 ]
		case Corresp.MENOR:
			escribir(22);
			
			comprobarToken(Corresp.MENOR);
			tipoE2 = E2();
			
			if( !tipoE2.esEntero() ) 
				reportarError(new ErrorTiposExpresionMayorMenor(tipoE2));
			
			return Tipo.logico(); // Con que sea != de Vacío nos vale

		default:
			reportarError(new ErrorExpresionMalFormada( lexema(codTokActual) ));
			return null;
		}



	}

	// Expresion (+,-)
	private static Tipo E2() {

		// Regla 23 [ E2 -> E3 E2aux ], unica regla
		escribir(23);
		
		Tipo tipoE3 = E3();
		Tipo tipoE2aux= E2aux();
		
		// No hay expresión +,-
		if( tipoE2aux.esVacio() )
			return tipoE3;
		
		// Si hay expresión +,- E3 tendrá
		// que ser de tipo entero
		else if( tipoE3.esEntero() )
			return Tipo.entero();
		
		else {
			reportarError(new ErrorTiposExpresionSumaResta(tipoE3));
			return null;
		}
	}

	// Aux para quitar rec izq de E2
	private static Tipo E2aux() {
		
		Tipo tipoE3;

		switch( codTokActual ) {

		// Regla 24 [ E2aux -> lambda ]
		case Corresp.PAR_CE:		// Follow(E2aux)
		case Corresp.PUNTO_COMA:
		case Corresp.COMA:
		case Corresp.MAYOR:
		case Corresp.MENOR:
			escribir(24);
			return Tipo.vacio();

		// Regla 25 [ E2aux -> + E3 E2aux ]
		case Corresp.MAS:			// First(+ E3 E2aux)
			escribir(25);
			
			comprobarToken(Corresp.MAS);
			tipoE3 = E3();
			
			if( !tipoE3.esEntero() )
				reportarError(new ErrorTiposExpresionSumaResta(tipoE3));
			
			E2aux();
			return Tipo.entero(); // Con que sea != de Vacío nos vale

		// Regla 26 [ E2aux -> - E3 E2aux ]
		case Corresp.MENOS:			// First(- E3 E2aux)
			escribir(26);
			
			comprobarToken(Corresp.MENOS);
			tipoE3 = E3();
			
			if( !tipoE3.esEntero() )
				reportarError(new ErrorTiposExpresionSumaResta(tipoE3));
			
			
			E2aux();
			
			return Tipo.entero(); // Con que sea != de Vacío nos vale


		default:
			reportarError(new ErrorExpresionMalFormada( lexema(codTokActual) ));
			return null;
		}
	}

	// Expresión (!)
	private static Tipo E3() {
		
		Tipo tipoX;

		switch( codTokActual ) {

		// Regla 27 [ E3 -> ! X ]
		case Corresp.NEGACION:		// First(! X)
			escribir(27);
			
			int linea = guardarLinea();
			
			comprobarToken(Corresp.NEGACION);
			tipoX = X();
			
			if( !tipoX.esLogico() )
				reportarError( new ErrorTiposExpresionNegacion(linea,tipoX) );
			
			return Tipo.logico();
			
		// Regla 28 [ E3 -> X ]
		case Corresp.PAR_AB:		// First(X)
		case Corresp.ID:
		case Corresp.ENTERO:
		case Corresp.CADENA:
			escribir(28);
			return X();
			
		default:
			reportarError(new ErrorExpresionMalFormada(lexema(codTokActual) ));
			return null;
		}

	}

	// Constante o su resultado es una constante
	private static Tipo X() {

		switch( codTokActual ) {

		// Regla 29 [ X -> ( E ) ]
		case Corresp.PAR_AB:		// First( (E) )
			escribir(29);
			
			comprobarToken(Corresp.PAR_AB);
			Tipo tipoE = E();
			comprobarToken(Corresp.PAR_CE);
			
			return tipoE;

		// Regla 30 [ X -> id Xaux ]
		case Corresp.ID:			// First(id Xaux)
			escribir(30);
		
			Integer posTS = (Integer) comprobarToken(Corresp.ID);
			
			Tipo tipoID = TablaS.getTipo( posTS );	// Regla semántica
			return Xaux(tipoID);

		// Regla 31 [ X -> entero ]
		case Corresp.ENTERO:
			escribir(31);
			comprobarToken(Corresp.ENTERO);
			return Tipo.entero();

		// Regla 32 [ X -> cadena ]
		case Corresp.CADENA:
			escribir(32);
			comprobarToken(Corresp.CADENA);
			return Tipo.cadena();

		default:
			reportarError(new ErrorExpresionMalFormada(lexema(codTokActual) ));
			return null;

		}
	}

	// Aux para quitar rec. izq. a X
	private static Tipo Xaux(Tipo tipoID) {
		
		int linea;

		switch( codTokActual ) {

		// Regla 33 [ Xaux -> lambda ]
		case Corresp.PAR_CE:		// Follow(Xaux)
		case Corresp.PUNTO_COMA:
		case Corresp.COMA:
		case Corresp.MAYOR:
		case Corresp.MENOR:
		case Corresp.MAS:
		case Corresp.MENOS:
			escribir(33);
			return tipoID;

		// Regla 34 [ Xaux -> ( A ) ]
		case Corresp.PAR_AB:		// First( (A) )
			escribir(34);
			
			if( !tipoID.esFuncion() ) 
				reportarError( new ErrorNoEsUnaFuncion(tipoID));
			
					
			linea = guardarLinea();
			
			comprobarToken(Corresp.PAR_AB);
			Tupla tiposArgumentos = A();
			comprobarToken(Corresp.PAR_CE);
			
			Tupla tiposParametrosFun = ((Tipo.Funcion) tipoID).getParams();	// Regla semántica
			Tipo tipoRetornoFun = ((Tipo.Funcion) tipoID).getTipoRetorno();
			if( !tiposArgumentos.equals(tiposParametrosFun) ) {
				reportarError( new ErrorTiposArgumentosNoValidos(linea,
																 tiposArgumentos,
																 tiposParametrosFun));												 
			}
			return tipoRetornoFun;


		// Regla 35 [ Xaux -> -- ]
		case Corresp.AUTO_DEC:
			escribir(35);
			
			linea = guardarLinea();
			
			comprobarToken(Corresp.AUTO_DEC);
			if( !tipoID.esEntero() ) {
				reportarError( new ErrorPosDecrementoNoValido(linea,
															  tipoID) );
			}
			return Tipo.entero();
		
			
		default:
			reportarError(new ErrorExpresionMalFormada(lexema(codTokActual) ));
			return null;

		}

	}

	// Sentencia
	private static Boolean S() {
		
		int linea;
		
		boolean esReturn;
		
		Integer posTS;
		Tipo tipoID, tipoE;
		
		switch( codTokActual ) {

		// Regla 36 [ S -> id Saux ]
		case Corresp.ID:		// First(id Saux)
			escribir(36);
						
			posTS = (Integer) comprobarToken(Corresp.ID); // Redundante pero bueno
			tipoID = TablaS.getTipo( posTS );			
			Saux(tipoID);
			esReturn = false;
			break;

		// Regla 37 [ S -> print ( E ) ; ]
		case Corresp.PRINT:		// First(print ( E ) ;)
			escribir(37);
			
			linea = guardarLinea();
			
			comprobarToken(Corresp.PRINT);
			comprobarToken(Corresp.PAR_AB);
			tipoE = E();
			comprobarToken(Corresp.PAR_CE);
			comprobarToken(Corresp.PUNTO_COMA);
			
			if( !tipoE.esCadena() && !tipoE.esEntero() )
				reportarError(new ErrorPrintNoValido(linea, tipoE));
			
			esReturn = false;
			break;


		// Regla 38 [ S -> input(id); ]
		case Corresp.INPUT:		// First(input(id);)
			escribir(38);
			
			linea = guardarLinea();
			
			comprobarToken(Corresp.INPUT);
			comprobarToken(Corresp.PAR_AB);
			posTS = (Integer) comprobarToken(Corresp.ID);
			comprobarToken(Corresp.PAR_CE);
			comprobarToken(Corresp.PUNTO_COMA);
			
			tipoID = TablaS.getTipo(posTS);
			if( !tipoID.esCadena() && !tipoID.esEntero() ) 
				reportarError(new ErrorInputNoValido(linea,tipoID));
			
			esReturn = false;
			break;


		// Regla 39 [ S -> if ( E ) Bi ]
		case Corresp.IF:		// First(if ( E ) Bi)
			escribir(39);
			
			linea = guardarLinea();
			
			comprobarToken(Corresp.IF);
			comprobarToken(Corresp.PAR_AB);
			tipoE = E();
			comprobarToken(Corresp.PAR_CE);
			
			if( !tipoE.esLogico() ) 
				reportarError(new ErrorIfNoValido(linea, tipoE));
			
			boolean tieneIfElseReturn = Bi();
			if( TablaS.estoyEnFuncion() && tieneIfElseReturn )
				return true;
			
			esReturn = false;
			break;

		// Regla 40 [ S -> return Y ; ]
		case Corresp.RETURN:	// First(return Y ;)
			escribir(40);
			
			linea = guardarLinea();
			
			comprobarToken(Corresp.RETURN);
			if( !TablaS.estoyEnFuncion() )
				reportarError(new ErrorReturnFueraSitio(linea));
			
			Tipo tipoY = Y();
			comprobarToken(Corresp.PUNTO_COMA);
			
			if( tipoY != TablaS.tipoRetornoEsperado() ) {
				reportarError(new ErrorTipoReturnIncompatibleFuncion(linea,
																	 tipoY,
																	 TablaS.tipoRetornoEsperado()));
			}
			esReturn = true;
			break;

		default:
			reportarError(new ErrorSentenciaMalConstruida( lexema(codTokActual) ));
			return null;
		}
		return esReturn;
	}

	// Aux para factorizar las reglas de S
	private static void Saux(Tipo tipoID) {
		
		int linea;

		switch( codTokActual ) {

		// Regla 41 [ Saux -> = E ; ]
		case Corresp.IGUAL:		// First(= E ;)
			escribir(41);
			
			linea = guardarLinea();
			
			comprobarToken(Corresp.IGUAL);
			Tipo tipoE = E();
			comprobarToken(Corresp.PUNTO_COMA);
			
			if( tipoE.esFuncion() )
				tipoE = ((Tipo.Funcion) tipoE).getTipoRetorno();
			
			if( tipoE != tipoID ) {				
				reportarError(new ErrorTipoAsignacionIncompatible(linea,
																  tipoID,
																  tipoE));
			}
			break;


		// Regla 42 [ Saux -> ( A ) ; ]
		case Corresp.PAR_AB:	// First(( A ) ;)
			escribir(42);
			
			if( !tipoID.esFuncion() ) 
				reportarError(new ErrorNoEsUnaFuncion(tipoID));
			
			
			linea = guardarLinea();
			
			comprobarToken(Corresp.PAR_AB);
			Tupla tiposArgumentos = A();
			comprobarToken(Corresp.PAR_CE);
			comprobarToken(Corresp.PUNTO_COMA);
			
			Tupla tiposParametrosFun = ((Tipo.Funcion) tipoID).getParams();	// Regla semántica
			if( !tiposArgumentos.equals(tiposParametrosFun) ) {
				reportarError( new ErrorTiposArgumentosNoValidos(linea,
																 tiposArgumentos,
																 tiposParametrosFun));												 
			}

			break;

		default:
			reportarError(new ErrorAsignacionOLlamadaMalConstruida( lexema(codTokActual) ));

		}

	}

	// X + "void"
	private static Tipo Y() {

		switch( codTokActual ) {

		// Regla 43 [ Y -> lambda ]
		case Corresp.PUNTO_COMA:	// Follow(Y)
			escribir(43);
			return Tipo.vacio();

		// Regla 44 [ Y -> E ]
		case Corresp.PAR_AB:		// First(E)
		case Corresp.ID:
		case Corresp.ENTERO:
		case Corresp.CADENA:
		case Corresp.NEGACION:
			escribir(44);
			return E();

		default:
			reportarError(new ErrorReturnIncorrecto( lexema(codTokActual) ));
			return null;
		}

	}

	// Argumento funcion
	private static Tupla A() {

		switch( codTokActual ) {

		// Regla 45 [ A -> lambda ]
		case Corresp.PAR_CE:		// Follow(A)
			escribir(45);
			return Tupla.vacia();

		// Regla 46 [ A -> E Ra ]
		case Corresp.PAR_AB:		// First(E Ra)
		case Corresp.ID:
		case Corresp.ENTERO:
		case Corresp.CADENA:
		case Corresp.NEGACION:
			escribir(46);
			Tipo tipoE = E();
			Tupla tuplaRa = Ra();
			
			if( tuplaRa.estaVacia() )
				return new Tupla(tipoE);
			else
				return new Tupla(tipoE,tuplaRa);

		default:
			reportarError(new ErrorArgumentoNoValido(lexema(codTokActual) ));
			return null;
		}
	}

	// Resto de argumentos funcion
	private static Tupla Ra() {

		switch( codTokActual ) {

		// Regla 47 [ Ra -> lambda ]
		case Corresp.PAR_CE:		// Follow(Ra)
			escribir(47);
			return Tupla.vacia();

		// Regla 48 [ Ra -> , E Ra ]
		case Corresp.COMA:			// First(, E Ra)
			escribir(48);
			pedirToken();
			Tipo tipoE = E();
			Tupla tuplaRa = Ra();
			
			if( tuplaRa.estaVacia() )
				return new Tupla(tipoE);
			else
				return new Tupla(tipoE,tuplaRa);

		default:
			reportarError(new ErrorArgumentoNoValido( lexema(codTokActual) ));
			return null;

		}
	}

	// Bloque if
	private static boolean Bi() {
		
		boolean esReturnS, tieneReturnElse, tieneReturnCuerpo;

		switch( codTokActual ) {

		// Regla 49 [ Bi -> S El ]
		case Corresp.ID:		// First(S El)
		case Corresp.PRINT:
		case Corresp.INPUT:
		case Corresp.IF:
		case Corresp.RETURN:
			escribir(49);
			esReturnS = S();
			tieneReturnElse = El();
			return esReturnS && tieneReturnElse;

		// Regla 50 [ Bi -> { Cie } El ]
		case Corresp.LLA_AB:	// First({ Cie } El]
			escribir(50);
			pedirToken();
			
			tieneReturnCuerpo = Cie(false);
			comprobarToken(Corresp.LLA_CE);
			tieneReturnElse = El();
			return tieneReturnCuerpo && tieneReturnElse;

		default:
			reportarError(new ErrorIfElseMalConstruido(lexema(codTokActual),
					  			  				   	   ErrorIfElseMalConstruido.IF ));
			return false;

		}

	}

	// Cuerpo if-else
	private static boolean Cie(boolean returnEncontrado) {

		switch( codTokActual ) {

		// Regla 51 [ Cie -> lambda ]
		case Corresp.LLA_CE:		// Follow(Cie)
			escribir(51);
			break;

		// Regla 52 [ Cie -> S Cie ]
		case Corresp.ID:		// First(S Cie)
		case Corresp.PRINT:
		case Corresp.INPUT:
		case Corresp.IF:
		case Corresp.RETURN:
			escribir(52);
			
			if(returnEncontrado)
				reportarError( new ErrorCodigoInalcanzable() );
			
			boolean esReturnS = S();
			return Cie(esReturnS);

		default:
			reportarError(new ErrorCuerpoIfElseIncorrecto( lexema(codTokActual) ));
		}
		
		return returnEncontrado;

	}

	// Else
	private static boolean El() {

		switch( codTokActual ) {

		// Regla 53 [ El -> lambda ]
		case Corresp.VAR:		// Follow(El)
		case Corresp.INT:
		case Corresp.STRING:
		case Corresp.BOOLEAN:
		case Corresp.ID:
		case Corresp.PRINT:
		case Corresp.INPUT:
		case Corresp.IF:
		case Corresp.RETURN:
		case Corresp.LLA_CE:
		case EOF:
			escribir(53);
			return false;

		// Regla 54 [ El -> else Be ]
		case Corresp.ELSE:		// Fisrt(else Be)
			escribir(54);
			pedirToken();
			boolean tieneReturnBe = Be();
			return tieneReturnBe;

		default:
			reportarError(new ErrorIfMalTerminado( lexema(codTokActual) ));
			return false;

		}
	}

	// Bloque else
	private static boolean Be() {

		switch( codTokActual ) {

		// Regla 55 [ Be -> S ]
		case Corresp.ID:		// First(S)
		case Corresp.PRINT:
		case Corresp.INPUT:
		case Corresp.IF:
		case Corresp.RETURN:
			escribir(55);
			boolean esReturnS = S();
			return esReturnS;

		// Regla 56 [ Be -> { Cie } ]
		case Corresp.LLA_AB:
			escribir(56);
			pedirToken();
			
			boolean tieneReturnCuerpo = Cie(false);
			
			comprobarToken(Corresp.LLA_CE);
			
			return tieneReturnCuerpo;

		default:
			reportarError(new ErrorIfElseMalConstruido(lexema(codTokActual),
				  	  			  				   	   ErrorIfElseMalConstruido.ELSE ));
			return false;

		}

	}


	private static void escribir(int numeroRegla) {
		salidaSint.escribir(numeroRegla + " ");
	}

	private static void reportarError( Error e ) {
		GestorErrores.reportar( e );
		Control.terminarEjecucion();
	}

	public static void terminarEjecucion() {}
	
	private static boolean zonaDeclaracion = false;
	public static boolean estoyEnDeclaracion() { return zonaDeclaracion; }

	private static int guardarLinea() { return AnalizadorLexico.lineaActual(); }
	
}
