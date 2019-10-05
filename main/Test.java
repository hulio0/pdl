package main;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Test {
	
	public static void main(String[] args) {
		
		// Encapsulamos nuestro fichero en un objeto RandomAccessFile, que tiene m�todos que nos
		// permite coger un puntero a donde estamos situados actualmente en el fichero. De esta forma,
		// podemos continuar donde lo "dejamos" si tenemos que retornar en alg�n momento
		File f = new File("a.txt");
		RandomAccessFile raf = null;
		try{ raf = new RandomAccessFile(f, "r"); } catch(IOException e) {}
		
		// Primero recorreremos el fichero completo y dejaremos un puntero en la cuarta l�nea
		int i=1;
		long puntero=-1;
		try {
			while(raf.readLine()!=null ) {
				i++;
				
				if(i==4) puntero = raf.getFilePointer();
			}
		} catch(IOException e) {}
		
		
		// Ahora imprimimos esa l�nea a ver si ha funcionado. Primero tenemos que movernos al puntero
		try { raf.seek(puntero); } catch(IOException e) {}
		
		// Ahora imprimimos
		try { System.out.println( raf.readLine() ); } catch(IOException e) {}
		
	}

}
