package tablasim;

import sintsem.tipo.Tipo;
import sintsem.tipo.Tupla;

// Objeto que encapsula una fila de la tabla de simbolos
public class FilaTS {

	private int id;
	private String lex;
	private Tipo tipo;
	private Integer desplazamiento;

	// Esto solo para funciones
	private Tupla parametros;
	private Tipo tipoRetorno;

	// Constructor por defecto

	public void setID(int idFila) { this.id=idFila; }
	public void setLex(String lexema) { this.lex=lexema; }
	public void setTipo(Tipo tipo) { this.tipo=tipo; }
	public void setDesp(Integer desp) { this.desplazamiento=desp; }

	public void setParam(Tupla params) { this.parametros = params; }
	public void setTipoRet(Tipo tipoR) { this.tipoRetorno = tipoR; }

	public int getID() { return id; }
	public String getLex() { return lex; }
	public Tipo getTipo() { return tipo; }

	//TODO implementar toString aquí y no en TS
}
