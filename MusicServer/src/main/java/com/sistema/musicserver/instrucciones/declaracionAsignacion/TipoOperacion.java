package com.sistema.musicserver.instrucciones.declaracionAsignacion;

import java.io.Serializable;

public enum TipoOperacion implements Serializable{
    SUMA,
    RESTA,
    MULTIPLICACION,
    DIVISION,
    MODULO,
    POTENCIA,
    MAYORQ,
    MENORQ,
    MAYOROI,
    MENOROI,
    NOTEQUALS,
    EQUALS,
    OR,
    AND,
    NAND,
    NOR,
    XOR
}
