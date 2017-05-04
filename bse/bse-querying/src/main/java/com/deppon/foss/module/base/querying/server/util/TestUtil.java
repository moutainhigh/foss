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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/server/util/TestUtil.java
 * 
 * FILE NAME        	: TestUtil.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-querying
 * PACKAGE NAME: com.deppon.foss.module.querying.server.util
 * FILE    NAME: TestUtil.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.querying.server.util;

import java.lang.reflect.Field;
import java.util.Date;


/**
 * 测试工具类
 * @author 078823-foss-panGuangJun
 * @date 2012-12-25 下午8:59:14
 */
public class TestUtil {
	@SuppressWarnings("rawtypes")
	public static <T> T createBean(Class<T> objectClass){
		 try {
			T obj = objectClass.newInstance();
			Field[] fields = TypeUtil.getFieldsArray(objectClass);
			Field.setAccessible(fields, true);
			for (Field field : fields) {
				Class typeClass = field.getType();
//				String  represent =field.toGenericString();
				String className = typeClass.getName();
				FieldType type = FieldType.getFieldType(className);
				switch (type) {
					case INT:field.set(obj, 1);
					break;
					case DOUBLE:field.set(obj,2d);
					break;
					case SHORT:field.set(obj,(short)2);
					break;
					case FLOAT:field.set(obj, 1f);
					break;
					case LONG:field.set(obj, 1L);
					break;
					case BYTE:field.set(obj,(byte)1);
					break;
					case CHAR:field.set(obj, 'a');
					break;
					case BOOLEAN:field.set(obj, true);
					break;
					case STRING:field.set(obj, "1");
					break;
					case DATE:field.set(obj, new Date());
					break;
					default:
						//field.set(obj,createBean(typeClass));
						break;
				}
				
			}
			return obj;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
