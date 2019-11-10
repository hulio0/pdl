package lexico;

public class Token {
	
	public static enum Modo { NORMAL, FRIENDLY };
	private static Modo modo = Modo.NORMAL;
	
	private int id;
	private Object atributo;
	
	public Token(int id,Object atributo) {
		this.id=id;
		this.atributo=atributo;
	}
	
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
	
	public static void setMode(Modo m) {
		modo=m;
	}
	
	public int id() { return id; }
	public Object atrib() { return atributo; }

}
