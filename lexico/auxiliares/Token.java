package lexico.auxiliares;

import lexico.Correspondencia;

public class Token {
	
	int id;
	Object atributo;
	
	public Token(int id,Object atributo) {
		this.id=id;
		this.atributo=atributo;
	}
	
	// Devuelve el String del token con su
	// id convertido a su significado
	public String toStringFriendly() {
		return "<"+Correspondencia.de(id)+","+atributo+">";
	}
	
	public String toString() {
		return toStringFriendly();
		//return "<"+id+","+"atributo"+">";
	}

}
