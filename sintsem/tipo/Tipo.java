package sintsem.tipo;

public enum Tipo {
	Cadena(128),
	Entero(2),
	Logico(2),
	Vacio(null),
	Funcion(null);

	private Integer tamano;
	private Tipo(Integer tamano) {
		this.tamano=tamano;
	}

	public Integer tamano() { return this.tamano; }

}
