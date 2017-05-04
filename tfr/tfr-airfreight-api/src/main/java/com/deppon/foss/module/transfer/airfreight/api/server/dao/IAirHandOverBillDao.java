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
 *  FILE PATH          :/IAirHandOverBillDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirHandOverBillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirHandOverBillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirUnshippedSerialNoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirHandOverBillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirHandOverBillGetSerialNoDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirHandOverStateDto;

public interface IAirHandOverBillDao {
	
	/**
	 * 查询所有未作废的航空正单交接单信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-10-31 9:44:14
	 */
	List<AirHandOverBillEntity> queryAirHandOverBill(AirHandOverBillDto airHandOverBillDto,int start,int limit);
	
	/**
	 * 获取总记录条数
	 * @author foss-liuxue(for IBM)
	 * @date 2012-10-31 9:44:47
	 */
	Long getCount(AirHandOverBillDto airHandOverBillDto);
	
	/**
	 * 根据id进行相应的出库操作
	 * @author foss-liuxue(for IBM)
	 * @date 2012-10-31 9:53:31
	 */
	int outStockAirHandOverBill(AirHandOverBillDto airHandOverBillDto);
	
	/**
	 * 根据选中的交接单号查询运单号和流水号
	 * @author foss-liuxue(for IBM)
	 * @param id
	 * @date 2012-11-1 10:42:11
	 */
	List<AirHandOverBillGetSerialNoDto> queryWaybillNoAndSerialNo(String id);
	
	/**
	 * 根据运单号和流水号查询库存表
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-2 10:22:29
	 */
	Long queryStock(AirHandOverBillGetSerialNoDto airHandOverBillGetSerialNoDto);
	
	/**
	 * 根据运单号和流水号查询出库表
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-2 10:24:12
	 */
	Long queryOutStock(AirHandOverBillGetSerialNoDto airHandOverBillGetSerialNoDto);
	
	/**
	 * 查询航空正单交接单明细
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-5 10:41:22
	 */
	List<AirHandOverBillDetailEntity> queryHandOverAirWaybill(AirHandOverBillDto airHandOverBillDto,int start,int limit);
	
	/**
	 * 生成航空正单交接单
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-5 14:43:55
	 */
	int addAirHandOverBill(AirHandOverBillEntity airHandOverBillEntity);
	
	/**
	 * 添加航空正单交接单明细
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-6 11:34:09
	 */
	int addAirHandOverBillDetail(List<AirHandOverBillDetailEntity> airHandOverBillDetailList);
	
	/**
	 * 根据正单号单个添加正单到正单明细列表
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-6 17:02:47
	 */
	List<AirHandOverBillDetailEntity> querySingleAirWaybill(AirHandOverBillDto airHandOverBillDto);
	
	/**
	 * 根据交接单ID查询正单交接单
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-7 16:19:45
	 */
	List<AirHandOverBillEntity> queryAirHandOverBillById(String id);
	
	/**
	 * 根据 交接单ID查询正单交接单明细
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-7 16:21:03
	 */
	List<AirHandOverBillDetailEntity> queryAirHandOverBillDetailByHandOverBillId(String id);
	
	/**
	 * 根据 交接单ID查询正单交接单明细
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-7 16:21:03
	 */
	List<AirHandOverBillDetailEntity> queryAirHandOverBillDetailForPrintByHandOverBillId(String[] ids);
	
	/**
	 * 根据交接单ID删除正单明细 
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-7 18:46:31
	 */
	int deleteAirHandOverBillDetailByHandOverBillId(String id);
	
	/**
	 * 修改航空正单交接单
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-7 18:48:53
	 */
	int updateAirHandOverBill(AirHandOverBillEntity airHandOverBillEntity);
	
	/**
	 * 查询起飞时间
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-7 下午3:06:25
	 */
	String queryTakeOffTime(AirHandOverBillDto airHandOverBillDto);

	/**
	 * 根据正单号查询交接单明细表判断是否做过交接 
	 * @author foss-liuxue(for IBM)
	 * @date 2013-1-6 下午9:12:38
	 */
	Long validateIsHandoverByWaybillNo(String waybillNo);

	/**
	 * 更改当前交接单下正单的交接状态
	 * @author foss-liuxue(for IBM)
	 * @date 2013-1-7 下午8:32:21
	 */
	int updateAirWaybillNoState(AirHandOverBillDto airHandOverBillDto);

	/**
	 * 根据航班日期和航班号判断是否做过交接
	 * @author foss-liuxue(for IBM)
	 * @date 2013-1-6 下午9:12:38
	 */
	Long validateIsHandoverByFlight(AirHandOverBillDto airHandOverBillDto);

	/**
	 * 接送货接口：根据运单号查询运单交接信息
	 * @author foss-liuxue(for IBM)
	 * @date 2013-1-14 上午11:20:07
	 */
	List<AirHandOverStateDto> queryAirHandOverBillInfo(String airWaybillNo);

	/**
	 * 修改走货路径状态
	 * @author foss-liuxue(for IBM)
	 * @date 2013-3-20 下午7:23:35
	 */
	int updatePathDetailStatus(AirHandOverBillDto airHandOverBillDto);

	/**
	 * 获取正单下所有未配载的运单号和流水号
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-14 下午4:08:33
	 */
	List<AirUnshippedSerialNoEntity> querySerialNoByAirWaybillNo(
			AirHandOverBillDto airHandOverBillDto);

	/**
	 * 提供给打印使用的接口
	 * @author foss-liuxue(for IBM)
	 * @date 2013-5-3 下午3:43:02
	 */
	List<AirHandOverBillEntity> queryAirHandOverBillByIdForPrint(String id);

	/**
	 * 根据运单号+流水号获取ID
	 * @author foss-liuxue(for IBM)
	 * @date 2013-7-15 下午4:47:03
	 */
	List<AirHandOverBillEntity> queryAirHandoverIdByWaybillNoAndSerialNo(
			AirHandOverBillGetSerialNoDto airHandOverBillGetSerialNoDto);
	
	

	/**
	 * com.deppon.foss.module.transfer.airfreight.api.server.dao
	 * desc: 根据正单id，查询该正单下面的所有运单流水号明细
	 * param:airWaybillIdList
	 * List<AirUnshippedSerialNoEntity>
	 * wqh
	 * 2015年8月21日上午11:13:59
	 */
	List<AirUnshippedSerialNoEntity> queryWaybillSerialNoByAirWaybillId(List<String> airWaybillIdList ); 

	
	/**
	 * com.deppon.foss.module.transfer.airfreight.api.server.dao
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
	 * 2015-9-14
	 * @param airHandOverBillDto
	 * @return
	 */
	int updateAirWaybillUnloadStatus(List<AirHandOverBillDto> list);
}