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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ISaleDepartmentService.java
 * 
 * FILE NAME        	: ISaleDepartmentService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDescExpandEntity;
/**
 * 营业部 Service接口
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午5:32:19
 */
public interface ISalesDescExpandService extends IService {
 
	/**
	 * 
	 * 根据类型新增描述信息
	 * @author 088933-foss-zhangjiheng
	 * @return 
	 * @date 2013-6-21 下午6:49:12
	 */
	void addSalesDescExpandByType(String[] list,SalesDescExpandEntity entity);
	 /**
     * 供oms使用
     * @author 273311
     * @date 2016-06-12 
     * @param salesDescExpandEntitys
     * @param entity
     * @return
     */
	
    void addSalesDescExpandByType(List<SalesDescExpandEntity> salesDescExpandEntitys,String[] list,SalesDescExpandEntity entity);
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
     * 供oms使用
     * @author 273311
     * @date 2016-06-12 
     * @param salesDescExpandEntitys
     * @param entity
     * @return
     */
    void updateSalesDescExpand(List<SalesDescExpandEntity> salesDescExpandEntitys, SalesDescExpandEntity entity);
    
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据部门编码和扩展类型 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:31:31
     */
    List<SalesDescExpandEntity> querySalesDescExpandByCode(String code,String type);	
	


	/**
	 * 分页下载营业部自提派送区域描述
	 * @param entity
	 * @param i
	 * @param thousand
	 * @return
	 */
	List<SalesDescExpandEntity> querySalesDescExpandForDownloadByPage(
			SalesDescExpandEntity entity, int start, int lmited);
	
	
    
}
