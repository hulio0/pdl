package sintsem;

import java.io.File;

import control.Control;
import control.Salida;
import errores.Error;
import errores.GestorErrores;
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

public class AnalizadorSintSem {

	// El semántico no tiene salida
	private static Salida salidaSint;

	private static int codTokActual;
	private static Object atribTokActual;

	public static void iniciar(File ficheroSalidaSint) {
		salidaSint = new Salida(ficheroSalidaSint);
		salidaSint.escribir("D ");

		pedirToken();

		P();
	}

	private static final int EOF = -1;
	private static void pedirToken() {
		Token t = AnalizadorLexico.genToken();

		if( t!=null ) {
			codTokActual = t.id();
			atribTokActual = t.atrib();
		}

		else {
			codTokActual = EOF;
			
			// eof no es un token en sí. Dejamos a null su "atributo"
			// pero podríamos ignorarlo ya que no afecta en nada
			atribTokActual = null;
		}
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
			System.out.println("ACEPTAR PROGRAMA");
			Control.terminarEjecucion();
			break;

		default:
			reportarError(new ErrorCuerpoProgramaIncorrecto( lexema(codTokActual) ));

		}
	}

	// Declaracion
	private static void D() {

		// Regla 5 [ D -> var T id ; ], única regla
		escribir(5);
		zonaDeclaracion = true;
		
		comprobarToken( Corresp.VAR );
				
		Tipo tipoT = T();
		Integer posTS = comprobarToken(Corresp.ID);
				
		zonaDeclaracion = false;
		comprobarToken(Corresp.PUNTO_COMA);	
		TablaS.agregarTipoDesp(posTS, tipoT);
	}

	// Tipo (devuelve el tipo de T, valga la redundancia)
	private static Tipo T() {
		switch( codTokActual ) {

		// Regla 6 [ T -> int ]
		case Corresp.INT:
			escribir(6);
			pedirToken();
			
			return Tipo.Entero;

		// Regla 7 [ T -> string ]
		case Corresp.STRING:
			escribir(7);
			pedirToken();
			
			return Tipo.Cadena;

		// Regla 8 [ T -> boolean ]
		case Corresp.BOOLEAN:
			escribir(8);
			pedirToken();
			
			return Tipo.Logico;

		default:
			reportarError(new ErrorTipoNoValido( lexema(codTokActual) ));
			return null;
		}
	}

	// Función
	private static void F() {

		// Regla 9 [ F -> function T2 id ( Pr ) { C } ], única regla
		escribir(9);
		
		comprobarToken(Corresp.FUNCTION);
		Tipo tipoT2 = T2();
		Integer posTS = comprobarToken(Corresp.ID);
		
		zonaDeclaracion = true;
		TablaS.abrirAmbito(tipoT2);
		
		comprobarToken(Corresp.PAR_AB);
		Tupla tuplaTiposPr = Pr();		
		comprobarToken(Corresp.PAR_CE);
		comprobarToken(Corresp.LLA_AB);
		
		zonaDeclaracion = false;
		TablaS.agregarTipoFuncion(posTS, tuplaTiposPr, tipoT2);
		
		boolean tieneReturnC = C(false);
		comprobarToken(Corresp.LLA_CE);

		if( !tieneReturnC && tipoT2 != Tipo.Vacio ) {
			System.out.println("ERROR, Falta sentencia return");
			System.exit(1);
		}
		TablaS.cerrarAmbito();
	}


	// Tipo + "void" (devuelve su tipo)
	private static Tipo T2() {

		switch( codTokActual ) {

		// Regla 10 [ T2 -> lambda ]
		case Corresp.ID:		// Follow(T2)
			escribir(10);
			return Tipo.Vacio;

		// Regla 11 [ T2 -> T ]
		case Corresp.INT:		// First(T)
		case Corresp.STRING:
		case Corresp.BOOLEAN:
			escribir(11);
			Tipo tipoDeT = T();
			return tipoDeT;

		default:
			reportarError(new ErrorTipoNoValido( lexema(codTokActual) ));
			return null;
		}

	}


	// Parametro (Devuelve su tupla de tipos)
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
			
			Tipo tipoT = T();
			Integer posTS = comprobarToken(Corresp.ID);
			
			TablaS.agregarTipoDesp(posTS, tipoT);
			
			Tupla tuplaRp = Rp();
						
			if( tuplaRp.estaVacia() )
				return new Tupla(tipoT);
			else
				return new Tupla(tipoT,tuplaRp);

		default:
			reportarError(new ErrorParametroNoValido( lexema(codTokActual) ));
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
			
			pedirToken();
			Tipo tipoT = T();
			Integer posTS = comprobarToken(Corresp.ID);
			
			TablaS.agregarTipoDesp(posTS, tipoT);
			
			Tupla tuplaRp = Rp();
			
			if( tuplaRp.estaVacia() )
				return new Tupla(tipoT);
			else
				return new Tupla(tipoT, tuplaRp);

		default:
			reportarError(new ErrorParametroNoValido( lexema(codTokActual) ));
			return null;
		}
	}


	// Cuerpo-funcion (devuelve si tiene return)
	private static boolean C(boolean returnEncontrado) {
		switch( codTokActual ) {

		// Regla 16 [ C -> lambda ]
		case Corresp.LLA_CE:	// Follow(C)
			escribir(16);
			break;

		// Regla 17 [ C -> DC ]
		case Corresp.VAR:		// First(DC)
			escribir(17);
			
			if(returnEncontrado) {
				System.out.println("TODO, Error Código inalcanzable");
				System.exit(1);
			}
			
			D();
			return C(false);	

		// Regla 18 [ C -> SC ]
		case Corresp.ID:		// First(SC)
		case Corresp.PRINT:
		case Corresp.INPUT:
		case Corresp.IF:
		case Corresp.RETURN:
			escribir(18);
			
			if(returnEncontrado) {
				System.out.println("TODO, Error Código inalcanzable");
				System.exit(1);
			}
			
			boolean esReturnS = S();
			return C(esReturnS);

		default:
			reportarError(new ErrorCuerpoFuncionIncorrecto( lexema(codTokActual) ));
		}
		
		return returnEncontrado;
	}

	// Expresion (>,<)
	private static Tipo E() {

		// Regla 19 [ E -> E2 Eaux ], unica regla
		escribir(19);
		
		Tipo tipoE2 = E2();
		Tipo tipoEaux = Eaux();

		if( tipoEaux == Tipo.Vacio )
			return tipoE2;
		else if( tipoE2 == Tipo.Entero )
			return Tipo.Logico;
		else {
			System.out.println("Error, expresión mayor/menor no válida");
			System.exit(1);
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
			
			return Tipo.Vacio;

		// Regla 21 [ Eaux -> > E2 ]
		case Corresp.MAYOR:			// Fist(> E2 )
			escribir(21);
			pedirToken();
			tipoE2 = E2();
			
			if( tipoE2 != Tipo.Entero ) {
				System.out.println("Error, expresión mayor/menor no válida");
				System.exit(1);
				return null;
			}
			else
				return Tipo.Logico; // Con que sea != de Vacío nos vale
				
			
		// Regla 22 [ Eaux -> < E2 ]
		case Corresp.MENOR:
			escribir(22);
			pedirToken();
			tipoE2 = E2();
			
			if( tipoE2 != Tipo.Entero ) {
				System.out.println("Error, expresión mayor/menor no válida");
				System.exit(1);
				return null;
			}
			else
				return Tipo.Logico; // Con que sea != de Vacío nos vale

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
		
		if( tipoE2aux == Tipo.Vacio )
			return tipoE3;
		else if( tipoE3 == Tipo.Entero )
			return Tipo.Entero;
		else {
			System.out.println("Error, suma/resta no válida");
			System.exit(1);
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
			
			return Tipo.Vacio;

		// Regla 25 [ E2aux -> + E3 E2aux ]
		case Corresp.MAS:			// First(+ E3 E2aux)
			escribir(25);
			pedirToken();
			tipoE3 = E3();
			
			if( tipoE3 != Tipo.Entero ) {
				System.out.println("Expresión suma, resta no válida");
				System.exit(1);
				return null;
			}
			
			return E2aux();

		// Regla 26 [ E2aux -> - E3 E2aux ]
		case Corresp.MENOS:			// First(- E3 E2aux)
			escribir(26);
			pedirToken();
			tipoE3 = E3();
			
			if( tipoE3 != Tipo.Entero ) {
				System.out.println("Expresión suma, resta no válida");
				System.exit(1);
				return null;
			}
			
			return E2aux();


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
			pedirToken();
			tipoX = X();
			
			if( tipoX != Tipo.Logico ) {
				System.out.println("Error, negación no válida");
				System.exit(1);
			}
			return Tipo.Logico;
			

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
			pedirToken();
			Tipo tipoE = E();
			comprobarToken(Corresp.PAR_CE);
			
			return tipoE;

		// Regla 30 [ X -> id Xaux ]
		case Corresp.ID:			// First(id Xaux)
			escribir(30);
		
			Integer posTS = comprobarToken(Corresp.ID);
			Tipo tipoID = TablaS.getTipoSemantico( posTS );
			
			if( tipoID == Tipo.Funcion ) {
				tipoID.setParam( TablaS.getParamSemantico(posTS) );
				tipoID.setTipoRetorno( TablaS.getTipoRetorno(posTS) );
			}
			
			return Xaux(tipoID);

		// Regla 31 [ X -> entero ]
		case Corresp.ENTERO:
			escribir(31);
			pedirToken();
			return Tipo.Entero;

		// Regla 32 [ X -> cadena ]
		case Corresp.CADENA:
			escribir(32);
			pedirToken();
			return Tipo.Cadena;

		default:
			reportarError(new ErrorExpresionMalFormada(lexema(codTokActual) ));
			return null;

		}
	}

	// Aux para quitar rec. izq. a X
	private static Tipo Xaux(Tipo tipoID) {

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
			pedirToken();
			
			if( tipoID != Tipo.Funcion ) {
				System.out.println("Error, llamada a función no válida. NO es una función");
				System.exit(1);
				return null;
			}
			
			Tupla tuplaA = A();
			comprobarToken(Corresp.PAR_CE);
			
			if( !tuplaA.equals(tipoID.getParam()) ) {
				System.out.println("Error, argumentos no válidos");
				System.exit(1);
			}
			
			return tipoID.getTipoRetorno();


		// Regla 35 [ Xaux -> -- ]
		case Corresp.AUTO_DEC:
			escribir(35);
			pedirToken();
			
			if( tipoID != Tipo.Entero ) {
				System.out.println("Error, postDecremento no válido");
				System.exit(1);
			}
			
			return Tipo.Entero;
		
			
		default:
			reportarError(new ErrorExpresionMalFormada(lexema(codTokActual) ));
			return null;

		}

	}

	// Sentencia
	private static boolean S() {
		
		Integer posTS;
		Tipo tipoID, tipoE;
		
		switch( codTokActual ) {

		// Regla 36 [ S -> id Saux ]
		case Corresp.ID:		// First(id Saux)
			escribir(36);
			
			posTS = comprobarToken(Corresp.ID); // Redundante pero bueno
			tipoID = TablaS.getTipoSemantico( posTS );
			
			if( tipoID == Tipo.Funcion ) {
				tipoID.setParam( TablaS.getParamSemantico(posTS) );
				tipoID.setTipoRetorno( TablaS.getTipoRetorno(posTS) );
			}
			
			Saux(tipoID);
			return false;

		// Regla 37 [ S -> print ( E ) ; ]
		case Corresp.PRINT:		// First(print ( E ) ;)
			escribir(37);
			pedirToken();
			comprobarToken(Corresp.PAR_AB);
			tipoE = E();
			comprobarToken(Corresp.PAR_CE);
			comprobarToken(Corresp.PUNTO_COMA);
			
			if( tipoE != Tipo.Cadena && tipoE != Tipo.Entero ) {
				System.out.println("Error, print no válido");
				System.exit(1);
			}
			return false;


		// Regla 38 [ S -> input(id); ]
		case Corresp.INPUT:		// First(input(id);)
			escribir(38);
			pedirToken();
			comprobarToken(Corresp.PAR_AB);
			posTS = comprobarToken(Corresp.ID);
			comprobarToken(Corresp.PAR_CE);
			comprobarToken(Corresp.PUNTO_COMA);
			
			tipoID = TablaS.getTipoSemantico(posTS);
			if( tipoID != Tipo.Cadena && tipoID != Tipo.Entero ) {
				System.out.println("Error, print no válido");
				System.exit(1);
			}
			
			return false;


		// Regla 39 [ S -> if ( E ) Bi ]
		case Corresp.IF:		// First(if ( E ) Bi)
			escribir(39);
			pedirToken();
			comprobarToken(Corresp.PAR_AB);
			tipoE = E();
			comprobarToken(Corresp.PAR_CE);
			
			if( tipoE != Tipo.Logico ) {
				System.out.println("Error if, no es una expresión lógica");
				System.exit(1);
			}
			
			boolean tieneIfElseReturn = Bi();
			
			if( TablaS.estoyEnFuncion() && tieneIfElseReturn )
				return true;
			
			return false;

		// Regla 40 [ S -> return Y ; ]
		case Corresp.RETURN:	// First(return Y ;)
			escribir(40);
			pedirToken();
			
			if( !TablaS.estoyEnFuncion() ) {
				System.out.println("Error, return mal posicionado");
				System.exit(1);
			}
			
			Tipo tipoY = Y();
			comprobarToken(Corresp.PUNTO_COMA);
			
			if( tipoY != TablaS.tipoRetornoEsperado() ) {
				System.out.println("Error, return no matchea función");
				System.exit(1);
			}
			
			return true;

		default:
			reportarError(new ErrorSentenciaMalConstruida( lexema(codTokActual) ));
			return false;
		}

	}

	// Aux para factorizar las reglas de S
	private static void Saux(Tipo tipoID) {

		switch( codTokActual ) {

		// Regla 41 [ Saux -> = E ; ]
		case Corresp.IGUAL:		// First(= E ;)
			escribir(41);
			pedirToken();
			
			Tipo tipoE = E();
			comprobarToken(Corresp.PUNTO_COMA);
			
			if(tipoE==Tipo.Funcion)
				tipoE = tipoE.getTipoRetorno();
			
			if( tipoE != tipoID ) {				
				System.out.println("Error, tipo asignación no compatible");
				System.exit(1);
			}
			
			break;


		// Regla 42 [ Saux -> ( A ) ; ]
		case Corresp.PAR_AB:	// First(( A ) ;)
			escribir(42);
			pedirToken();
			
			if( tipoID != Tipo.Funcion ) {
				System.out.println("Error, llamada a función no válida. NO es una función");
				System.exit(1);
			}
			
			Tupla tuplaA = A();
			comprobarToken(Corresp.PAR_CE);
			comprobarToken(Corresp.PUNTO_COMA);
						
			if( !tuplaA.equals(tipoID.getParam()) ) {
				System.out.println("Error, argumentos no válidos");
				System.exit(1);
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
			return Tipo.Vacio;

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
			
			if(returnEncontrado) {
				System.out.println("Error, código inalcanzable if-else");
				System.exit(1);
			}
			
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

	
	// Devuelve la posTS si el token esperado es un ID y
	// la comprobación ha salido correctamente. En el resto de
	// casos devuelve null
	private static Integer comprobarToken(int tokenEsperado) {
		
		Integer res = null;

		// Ok, pedimos otro token y seguimos
		if(codTokActual == tokenEsperado) {
			
			if( codTokActual == Corresp.ID ) 
				res = (Integer) atribTokActual;
			
			pedirToken();
		} 
		else
			reportarError(new ErrorTokenNoEsperado(lexema(codTokActual),
								  			   	   lexema(tokenEsperado)) );
		
		return res;
	}

	private static void escribir(int numeroRegla) {
		salidaSint.escribir(numeroRegla + " ");
	}

	private static void reportarError( Error e ) {
		GestorErrores.reportar( e );
		Control.terminarEjecucion();
	}

	// Devuelve el lexema correspondiente al código de token que le pasamos.
	// Por ejemplo, con PUNTO_COMA devolveria ";" , con ENTERO devolveria el
	// numero que leyo el lexico, etc.
	private static String lexema(int codToken) {

		switch( codToken ) {

		// Los únicos casos en los que importa el atributo son
		// ENTERO,CADENA e ID. El resto, la clase Corresp nos
		// da ya el lexema correspondiente

		case Corresp.ENTERO:
		case Corresp.CADENA:
			return atribTokActual.toString();

		// Siempre encontraremos el id en la TS
		case Corresp.ID:
			return TablaS.getLexemaSintactico( (Integer) atribTokActual );

		case EOF:
			return "Fin del fichero";
		default:
			return Corresp.de(codToken);

		}
	}

	public static void terminarEjecucion() {
		// A priori no hay nada que cerrar/salvar.
		// Ponemos el método por consistencia (todas las clases
		// principales de cada módulo lo tienen así que...)
	}
	
	//-----------------------Vainas del semántico--------------------------------
	private static boolean zonaDeclaracion = false;
	public static boolean estoyEnDeclaracion() {
		return zonaDeclaracion;
	}

}
