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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/server/util/FieldType.java
 * 
 * FILE NAME        	: FieldType.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.querying.server.util;

/**
 * 
 * 枚举类型
 * 
 * 
 * 数据类型
 * 
 * 
 * @date Mar 20, 2013 10:26:01 AM
 */
public enum FieldType {
	/**
	 * 
	 * 整型
	 */
	INT("int","java.lang.Integer"),
	/**
	 * 
	 * 双精度型
	 */
	DOUBLE("double","java.lang.Double"),
	/**
	 * 
	 * 字符型
	 */
	CHAR("char","java.lang.Character"),
	/**
	 * 
	 * 字符串型
	 */
	STRING("java.lang.String"),
	/**
	 * 
	 * 短整型
	 */
	SHORT("short","java.lang.Short"),
	/**
	 * 
	 * 长整型
	 */
	LONG("long","java.lang.Long"),
	/**
	 * 
	 * 字节型
	 */
	BYTE("byte","java.lang.Byte"),
	/**
	 * 
	 * Float型
	 */
	FLOAT("float","java.lang.Float"),
	/**
	 * 
	 * 布尔型
	 */
	BOOLEAN("boolean","java.lang.Boolean"),
	/**
	 * 
	 * 日期型
	 */
	DATE("java.util.Date"),
	/**
	 * 
	 * 其他类型
	 */
	OTHER();
	/**
	 * 
	 * 类型名称
	 */
	private String typeName;
	
	private String packName;
	/**
	 * 
	 * 构造函数
	 */
	private FieldType(){
		
	}
	/**
	 * 
	 * 构造函数重载
	 */
	private FieldType(String packName) {
		this.packName = packName;
	}
	/**
	 * 
	 * 构造函数重载
	 */
	private FieldType(String typeName,String packName) {
		this.typeName = typeName;
		this.packName = packName;
	}
	/**
	 * 
	 * 构造静态方法调用
	 */
	public static FieldType getFieldType(String className){
		//非空判断
		if(null == className || "".intern().equals(className.trim().intern())) {
			return null;
		}
		/*
		 * 检查字符串的引用
		 * 整型
		 * 双精度型
		 * 长整型
		 * 短整型
		 * 字符型
		 * 字符串行
		 * Float型
		 * 等
		 * 
		 */
		if(className.intern().equals(INT.typeName.intern()) || className.intern().equals(INT.packName.intern()) ) {
			return INT;
		}
		if(className.intern().equals(DOUBLE.typeName.intern())  || className.intern().equals(DOUBLE.packName.intern()) ) {
			return DOUBLE;
		}
		if(className.intern().equals(CHAR.typeName.intern()) || className.intern().equals(CHAR.packName.intern())) {
			return CHAR;
		}
		if(className.intern().equals(STRING.packName.intern())) {
			return STRING;
		}
		if(className.intern().equals(DATE.packName.intern())) {
			return DATE;
		}
		if(className.intern().equals(SHORT.typeName.intern()) || className.intern().equals(SHORT.packName.intern())) {
			return SHORT;
		}
		if(className.intern().equals(LONG.typeName.intern())  || className.intern().equals(LONG.packName.intern())){
			return LONG;
		}
		if(className.intern().equals(BYTE.typeName.intern())  || className.intern().equals(BYTE.packName.intern())) {
			return BYTE;
		}
		if(className.intern().equals(FLOAT.typeName.intern())  || className.intern().equals(FLOAT.packName.intern())) {
			return FLOAT;
		}
		if(className.intern().equals(BOOLEAN.typeName.intern()) || className.intern().equals(BOOLEAN.packName.intern()) ){
			return BOOLEAN;
		}
		return OTHER;
	}
}