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
 * PROJECT NAME	: bse-util
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/base/util/DataBundle.java
 * 
 * FILE NAME        	: DataBundle.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.base.util;

import java.io.Serializable;
import java.util.List;

 
/**
 *  数据下载使用
 *  
 * @author zhangdongping
 * @date 2012-10-15 下午4:29:18
 * @since
 * @version
 */
public class DataBundle implements Serializable { 
	 
    /**
     * 序列化对象
     */
    private static final long serialVersionUID = -1835756229264517719L;
    
	/**
	 * 下载时包装数据
	 */
	private Object object; 
	
	/**
	 * 是否删除
	 */
	private String needDeleteLocalData = "N";
	
	
	/**
	 * 用于区分不同的org进行下载的时候的不同的水位和org对应关系
	 */
	private List<ClientUpdateDataPack> updateList;
	
	 
	/**
	 * @return the updateList
	 */
	public List<ClientUpdateDataPack> getUpdateList() {
		return updateList;
	}

	/**
	 * @param updateList the updateList to set
	 */
	public void setUpdateList(List<ClientUpdateDataPack> updateList) {
		this.updateList = updateList;
	}
	
	/**
	 * @return object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * @param  object  
	 */
	public DataBundle setObject(Object object) {
		this.object = object;
		return this;
	}

	/**
	 * @return needDeleteLocalData
	 */
	public String getNeedDeleteLocalData() {
		return needDeleteLocalData;
	}

	/**
	 * @param  needDeleteLocalData  
	 */
	public void setNeedDeleteLocalData(String needDeleteLocalData) {
		this.needDeleteLocalData = needDeleteLocalData;
	}
	
}
