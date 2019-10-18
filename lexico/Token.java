package lexico;

public class Token {
	
	int id;
	Object atributo;
	
	public Token(int id,Object atributo) {
		this.id=id;
		this.atributo=atributo;
	}
	
	// Devuelve el .toString del token con su codigo
	// en su representacion "human friendly"
	public String toStringFriendly() {
		return "<"+Correspondencia.de(id)+","+atributo+">";
	}
	
	public String toString() {
		//return toStringFriendly();
		return "<"+id+","+atributo+">";
	}
	
	public int id() { return id; }
	public Object atrib() { return atributo; }

}
