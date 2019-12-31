package main;

import sintsem.tipo.Tipo;
import sintsem.tipo.Tupla;

public class Test {

	public static void main(String[] args) {

		Tupla t1 = new Tupla(Tipo.Entero);		
		Tupla t2 = new Tupla(Tipo.Cadena,t1);
		Tupla t2Otra = new Tupla(Tipo.Cadena,t1);
		
		System.out.println( t2 );
		
	}

}
