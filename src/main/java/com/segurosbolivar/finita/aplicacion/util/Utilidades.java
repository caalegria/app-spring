package com.segurosbolivar.finita.aplicacion.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;

import com.segurosbolivar.finita.aplicacion.dto.ObjectValueType;
import com.segurosbolivar.finita.aplicacion.dto.UsuarioLogin;
import com.segurosbolivar.finita.aplicacion.service.ConditionMap;

/**
 * 
 * @author Gustavo Adolfo Lopez Mendieta(Shark_426) - galopez@asesoftware
 *
 */
public class Utilidades {
	
	public static final Logger logger = Logger.getLogger(Utilidades.class);
	public static final String pattern_1 = "MM-dd-yyyy";
	public static final String pattern_2= "MM/dd/yyyy";
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
	
	public static enum OrderBy {
		ASC, DESC
	}
	
	public static void datosDeLogin(Model model,UsuarioLogin login) {		
		model.addAttribute("userLogin",login.getUsername());
		model.addAttribute("RolAut",login.getRol());
		logger.info(Log.getCurrentClassAndMethodNames(Utilidades.class.getName(), "Usuario Logeado: "+login.toString()));
	}
	
	
	@SuppressWarnings("rawtypes")
	public static Predicate addPredicateCriterio(CriteriaBuilder builder, Predicate pPredicate, Root root,short pOperation,boolean embebedId,String pProperty, Object pValueFrom) {
		Object valueTo = null;
		Object valueFrom = pValueFrom;
		if(pOperation == ConditionMap.CRITERIO_OPER_DESDE_HASTA) {
			ObjectValueType ref = new ObjectValueType(valueFrom);
			if(ref.getType() == ObjectValueType.TYPE_ARRAY) {
				Object[] objs = ref.valueArray();
				if(objs.length > 1) {
					valueFrom = objs[0];
					valueTo = objs[1];
				}
			}
		} else if(pOperation == ConditionMap.CRITERIO_OPER_IN || 
				  pOperation == ConditionMap.CRITERIO_OPER_NOT_IN) {
			ObjectValueType ref = new ObjectValueType(valueFrom);
			if(ref.getType() == ObjectValueType.TYPE_STRING) {
				String sRef = ref.valueString();
				String[] s = sRef.split(",");
				valueFrom = s;
			}
		}
		return addPredicateCriterio(builder,pPredicate,root,pOperation,embebedId,pProperty,valueFrom,valueTo);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Predicate addPredicateCriterio(CriteriaBuilder builder, Predicate pPredicate, Root root,short pOperation,boolean embebedId, String pProperty, Object pValueFrom, Object pValueTo) {
		Predicate predicate = null;
		ObjectValueType valueFrom = new ObjectValueType(pValueFrom); 
		ObjectValueType valueTo = new ObjectValueType(pValueTo); 
		try {
			switch (pOperation) {
				case ConditionMap.CRITERIO_OPER_IGUAL:
					switch (valueFrom.getType()) {
						case ObjectValueType.TYPE_SHORT:
							predicate = builder.equal(root.get(pProperty),valueFrom.valueShort());
							break;
						case ObjectValueType.TYPE_INTEGER:
							predicate = builder.equal(root.get(pProperty),valueFrom.valueInteger());
							break;
						case ObjectValueType.TYPE_LONG:
							predicate = builder.equal(root.get(pProperty),valueFrom.valueLong());
							break;
						case ObjectValueType.TYPE_FLOAT:
							predicate = builder.equal(root.get(pProperty),valueFrom.valueFloat());
							break;
						case ObjectValueType.TYPE_DOUBLE:
							predicate = builder.equal(root.get(pProperty),valueFrom.valueDouble());
							break;
						case ObjectValueType.TYPE_STRING:
							if(pProperty.contains(".")) {
								if(embebedId) {
									String [] props= pProperty.split("\\.");
									predicate = builder.equal(root.get(props[0]).get(props[1]),valueFrom.toString());
								}
							}else {
								predicate = builder.equal(root.get(pProperty),valueFrom.toString());
							}
							break;
						case ObjectValueType.TYPE_BOOLEAN:
							predicate = builder.equal(root.get(pProperty),valueFrom.valueBoolean());
							break;
						case ObjectValueType.TYPE_DATE:
							predicate = builder.equal(root.get(pProperty),valueFrom.valueDate());
							break;
						default:
							predicate = builder.equal(root.get(pProperty),pValueFrom);
							break;
					}
					break;
				case ConditionMap.CRITERIO_OPER_IS_NULL: 
				case ConditionMap.CRITERIO_OPER_IGUAL_O_NULO:
					predicate = builder.isNull(root.get(pProperty));
					if(pOperation == ConditionMap.CRITERIO_OPER_IGUAL_O_NULO) {
						Predicate pEquals = addPredicateCriterio(builder,pPredicate,root,pOperation,embebedId,pProperty,pValueFrom);
						if(pEquals != null) {
							predicate = builder.or(predicate,pEquals);
						}
					}
					//predicate 
					break;
				case ConditionMap.CRITERIO_OPER_IS_NOT_NULL:
					predicate = builder.isNotNull(root.get(pProperty));
					break;
				case ConditionMap.CRITERIO_OPER_DIFER:
					switch (valueFrom.getType()) {
						case ObjectValueType.TYPE_SHORT:
							predicate = builder.notEqual(root.get(pProperty),valueFrom.valueShort());
							break;
						case ObjectValueType.TYPE_INTEGER:
							predicate = builder.notEqual(root.get(pProperty),valueFrom.valueInteger());
							break;
						case ObjectValueType.TYPE_LONG:
							predicate = builder.notEqual(root.get(pProperty),valueFrom.valueLong());
							break;
						case ObjectValueType.TYPE_FLOAT:
							predicate = builder.notEqual(root.get(pProperty),valueFrom.valueFloat());
							break;
						case ObjectValueType.TYPE_DOUBLE:
							predicate = builder.notEqual(root.get(pProperty),valueFrom.valueDouble());
							break;
						case ObjectValueType.TYPE_STRING:
							predicate = builder.notEqual(root.get(pProperty),valueFrom.valueString());
							break;
						case ObjectValueType.TYPE_BOOLEAN:
							predicate = builder.notEqual(root.get(pProperty),valueFrom.valueBoolean());
							break;
						case ObjectValueType.TYPE_DATE:
							predicate = builder.notEqual(root.get(pProperty),valueFrom.valueDate());
							break;
						default:
							predicate = builder.notEqual(root.get(pProperty),pValueFrom);
							break;
					}
					break;
				case ConditionMap.CRITERIO_OPER_LIKE:
					predicate = builder.like(root.get(pProperty),valueFrom.valueString());
					break;
				case ConditionMap.CRITERIO_OPER_MAYOR:
					switch (valueFrom.getType()) {
						case ObjectValueType.TYPE_SHORT:
							predicate = builder.greaterThan(root.get(pProperty),valueFrom.valueShort());
							break;
						case ObjectValueType.TYPE_INTEGER:
							predicate = builder.greaterThan(root.get(pProperty),valueFrom.valueInteger());
							break;
						case ObjectValueType.TYPE_LONG:
							predicate = builder.greaterThan(root.get(pProperty),valueFrom.valueLong());
							break;
						case ObjectValueType.TYPE_FLOAT:
							predicate = builder.greaterThan(root.get(pProperty),valueFrom.valueFloat());
							break;
						case ObjectValueType.TYPE_DOUBLE:
							predicate = builder.greaterThan(root.get(pProperty),valueFrom.valueDouble());
							break;
						case ObjectValueType.TYPE_STRING:
							predicate = builder.greaterThan(root.get(pProperty),valueFrom.valueString());
							break;
						case ObjectValueType.TYPE_BOOLEAN:
							predicate = builder.greaterThan(root.get(pProperty),valueFrom.valueBoolean());
							break;
						case ObjectValueType.TYPE_DATE:
							predicate = builder.greaterThan(root.get(pProperty),valueFrom.valueDate());
							break;
					}
					break;
				case ConditionMap.CRITERIO_OPER_MENOR:
					switch (valueFrom.getType()) {
						case ObjectValueType.TYPE_SHORT:
							predicate = builder.lessThan(root.get(pProperty),valueFrom.valueShort());
							break;
						case ObjectValueType.TYPE_INTEGER:
							predicate = builder.lessThan(root.get(pProperty),valueFrom.valueInteger());
							break;
						case ObjectValueType.TYPE_LONG:
							predicate = builder.lessThan(root.get(pProperty),valueFrom.valueLong());
							break;
						case ObjectValueType.TYPE_FLOAT:
							predicate = builder.lessThan(root.get(pProperty),valueFrom.valueFloat());
							break;
						case ObjectValueType.TYPE_DOUBLE:
							predicate = builder.lessThan(root.get(pProperty),valueFrom.valueDouble());
							break;
						case ObjectValueType.TYPE_STRING:
							predicate = builder.lessThan(root.get(pProperty),valueFrom.valueString());
							break;
						case ObjectValueType.TYPE_BOOLEAN:
							predicate = builder.lessThan(root.get(pProperty),valueFrom.valueBoolean());
							break;
						case ObjectValueType.TYPE_DATE:
							predicate = builder.lessThan(root.get(pProperty),valueFrom.valueDate());
							break;
					}
					break;
				case ConditionMap.CRITERIO_OPER_MAYORIGUAL:
					switch (valueFrom.getType()) {
						case ObjectValueType.TYPE_SHORT:
							predicate = builder.greaterThanOrEqualTo(root.get(pProperty),valueFrom.valueShort());
							break;
						case ObjectValueType.TYPE_INTEGER:
							predicate = builder.greaterThanOrEqualTo(root.get(pProperty),valueFrom.valueInteger());
							break;
						case ObjectValueType.TYPE_LONG:
							predicate = builder.greaterThanOrEqualTo(root.get(pProperty),valueFrom.valueLong());
							break;
						case ObjectValueType.TYPE_FLOAT:
							predicate = builder.greaterThanOrEqualTo(root.get(pProperty),valueFrom.valueFloat());
							break;
						case ObjectValueType.TYPE_DOUBLE:
							predicate = builder.greaterThanOrEqualTo(root.get(pProperty),valueFrom.valueDouble());
							break;
						case ObjectValueType.TYPE_STRING:
							predicate = builder.greaterThanOrEqualTo(root.get(pProperty),valueFrom.valueString());
							break;
						case ObjectValueType.TYPE_BOOLEAN:
							predicate = builder.greaterThanOrEqualTo(root.get(pProperty),valueFrom.valueBoolean());
							break;
						case ObjectValueType.TYPE_DATE:
							predicate = builder.greaterThanOrEqualTo(root.get(pProperty),valueFrom.valueDate());
							break;
					}
					break;
				case ConditionMap.CRITERIO_OPER_MENORIGUAL:
					switch (valueFrom.getType()) {
						case ObjectValueType.TYPE_SHORT:
							predicate = builder.lessThanOrEqualTo(root.get(pProperty),valueFrom.valueShort());
							break;
						case ObjectValueType.TYPE_INTEGER:
							predicate = builder.lessThanOrEqualTo(root.get(pProperty),valueFrom.valueInteger());
							break;
						case ObjectValueType.TYPE_LONG:
							predicate = builder.lessThanOrEqualTo(root.get(pProperty),valueFrom.valueLong());
							break;
						case ObjectValueType.TYPE_FLOAT:
							predicate = builder.lessThanOrEqualTo(root.get(pProperty),valueFrom.valueFloat());
							break;
						case ObjectValueType.TYPE_DOUBLE:
							predicate = builder.lessThanOrEqualTo(root.get(pProperty),valueFrom.valueDouble());
							break;
						case ObjectValueType.TYPE_STRING:
							predicate = builder.lessThanOrEqualTo(root.get(pProperty),valueFrom.valueString());
							break;
						case ObjectValueType.TYPE_BOOLEAN:
							predicate = builder.lessThanOrEqualTo(root.get(pProperty),valueFrom.valueBoolean());
							break;
						case ObjectValueType.TYPE_DATE:
							predicate = builder.lessThanOrEqualTo(root.get(pProperty),valueFrom.valueDate());
							break;
					}
					break;
				case ConditionMap.CRITERIO_OPER_DESDE_HASTA:
					if(valueFrom.getType() == valueTo.getType()) {
						switch (valueFrom.getType()) {
							case ObjectValueType.TYPE_SHORT:
								predicate = builder.between(root.get(pProperty),valueFrom.valueShort(),valueTo.valueShort());
								break;
							case ObjectValueType.TYPE_INTEGER:
								predicate = builder.between(root.get(pProperty),valueFrom.valueInteger(),valueTo.valueInteger());
								break;
							case ObjectValueType.TYPE_LONG:
								predicate = builder.between(root.get(pProperty),valueFrom.valueLong(),valueTo.valueLong());
								break;
							case ObjectValueType.TYPE_FLOAT:
								predicate = builder.between(root.get(pProperty),valueFrom.valueFloat(),valueTo.valueFloat());
								break;
							case ObjectValueType.TYPE_DOUBLE:
								predicate = builder.between(root.get(pProperty),valueFrom.valueDouble(),valueTo.valueDouble());
								break;
							case ObjectValueType.TYPE_STRING:
								predicate = builder.between(root.get(pProperty),valueFrom.valueString(),valueTo.valueString());
								break;
							case ObjectValueType.TYPE_BOOLEAN:
								predicate = builder.between(root.get(pProperty),valueFrom.valueBoolean(),valueTo.valueBoolean());
								break;
							case ObjectValueType.TYPE_DATE:
								predicate = builder.between(root.get(pProperty),valueFrom.valueDate(),valueTo.valueDate());
								break;
						}
					}
					break;
				case ConditionMap.CRITERIO_OPER_IN:
				case ConditionMap.CRITERIO_OPER_NOT_IN:
					if(valueFrom.getType() == ObjectValueType.TYPE_ARRAY) {
						Object[] lista = valueFrom.valueArray();
						if(lista.length > 0) {
							Path	path = root.get(pProperty);
							In in = builder.in(path);
							for(int i=0; i < lista.length; i++) {
								Serializable ser = null;
								if( lista[i] instanceof String) {
									ser = convertValueToIn(path,(String)lista[i]);
								} else {
									ser = (Serializable) lista[i];
								}
								if(ser != null) {
									in.value(ser);
								}
							}
							if(pOperation == ConditionMap.CRITERIO_OPER_NOT_IN) {
								predicate = builder.not(in);
							} else {
								predicate = in;
							}
						}
					}
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(predicate == null) {
			predicate = pPredicate;
		} else if (pPredicate != null) {
			predicate = builder.and(pPredicate,predicate);
		}
		return predicate;
	}
	
	
	@SuppressWarnings("rawtypes")
	private static Serializable convertValueToIn(Path path, String pValue) {
		Serializable valor = null;
		if(path.getJavaType() == Integer.class) {
			try {
				Integer v = Integer.valueOf(pValue);
				valor = (Serializable)v;
			} catch (Exception e) {}
		} else if(path.getJavaType() == Short.class) {
			try {
				Short v = Short.valueOf(pValue);
				valor = (Serializable)v;
			} catch (Exception e) {}
		} else if(path.getJavaType() == Double.class) {
			try {
				Double v = Double.valueOf(pValue);
				valor = (Serializable)v;
			} catch (Exception e) {}
		} else if(path.getJavaType() == Long.class) {
			try {
				Long v = Long.valueOf(pValue);
				valor = (Serializable)v;
			} catch (Exception e) {}
		} else if(path.getJavaType() == Float.class) {
			try {
				Float v = Float.valueOf(pValue);
				valor = (Serializable)v;
			} catch (Exception e) {}
		} else {
			valor = (Serializable)pValue;
		}
		return valor;
	}
	
	/**
	 * Agrega la clausula order by para la consultas
	 * 
	 * @param sb Stringbuffer con el inicio de la consulta
	 * @param ordering Campo por el que se ordena y booleano indicando si el
	 *            ordenamiento es ascendente (false para descendente).
	 */
	public static void buildOrder(StringBuffer sb, Map<String, Boolean> ordering) {
		if (ordering != null) {
			sb.append(" order by ");

			Iterator<Map.Entry<String, Boolean>> it = ordering.entrySet().iterator();
			boolean again = false;
			while (it.hasNext()) {
				Map.Entry<String, Boolean> me = it.next();
				if ( again ) sb.append(", ");
				again = true;
				sb.append("a.").append(me.getKey().toString()).append(" ").append((me.getValue() == null) || (me.getValue() == true) ? OrderBy.ASC : OrderBy.DESC);
			}
		}
	}

}
