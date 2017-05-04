/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-querying
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/server/util/TypeUtil.java
 * 
 * FILE NAME        	: TypeUtil.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.querying.server.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TypeUtil {
	
	
	@SuppressWarnings({ "unused", "rawtypes" })
	private static String getJdbcType(Class clazz) {
		String className = clazz.getName();
		FieldType type = FieldType.getFieldType(className);
		switch (type) {
//			case INT:;
//			case DOUBLE:;
//			case SHORT:;
//			case FLOAT:;
//			case BOOLEAN:;
			case LONG:return "NUMERIC";
//			case BYTE:;
//			case CHAR:;
			case DATE:return "DATE";
			case STRING:
			case OTHER:return "VARCHAR";
			default:
				//field.set(obj,createBean(typeClass));
				break;
		}
		return "";
	}
	@SuppressWarnings("rawtypes")
	private static List<Field> getFields(Class clazz) {
		List<Field> fieldsList = new ArrayList<Field>();
		Class parentClazz = clazz;
		while(true){
			parentClazz = parentClazz.getSuperclass();
			if(parentClazz.getSimpleName().equals("Object")){
				break;
			}
			Field[] superFields = parentClazz.getDeclaredFields();
			for (Field field : superFields) {
				if(!field.getName().equals("serialVersionUID") && !field.getType().getSimpleName().equals("List") 
					&& !field.toGenericString().contains("final") 	
					&& !field.toGenericString().contains("static")	
						){
					fieldsList.add(field);
				}
			}
		}
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if(!field.getName().equals("serialVersionUID") && !field.getType().getSimpleName().equals("List") 
					&& !field.toGenericString().contains("final") 	
					&& !field.toGenericString().contains("static")	
					){
				fieldsList.add(field);
			}
		}
		return fieldsList;
	}
	
	@SuppressWarnings("rawtypes")
	public static Field[] getFieldsArray(Class clazz) {
		List<Field> fieldList = getFields(clazz);
		Field[] fields = new Field[fieldList.size()];
		fieldList.toArray(fields);
		return fields;
	}
	
}
