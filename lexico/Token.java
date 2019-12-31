package lexico;

public class Token {
	
	public static enum Modo { NORMAL, FRIENDLY };
	
	// Por defecto, mostramos los códigos numéricos de los tokens
	private static Modo modo = Modo.NORMAL;
	
	private int id;
	private Object atributo;
	
	public Token(int id,Object atributo) {
		this.id=id;
		this.atributo=atributo;
	}
	
	public Token(int id) {
		this.id=id;
		this.atributo="";
	}
	
	@Override
	public String toString() {
		
		switch( modo )
		{
		case NORMAL:
			return "<"+id+","+atributo+">";
			
		case FRIENDLY:
			return "<"+Corresp.friendlyDe(id)+","+atributo+">";
			
		default:
			return "ERROR MODO TOKEN";
		}
				
	}
	
	
	public int id() { return id; }
	public Object atrib() { return atributo; }
	
	public static void setMode(Modo m) { modo=m; }	
	
	public static final int EOF = -1;
	public static Token eof() { return new Token(EOF); }
	
	public boolean esEOF() { return this.id == EOF; }
	
}
