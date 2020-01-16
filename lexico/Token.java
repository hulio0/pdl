package lexico;

public class Token {
	
	// El modo Friendly muestra, en vez del código numérico del token
	// pues su significado (en vez del código 6 monstraría INT)
	public static enum Modo { Normal, Friendly };
	
	// Por defecto, estamos en modo normal
	private static Modo modo = Modo.Normal;
	public static void setFriendlyMode() { modo=Modo.Friendly; }
	
	private int id;
	private Object atributo;
	
	public Token(int id,Object atributo) {
		this.id=id;
		this.atributo=atributo;
	}
	
	public Token(int id) {
		this(id,"-");
	}
	
	@Override
	public String toString() {
		
		String codToken="";
		
		switch(modo) {
		
		case Normal:
			codToken = id+"";
			break;
			
		case Friendly:
			codToken = Corresp.friendlyDe(id);
			break;
		
		}
		
		return "<"+codToken+","+atributo+">";
	}
	
	public int id() { return id; }
	public Object atrib() { return atributo; }
		
	
	public static final int EOF = -1;
	public static Token eof() { return new Token(EOF); }
	public boolean esEOF() { return this.id == EOF; }
	
}
