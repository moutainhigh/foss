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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/dao/IAdjustTransportationPathDao.java
 * 
 *  FILE NAME     :IAdjustTransportationPathDao.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AdjustEntity;

/**
 * 调整走货路径Dao
 * @author huyue
 * @date 2012-10-12 下午6:48:36
 */
public interface IAdjustTransportationPathDao{

	/**
	 * 查询调整走货路径,根据组织号查询库区LIST, 分页
	 * @author huyue
	 * @date 2012-10-12 下午6:49:06
	 */
	List<AdjustEntity> queryLevel1(AdjustEntity adjustEntity, int limit,
			int start);

	/**
	 * 根据运单号,部门号,原库区编号查询改变后的库区list
	 * @author huyue
	 * @date 2012-11-8 下午6:08:24
	 */
	List<String> queryNewGoodsAreaCodeList(Map<String, String> map);
	
	/**
	 * 查询调整走货路径2级list, 根据waybill
	 * @author huyue
	 * @date 2012-10-12 下午8:08:53
	 */
	List<AdjustEntity> queryLevel2(AdjustEntity adjustEntity);
	
	/**
	 * 查询走货路径count
	 * @author huyue
	 * @date 2012-10-12 下午6:49:54
	 */
	Long getCount(AdjustEntity adjustEntity);

	/**
	 * 修改走货路径第三层查询,根据运单匹配在库货件号 
	 * @author huyue
	 * @date 2012-10-12 下午8:01:13
	 */
	List<AdjustEntity> queryLevel3(AdjustEntity adjustEntity);

	/**
	 * 根据部门查询运单信息
	 * @author huyue
	 * @date 2012-10-12 下午9:34:38
	 */
	List<AdjustEntity> queryWaybillList(AdjustEntity adjustEntity);
	
	/**
	 * 根据部门&修改后的货区编号查询运单信息
	 * @author huyue
	 * @date 2012-11-5 下午4:50:00
	 */
	List<AdjustEntity> queryChangedWaybillList(Map<String, String> map);

	/** 
	* @Title: queryLevel1ByOrgCodesList 
	* @Description: 查询调整走货路径,根据组织号查询库区LIST, 分页
	* @param paramMap
	* @param limit
	* @param start
	* @return  设定文件 
	* @return List<AdjustEntity>    返回类型 
	* @see queryLevel1ByOrgCodesList
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-6-3 下午10:51:28   
	* @throws 
	*/ 
	List<AdjustEntity> queryLevel1ByParamMap(Map<String, Object> paramMap, int limit, int start);

	/** 
	* @Title: queryWaybillListByOrgCodesList 
	* @Description: 根据组织号获取运单号list
	* @param paramMap
	* @return  设定文件 
	* @return List<AdjustEntity>    返回类型 
	* @see queryWaybillListByOrgCodesList
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-6-3 下午11:18:28   
	* @throws 
	*/ 
	List<AdjustEntity> queryWaybillListByParamMap(Map<String, Object> paramMap);
}