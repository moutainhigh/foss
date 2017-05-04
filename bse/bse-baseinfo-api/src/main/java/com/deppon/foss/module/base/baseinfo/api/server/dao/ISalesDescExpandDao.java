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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/ISaleDepartmentDao.java
 * 
 * FILE NAME        	: ISaleDepartmentDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDescExpandEntity;
/**
 * 营业部 DAO接口
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午5:31:31
 */
public interface ISalesDescExpandDao {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:31:31
     */
	int addSalesDescExpand(SalesDescExpandEntity entity);


    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:31:31
     */
    int updateSalesDescExpand(SalesDescExpandEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:31:31
     */
    List<SalesDescExpandEntity> querySalesDescExpandByCode(String code,String active,String type);	
	


	/**
	 * 分页下载营业部自提派送区域描述
	 * @param entity
	 * @param i
	 * @param thousand
	 * @return
	 */
	List<SalesDescExpandEntity> querySalesDescExpandForDownloadByPage(
			SalesDescExpandEntity entity, int start, int lmited);
	
	/**
	 * 下载营业部信息
	 * @param entity
	 * @return
	 */
	List<SalesDescExpandEntity> querySalesDescExpandForDownload(
			SalesDescExpandEntity entity);
}
