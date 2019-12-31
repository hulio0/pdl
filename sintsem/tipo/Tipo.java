package sintsem.tipo;

public enum Tipo {
	Cadena(128,"cadena"),
	Entero(2,"entero"),
	Logico(2,"logico"),
	Vacio(null,"-"),
	Funcion(null,"funcion");

	private Integer tamano;
	private String nombre;
	private Tipo(Integer tamano,String nombre) {
		this.tamano=tamano;
		this.nombre=nombre;
	}
	public Integer tamano() { return this.tamano; }
	
	private Tupla param;
	private Tipo tipoRetorno;
	public void setParam(Tupla param) { this.param=param; }
	public void setTipoRetorno(Tipo tipoRetorno) { this.tipoRetorno=tipoRetorno; }
	public Tupla getParam() { return this.param; }
	public Tipo getTipoRetorno() { return this.tipoRetorno; }
	
	@Override
	public String toString() {
		return nombre;
	}

}
