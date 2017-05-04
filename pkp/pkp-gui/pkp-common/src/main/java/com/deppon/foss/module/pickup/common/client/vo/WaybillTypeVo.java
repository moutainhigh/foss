/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/vo/DataDictionaryValueVo.java
 * 
 * FILE NAME        	: DataDictionaryValueVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.vo;

import java.io.Serializable;


/**
 * 
 * 数据字典VO
 * @author 025000-FOSS-helong
 * @date 2012-12-24 下午08:53:31
 */
public class WaybillTypeVo implements Serializable{

	//序列化版本号
	private static final long serialVersionUID = 1L;

	/**
	 * 字典ID
	 */
	private String id;

	/**
	 * 数据字典值名称
	 */
	private String valueName;

	/**
	 * 数据字典值代码
	 */
	private String valueCode;
	
	/**
	 * 数据字典值名称
	 */
	public String getValueName() {
		return valueName;
	}

	/**
	 * 数据字典值名称
	 */
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	/**
	 * 数据字典值代码
	 */
	public String getValueCode() {
		return valueCode;
	}

	/**
	 * 数据字典值代码
	 */
	public void setValueCode(String valueCode) {
		this.valueCode = valueCode;
	}

	/**
	 * 字典ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 字典ID
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * 
	 * 重写toString()方法，使下拉框显示数据字典名称
	 * @author 025000-FOSS-helong
	 * @date 2012-12-24 下午08:32:08
	 * @return
	 */
	public String toString() {
		return valueName;
	}
	
	/**
	 * 
	 * 比较两个数据字典对象的值
	 * @author 025000-FOSS-helong
	 * @date 2012-12-24 下午08:21:50
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof WaybillTypeVo)) {
			return false;
		}
		WaybillTypeVo other = (WaybillTypeVo) obj;
		
		if(other.valueCode==null){
			return this.valueCode==null;
		}
		
		if (!other.valueCode.equals(this.valueCode)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * 自定义hashCode
	 * @author 025000-FOSS-helong
	 * @date 2012-12-24 下午08:35:08
	 * @return
	 */
	@Override
	public int hashCode() {
		return valueName.hashCode() + valueCode.hashCode();
	}

}