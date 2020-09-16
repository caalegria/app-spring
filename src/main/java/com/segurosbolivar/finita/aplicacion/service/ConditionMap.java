package com.segurosbolivar.finita.aplicacion.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConditionMap {

	static final public short CRITERIO_OPER_IGUAL = 1;
	static final public short CRITERIO_OPER_DIFER = 2;
	static final public short CRITERIO_OPER_LIKE = 3;
	static final public short CRITERIO_OPER_MAYOR = 4;
	static final public short CRITERIO_OPER_MENOR = 5;
	static final public short CRITERIO_OPER_MAYORIGUAL = 6;
	static final public short CRITERIO_OPER_MENORIGUAL = 7;
	static final public short CRITERIO_OPER_IN = 8;
	static final public short CRITERIO_OPER_NOT_IN = 9;
	static final public short CRITERIO_OPER_IS_NULL = 10;
	static final public short CRITERIO_OPER_IS_NOT_NULL = 11;
	static final public short CRITERIO_OPER_IGUAL_O_NULO = 12;
	static final public short CRITERIO_OPER_DESDE_HASTA = 13;
	
	private Map<String, List<Object>> props = null;
	
	public ConditionMap(){
		if(props==null){
			props = new HashMap<String, List<Object>>();
		}
	}
	
	public void addContition(String property, Object value, short oper)
	{
		List<Object> condition = null;
		//Valores de campos
		condition = new ArrayList<Object>();
		condition.add(value);
		condition.add(oper);
		props.put(property, condition);
	}

	public Map<String, List<Object>> getProps() {
		return props;
	}

	public void setProps(Map<String, List<Object>> props) {
		this.props = props;
	}
	
	public void cleanAll() {
		if(this.props!=null) {
			if(!this.props.isEmpty()) {
				this.props.clear();			
			}		
		}
	}
	
	public void cleanKey(String key) {
		if(this.props!=null) {
			if(!this.props.isEmpty()) {
				Object obj=this.props.get(key);
				this.props.remove(obj);			
			}		
		}
	}
}