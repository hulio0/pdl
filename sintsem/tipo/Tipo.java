package sintsem.tipo;

public enum Tipo {
	Cadena(64,"cadena"),
	Entero(1,"entero"),
	Logico(1,"logico"),
	Vacio(null,"-"),
	Funcion(null,"funcion");

	
	private Integer tamano;		// Tamaño en palabras
	private String nombre;
	private Tipo(Integer tamano,String nombre) {
		this.tamano=tamano;
		this.nombre=nombre;
	}
	public Integer tamano() { return this.tamano; }
	
	@Override
	public String toString() {
		return nombre;
	}
	
	
	// Código si es de tipo función
	private Tupla param;
	private Tipo tipoRetorno;
	public void setParam(Tupla param) { this.param=param; }
	public void setTipoRetorno(Tipo tipoRetorno) { this.tipoRetorno=tipoRetorno; }
	public Tupla getParam() { return this.param; }
	public Tipo getTipoRetorno() { return this.tipoRetorno; }
	
	

}
