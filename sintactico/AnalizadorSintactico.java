package sintactico;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import control.Salida;
import lexico.AnalizadorLexico;
import lexico.Corresp;
import lexico.Token;
import tablasim.TablaS;

public class AnalizadorSintactico {
	
	private static Salida salidaSint;
	
	// Sólo nos interesa saber qué token es
	private static int codTokActual;
	
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
	
	private static final Set<Integer> followPr = new 
			HashSet<Integer>( Arrays.asList( Corresp.PAR_CE ) );
	
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
		case Corresp.PAR_CE:	// Follow(C)
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
			//comprobarToken(Corresp.MAYOR); 
			E2();
			Eaux();
			
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
		
		// Regla 31 [ E5 -> X  E5aux ], unica regla
		X();
		E5aux();
		
	}
	
	// Aux para quitar rec izq de E4
	private static void E5aux() {
		
		switch( codTokActual ) {
		
		// Regla 32 [ E5aux -> lambda ]
		case Corresp.PAR_CE:		// Follow(E5aux)
		case Corresp.PUNTO_COMA:
		case Corresp.COMA:
		case Corresp.MAYOR:
		case Corresp.MENOR:
		case Corresp.MAS:
		case Corresp.MENOS:
			escribir(32);
			break;
			
		// Regla 33 [ E5aux -> ! E5aux ]
		case Corresp.NEGACION:		// First(! E5aux)
			escribir(33);
			pedirToken();
			E5aux();
			break;
			
		default:
			System.out.println("ERROR E5aux");
		}
		
	}

	// Constante o su resultado es una constante
	private static void X() {
		
		switch( codTokActual ) {
		
		// Regla 34 [ X -> ( E ) ]
		case Corresp.PAR_CE:		// First( (E) )
			escribir(34);
			pedirToken();
			E();
			comprobarToken(Corresp.PAR_CE);
			break;
			
		// Regla 35 [ X -> id Xaux ]
		case Corresp.ID:			// First(id Xaux)
			escribir(35);
			pedirToken();
			Xaux();
			break;
			
		// Regla 36 [ X -> entero ]
		case Corresp.ENTERO:
			escribir(36);
			pedirToken();
			break;
			
		// Regla 37 [ X -> cadena ]
		case Corresp.CADENA:
			escribir(37);
			pedirToken();
			break;
			
		default:
			System.out.println("ERROR X");
			
		}	
	}
	
	// Aux para quitar rec. izq. a X
	private static void Xaux() {
		
		switch( codTokActual ) {
		
		// Regla 38 [ Xaux -> lambda ]
		case Corresp.PAR_CE:		// Follow(Xaux)
		case Corresp.PUNTO_COMA:
		case Corresp.COMA:
		case Corresp.MAYOR:
		case Corresp.MENOR:
		case Corresp.MAS:
		case Corresp.MENOS:
		case Corresp.NEGACION:
			escribir(38);
			break;
			
		// Regla 39 [ Xaux -> ( A ) ]
		case Corresp.PAR_AB:		// First( (A) ) 
			escribir(39);
			pedirToken();
			A();
			comprobarToken(Corresp.PAR_CE);
			break;
			
			
		// Regla 40 [ Xaux -> -- ]
		case Corresp.AUTO_DEC:
			escribir(40);
			pedirToken();
			break;
			
		default:
			System.out.println("ERROR Xaux");
			
		}
		
	}
	
	// Sentencia
	private static void S() {
		
		switch( codTokActual ) {
		
		// Regla 41 [ S -> id Saux ]
		case Corresp.ID:		// First(id Saux)
			escribir(41);
			pedirToken();
			Saux();
			break;
			
		// Regla 42 [ S -> print ( E ) ; ]
		case Corresp.PRINT:		// First(print ( E ) ;)
			escribir(42);
			pedirToken();
			comprobarToken(Corresp.PAR_AB);
			E();
			comprobarToken(Corresp.PAR_CE);
			comprobarToken(Corresp.PUNTO_COMA);
			break;
			
			
		// Regla 43 [ S -> input(id); ]
		case Corresp.INPUT:		// First(input(id);)
			escribir(43);
			pedirToken();
			comprobarToken(Corresp.PAR_AB);
			comprobarToken(Corresp.ID);
			comprobarToken(Corresp.PAR_CE);
			comprobarToken(Corresp.PUNTO_COMA);
			break;
		
		
		// Regla 44 [ S -> if ( E ) B ]
		case Corresp.IF:		// First(if ( E ) B)
			escribir(44);
			pedirToken();
			comprobarToken(Corresp.PAR_AB);
			E();
			comprobarToken(Corresp.PAR_CE);
			B();
			break;
			
		// Regla 45 [ S -> return Y ; ]
		case Corresp.RETURN:	// First(return Y ;)
			escribir(45);
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
		
		// Regla 46 [ Saux -> lambda ]
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
			escribir(46);
			break;
			
		// Regla 47 [ Saux -> = E ; ]
		case Corresp.IGUAL:		// First(= E ;)
			escribir(47);
			pedirToken();
			E();
			comprobarToken(Corresp.PUNTO_COMA);
			break;
			
		
		// Regla 48 [ Saux -> ( A ) ; ]
		case Corresp.PAR_AB:	// First(( A ) ;)
			escribir(48);
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
		
		// Regla 49 [ Y -> lambda ]
		case Corresp.PUNTO_COMA:	// Follow(Y)
			escribir(49);
			break;
			
		// Regla 50 [ Y -> E ]
		case Corresp.PAR_AB:		// First(E)
		case Corresp.ID:
		case Corresp.ENTERO:
		case Corresp.CADENA:
			escribir(50);
			E();
			break;
			
		default:
			System.out.println("ERROR Y");
		}
		
	}
	
	// Argumento funcion
	private static void A() {
		
		switch( codTokActual ) {
		
		// Regla 51 [ A -> lambda ]
		case Corresp.PAR_CE:		// Follow(A)
			escribir(51);
			break;
			
		// Regla 52 [ A -> E Ar ]
		case Corresp.PAR_AB:		// First(E Ar)
		case Corresp.ID:
		case Corresp.ENTERO:
		case Corresp.CADENA:
			escribir(52);
			E();
			Ar();
			break;
			
		default:
			System.out.println("ERROR A");	
		}
	}
	
	// Resto de argumentos funcion
	private static void Ar() {
		
		switch( codTokActual ) {
		
		// Regla 53 [ Ar -> lambda ]
		case Corresp.PAR_CE:		// Follow(Ar)
			escribir(53);
			break;
			
		// Regla 54 [ Ar -> , E Ar ]
		case Corresp.COMA:			// First(, E Ar)
			escribir(54);
			pedirToken();
			E();
			Ar();
			break;
			
		default:
			System.out.println("ERROR Ar");
		
		}
	}
	
	// Bloque if-else
	private static void B() {
		
		switch( codTokActual ) {
		
		// Regla 55 [ B -> S El ]
		case Corresp.ID:		// First(S El)
		case Corresp.PRINT:
		case Corresp.INPUT:
		case Corresp.IF:
		case Corresp.RETURN:
			escribir(55);
			S();
			El();
			break;
			
		// Regla 56 [ B -> { Cie } El ]
		case Corresp.LLA_AB:	// First({ Cie } El]
			escribir(56);
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
		
		// Regla 57 [ Cie -> lambda ]
		case Corresp.LLA_CE:		// Follow(Cie)
			escribir(57);
			break;
			
		// Regla 58 [ Cie -> S Cie ]
		case Corresp.ID:		// First(S Cie)
		case Corresp.PRINT:
		case Corresp.INPUT:
		case Corresp.IF:
		case Corresp.RETURN:
			escribir(58);
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
		
		// Regla 59 [ El -> lambda ]
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
			escribir(59);
			break;
		
		// Regla 60 [ El -> else B ]
		case Corresp.ELSE:		// Fisrt(else B)
			escribir(60);
			pedirToken();
			B();
			break;
			
		default:
			System.out.println("ERROR El");
		
		}
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
