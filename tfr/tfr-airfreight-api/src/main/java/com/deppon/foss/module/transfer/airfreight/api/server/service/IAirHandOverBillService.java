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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/IAirHandOverBillService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirHandOverBillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirHandOverBillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirUnshippedSerialNoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirHandOverBillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirHandOverStateDto;

public interface IAirHandOverBillService extends IService {
	
	/**
	 * 查询所有未作废的航空正单交接单信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-10-31 9:46:08
	 */
	List<AirHandOverBillEntity> queryAirHandOverBill(AirHandOverBillDto airHandOverBillDto,int start,int limit);
	
	/**
	 * 获取总记录条数
	 * @author foss-liuxue(for IBM)
	 * @date 2012-10-31 9:46:53
	 */
	Long getCount(AirHandOverBillDto airHandOverBillDto);
	
	/**
	 * 出库操作
	 * @author foss-liuxue(for IBM)
	 * @date 2012-10-31 10:00:46
	 */
	int outStockAirHandOverBill(List<AirHandOverBillDto> airHandOverBillDtos);
	
	/**
	 * 查询航空正单交接单明细
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-5 10:41:22
	 */
	List<AirHandOverBillDetailEntity> queryHandOverAirWaybill(AirHandOverBillDto airHandOverBillDto,int start,int limit);
	
	/**
	 * 生成或修改航空正单交接单
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-5 14:43:55
	 */
	int saveOrUpdateAirHandOverBill(AirHandOverBillDto airHandOverBillDto);
	
	/**
	 * 根据正单号单个添加正单到正单明细列表
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-6 17:02:47
	 */
	AirHandOverBillDetailEntity querySingleAirWaybill(AirHandOverBillDto airHandOverBillDto);
	
	/**
	 * 根据正单交接单ID查询交接单信息以及正单明细信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-7 16:24:38
	 */
	AirHandOverBillDto loadAirHandOverBillInfo(String id);
	
	/**
	 * 查询起飞时间
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-7 下午3:06:25
	 */
	String queryTakeOffTime(AirHandOverBillDto airHandOverBillDto);

	/**
	 * 获取正单交接单
	 * @author foss-liuxue(for IBM)
	 * @date 2013-1-10 下午4:39:50
	 */
	String getAirHandOverBillNo();

	/**
	 * 接送货接口：根据运单号查询运单交接信息
	 * @author foss-liuxue(for IBM)
	 * @date 2013-1-14 上午11:20:07
	 */
	List<AirHandOverStateDto> queryAirHandOverBillInfo(String airWaybillNo);

	/**
	 * 提供给打印使用的接口
	 * @author foss-liuxue(for IBM)
	 * @date 2013-5-3 下午3:43:02
	 */
	AirHandOverBillDto loadAirHandOverBillInfoForPrint(String id,String[] ids);

	/**
	 * 根据正单号修改交接单出库状态
	 * @author foss-liuxue(for IBM)
	 * @date 2013-5-16 下午3:05:06
	 */
	void updateStockStatusByAirWaybillNo(String airWaybillNo);

	/**
	 * 根据运单号+流水号修改交接单出库状态
	 * @author foss-liuxue(for IBM)
	 * @date 2013-7-15 下午4:00:50
	 */
	int updateStockStatusByWaybillNo(String waybillNo,String serialNo);

	/**
	 * com.deppon.foss.module.transfer.airfreight.api.server.service
	 * desc:  根据正单id，查询该正单下面的所有运单流水号明细
	 * param:airWaybillIdList
	 * List<AirUnshippedSerialNoEntity>
	 * wqh
	 * 2015年8月21日上午11:18:06
	 */
	List<AirUnshippedSerialNoEntity> queryWaybillSerialNoByAirWaybillId(List<String> airWaybillIdList); 
	
	/**
	 * desc: 根据正单id，查询该正单下面的所有库存中运单流水号明细
	 * param:airWaybillIdList,orgCode,goodsAreaCode
	 * List<AirUnshippedSerialNoEntity>
	 * wqh
	 * 2015年8月21日下午3:27:07
	 */
	List<AirUnshippedSerialNoEntity> queryStockSerialNoByAirWaybillId(List<String> airWaybillIdList,String orgCode,String goodsAreaCode);
	
	
	/**
	 * 更改正单交接单的卸车状态
	 * @Author 263072
	 * 2015-9-15 19:41:09
	 * @param list
	 * @return
	 */
	int updateAirWaybillUnloadStatus(List<AirHandOverBillDto> list);

	/**
	 * @packageName:com.deppon.foss.module.transfer.airfreight.api.server.service
	 * @desc: 查询航空正单交接单new方法
	 * @param:
	 * @return: List<AirHandOverBillEntity>
	 * @author: wqh
	 * @date 2015年11月24日下午4:11:27
	 */
	List<AirHandOverBillEntity> queryAirHandOverBillNew(AirHandOverBillDto airHandOverBillDto,int start,int limit);
	
}