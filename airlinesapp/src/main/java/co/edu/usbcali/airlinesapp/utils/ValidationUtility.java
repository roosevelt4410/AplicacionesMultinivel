package co.edu.usbcali.airlinesapp.utils;

import java.math.BigDecimal;
import java.util.Objects;

public class ValidationUtility {
	
	 	public static <T extends Exception> void isNull(Object value, T exception) throws T {
	        if (Objects.isNull(value)) {
	            throw exception;
	        }
	    }

	    public static <T extends Exception> void stringIsNullOrBlank(String value, T exception) throws T {
	    	isNull(value, exception);
	        if (value.trim().isEmpty()) {
	            throw exception;
	       }
	    }

	    public static <T extends Exception> void stringLength(String valor, Integer longitud, T excepcion) throws T {
	        if (longitud.compareTo(valor.trim().length()) < 0) {
	            throw excepcion;
	        }
	    }

	    public static <T extends Exception> void integerIsNullOrZero(Integer valor, T excepcion) throws T {
	        isNull(valor, excepcion);
	        if (valor.compareTo(0) == 0) {
	            throw excepcion;
	        }
	    }

	    public static <T extends Exception> void integerIsNullOrLessZero(Integer valor, T excepcion) throws T {
	        isNull(valor, excepcion);
	        if (valor.compareTo(0) <= 0) {
	            throw excepcion;
	        }
	    }

	    public static <T extends Exception> void bigDecimalIsNullOrZero(BigDecimal valor, T excepcion) throws T {
	        isNull(valor, excepcion);
	        if (valor.compareTo(BigDecimal.ZERO) == 0) {
	            throw excepcion;
	        }
	    }

	    public static <T extends Exception> void bigDecimalIsNullOrLessZero(BigDecimal valor, T excepcion) throws T {
	        isNull(valor, excepcion);
	        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
	            throw excepcion;
	        }
	    }

}
