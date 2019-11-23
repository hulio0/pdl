package sintactico;

// Lista de situaciones en las que se puede
// encontrar trabajando el analizador sintáctico
public enum Contexto {
	
	DECLARACION_VAR,
	TIPO_FUNCION,
	DECLARACION_FUN,
	PARAMETRO_FUN,
	CUERPO_FUN,
	EXPRESION,
	SENTENCIA,
	ARGUMENTO_FUN
}
