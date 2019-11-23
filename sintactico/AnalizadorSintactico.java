package sintactico;

import java.io.File;

import control.Salida;
import lexico.AnalizadorLexico;
import lexico.Corresp;
import lexico.Token;
import tablasim.TablaS;

public class AnalizadorSintactico {
	
	private static Salida salidaSint;
	
	// Sólo nos interesa saber qué token es
	private static int codTokActual;
	
	// Permite conocer qué estructura estamos analizando
	// actualmente (útil para dar mensajes de error más específicos)
	private static Contexto contexto;
	
	public static void iniciar(File ficheroSalidaSint) {
		salidaSint = new Salida(ficheroSalidaSint);
		  salidaSint.escribir("D ");
		
		pedirToken();
		
		P();
	}
	
	private static final int EOF = -1;
	private static void pedirToken() {
		Token t = AnalizadorLexico.genToken();
		codTokActual = ( t != null ? t.id() : EOF );
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
			System.out.println("TODO ACEPTAR PROGRAMA");
			break;
			
		default:
			System.out.println("ERROR P");
		}
	}
	
	// Declaracion
	private static void D() {
		
		// Regla 5 [ D -> var T id ; ], única regla
		escribir(5);
		comprobarToken( Corresp.VAR );
		T();
		comprobarToken( Corresp.ID );
		comprobarToken(Corresp.PUNTO_COMA);
		
	}
	
	// Tipo
	private static void T() {
		
		switch( codTokActual ) {
		
		// Regla 6 [ T -> int ]
		case Corresp.INT:
			escribir(6);
			pedirToken();
			break;
			
		// Regla 7 [ T -> string ]
		case Corresp.STRING:
			escribir(7);
			pedirToken();
			break;
			
		// Regla 8 [ T -> boolean ]
		case Corresp.BOOLEAN:
			escribir(8);
			pedirToken();
			break;
			
		default:
			System.out.println("ERROR T");
		
		}
	}
	
	// Función
	private static void F() {
		
		// Regla 9 [ F -> function T2 id ( Pr ) { C } ], única regla
		escribir(9);
		comprobarToken(Corresp.FUNCTION);
		T2();
		comprobarToken(Corresp.ID);
		comprobarToken(Corresp.PAR_AB);
		
		TablaS.abrirAmbito();
		
		Pr();
		comprobarToken(Corresp.PAR_CE);
		comprobarToken(Corresp.LLA_AB);
		C();
		comprobarToken(Corresp.LLA_CE);
		
		TablaS.cerrarAmbito();
	}

	
	// Tipo + "void"
	private static void T2() {
		
		switch( codTokActual ) {
		
		// Regla 10 [ T2 -> lambda ]
		case Corresp.ID:		// Follow(T2)
			escribir(10);
			break;
			
		// Regla 11 [ T2 -> T ]
		case Corresp.INT:		// First(T)
		case Corresp.STRING:
		case Corresp.BOOLEAN:
			escribir(11);
			T();
			break;
			
		default:
			System.out.println("ERROR T2");
		}
		
	}

	
	// Parametro
	private static void Pr() {
		
		switch( codTokActual ) {
		
		// Regla 12 [ Pr -> lambda ]
		case Corresp.PAR_CE:	// Follow(Pr)
			escribir(12);
			break;
			
		
		// Regla 13 [ Pr -> T id Rp ]
		case Corresp.INT:		// First(T id Rp)
		case Corresp.STRING:
		case Corresp.BOOLEAN:
			escribir(13);
			T();
			comprobarToken(Corresp.ID);
			Rp();
			break;
			
		default:
			System.out.println("ERROR Pr");
		}
	}
		
	// Resto Parametros
	private static void Rp() {
		
		switch( codTokActual ) {
		
		// Regla 14 [ Rp -> lambda ]
		case Corresp.PAR_CE:	// Follow(Rp)
			escribir(14);
			break;
			
		// Regla 15 [ Rp -> , T id Rp ]
		case Corresp.COMA:		// First(, T id Rp)
			escribir(15);
			pedirToken();
			T();
			comprobarToken(Corresp.ID);
			Rp();
			break;
			
		default:
			System.out.println("ERROR Rp");
		}
	}
	
	
	// Cuerpo-funcion
	private static void C() {
		
		switch( codTokActual ) {
		
		// Regla 16 [ C -> lambda ]
		case Corresp.LLA_CE:	// Follow(C)
			escribir(16);
			break;
			
		// Regla 17 [ C -> DC ]
		case Corresp.VAR:		// First(DC)
			escribir(17);
			D();
			C();
			break;
			
		// Regla 18 [ C -> SC ]
		case Corresp.ID:		// First(SC)
		case Corresp.PRINT:
		case Corresp.INPUT:
		case Corresp.IF:
		case Corresp.RETURN:
			escribir(18);
			S();
			C();
			break;
			
		default:
			System.out.println("ERROR C");
		}
		
	}
	
	// Expresion (>)
	private static void E() {
		
		// Regla 19 [ E -> E2 Eaux ], unica regla
		escribir(19);
		E2();
		Eaux();
		
	}
	
	// Aux para quitar rec izq de E
	private static void Eaux() {
		
		switch( codTokActual ) {
		
		// Regla 20 [ Eaux -> lambda ]
		case Corresp.PAR_CE:		// Follow(Eaux)
		case Corresp.PUNTO_COMA:
		case Corresp.COMA:
			escribir(20);
			break;
			
		// Regla 21 [ Eaux -> > E2 Eaux ]
		case Corresp.MAYOR:
			escribir(21);
			pedirToken();
			E2();
			Eaux();
			break;
			
		default:
			System.out.println("ERROR Eaux");
		}
		
		
		
	}
	
	// Expresion (<)
	private static void E2() {
		
		// Regla 22 [ E2 -> E3 E2aux ], unica regla
		escribir(22);
		E3();
		E2aux();
		
	}
	
	// Aux para quitar rec izq de E2
	private static void E2aux() {
		
		switch( codTokActual ) {
		
		// Regla 23 [ E2aux -> lambda ]
		case Corresp.PAR_CE:		// Follow(E2aux)
		case Corresp.PUNTO_COMA:
		case Corresp.COMA:
		case Corresp.MAYOR:
			escribir(23);
			break;
			
		// Regla 24 [ E2aux -> < E3 E2aux ]
		case Corresp.MENOR:			// First(< E3 E2aux)
			escribir(24);
			pedirToken();
			E3();
			E2aux();
			break;
			
			
		default:
			System.out.println("ERROR E2aux");
		}
		
		
		
	}
	
	// Expresion (+)
	private static void E3() {
		
		// Regla 25 [ E3 -> E4 E3aux ], unica regla
		escribir(25);
		E4();
		E3aux();
	}
	
	// Aux para quitar rec izq de E3
	private static void E3aux() {
		
		switch( codTokActual ) {
		
		// Regla 26 [ E3aux -> lambda ]
		case Corresp.PAR_CE:		// Follow(E3aux)
		case Corresp.PUNTO_COMA:
		case Corresp.COMA:
		case Corresp.MAYOR:
		case Corresp.MENOR:
			escribir(26);
			break;
			
		// Regla 27 [ E3aux -> + E4 E3aux ]
		case Corresp.MAS:			// First(+ E4 E3aux)
			escribir(27);
			pedirToken();
			E4();
			E3aux();
			break;
			
		default:
			System.out.println("ERROR E3aux");
		}
	}
	
	// Expresion (-)
	private static void E4() {
		
		// Regla 28 [ E4 -> E5 E4aux ], unica regla
		escribir(28);
		E5();
		E4aux();
		
	}
	
	// Aux para quitar rec izq de E4
	private static void E4aux() {
		
		switch( codTokActual ) {
		
		// Regla 29 [ E4aux -> lambda ]
		case Corresp.PAR_CE:		// Follow(E4aux)
		case Corresp.PUNTO_COMA:
		case Corresp.COMA:
		case Corresp.MAYOR:
		case Corresp.MENOR:
		case Corresp.MAS:
			escribir(29);
			break;
			
		// Regla 30 [ E4aux -> - E5 E4aux ]
		case Corresp.MENOS:			// First(- E5 E4aux)
			escribir(30);
			pedirToken();
			E5();
			E4aux();
			break;
			
		default:
			System.out.println("ERROR E4aux");
		
		}
		
	}
	
	// Expresión (!)
	private static void E5() {
		
		switch( codTokActual ) {
		
		// Regla 31 [ E5 -> ! X ]
		case Corresp.NEGACION:		// First(! X)
			escribir(31);
			pedirToken();
			X();
			break;
			
		// Regla 32 [ E5 -> X ]
		case Corresp.PAR_AB:		// First(X)
		case Corresp.ID:
		case Corresp.ENTERO:
		case Corresp.CADENA:
			escribir(32);
			X();
			break;
			
		default:
			System.out.println("ERROR E5");
		}
		
		
		
		
	}

	// Constante o su resultado es una constante
	private static void X() {
		
		switch( codTokActual ) {
		
		// Regla 33 [ X -> ( E ) ]
		case Corresp.PAR_AB:		// First( (E) )
			escribir(33);
			pedirToken();
			E();
			comprobarToken(Corresp.PAR_CE);
			break;
			
		// Regla 34 [ X -> id Xaux ]
		case Corresp.ID:			// First(id Xaux)
			escribir(34);
			pedirToken();
			Xaux();
			break;
			
		// Regla 35 [ X -> entero ]
		case Corresp.ENTERO:
			escribir(35);
			pedirToken();
			break;
			
		// Regla 36 [ X -> cadena ]
		case Corresp.CADENA:
			escribir(36);
			pedirToken();
			break;
			
		default:
			System.out.println("ERROR X");
			
		}	
	}
	
	// Aux para quitar rec. izq. a X
	private static void Xaux() {
		
		switch( codTokActual ) {
		
		// Regla 37 [ Xaux -> lambda ]
		case Corresp.PAR_CE:		// Follow(Xaux)
		case Corresp.PUNTO_COMA:
		case Corresp.COMA:
		case Corresp.MAYOR:
		case Corresp.MENOR:
		case Corresp.MAS:
		case Corresp.MENOS:
			escribir(37);
			break;
			
		// Regla 38 [ Xaux -> ( A ) ]
		case Corresp.PAR_AB:		// First( (A) ) 
			escribir(38);
			pedirToken();
			A();
			comprobarToken(Corresp.PAR_CE);
			break;
			
			
		// Regla 39 [ Xaux -> -- ]
		case Corresp.AUTO_DEC:
			escribir(39);
			pedirToken();
			break;
			
		default:
			System.out.println("ERROR Xaux");
			
		}
		
	}
	
	// Sentencia
	private static void S() {
		
		switch( codTokActual ) {
		
		// Regla 40 [ S -> id Saux ]
		case Corresp.ID:		// First(id Saux)
			escribir(40);
			pedirToken();
			Saux();
			break;
			
		// Regla 41 [ S -> print ( E ) ; ]
		case Corresp.PRINT:		// First(print ( E ) ;)
			escribir(41);
			pedirToken();
			comprobarToken(Corresp.PAR_AB);
			E();
			comprobarToken(Corresp.PAR_CE);
			comprobarToken(Corresp.PUNTO_COMA);
			break;
			
			
		// Regla 42 [ S -> input(id); ]
		case Corresp.INPUT:		// First(input(id);)
			escribir(42);
			pedirToken();
			comprobarToken(Corresp.PAR_AB);
			comprobarToken(Corresp.ID);
			comprobarToken(Corresp.PAR_CE);
			comprobarToken(Corresp.PUNTO_COMA);
			break;
		
		
		// Regla 43 [ S -> if ( E ) Bi ]
		case Corresp.IF:		// First(if ( E ) Bi)
			escribir(43);
			pedirToken();
			comprobarToken(Corresp.PAR_AB);
			E();
			comprobarToken(Corresp.PAR_CE);
			Bi();
			break;
			
		// Regla 44 [ S -> return Y ; ]
		case Corresp.RETURN:	// First(return Y ;)
			escribir(44);
			pedirToken();
			Y();
			comprobarToken(Corresp.PUNTO_COMA);
			break;
			
		default:
			System.out.println("ERROR X");
		}
		
	}
	
	// Aux para quitar rec. izq. a S
	private static void Saux() {
		
		switch( codTokActual ) {
		
		// Regla 45 [ Saux -> lambda ]
		case Corresp.VAR:		// Follow(Saux)
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
			escribir(45);
			break;
			
		// Regla 46 [ Saux -> = E ; ]
		case Corresp.IGUAL:		// First(= E ;)
			escribir(46);
			pedirToken();
			E();
			comprobarToken(Corresp.PUNTO_COMA);
			break;
			
		
		// Regla 47 [ Saux -> ( A ) ; ]
		case Corresp.PAR_AB:	// First(( A ) ;)
			escribir(47);
			pedirToken();
			A();
			comprobarToken(Corresp.PAR_CE);
			comprobarToken(Corresp.PUNTO_COMA);
			break;
			
		default:
			System.out.println("ERROR Saux");
			
		}
		
	}
	
	// X + "void"
	private static void Y() {
		
		switch( codTokActual ) {
		
		// Regla 48 [ Y -> lambda ]
		case Corresp.PUNTO_COMA:	// Follow(Y)
			escribir(48);
			break;
			
		// Regla 49 [ Y -> E ]
		case Corresp.PAR_AB:		// First(E)
		case Corresp.ID:
		case Corresp.ENTERO:
		case Corresp.CADENA:
		case Corresp.NEGACION:
			escribir(49);
			E();
			break;
			
		default:
			System.out.println("ERROR Y");
		}
		
	}
	
	// Argumento funcion
	private static void A() {
		
		switch( codTokActual ) {
		
		// Regla 50 [ A -> lambda ]
		case Corresp.PAR_CE:		// Follow(A)
			escribir(50);
			break;
			
		// Regla 51 [ A -> E Ra ]
		case Corresp.PAR_AB:		// First(E Ra)
		case Corresp.ID:
		case Corresp.ENTERO:
		case Corresp.CADENA:
		case Corresp.NEGACION:
			escribir(51);
			E();
			Ra();
			break;
			
		default:
			System.out.println("ERROR A");	
		}
	}
	
	// Resto de argumentos funcion
	private static void Ra() {
		
		switch( codTokActual ) {
		
		// Regla 52 [ Ra -> lambda ]
		case Corresp.PAR_CE:		// Follow(Ra)
			escribir(52);
			break;
			
		// Regla 53 [ Ra -> , E Ra ]
		case Corresp.COMA:			// First(, E Ra)
			escribir(53);
			pedirToken();
			E();
			Ra();
			break;
			
		default:
			System.out.println("ERROR Ar");
		
		}
	}
	
	// Bloque if
	private static void Bi() {
		
		switch( codTokActual ) {
		
		// Regla 54 [ Bi -> S El ]
		case Corresp.ID:		// First(S El)
		case Corresp.PRINT:
		case Corresp.INPUT:
		case Corresp.IF:
		case Corresp.RETURN:
			escribir(54);
			S();
			El();
			break;
			
		// Regla 55 [ Bi -> { Cie } El ]
		case Corresp.LLA_AB:	// First({ Cie } El]
			escribir(55);
			pedirToken();
			Cie();
			comprobarToken(Corresp.LLA_CE);
			El();
			break;
			
		default:
			System.out.println("ERROR B");
		
		}
		
	}
	
	// Cuerpo if-else
	private static void Cie() {
		
		switch( codTokActual ) {
		
		// Regla 56 [ Cie -> lambda ]
		case Corresp.LLA_CE:		// Follow(Cie)
			escribir(56);
			break;
			
		// Regla 57 [ Cie -> S Cie ]
		case Corresp.ID:		// First(S Cie)
		case Corresp.PRINT:
		case Corresp.INPUT:
		case Corresp.IF:
		case Corresp.RETURN:
			escribir(57);
			S();
			Cie();
			break;
			
		default:
			System.out.println("ERROR Cie");
		
		}
		
	}
	
	// Else
	private static void El() {
		
		switch( codTokActual ) {
		
		// Regla 58 [ El -> lambda ]
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
			escribir(58);
			break;
		
		// Regla 59 [ El -> else Be ]
		case Corresp.ELSE:		// Fisrt(else Be)
			escribir(59);
			pedirToken();
			Be();
			break;
			
		default:
			System.out.println("ERROR El");
		
		}
	}
	
	// Bloque else
	private static void Be() {
		
		switch( codTokActual ) {
		
		// Regla 60 [ Be -> S ]
		case Corresp.ID:		// First(S)
		case Corresp.PRINT:
		case Corresp.INPUT:
		case Corresp.IF:
		case Corresp.RETURN:
			escribir(60);
			S();
			break;
			
		// Regla 61 [ Be -> { Cie } ]
		case Corresp.LLA_AB:
			escribir(61);
			pedirToken();
			Cie();
			comprobarToken(Corresp.LLA_CE);
			break;
			
		default:
			System.out.println("Error");
		
		}
		
	}
	
	private static void error( Error e ) {
	}
	
	private static void comprobarToken( int tokenEsperado ) {
		
		// Ok, pedimos otro token y seguimos
		if( codTokActual == tokenEsperado )
			pedirToken();
		
		else
			System.out.println("TO-DO, ERROR TOKEN NO ESPERADO");
	}	
	
	private static void escribir( int numeroRegla ) {
		salidaSint.escribir(numeroRegla + " ");
	}

}
