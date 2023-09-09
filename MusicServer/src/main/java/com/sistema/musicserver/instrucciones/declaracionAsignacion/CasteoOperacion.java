package com.sistema.musicserver.instrucciones.declaracionAsignacion;

import com.sistema.musicserver.errors.ErrorSemantico;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class CasteoOperacion implements Serializable {

    private Dato dato1;
    private Dato dato2;
    private TipoOperacion tipoOp;
    private ArrayList<ErrorSemantico> errorsSemanticos;

    public CasteoOperacion(ArrayList<ErrorSemantico> errorsSemanticos) {
        this.errorsSemanticos = errorsSemanticos;
    }

    public Dato resultOp(Dato datoLeft, Dato datoRight, TipoOperacion tipoOp) {
        this.dato1 = datoLeft;
        this.dato2 = datoRight;
        Dato datoResult = new Dato(true, 0, TipoDato.ENTERO);
        datoResult.setToken(dato2.getToken());
        if (!this.datoInicializado()) {
            return datoResult;
        }
        switch (tipoOp) {
            case SUMA:
                datoResult = operacionSuma();
                break;
            case RESTA:
                datoResult = operacionResta();
                break;
            case MULTIPLICACION:
                datoResult = operacionMultpli();
                break;
            case DIVISION:
                datoResult = operacionDiv();
                break;
            case MODULO:
                datoResult = operacionMod();
                break;
            case POTENCIA:
                datoResult = operacionPotencia();
                break;
            case MAYORQ:
                datoResult = operacionMayorQ();
                break;
            case MENORQ:
                datoResult = operacionMenorQ();
                break;
            case MAYOROI:
                datoResult = operacionMayorQueOIgual();
                break;
            case MENOROI:
                datoResult = operacionMenorQueOIgual();
                break;
            case NOTEQUALS:
                datoResult = operacionDiferenteQue();
                break;
            case EQUALS:
                datoResult = operacionIgualA();
                break;
            case OR:
                datoResult = operacionOR();
                break;
            case AND:
                datoResult = operacionAND();
                break;
            case NAND:
                datoResult = operacionNAND();
                break;
            case NOR:
                datoResult = operacionNOR();
                break;
            case XOR:
                datoResult = operacionXOR();
                break;
            case ISNULO:
                datoResult = isNulo();
                break;
            case NOT:
                datoResult = not();
                break;
        }
        return datoResult;
    }

    private Dato not() {
        Dato datoResult = new Dato(true, 0, TipoDato.BOOLEAN);
        if (this.dato2.getTipoDato() == TipoDato.BOOLEAN) {
            datoResult.setBooleano(!this.dato2.isBooleano());
        }else{
            errorsSemanticos.add(new ErrorSemantico(this.dato2.getToken(), "El operador NOT solo hacepta datos booleanos para su funcionamiento"));
        }
        return datoResult;
    }

    private Dato isNulo() {
        Dato datoResult = new Dato(true, 0, TipoDato.BOOLEAN);
        datoResult.setBooleano(this.dato2.isInicializado());
        return datoResult;
    }

    private Dato operacionMayorQ() {
        Dato datoResult = new Dato(true, 0, TipoDato.ENTERO);
        switch (this.dato1.getTipoDato()) {
            case ENTERO:
                switch (this.dato2.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getNumero() > this.dato2.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getNumero() > this.dato2.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion > (mayorQ) hacepta un entero compararado con entero o decimal"));
                        break;

                }
                break;
            case DECIMAL:
                switch (this.dato2.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getDecimal() > this.dato2.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getDecimal() > this.dato2.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion > (mayorQ) hacepta un Decimal compararado con entero o decimal"));
                        break;
                }
                break;
            case CADENA:
                switch (this.dato2.getTipoDato()) {
                    case CADENA:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getCadena().length() > this.dato2.getCadena().length());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion > (mayorQ) hacepta una Cadena compararado con otra cadena"));
                        break;
                }
                break;
            default:
                //error
                this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion > (mayorQ) No acepta los valores ingresados, la comparacion no se puede efectuar"));
                break;
        }
        return datoResult;

    }

    private Dato operacionMayorQueOIgual() {
        Dato datoResult = new Dato(true, 0, TipoDato.ENTERO);
        switch (this.dato1.getTipoDato()) {
            case ENTERO:
                switch (this.dato2.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getNumero() >= this.dato2.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getNumero() >= this.dato2.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion >= (mayor Que o igual) hacepta un entero compararado con entero o decimal"));
                        break;

                }
                break;
            case DECIMAL:
                switch (this.dato2.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getDecimal() >= this.dato2.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getDecimal() >= this.dato2.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion  >= (mayor Que o igual) hacepta un Decimal compararado con entero o decimal"));
                        break;
                }
                break;
            case CADENA:
                switch (this.dato2.getTipoDato()) {
                    case CADENA:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getCadena().length() >= this.dato2.getCadena().length());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion >= (mayor Que o igual) hacepta una Cadena compararado con otra cadena"));
                        break;
                }
                break;
            default:
                //error
                this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion >= (mayor Que o igual) No acepta los valores ingresados, la comparacion no se puede efectuar"));
                break;
        }
        return datoResult;

    }

    private Dato operacionMenorQ() {
        Dato datoResult = new Dato(true, 0, TipoDato.ENTERO);
        switch (this.dato1.getTipoDato()) {
            case ENTERO:
                switch (this.dato2.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getNumero() < this.dato2.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getNumero() < this.dato2.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion < (menor que) hacepta un entero compararado con entero o decimal"));
                        break;

                }
                break;
            case DECIMAL:
                switch (this.dato2.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getDecimal() < this.dato2.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getDecimal() < this.dato2.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion < (menor que) hacepta un Decimal compararado con entero o decimal"));
                        break;
                }
                break;
            case CADENA:
                switch (this.dato2.getTipoDato()) {
                    case CADENA:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getCadena().length() < this.dato2.getCadena().length());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion < (menor que) hacepta una Cadena compararado con otra cadena"));
                        break;
                }
                break;
            default:
                //error
                this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion < (Menor que) No acepta los valores ingresados, la comparacion no se puede efectuar"));
                break;
        }
        return datoResult;

    }

    private Dato operacionMenorQueOIgual() {
        Dato datoResult = new Dato(true, 0, TipoDato.ENTERO);
        switch (this.dato1.getTipoDato()) {
            case ENTERO:
                switch (this.dato2.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getNumero() <= this.dato2.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getNumero() <= this.dato2.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion <= (menor que o igual) hacepta un entero compararado con entero o decimal"));
                        break;

                }
                break;
            case DECIMAL:
                switch (this.dato2.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getDecimal() <= this.dato2.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getDecimal() <= this.dato2.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion <= (menor que o igual) hacepta un Decimal compararado con entero o decimal"));
                        break;
                }
                break;
            case CADENA:
                switch (this.dato2.getTipoDato()) {
                    case CADENA:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getCadena().length() <= this.dato2.getCadena().length());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion <= (menor que o igual) hacepta una Cadena compararado con otra cadena"));
                        break;
                }
                break;
            default:
                //error
                this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion <= (menor que o igual) No acepta los valores ingresados, la comparacion no se puede efectuar"));
                break;
        }
        return datoResult;

    }

    private Dato operacionDiferenteQue() {
        Dato datoResult = new Dato(true, 0, TipoDato.ENTERO);
        switch (this.dato1.getTipoDato()) {
            case ENTERO:
                switch (this.dato2.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getNumero() != this.dato2.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getNumero() != this.dato2.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion != (Diferente Que) hacepta un entero compararado con entero o decimal"));
                        break;

                }
                break;
            case DECIMAL:
                switch (this.dato2.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getDecimal() != this.dato2.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getDecimal() != this.dato2.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion  != (Diferente Que) hacepta un Decimal compararado con entero o decimal"));
                        break;
                }
                break;
            case CADENA:
                switch (this.dato2.getTipoDato()) {
                    case CADENA:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(!this.dato1.getCadena().equals(this.dato2.getCadena()));
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion != (Diferente Que) hacepta una Cadena compararado con otra cadena"));
                        break;
                }
                break;
            case CHAR:
                switch (this.dato2.getTipoDato()) {
                    case CHAR:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getCaracter() != this.dato2.getCaracter());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion != (Diferente Que) hacepta una Cadena compararado con otra cadena"));
                        break;
                }
                break;
            default:
                //error
                this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion) != (Diferente Que) No acepta los valores ingresados, la comparacion no se puede efectuar"));
                break;
        }
        return datoResult;

    }

    private Dato operacionIgualA() {
        Dato datoResult = new Dato(true, 0, TipoDato.ENTERO);
        switch (this.dato1.getTipoDato()) {
            case ENTERO:
                switch (this.dato2.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getNumero() == this.dato2.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getNumero() == this.dato2.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion == (Igual A) hacepta un entero compararado con entero o decimal"));
                        break;

                }
                break;
            case DECIMAL:
                switch (this.dato2.getTipoDato()) {
                    case ENTERO:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getDecimal() == this.dato2.getNumero());
                        break;
                    case DECIMAL:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getDecimal() == this.dato2.getDecimal());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion   == (Igual A) hacepta un Decimal compararado con entero o decimal"));
                        break;
                }
                break;
            case CADENA:
                switch (this.dato2.getTipoDato()) {
                    case CADENA:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getCadena().equals(this.dato2.getCadena()));
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion  == (Igual A) hacepta una Cadena compararado con otra cadena"));
                        break;
                }
                break;
            case CHAR:
                switch (this.dato2.getTipoDato()) {
                    case CHAR:
                        datoResult.setTipoDato(TipoDato.BOOLEAN);
                        datoResult.setBooleano(this.dato1.getCaracter() == this.dato2.getCaracter());
                        break;
                    default:
                        //error
                        this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion  == (Igual A) hacepta una Cadena compararado con otra cadena"));
                        break;
                }
                break;
            default:
                //error
                this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "La operacion)  == (Igual A) No acepta los valores ingresados, la comparacion no se puede efectuar"));
                break;
        }
        return datoResult;

    }

    private Dato operacionOR() {
        Dato datoResult = new Dato(true, 0, TipoDato.ENTERO);
        datoResult.setTipoDato(TipoDato.BOOLEAN);
        if (this.dato1.getTipoDato() == TipoDato.BOOLEAN && this.dato2.getTipoDato() == TipoDato.BOOLEAN) {
            datoResult.setBooleano(this.dato1.isBooleano() || this.dato2.isBooleano());
        } else {
            this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "El Operador OR solo acepta comparacion entre expresiones Booleanas"));
        }

        return datoResult;
    }

    private Dato operacionAND() {
        Dato datoResult = new Dato(true, 0, TipoDato.ENTERO);
        datoResult.setTipoDato(TipoDato.BOOLEAN);
        if (this.dato1.getTipoDato() == TipoDato.BOOLEAN && this.dato2.getTipoDato() == TipoDato.BOOLEAN) {
            datoResult.setBooleano(this.dato1.isBooleano() && this.dato2.isBooleano());
        } else {
            this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "El Operador AND solo acepta comparacion entre expresiones Booleanas"));
        }

        return datoResult;
    }

    private Dato operacionNAND() {
        Dato datoResult = new Dato(true, 0, TipoDato.ENTERO);
        datoResult.setTipoDato(TipoDato.BOOLEAN);
        if (this.dato1.getTipoDato() == TipoDato.BOOLEAN && this.dato2.getTipoDato() == TipoDato.BOOLEAN) {
            datoResult.setBooleano(!(this.dato1.isBooleano() && this.dato2.isBooleano()));
        } else {
            this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "El Operador NAND solo acepta comparacion entre expresiones Booleanas"));
        }

        return datoResult;
    }

    private Dato operacionNOR() {
        Dato datoResult = new Dato(true, 0, TipoDato.ENTERO);
        datoResult.setTipoDato(TipoDato.BOOLEAN);
        if (this.dato1.getTipoDato() == TipoDato.BOOLEAN && this.dato2.getTipoDato() == TipoDato.BOOLEAN) {
            datoResult.setBooleano(!(this.dato1.isBooleano() || this.dato2.isBooleano()));
        } else {
            this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "El Operador NOR solo acepta comparacion entre expresiones Booleanas"));
        }

        return datoResult;
    }

    private Dato operacionXOR() {
        Dato datoResult = new Dato(true, 0, TipoDato.ENTERO);
        datoResult.setTipoDato(TipoDato.BOOLEAN);
        if (this.dato1.getTipoDato() == TipoDato.BOOLEAN && this.dato2.getTipoDato() == TipoDato.BOOLEAN) {
            datoResult.setBooleano((this.dato1.isBooleano() && !this.dato2.isBooleano()) || (!this.dato1.isBooleano() && this.dato2.isBooleano()));
        } else {
            this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "El Operador NOR solo acepta comparacion entre expresiones Booleanas"));
        }

        return datoResult;
    }

    private Dato operacionSuma() {
        Dato datoResult = new Dato(true, 0, TipoDato.ENTERO);
        switch (this.dato1.getTipoDato()) {
            case CADENA:
                datoResult.setTipoDato(TipoDato.CADENA);
                datoResult.setCadena(sumaStringDato());
                return datoResult;
            case BOOLEAN:
                datoResult.setTipoDato(TipoDato.BOOLEAN);
                datoResult.setBooleano(this.sumaBooleanDato());
                return datoResult;
            case CHAR:
                datoResult.setTipoDato(TipoDato.CHAR);
                datoResult.setCaracter(this.sumaCharDatp());
                return datoResult;
            case DECIMAL:
                datoResult.setTipoDato(TipoDato.DECIMAL);
                datoResult.setDecimal(this.sumaDecimalDato());
                return datoResult;
            default:
                datoResult.setTipoDato(TipoDato.ENTERO);
                datoResult.setNumero(this.sumaEnteroDato());
                return datoResult;
        }

    }

    private Dato operacionResta() {
        Dato datoResult = new Dato(true, 0, TipoDato.ENTERO);
        if (dato1.getTipoDato() == TipoDato.DECIMAL) {
            switch (this.dato2.getTipoDato()) {
                case CADENA:
                    //no de puede restar numero - cadena
                    this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "no de puede restar un valor numero - cadena"));
                    break;
                case BOOLEAN:
                    int tmp = this.dato2.isBooleano() ? 1 : 0;
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getDecimal() - tmp) * 1e6));
                    return datoResult;
                case CHAR:
                    int tmpc = this.dato2.getCaracter();
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getDecimal() - tmpc) * 1e6));
                    return datoResult;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getDecimal() - dato2.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getDecimal() - dato2.getNumero()) * 1e6));
                    return datoResult;
            }
        }

        if (dato1.getTipoDato() == TipoDato.ENTERO) {
            switch (this.dato2.getTipoDato()) {
                case CADENA:
                    //no de puede restar numero - cadena
                    this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "no de puede restar un valor numero - cadena"));

                    break;
                case BOOLEAN:
                    int tmp = this.dato2.isBooleano() ? 1 : 0;
                    datoResult.setNumero(dato1.getNumero() - tmp);
                    return datoResult;
                case CHAR:
                    int tmpc = this.dato2.getCaracter();
                    datoResult.setNumero(dato1.getNumero() - tmpc);
                    return datoResult;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getNumero() - dato2.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setNumero(dato1.getNumero() - dato2.getNumero());
                    return datoResult;
            }

        }
        this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "no de puede restar un valor no numerico - otro valor"));

        return datoResult;

    }

    private Dato operacionMultpli() {
        Dato datoResult = new Dato(true, 0, TipoDato.ENTERO);
        if (dato1.getTipoDato() == TipoDato.DECIMAL) {
            switch (this.dato2.getTipoDato()) {
                case CADENA:
                    //no de puede multiplicar numero * cadena
                    this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "no de puede multiplicar un valor numero * cadena"));

                    break;
                case BOOLEAN:
                    int tmp = this.dato2.isBooleano() ? 1 : 0;
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getDecimal() * tmp) * 1e6));
                    return datoResult;
                case CHAR:
                    int tmpc = this.dato2.getCaracter();
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getDecimal() * tmpc) * 1e6));
                    return datoResult;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getDecimal() * dato2.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getDecimal() * dato2.getNumero()) * 1e6));
                    return datoResult;
            }
        }

        if (dato1.getTipoDato() == TipoDato.ENTERO) {
            switch (this.dato2.getTipoDato()) {
                case CADENA:
                    this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "no de puede multiplicar un valor numero * cadena"));
                    break;
                case BOOLEAN:
                    int tmp = this.dato2.isBooleano() ? 1 : 0;
                    datoResult.setNumero(dato1.getNumero() * tmp);
                    return datoResult;
                case CHAR:
                    int tmpc = this.dato2.getCaracter();
                    datoResult.setNumero(dato1.getNumero() * tmpc);
                    return datoResult;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getNumero() * dato2.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setNumero(dato1.getNumero() * dato2.getNumero());
                    return datoResult;
            }

        }
        this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "no de puede multiplicar un valor no numerico * otro valor"));
        return datoResult;

    }

    private Dato operacionDiv() {
        Dato datoResult = new Dato(true, 0, TipoDato.ENTERO);
        if (dato1.getTipoDato() == TipoDato.DECIMAL) {
            switch (this.dato2.getTipoDato()) {
                case CADENA:
                    this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "no de puede dividir un valor numero / cadena"));

                    break;
                case BOOLEAN:
                    int tmp = this.dato2.isBooleano() ? 1 : 0;
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getDecimal() / tmp) * 1e6));
                    return datoResult;
                case CHAR:
                    int tmpc = this.dato2.getCaracter();
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getDecimal() / tmpc) * 1e6));
                    return datoResult;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getDecimal() / dato2.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getDecimal() / dato2.getNumero()) * 1e6));
                    return datoResult;
            }
        }

        if (dato1.getTipoDato() == TipoDato.ENTERO) {
            switch (this.dato2.getTipoDato()) {
                case CADENA:
                    //no de puede restar numero - cadena
                    this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "no de puede dividir un valor numero / cadena"));

                    break;
                case BOOLEAN:
                    int tmp = this.dato2.isBooleano() ? 1 : 0;
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getNumero() / tmp) * 1e6));
                    return datoResult;
                case CHAR:
                    int tmpc = this.dato2.getCaracter();
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getNumero() / tmpc) * 1e6));
                    return datoResult;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getNumero() / dato2.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getNumero() / dato2.getNumero()) * 1e6));
                    return datoResult;
            }

        }
        this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "no de puede dividir un valor no numero / otro valor"));
        return datoResult;

    }

    private Dato operacionMod() {
        Dato datoResult = new Dato(true, 0, TipoDato.ENTERO);
        if (dato1.getTipoDato() == TipoDato.DECIMAL) {
            switch (this.dato2.getTipoDato()) {
                case CADENA:
                    this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "no de puede realizar Mod a un valor numero / cadena"));

                    break;
                case BOOLEAN:
                    int tmp = this.dato2.isBooleano() ? 1 : 0;
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getDecimal() % tmp) * 1e6));
                    return datoResult;
                case CHAR:
                    int tmpc = this.dato2.getCaracter();
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getDecimal() % tmpc) * 1e6));
                    return datoResult;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getDecimal() % dato2.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getDecimal() % dato2.getNumero()) * 1e6));
                    return datoResult;
            }
        }

        if (dato1.getTipoDato() == TipoDato.ENTERO) {
            switch (this.dato2.getTipoDato()) {
                case CADENA:
                    //no de puede restar numero - cadena
                    this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "no de puede realizar Mod a un valor numero / cadena"));

                    break;
                case BOOLEAN:
                    int tmp = this.dato2.isBooleano() ? 1 : 0;
                    datoResult.setNumero(dato1.getNumero() % tmp);
                    return datoResult;
                case CHAR:
                    int tmpc = this.dato2.getCaracter();
                    datoResult.setNumero(dato1.getNumero() % tmpc);
                    return datoResult;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round((dato1.getNumero() % dato2.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setNumero(dato1.getNumero() % dato2.getNumero());
                    return datoResult;
            }

        }
        this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "no de puede realizar MOD a un valor no numero / otro valor"));
        return datoResult;

    }

    private Dato operacionPotencia() {
        Dato datoResult = new Dato(true, 0, TipoDato.ENTERO);
        if (dato1.getTipoDato() == TipoDato.DECIMAL) {
            switch (this.dato2.getTipoDato()) {
                case CADENA:
                    this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "no de puede realizar Potnecia a un valor numero ^ cadena"));

                    break;
                case BOOLEAN:
                    this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "no de puede realizar Potnecia a un valor numero ^ boolean"));
                    break;
                case CHAR:
                    this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "no de puede realizar Potnecia a un valor numero ^ char"));
                    break;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round(Math.pow(dato1.getDecimal(), dato2.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round(Math.pow(dato1.getDecimal(), dato2.getNumero()) * 1e6));
                    return datoResult;
            }
        }

        if (dato1.getTipoDato() == TipoDato.ENTERO) {
            switch (this.dato2.getTipoDato()) {
                case CADENA:
                    this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "no de puede realizar Potnecia a un valor numero ^ cadena"));

                    break;
                case BOOLEAN:
                    this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "no de puede realizar Potnecia a un valor numero ^ boolean"));
                    break;
                case CHAR:
                    this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "no de puede realizar Potnecia a un valor numero ^ char"));
                    break;
                case DECIMAL:
                    datoResult.setTipoDato(TipoDato.DECIMAL);
                    datoResult.setDecimal(Math.round(Math.pow(dato1.getNumero(), dato2.getDecimal()) * 1e6));
                    return datoResult;
                default:
                    datoResult.setNumero((int) Math.pow(dato1.getNumero(), dato2.getNumero()));
                    return datoResult;
            }

        }
        this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "no de puede realizar POTENCIA a un valor no numero ^ otro valor"));
        return datoResult;

    }

    /**
     * suma de un string + otro cualquier tipo de dato por los tnato se
     * concatena
     *
     * @return
     */
    private String sumaStringDato() {
        switch (this.dato2.getTipoDato()) {
            case CADENA:
                return dato1.getCadena() + dato2.getCadena();
            case BOOLEAN:
                String tmp = dato2.isBooleano() ? "verdadero" : "falso";
                return dato1.getCadena() + tmp;
            case CHAR:
                return dato1.getCadena() + dato2.getCaracter();
            case DECIMAL:
                return dato1.getCadena() + dato2.getCaracter();
            default:
                return dato1.getCadena() + dato2.getNumero();
        }
    }

    private Boolean sumaBooleanDato() {
        //error no se puese sumar un boolean + cualquier dato
        this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "error no se puese sumar un boolean + cualquier dato"));

        return dato1.isBooleano();
    }

    private char sumaCharDatp() {
        //error no se puese dumar un char + cualquier dato
        this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "error no se puese dumar un char + cualquier dato"));

        return dato1.getCaracter();
    }

    private Double sumaDecimalDato() {
        switch (this.dato2.getTipoDato()) {
            case CADENA:
                //error no se puede sumar una decimal con una cadena
                this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "no se puede sumar una decimal con una cadena"));

                return dato1.getDecimal();
            case BOOLEAN:
                double tmp = dato2.isBooleano() ? 1 : 0;
                return dato1.getDecimal() + tmp;
            case CHAR:
                return dato1.getDecimal() + dato2.getCaracter();
            case DECIMAL:
                return dato1.getDecimal() + dato2.getDecimal();
            default:
                return dato1.getDecimal() + dato2.getNumero();
        }
    }

    private int sumaEnteroDato() {
        switch (this.dato2.getTipoDato()) {
            case CADENA:
                //error no se puede sumar una decimal con una cadena
                this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "no se puede sumar una Numero con una cadena"));

                return dato1.getNumero();
            case BOOLEAN:
                int tmp = dato2.isBooleano() ? 1 : 0;
                return dato1.getNumero() + tmp;
            case CHAR:
                return dato1.getNumero() + dato2.getCaracter();
            case DECIMAL:
                int tmps = (int) Math.round(dato2.getDecimal());
                return dato1.getNumero() + tmps;
            default:
                return dato1.getNumero() + dato2.getNumero();
        }
    }

    private boolean datoInicializado() {
        boolean inicializado = true;
        if (tipoOp != TipoOperacion.ISNULO) {
            if (!this.dato1.isInicializado()) {
                //error variable no inicalizada
                this.errorsSemanticos.add(new ErrorSemantico(dato1.getToken(), "Variable no inicializada"));
                inicializado = false;
            }
            if (!this.dato2.isInicializado()) {
                //error variable no inicalizada
                this.errorsSemanticos.add(new ErrorSemantico(dato2.getToken(), "Variable no inicializada"));
                inicializado = false;
            }
        }
        return inicializado;
    }

    public Dato getDato1() {
        return dato1;
    }

    public void setDato1(Dato dato1) {
        this.dato1 = dato1;
    }

    public Dato getDato2() {
        return dato2;
    }

    public void setDato2(Dato dato2) {
        this.dato2 = dato2;
    }

    public TipoOperacion getTipoOp() {
        return tipoOp;
    }

    public void setTipoOp(TipoOperacion tipoOp) {
        this.tipoOp = tipoOp;
    }

    public ArrayList<ErrorSemantico> getErrorsSemanticos() {
        return errorsSemanticos;
    }

    public void setErrorsSemanticos(ArrayList<ErrorSemantico> errorsSemanticos) {
        this.errorsSemanticos = errorsSemanticos;
    }

}
