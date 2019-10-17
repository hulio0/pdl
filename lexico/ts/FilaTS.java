package lexico.ts;

// Objeto que encapsula una fila de la tabla de símbolos

// TO-DO: Completar los atributos (las columnas, importante los getters)
public class FilaTS {
	
	private String lexema;
	
	public FilaTS(String lexema) {
		this.lexema=lexema;
	}
	
	public String lex() { return lexema; }

}
