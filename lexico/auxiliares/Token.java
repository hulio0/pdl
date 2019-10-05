package lexico.auxiliares;

import lexico.Correspondencia;

public class Token {
	
	int id;
	int atributo;  // Suele ser pos. en x sitio
	
	public Token(int id,int atributo) {
		this.id=id;
		this.atributo=atributo;
	}
	
	// Devuelve el String del token con su
	// id convertido a su significado
	public String toStringFriendly() {
		return "<"+Correspondencia.de(id)+","+atributo+">";
	}
	
	public String toString() {
		return "<"+id+","+"atributo"+">";
	}

}
