package main;

import sintsem.tipo.Tipo;
import sintsem.tipo.Tupla;

public class Test {

	public static void main(String[] args) {

		Tipo t = Tipo.entero();
		Tupla param = Tupla.vacia();
		
		Tipo fun = Tipo.funcion(param, t);
		
		System.out.println( fun.esFuncion() );
		
	}

}
