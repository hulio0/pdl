package sintsem.tipo2;

import sintsem.tipo.Tupla;

public class Tipo {
	
	private Integer tamano;
	private String nombre;
	
	protected Tipo(Integer tamano,String nombre) {
		this.tamano = tamano;
		this.nombre = nombre;
	}
	
	@Override
	public String toString() { return this.nombre; }
	public Integer getTamano()  { return this.tamano; }
	
	private static final Tipo ENTERO = new Tipo(1,"entero");
	private static final Tipo CADENA = new Tipo(64,"cadena");
	private static final Tipo LOGICO = new Tipo(1,"logico");
	private static final Tipo VACIO = new Tipo(null,"-");
	
	public static Tipo entero() { return ENTERO; }
	public static Tipo cadena() { return CADENA; }
	public static Tipo logico() { return LOGICO; }
	public static Tipo vacio() { return VACIO; }
	
	public static Funcion funcion(Tupla params,Tipo tipoRetorno) { return new Funcion(params,tipoRetorno); } 
	
	private static class Funcion extends Tipo {

		private Tupla params;
		private Tipo tipoRetorno;
		
		public Funcion(Tupla params,Tipo tipoRetorno) {
			super(null,"funcion");
			this.params=params;
			this.tipoRetorno=tipoRetorno;
		}
		
		public Tupla getParams() { return params; }
		public Tipo getTipoRetorno() { return tipoRetorno; }
	}
		
}
