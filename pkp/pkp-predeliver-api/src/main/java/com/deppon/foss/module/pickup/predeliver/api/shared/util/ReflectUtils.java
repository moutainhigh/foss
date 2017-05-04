package com.deppon.foss.module.pickup.predeliver.api.shared.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.deppon.foss.module.pickup.predeliver.api.shared.annotation.AllowBlank;

/**
 * @ClassName: ReflectUtils
 * @Description: 反射工具类
 * @author 237982-foss-fangwenjun
 * @date 2015-3-31 上午9:14:39
 * 
 */
public class ReflectUtils {

	/**
	 * @Title: fieldValueIsNull
	 * @Description: 判断所传对象的所有字段是否为空
	 * @param obj
	 *            传入对象
	 * @param sup
	 *            是否查找所有父类直至Object.class true：查询父类，false：不查询父类
	 * @return false：没有空字段，true:有空字段
	 */
	public static boolean fieldValueIsNull(Object obj, boolean sup) {
		// 判断传入对象是否为空
		if(obj == null) {
			return true;
		}
		for (Class<?> clzz = obj.getClass(); clzz != Object.class; clzz = clzz.getSuperclass()) {
			// 过去对象内的所有成员变量
			Field[] fields = clzz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				try {
					if("serialVersionUID".equals(field.getName())){
						continue;
					}
					// 创建属性描述器
					PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clzz);
					// 获得get方法
					Method getMethod = pd.getReadMethod();
					// 获取方法上AllowBlank注解
					AllowBlank allowBlank = getMethod.getAnnotation(AllowBlank.class);		
					if(allowBlank != null) {
						try {
							// 判断注解的值
							if(allowBlank.value() == false){
								// 执行get方法
								Object valObj = getMethod.invoke(obj, null);
								if(valObj == null || "".equals(valObj.toString().trim())){
									return true;
								}
							}
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				} catch (IntrospectionException e1) {
					e1.printStackTrace();
				}
			}
			// 判断是否访问父类
			if (!sup) {
				break;
			}
		}
		return false;
	}
	
}
