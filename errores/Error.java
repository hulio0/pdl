package errores;

// El gestor de errores recibe implementaciones de
// esta interfaz. Lo unico que se exige es que cada 
// error devuelva un string que contenga su descripcion
public interface Error {
	String getDesc();
}
