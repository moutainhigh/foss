package com.deppon.foss.module.base.baseinfo.server.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @author 092020-lipengfei
 * @version V1.0
 * @Description JAVA深度复制
 * @Time 2014-4-16
 */
public class DeepCloneUtil {
	@SuppressWarnings("unchecked")
	public static <T> T of(T t) {
		if (t instanceof Serializable) {
			try {
				ByteArrayOutputStream bo = new ByteArrayOutputStream();
				ObjectOutputStream oo = new ObjectOutputStream(bo);
				oo.writeObject(t);
				ByteArrayInputStream bi = new ByteArrayInputStream(
						bo.toByteArray());
				ObjectInputStream oi = new ObjectInputStream(bi);
				return (T) (oi.readObject());
			} catch (Exception ex) {
				return t;
			}
		}
		return t;
	}
	
	public static <K,V extends K>V parentToChild(K parent,V child){
		Field[] pFields=parent.getClass().getDeclaredFields();
		for(Field pField:pFields){
			pField.setAccessible(true);
			Object value;
			if("serialVersionUID".equals(pField.getName())){
				continue;
			}
			try {
				value = pField.get(parent);
				pField.set(child, value);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return child;
	}
	private DeepCloneUtil() {
	}
}
