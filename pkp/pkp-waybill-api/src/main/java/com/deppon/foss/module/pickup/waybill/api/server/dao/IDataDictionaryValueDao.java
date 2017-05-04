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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/IDataDictionaryValueDao.java
 * 
 * FILE NAME        	: IDataDictionaryValueDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import org.apache.xmlbeans.impl.xb.ltgfmt.Code;

import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;

/**
 * 
 * 数据字典数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-16 下午5:30:50, </p>
 * @author foss-sunrui
 * @date 2012-10-16 下午5:30:50
 * @since
 * @version
 */
public interface IDataDictionaryValueDao {
	
	/**
     * 
     * <p>通过主键获取</p> 
     * @author foss-sunrui
     * @date 2012-10-16 下午5:30:43
     * @param id
     * @return
     * @see
     */
    DataDictionaryValueEntity queryByPrimaryKey(String id);
    
    /**
     * 
     * <p>通过词条代码查询</p> 
     * @author foss-sunrui
     * @date 2012-10-16 下午5:30:46
     * @param termsCode
     * @return
     * @see
     */
    List<DataDictionaryValueEntity> queryByTermsCode(String termsCode);
	
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
    boolean addDataDictionaryValue(DataDictionaryValueEntity dataDictionaryValue);

	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	void updateDataDictionaryValue(DataDictionaryValueEntity dataDictionaryValue);

	/**
	 * 
	 * 根据Code查找数据字典
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-4 上午11:46:28
	 */
	DataDictionaryValueEntity queryDataDictoryValueByCode(String termsCode, String valueCode);

	/**
	 * 删除
	 * @param dataDictionaryValue
	 */
	void delete(DataDictionaryValueEntity dataDictionaryValue);

	/**
	 * 根据Code查找航班信息
	 * 兼容历史数据
	 * @param termsCode
	 * @param valueCode
	 * 
	 * @return
	 */
	DataDictionaryValueEntity queryDataDictoryValueByValueCode(
			String termsCode, String valueCode);

	   /**
	    * 获取地址信息
	    */
	String getReceivingAddress(String code);
}