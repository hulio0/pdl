package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test3 {
	
	public static void main(String[] args) {
		
		// Pattern es el objeto que encapsula la expresi�n regular
		Pattern exp = Pattern.compile("[a-z|A-Z]");
		
		// Matcher es un objeto que se crea a partir de una referencia a una expresi�n y de otra cadena.
		// B�sicamente permite probar si la cadena que le hemos pasado hace MATCH con la expresi�n
		Matcher x;
		char c;
		for(int i=(int)'A';i<(int)'z';i++) {
			
			c = (char) i;
			x = exp.matcher(c+"");
			System.out.println( x.matches() );

			
		}
		Pattern p = Pattern.compile("[_|'|/|=|+|>|<|!|,|;|(|)|{|}|-]");
		System.out.println( p.matcher("-").matches() );



		
	}

}
