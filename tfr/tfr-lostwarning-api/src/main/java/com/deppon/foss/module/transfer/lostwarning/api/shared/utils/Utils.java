package com.deppon.foss.module.transfer.lostwarning.api.shared.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	
	/**
	 * @Description: 对象间相同参数复制属性值
	 * @date 2015-6-15 下午5:45:47   
	 * @author 263072 
	 * @param fromObj
	 * @param toObj
	 * @return
	 */
	public static Object copyObjProperties(Object fromObj, Object toObj) {

		String fieldName, str, getName, setName;
		List fieldsList = new ArrayList();
		Method getMethod = null;
		Method setMethod = null;
		try {
			Class classFrom = fromObj.getClass();
			Class classTo = toObj.getClass();

			Field[] field1 = classFrom.getDeclaredFields();
			Field[] field2 = classTo.getDeclaredFields();
			// 两个类属性比较剔除不相同的属性，只留下相同的属性
			for (int i = 0; i < field2.length; i++) {
				for (int j = 0; j < field1.length; j++) {
					if (field1[j].getName().equals(field2[i].getName())) {
						fieldsList.add(field1[j]);
						break;
					}
				}
			}
			
			if (null != fieldsList && fieldsList.size() > 0) {
				for (int i = 0; i < fieldsList.size(); i++) {
					// 获取属性名称
					Field field = (Field) fieldsList.get(i);
					fieldName = field.getName();
					// 属性名第一个字母大写
					str = fieldName.substring(0, 1).toUpperCase();
					// 拼凑get 和set 方法名
					getName = "get" + str + fieldName.substring(1);
					setName = "set" + str + fieldName.substring(1);
					try{
						// 获取get、set方法
						getMethod = classFrom.getMethod(getName, new Class[] {});
						setMethod = classTo.getMethod(setName,
								new Class[] { field.getType() });
						
						// 获取属性值
						Object obj = getMethod.invoke(fromObj, new Object[] {});
						// 将属性值放入另一个对象中对应的属性
						if (null != obj) {
							setMethod.invoke(toObj, new Object[] { obj });
						}
					}catch (Exception e) {
						System.out.println("not exists this method:"+getName);
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toObj;
	}
	
	
	/***
	 * @Description: 判断字符串是否为空， 若为空返回true,非空返回false;
	 * @date 2015-6-16 下午2:37:40   
	 * @author 263072 
	 * @param str
	 * @return
	 */
	public static boolean isStrNull(String str){
		boolean res = false;
		if(str==null||"".equals(str)){
			res = true;
		}
		return res;
	}
}
