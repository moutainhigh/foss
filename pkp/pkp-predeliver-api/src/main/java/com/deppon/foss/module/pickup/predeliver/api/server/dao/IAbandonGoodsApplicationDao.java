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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/dao/IAbandonGoodsApplicationDao.java
 * 
 * FILE NAME        	: IAbandonGoodsApplicationDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AbandonGoodsApplicationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonGoodsResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonedGoodsDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonedGoodsOaDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonedGoodsSearchDto;

/**
* @ClassName: 	IAbandonGoodsApplicationDao
* @Description: 弃货dao
* @author 
* @date 		2012-10-25 上午10:40:12
*
*/
public interface IAbandonGoodsApplicationDao {
	/**
	 * 
	 * 插入数据
	 * @author ibm-lizhiguo
	 * @date 2012-10-26 下午2:28:22
	 */
	void insertAbandonGoodsApplication(AbandonGoodsApplicationEntity entity);
	
	/**
	 * 
	 * 插入数据
	 * @author hrh
	 * @date 2013-08-17 11:15:00 AM
	 */
	void editAbandonGoodsApplication(AbandonGoodsApplicationEntity entity);
	
	/**
	 * 
	 * 查询弃货
	 * @author ibm-lizhiguo
	 * @date 2012-10-26 下午2:28:27
	 */
	List<AbandonGoodsResultDto> searchAbandonGoodsList(AbandonedGoodsSearchDto dto);
	
	/**
	 * 查询无标签转弃货
	 * @param dto
	 * @return
	 */
	List<AbandonGoodsResultDto> searchNoTagAbandonGoodsList(AbandonedGoodsSearchDto dto);
	
	/**
	 * 查询弃货 名字可以模糊匹配
	 * @param dto
	 * @return
	 */
	List<AbandonGoodsResultDto> searchAbandonGoodsListWithAmbuiousName(AbandonedGoodsSearchDto dto);
	
	/**
	 * 
	 * 查询弃货 总数量
	 * @author ibm-lizhiguo
	 * @date 2012-10-26 下午2:28:58
	 */
	Long getTotalCount(AbandonedGoodsSearchDto dto);

	/**
	 * 
	 * 通过弃货ID，获得弃货的详细信息
	 * @author ibm-lizhiguo
	 * @date 2012-10-30 上午10:32:24
	 */
	AbandonedGoodsDetailDto getAbandonGoodsDetailById(String id);
	
	/**
	 * 通过弃货id，获得无标签转弃货的详细信息
	 * @param id
	 * @return
	 */
	AbandonedGoodsDetailDto getNoTagAbandonGoodsDetailById(String id);

	/**
	 * 
	 * 更具运单号和部门编码，获得货物在该部门的入库时间
	 * MAP(waybillNo,orgCode)
	 * @author ibm-lizhiguo
	 * @date 2012-11-12 上午10:16:05
	 */
	Date getInStockTimeByWaybillNoAndOrgCode(Map<String,String> argMap);

	/**
	 * UPDATE AbandonGoodsApplicationEntity By Waybill No
	 * @date 2012-11-12 上午13:16:05
	 */
	int updateByWaybillNo(AbandonGoodsApplicationEntity entity);

	/**
	 * 通过弃货time，获得弃货List
	 * @date 2012-11-27 下午6:46:42
	 */
	List<AbandonedGoodsDetailDto> searchAbandonGoodsByTimeList(
			AbandonedGoodsSearchDto dto);

	/**
	 * 费用弃货工作流 query
	 * @date 2012-11-28 下午3:32:10
	 */
	List<AbandonedGoodsOaDto> queryAbandonedGoodsOaDto(String waybillNo);
	
	
	/**
	 * 
	 * 通过弃货waybill no，获得弃货的详细信息
	 * @date 2012-10-30 上午10:32:24
	 */
	AbandonedGoodsDetailDto getAbandonGoodsDetailByWaybillNo(String waybillNo);

	int updateByKey(AbandonGoodsApplicationEntity entity);

	List<AbandonGoodsResultDto> queryAbandonedsForStatus(AbandonedGoodsSearchDto dto);
	
	/**
	 * 通过id查询弃货类型
	 * @param id
	 * @return
	 */
	String getAbandonGoodsTypeById(String id);

}