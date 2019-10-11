package lexico.auxiliares;

// Objeto que encapsula una fila de la tabla de símbolos

// TO-DO: Completar los atributos (las columnas, importante los getters)
public class FilaTS {
	
	private int id;
	private String lex;
	
	
	// Constructor por defecto
	
	
	
	public void setID(int idFila) { this.id=idFila; }
	public void setLex(String lexema) { this.lex=lexema; }

	public int getID() { return id; }
	public String getLex() { return lex; }

}
