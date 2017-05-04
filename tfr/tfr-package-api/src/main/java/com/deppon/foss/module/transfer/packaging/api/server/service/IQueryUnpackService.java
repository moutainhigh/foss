/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-package-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/server/service/IQueryUnpackService.java
 *  
 *  FILE NAME          :IQueryUnpackService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.transfer.packaging.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackDetailsEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackResultEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.CurrentDeptDto;

/**
 * TODO 查询营业部代打包装的业务接口
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午6:19:47
 */
public interface IQueryUnpackService extends IService {
	/**
	 * 
	 * service层，查询所有需要本外场代打包装的货物信息
	 * @param queryUnpackConditionEntity 查詢條件
	 * @param limit 限制数
	 * @param start 开始数
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-12 下午4:10:00
	 * @see com.deppon.foss.module.transfer.packaging.api.server.service.IQueryUnpackService#queryUnpackALL(com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackConditionEntity, int, int)
	 */
	List<QueryUnpackResultEntity> queryUnpackALL(QueryUnpackConditionEntity queryUnpackConditionEntity,int limit,int start);
	/**
	 * 
	 * service层，查询每票货物的木架区库存、是否已包装信息
	 * @param waybillno 运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-12 下午4:11:47
	 * @see com.deppon.foss.module.transfer.packaging.api.server.service.IQueryUnpackService#queryUnpackDetails(java.lang.String)
	 */
	List<QueryUnpackDetailsEntity> queryUnpackDetails(QueryUnpackConditionEntity unpackCondition);
	/**
	 * 分页总数
	 * @param queryUnpackConditionEntity 查询条件
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-12 下午5:17:54
	 */
	Long queryTotalCount(QueryUnpackConditionEntity queryUnpackConditionEntity);
	/**
	 * 
	 * 获取当前登录人组织信息
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-26 下午8:01:34
	 */
	CurrentDeptDto queryCurrentDept();
	/**
	 * 
	 * 输出代包装信息导出流
	 * @author 046130-foss-xuduowei
	 * @date 2013-03-22 下午8:01:34
	 */
	InputStream exportExcelStream(QueryUnpackConditionEntity queryUnpackConditionEntity);
}