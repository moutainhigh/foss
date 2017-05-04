package com.deppon.foss.print.util;

import java.lang.reflect.*;

/**
 * 利用反射构造对象实例
 * @author niujian
 */
public final class ObjectCreator {

	private ObjectCreator() {
	}
	
	public static Object createObect(String className, ClassLoader clzLoader) throws Exception {
		return createObject(clzLoader.loadClass(className));
	}

	public static Object createObject(String className) throws Exception {
		return createObject(Class.forName(className));
	}

	public static Object createObject(Class classObject) throws Exception {
		return classObject.newInstance();
	}

	public static Object createObject(String className, Object[] params)
			throws Exception {
		return createObject(Class.forName(className), params);
	}

	public static Object createObject(Class classObject, Object[] params)
			throws Exception {
		Constructor[] constructors = classObject.getConstructors();
		Object object = null;
		for (int counter = 0; counter < constructors.length; counter++) {
			try {
				object = constructors[counter].newInstance(params);
			} catch (Exception e) {
				if (e instanceof InvocationTargetException){
					((InvocationTargetException) e).getTargetException()
							.printStackTrace();
				}
			}
		}
		if (object == null){
			throw new InstantiationException();
		}
		return object;
	}
}
