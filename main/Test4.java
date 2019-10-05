package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

import lexico.auxiliares.LazyChar;

public class Test4 {
	
	public static void main(String[] args) throws IOException {
		
		
		File f = new File("b.txt");
		FileReader fr = new FileReader(f);
		
		int c;
		String s;
		while( (c=fr.read())!=-1 ) {
			s = ((char) c)+"";
			System.out.println( LazyChar.of((char) c));
		}
		
				
	}

}
