package main;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

public class Test2 {

	public static void main(String[] args) throws IOException {
		
		File f = new File("a.txt");
		RandomAccessFile raf = new RandomAccessFile(f, "r");
		
		// Guardaremos un puntero a CADA L�NEA
		ArrayList<Long> punteros = new ArrayList<Long>();
		for(int i=0;raf.readLine()!=null;i++) 
			punteros.add(i,raf.getFilePointer());
		
		// Ahora pedimos por consola qu� linea se quiere imprimir (hay 6 l�neas)
		int res = new Scanner(System.in).nextInt();
		
		// Nos situamos en la l�nea solicitada
		raf.seek(punteros.get(res-1));
		
		// Imprimimos la l�nea
		System.out.println( raf.readLine() );
		
		
	}
	
}
