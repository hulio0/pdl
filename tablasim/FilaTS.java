package tablasim;

import errores.GestorErrores;
import sintsem.tipo.Tipo;
import sintsem.tipo.Tipo.Funcion;
import sintsem.tipo.Tupla;

// Objeto que encapsula una fila de la tabla de simbolos
public class FilaTS {

	private int id;
	private String lex;
	private Tipo tipo;
	private Integer desplazamiento;

	// Constructor por defecto

	public void setID(int idFila) { this.id=idFila; }
	public void setLex(String lexema) { this.lex=lexema; }
	public void setTipo(Tipo tipo) { this.tipo=tipo; }
	public void setDesp(Integer desp) { this.desplazamiento=desp; }

	public int getID() { return id; }
	public String getLex() { return lex; }
	public Tipo getTipo() { return tipo; }

	@Override
	public String toString() {
		String res = "*'"+lex+"'\n"
					+"\t+id:"+id+"\n";


		if(GestorErrores.huboError()) {
			return res;
		}

		res+="\t+Tipo:'"+tipo+"'\n";

		if( !tipo.esFuncion() ) {
			res+="\t+Despl:"+desplazamiento+"\n";
		}else {

			Tupla parametros = ((Funcion) tipo).getParams();
			Tipo tipoRetorno = ((Funcion) tipo).getTipoRetorno();

			res+="\t+numParam:"+parametros.numElem()+"\n";

			for(int i=0;i<parametros.numElem();i++) {
				res+="\t\t+TipoParam"+dosDigitos(i+1)+":'"+parametros.get(i)+"'\n";
			}

			res+="\t+TipoRetorno:'"+tipoRetorno+"'\n";

			// Esto de la etiqueta lo exige Draco, who knows why...
			res+="\t+EtiqFuncion:'Et"+lex+dosDigitos(id)+"'\n";
		}

		return res;
	}

	// Muestra el entero con dos digitos. Por ejemplo
	// 2 lo muestra como 02, 8 como 08 , etc.
	private String dosDigitos(int i) {
		return String.format("%02d",i);
	}
}
