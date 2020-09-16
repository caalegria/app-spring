package com.segurosbolivar.finita.aplicacion.dto;

import java.io.Serializable;
import java.util.Date;

public class ObjectValueType  implements Serializable {

	private static final long serialVersionUID = -9195856944085022877L;
	
	public static final int TYPE_NONE = 0;
	public static final int TYPE_SHORT = 1;
	public static final int TYPE_INTEGER = 2;
	public static final int TYPE_LONG = 3;
	public static final int TYPE_FLOAT = 4;
	public static final int TYPE_DOUBLE = 5;
	public static final int TYPE_STRING = 6;
	public static final int TYPE_BOOLEAN = 7;
	public static final int TYPE_DATE = 8;
	public static final int TYPE_ARRAY = 9;
	public static final int TYPE_OTHER = 10;
	
	private Object value;
	private int type;
	
	public ObjectValueType(Object pValue) {
		this.value = pValue;
		if(this.value != null) {
			if(this.value instanceof Short) {
				this.type = TYPE_SHORT;
			} else if(this.value instanceof Integer) {
				this.type = TYPE_INTEGER;
			} else if(this.value instanceof Long) {
				this.type = TYPE_LONG;
			} else if(this.value instanceof Float) {
				this.type = TYPE_FLOAT;
			} else if(this.value instanceof Double) {
				this.type = TYPE_DOUBLE;
			} else if(this.value instanceof String) {
				this.type = TYPE_STRING;
			} else if(this.value instanceof Boolean) {
				this.type = TYPE_BOOLEAN;
			} else if(this.value instanceof Date) {
				this.type = TYPE_DATE;
			} else if(this.value.getClass().isArray()) {
				this.type = TYPE_ARRAY;
			} else {
				this.type = TYPE_OTHER;
			}
		}
	}
	public int getType() {
		return type;
	}
	public Short valueShort() {
		try {
			return (Short)this.value;
		} catch (Exception e) {}
		return null;
	}
	public Integer valueInteger() {
		try {
			return (Integer)this.value;
		} catch (Exception e) {}
		return null;
	}
	public Long valueLong() {
		try {
			return (Long)this.value;
		} catch (Exception e) {}
		return null;
	}
	public Float valueFloat() {
		try {
			return (Float)this.value;
		} catch (Exception e) {}
		return null;
	}
	public Double valueDouble() {
		try {
			return (Double)this.value;
		} catch (Exception e) {}
		return null;
	}
	public String valueString() {
		try {
			return (String)this.value;
		} catch (Exception e) {}
		return null;
	}
	public Boolean valueBoolean() {
		try {
			return (Boolean)this.value;
		} catch (Exception e) {}
		return null;
	}
	public Date valueDate() {
		try {
			return (Date)this.value;
		} catch (Exception e) {}
		return null;
	}
	public Object[] valueArray() {
		try {
			return (Object[])this.value;
		} catch (Exception e) {}
		return null;
	}
}
