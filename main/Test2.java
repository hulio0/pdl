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
		
		// Guardaremos un puntero a CADA LÍNEA
		ArrayList<Long> punteros = new ArrayList<Long>();
		for(int i=0;raf.readLine()!=null;i++) 
			punteros.add(i,raf.getFilePointer());
		
		// Ahora pedimos por consola qué linea se quiere imprimir (hay 6 líneas)
		int res = new Scanner(System.in).nextInt();
		
		// Nos situamos en la línea solicitada
		raf.seek(punteros.get(res-1));
		
		// Imprimimos la línea
		System.out.println( raf.readLine() );
		
		
	}
	
}
