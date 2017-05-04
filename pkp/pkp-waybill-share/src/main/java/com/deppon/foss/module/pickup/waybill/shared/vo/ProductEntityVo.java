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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/vo/ProductEntityVo.java
 * 
 * FILE NAME        	: ProductEntityVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.vo;

/**
 * 
 * 产品实体VO
 * @author 025000-FOSS-helong
 * @date 2012-12-24 下午08:54:03
 */
public class ProductEntityVo {

	/**
	 * 产品ID
	 */
	private String id;
	
	/**
	 * 产品名称
	 */
	private String name;

	/**
	 * 产品编码
	 */
	private String code;
	
	/**
	 * 网点类型 DictionaryValueConstants 常量中有定义 KY、PX
	 */
	private String destNetType;


	/**
	 * 产品ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 产品ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 产品名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 产品名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 产品编码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 产品编码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 网点类型 DictionaryValueConstants 常量中有定义 KY、PX
	 */
	public String getDestNetType() {
		return destNetType;
	}

	/**
	 * 网点类型 DictionaryValueConstants 常量中有定义 KY、PX
	 */
	public void setDestNetType(String destNetType) {
		this.destNetType = destNetType;
	}

	/**
	 * 
	 * 重写toString()方法，使下拉框显示数据字典名称
	 * @author 025000-FOSS-helong
	 * @date 2012-12-24 下午08:32:08
	 * @return
	 */
	@Override
	public String toString() {
		return name;
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
		if (!(obj instanceof ProductEntityVo)) {
			return false;
		}
		ProductEntityVo other = (ProductEntityVo) obj;
		if(other.code==null){
			return this.code==null;
		}
		
		if (!other.code.equals(this.code)) {
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
		return name.hashCode() + code.hashCode();
	}
	
}