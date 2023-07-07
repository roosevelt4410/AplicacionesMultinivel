package co.edu.usbcali.airlinesapp.utils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class ValidationsUtil {

	public static void isNull(Object valor, String mensaje) throws Exception {
        if(valor == null) throw new Exception(mensaje);
    }

	public static void stringIsNullOrBlank(String valor, String mensaje) throws Exception {
	    isNull(valor, mensaje);
	    if (valor.trim().isEmpty()) {
	        throw new Exception(mensaje);
	    }
	}

    public static void lenghtString(String valor, int lenght, String mensaje) throws Exception {
        if(valor.trim().length() > lenght) throw new Exception(mensaje);
    }

    public static void integerIsNullOrZero(Integer valor, String mensaje) throws Exception {
        isNull(valor, mensaje);
        if(valor.compareTo(0) == 0) throw new Exception(mensaje);
    }

    public static void stringMailValidatePattern(String valor, String mensaje) throws Exception {
        stringIsNullOrBlank(valor, mensaje);
        if (!Pattern.matches(ConstantesUtility.PATTERN_MAIL_REGEX, valor)) throw new Exception(mensaje);
    }

    public static void integerIsNullOrLessZero(Integer valor, String mensaje) throws Exception {
        isNull(valor, mensaje);
        if(valor.compareTo(0) <= 0) throw new Exception(mensaje);
    }

    public static void bigDecimalIsNullOrZero(BigDecimal valor, String mensaje) throws Exception {
        isNull(valor, mensaje);
        if(valor.compareTo(BigDecimal.ZERO) == 0) throw new Exception(mensaje);
    }

    public static void bigDecimalIsNullOrLessZero(BigDecimal valor, String mensaje) throws Exception {
        isNull(valor, mensaje);
        if(valor.compareTo(BigDecimal.ZERO) <= 0) throw new Exception(mensaje);
    }

}
