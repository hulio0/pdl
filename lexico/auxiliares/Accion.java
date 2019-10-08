package lexico.auxiliares;

// Enumerado que recoge TODAS las acciones semanticas
// que puede llegar a ejecutar el analizador lexico
public enum Accion {
	LEER, 
	CONCATENAR, 
	DECLARAR_NUM, 
	INCREMENTAR_NUM,
	GENERAR_PR_ID,
	GENERAR_ENTERO,
	GENERAR_CADENA, 
	GENERAR_MENOS,
	GENERAR_POS_DECREMENTO, 
	GENERAR_IGUAL, 
	GENERAR_MAS,
	GENERAR_MAYOR,
	GENERAR_MENOR,
	GENERAR_DISTINTO,
	GENERAR_COMA,
	GENERAR_PUNTO_COMA,
	GENERAR_PARENTESIS_AB,
	GENERAR_PARENTESIS_CE,
	GENERAR_LLAVE_AB,
	GENERAR_LLAVE_CE,
	ERR_CARACTER_NO_PERMITIDO,
	ERR_COMENTARIO_MAL_FORMADO,
	ERR_CADENA_NO_TERMINADA,
	TERMINAR_EJECUCION
}
